<template>
  <div class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>마이페이지</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-form-group label="이메일" label-for="email">
              <b-form-input
                id="email"
                v-model="newUser.email"
                :placeholder="user.email"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="name">
              <b-form-input
                type="text"
                id="name"
                v-model="newUser.name"
                :placeholder="user.name"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호" label-for="password">
              <b-form-input
                type="password"
                id="password"
                v-model="newUser.password"
                required
                placeholder="비밀번호를 다시 입력해주세요"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="전화번호" label-for="tel">
              <b-form-input
                type="tel"
                id="tel"
                v-model="newUser.tel"
                :placeholder="user.tel"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="주소" label-for="address">
              <b-form-input
                type="text"
                id="address"
                v-model="newUser.address"
                :placeholder="user.address"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >회원정보 변경
            </b-button>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="userOut"
              >탈퇴
            </b-button>
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >뒤로</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState, mapActions } from "vuex";
export default {
  name: "MemberJoin",
  computed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      newUser: {
        email: null,
        name: null,
        password: null,
        tel: null,
        address: null,
      },
    };
  },
  methods: {
    ...mapActions(["getUserByUserId", "logout"]),
    confirm() {
      //save api 날리기
      console.log(this.newUser);
      http
        .put(`/api/member/${this.user.id}`, this.newUser)
        .then(({ data }) => {
          alert(`유저 정보가 업데이트 되었습니다.`);
          this.getUserByUserId(data);
        })
        .catch((error) => {
          alert(error);
        });
    },
    movePage() {
      this.$router.push({ name: "Home" });
    },
    userOut() {
      http.delete(`/api/member/${this.user.id}`).then(() => {
        this.logout();
        alert("탈퇴되었습니다");
        this.movePage();
      });
    },
  },
};
</script>

<style></style>
