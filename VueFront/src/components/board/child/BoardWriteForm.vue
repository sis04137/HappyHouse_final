<template>
  <v-row class="mb-1">
    <v-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="userid-group"
          label="작성자:"
          label-for="userid"
          description="작성자를 입력하세요."
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="user.name"
            type="text"
            required
            readonly
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="subject-group"
          label="제목:"
          label-for="subject"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <v-btn
          outlined
          color="#2E64FE"
          type="submit"
          size="sm"
          class="m-1"
          v-if="this.type === 'register'"
          >글작성</v-btn
        >
        <v-btn
          outlined
          color="#2E64FE"
          type="submit"
          size="sm"
          class="m-1"
          v-else
          >글수정</v-btn
        >
        <v-btn outlined color="#FE2E64" type="reset" size="sm" class="m-1"
          >초기화</v-btn
        >
      </b-form>
    </v-col>
  </v-row>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";

export default {
  name: "BoardWriteForm",
  data() {
    return {
      article: {
        articleno: 0,
        userid: 0,
        subject: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
        this.article = data;
      });
      this.isUserid = true;
    }
  },
  computed: {
    ...mapState(["user"]),
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";

      err &&
        !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.$router.push({ name: "BoardList" });
    },
    registArticle() {
      console.log(this.user.id);
      http
        .post(`/board`, {
          userid: this.user.id,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(() => {
          this.moveList();
        });
    },
    modifyArticle() {
      http
        .put(`/board/${this.article.articleno}`, {
          articleno: this.article.articleno,
          userid: this.user.id,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(() => {
          this.$router.push({ name: "BoardList" });
        });
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
  },
};
</script>

<style></style>
