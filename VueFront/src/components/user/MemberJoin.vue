<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
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
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="name">
              <b-form-input
                type="text"
                id="name"
                v-model="user.name"
                required
                placeholder="이름 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="password">
              <b-form-input
                type="password"
                id="password"
                v-model="user.password"
                required
                placeholder="비밀번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="전화번호:" label-for="tel">
              <b-form-input
                type="tel"
                id="tel"
                v-model="user.tel"
                required
                placeholder="전화번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="주소:" label-for="address">
              <b-form-input
                type="text"
                id="address"
                v-model="user.address"
                required
                placeholder="주소 입력...."
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >회원가입
            </b-button>
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >로그인</b-button
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
export default {
  name: "MemberJoin",
  data() {
    return {
      isLoginError: false,
      user: {
        email: "",
        password: "",
        name: "",
        tel: "",
        address: "",
      },
    };
  },
  methods: {
    confirm() {
      //save api 날리기
      http.post(`/api/member/save`, this.user).then(() => {
        alert(`${this.user.email}로 로그인해주세요`);
        this.$router.push({ name: "SignIn" });
      });
    },
    movePage() {
      this.$router.push({ name: "SignUp" });
    },
  },
};
</script>

<style></style>
