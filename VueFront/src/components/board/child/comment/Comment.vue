<template>
  <div v-show="isShow" class="comment">
    <div class="head">
      {{ username }} ({{ getFormatDate(comment.comment_time) }})
    </div>
    <div class="content" v-html="enterToBr(comment.comment)"></div>
    <!-- 로그인 기능 구현 후 로그인한 자신의 글에만 보이게 한다. -->
    <div
      v-if="this.user.id == comment.user_id || this.user.role == 'ADMIN'"
      class="cbtn"
    >
      <label @click="modifyCommentView">수정</label>|
      <label @click="deleteComment">삭제</label>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import moment from "moment";
import { mapState } from "vuex";
export default {
  props: {
    comment: Object,
    // comment: {
    //   type: Array,
    //   default: () => [{}],
    // },
  },
  computed: {
    ...mapState(["user"]),
  },
  created() {
    http
      .get(`/api/member/${this.comment.user_id}`) //요 부분 코멘트에서 끌어오게 수정했습니다
      .then(({ data }) => {
        this.username = data.name;
      })
      .catch((error) => console.log(error));
  },
  data() {
    return {
      isShow: true,
      username: "",
    };
  },
  methods: {
    modifyCommentView() {
      this.$emit("modify-comment", {
        comment_no: this.comment.comment_no,
        comment: this.comment.comment,
        isbn: this.comment.isbn,
        user_id: this.comment.user_id,
      });
    },
    deleteComment() {
      if (confirm("정말 삭제하시겠습니까?")) {
        http.delete(`/comment/${this.comment.comment_no}`).then(({ data }) => {
          // console.log(data);
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === this.comment.comment_no) {
            // if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          // else {
          // msg = "삭제 처리시 문제가 발생했습니다.";
          // }
          alert(msg);
          this.$store.dispatch("getComments", this.comment.isbn);
        });
      }
    },
    getFormatDate(regtime) {
      //구글검색: 자바스크립트 천원단위 정규표현식
      // return String(x).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      //\b(?=(\d{})+(?!\d))가 정규식 구문.
      return moment(new Date(regtime)).format("YYYY-MM-DD HH:mm:ss");
    },
    enterToBr(str) {
      // 문자열에 enter값을 <br />로 변경.(html상에서 줄바꿈 처리)
      return String(str).replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
};
</script>

<style scoped>
.comment {
  text-align: left;
  border-radius: 5px;
  background-color: #f5fbef;
  padding: 10px 20px;
  margin: 10px;
}
.head {
  font-weight: bold;
  margin-bottom: 5px;
}
.content {
  padding: 5px;
}
.cbtn {
  text-align: right;
  color: steelblue;
  margin: 5px 0px;
}
</style>
