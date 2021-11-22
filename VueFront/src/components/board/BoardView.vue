<template>
  <!-- <b-container class="bv-example-row mt-3"> -->
  <div>
    <v-container class="text-center">
      <v-row>
        <v-col>
          <!-- <b-alert show><h3>글보기</h3></b-alert> -->
          <v-banner outlined rounded shaped single-line tile color="#30ac7c"
            ><h3>글보기</h3></v-banner
          >
        </v-col>
      </v-row>
      <v-row class="mb-1">
        <v-col class="text-left">
          <!-- <v-btn variant="outline-primary" color="outline-secondary"  @click="listArticle" -->
          <v-btn outlined color="#01A9DB" @click="listArticle">목록</v-btn>
        </v-col>
        <v-col
          v-if="
            user != null && (user.role == 'ADMIN' || user.id == article.userid)
          "
          class="text-right"
        >
          <v-btn
            outlined
            color="#2E64FE"
            variant="outline-info"
            size="sm"
            @click="moveModifyArticle"
            class="mr-2"
            >글수정</v-btn
          >
          <v-btn
            outlined
            color="#FE2E64"
            v-if="
              user != null &&
              (user.role == 'ADMIN' || user.id == article.userid)
            "
            variant="outline-danger"
            size="sm"
            @click="deleteArticle"
            >글삭제</v-btn
          >
        </v-col>
      </v-row>
      <v-row class="mb-1">
        <v-card outlined width="100%">
          <v-card-title
            primary-title
            class="justify-center"
            border-variant="dark"
            ><h4>
              {{ article.articleno }}. {{ article.subject }} [{{ article.hit }}]
            </h4>
          </v-card-title>
          <v-card-subtitle class="text-left">
            <div>
              <h6>작성자: {{ this.username }}</h6>
            </div>
            <div>
              <h6>작성: {{ article.regtime }}</h6>
            </div>
            <div>
              <h6>수정: {{ article.modified }}</h6>
            </div>
          </v-card-subtitle>
          <v-divider></v-divider>
          <v-card-text class="text-left" hight>
            <div v-html="message"></div>
          </v-card-text>
        </v-card>
      </v-row>
      <!-- </b-container> -->
      <comment-write :isbn="isbn" />
      <comment-write
        v-if="isModifyShow && modifyComment != null"
        :modifyComment="modifyComment"
        @modify-comment-cancel="onModifyCommentCancel"
      />
      <comment
        v-for="(comment, index) in comments"
        :key="index"
        :comment="comment"
        @modify-comment="onModifyComment"
      />
    </v-container>
  </div>
</template>

<script>
// import moment from "moment";
import http from "@/util/http-common";
import { mapGetters, mapState } from "vuex";
import CommentWrite from "@/components/board/child/comment/CommentWrite.vue";
import Comment from "@/components/board/child/comment/Comment.vue";

export default {
  components: {
    CommentWrite,
    Comment,
  },
  data() {
    return {
      article: {},
      isbn: this.$route.params.articleno,
      isModifyShow: false,
      modifyComment: Object,
      uid: 2,
      username: "",
    };
  },
  computed: {
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    ...mapState(["user"]),
    // changeDateFormat() {
    //   return moment(new Date(this.article.regtime)).format(
    //     "YYYY.MM.DD hh:mm:ss"
    //   );
    // },
    ...mapGetters(["comments"]),
  },
  created() {
    http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
      http
        .get(`/api/member/${data.userid}`)
        .then(({ data }) => {
          console.log(data);
          this.username = data.name;
        })
        .catch((error) => console.log(error));
    });
    this.$store.dispatch("getComments", this.isbn);
  },

  methods: {
    listArticle() {
      this.$router.push({ name: "BoardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "BoardUpdate",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "BoardDelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
    onModifyComment(comment) {
      this.isModifyShow = true;
      this.modifyComment = comment;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    },
  },
};
</script>

<style></style>
