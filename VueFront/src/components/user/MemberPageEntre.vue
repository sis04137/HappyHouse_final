<template>
  <section class="section-container">
    <v-row class="signin" style="width: 100%; height: 100%">
      <v-col cols="4" class="left" align-content="center"> </v-col>
      <v-col cols="8" class="right"
        ><div style="width: 100%">
          <h2>비밀번호를 다시 입력해주세요</h2>
          <v-text-field
            id="password"
            v-model="password"
            label="Password"
            required
            outlined
            dense
            filled
            :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPass = !showPass"
            :type="showPass ? 'text' : 'password'"
          ></v-text-field>

          <div class="text-center">
            <v-row>
              <v-col>
                <v-btn
                  class="signin-btn"
                  rounded
                  color="white"
                  dark
                  @click="confirm"
                >
                  마이페이지로
                </v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn
                  class="signin-btn"
                  rounded
                  color="white"
                  dark
                  :to="{ name: 'MyPageMain' }"
                >
                  뒤로
                </v-btn>
                <v-btn
                  class="signin-btn"
                  rounded
                  color="white"
                  dark
                  @click="findPass"
                >
                  비밀번호 찾기
                </v-btn>
              </v-col>
            </v-row>
          </div>
        </div>
      </v-col>
    </v-row>
  </section>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState } from "vuex";
export default {
  name: "MemberJoin",
  computed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      isLoginError: false,
      showPass: false,
      password: "",
    };
  },
  methods: {
    movePage() {
      // save api 날리기
      http.post(`/api/member/save`, this.user).then(() => {
        alert(`${this.user.email}로 로그인해주세요`);
        this.$router.push({ name: "SignIn" });
      });
    },
    confirm() {
      if (this.user.password == this.password) {
        this.$router.push({ name: "MyPage" });
      } else {
        alert("회원 정보 변경을 위해서는 비밀번호가 일치해야 합니다.");
      }
    },
    findPass() {
      http.post(`/api/member/findpass`, this.user.email).then(() => {
        alert(
          `가입하신 이메일로 임시 비밀번호가 발송되었습니다. 임시 비밀번호로 인증하신 후 마이페이지에 접속하셔서 비밀번호를 변경해주세요.`
        );
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.section-container {
  background: #fff;
  width: 100%;
  box-shadow: 0 0 1px 1px rgba($color: #000000, $alpha: 0.1);
  box-sizing: border-box;
  .signin {
    padding: 0;
    min-width: 100%;
    min-height: 1000px;
    box-shadow: 0 0 1px 1px rgba($color: #000000, $alpha: 0.1);
    .right {
      padding: 30px;
      justify-content: center;
      align-items: center;
      box-sizing: border-box;
      display: flex;
      color: #30ac7c;
      background-color: #ffffff;
    }
    .left {
      padding: 30px;
      justify-content: center;
      align-items: center;
      box-sizing: border-box;
      display: flex;
      background: #30ac7c;
      color: #fff;
      h2 {
        text-align: center;
        margin: 30px 0;
      }
      .signin-btn {
        width: 100%;
        color: #30ac7c;
      }
    }
  }
}
</style>
