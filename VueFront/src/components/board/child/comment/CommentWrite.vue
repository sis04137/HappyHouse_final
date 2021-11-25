<template>
  <div class="regist">
    <div v-if="modifyComment != null" class="regist_form">
      <textarea
        class="modi"
        name="comment"
        id="comment"
        v-model="modifyComment.comment"
        cols="35"
        rows="2"
        style="board"
      ></textarea>
      <button class="small" @click="updateCommentCancel">취소</button>
      <button class="small" @click="updateComment">수정</button>
    </div>
    <div v-else class="regist_form">
      <textarea
        name="comment"
        id="comment"
        v-model="comment"
        cols="35"
        rows="2"
      >
      </textarea>
      <button class="sub" @click="registComment">등록</button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import http from "@/util/http-common.js";

export default {
  props: {
    isbn: String,
    modifyComment: Object,
  },
  computed: {
    ...mapState(["user"]),
  },

  data() {
    return {
      //차후 작성자 이름은 로그인 구현후 로그인한 사용자로 바꾼다.
      // user_id: "",
      comment: "",
    };
  },
  methods: {
    registComment() {
      console.log("call registComment");
      http
        .post("/comment", {
          user_id: this.user.id,
          comment: this.comment,
          isbn: this.isbn,
        })
        .then(() => {
          console.log("registered");
          this.content = "";
          this.$store.dispatch("getComments", this.isbn);
          this.comment = "";
        })
        .catch((e) => {
          console.log(e);
        });
    },
    updateComment() {
      http
        .put(`/comment`, {
          comment_no: this.modifyComment.comment_no,
          comment: this.modifyComment.comment,
        })
        .then(() => {
          this.$store.dispatch("getComments", this.modifyComment.isbn);
          this.updateCommentCancel();
        });
    },
    updateCommentCancel() {
      this.$emit("modify-comment-cancel", false);
    },
  },
};
</script>

<style scoped>
textarea {
  width: 95%;
  font-size: large;
  border-style: groove;
  /* border: 3px; */
  /* color: rgba(68, 0, 255, 0); */
  outline: auto;
  outline-color: #b9d899;
  outline-width: 0.5px;
}
textarea.modi {
  width: 85%;
  font-size: medium;
  border-style: groove;
  /* border: 3px; */
  /* color: rgba(68, 0, 255, 0); */
  outline: auto;
  outline-color: #b9d899;
}
button.sub {
  outline-style: auto;
  float: right;
  width: 30px;
  outline-color: #718062;
}
button.small {
  color: "#2E64FE";
  /* outline-style: hidden; */
  outline-style: auto;
  outline-color: #718062;
  width: 37px;
  float: right;
  font-size: medium;
  font-weight: normal;
}
</style>
