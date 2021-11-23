<template>
  <section class="section-container">
    <v-row class="signin" style="width: 100%; height: 100%">
      <v-col cols="4" class="left" align-content="center">
        <div style="width: 100%">
          <h2>회원 정보 변경</h2>
          <v-text-field
            required
            outlined
            dark
            filled
            dense
            id="email"
            v-model="newUser.email"
            :placeholder="user.email"
          ></v-text-field>
          <v-text-field
            id="password"
            v-model="newUser.password"
            label="Password"
            required
            outlined
            dense
            dark
            filled
          ></v-text-field>
          <v-text-field
            required
            outlined
            dark
            filled
            dense
            id="name"
            v-model="newUser.name"
            :placeholder="user.name"
            label="Name"
          ></v-text-field>
          <v-text-field
            required
            outlined
            dark
            filled
            dense
            id="address"
            v-model="newUser.address"
            :placeholder="user.address"
            label="Address"
          ></v-text-field>
          <v-text-field
            required
            outlined
            dark
            filled
            dense
            type="tel"
            id="tel"
            v-model="newUser.tel"
            :placeholder="user.tel"
            label="Tel"
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
                  정보 변경
                </v-btn>
              </v-col>
            </v-row>
            <!-- 얘 가로정렬 하고 싶은데 안 됨ㅠㅠ  -->
            <v-row>
              <v-col>
                <v-btn
                  class="signin-btn"
                  rounded
                  color="white"
                  dark
                  @click="movePage"
                >
                  뒤로
                </v-btn>
                <v-btn
                  class="signin-btn"
                  rounded
                  color="white"
                  dark
                  @click="userOut"
                >
                  탈퇴
                </v-btn>
              </v-col>
            </v-row>
          </div>
        </div>
      </v-col>
      <v-col cols="8" class="right"> MyPage </v-col>
    </v-row>
  </section>
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

        showPass: false,
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
