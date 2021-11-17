<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >이메일 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                required
                placeholder="이메일 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="password">
              <b-form-input
                type="password"
                id="password"
                v-model="user.password"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >로그인</b-button
            >
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common.js";
import { mapActions } from "vuex";
export default {
  name: "MemberLogin",
  data() {
    return {
      isLoginError: false,
      user: {
        email: "",
        password: "",
      },
    };
  },
  //login 날리고 null아니면 저장
  methods: {
    ...mapActions(["getUserByUserId"]),
    confirm() {
      http
        .post(`/api/member/login`, this.user)
        .then(({ data }) => {
          if (data == null) {
            alert(`다시 로그인해주세요.`);
          } else {
            alert(`${this.user.email}로 로그인되었습니다`);
            console.log(data);
            this.getUserByUserId(data.id);
            this.$router.push({ name: "Home" });
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    movePage() {
      this.$router.push({ name: "SignUp" });
    },
  },
};
</script>

<style></style>
