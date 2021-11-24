<template>
  <section class="section-container">
    <h2>관심매물 관리</h2>
    <v-navigation-drawer absolute permanent right>
      <template v-slot:prepend>
        <v-list-item two-line>
          <v-list-item-avatar>
            <img src="https://randomuser.me/api/portraits/women/81.jpg" />
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>Jane Smith</v-list-item-title>
            <v-list-item-subtitle>Logged In</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </template>

      <v-divider></v-divider>

      <v-list dense>
        <v-list-item>
          <v-list-item-icon>
            <v-icon>mdi-pin</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>아이템1</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-list-item-icon>
            <v-icon>mdi-pin</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>아이템1</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-list-item-icon>
            <v-icon>mdi-pin</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>아이템1</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th>이름</th>
            <th>주소</th>
            <th>세대수</th>
            <th>현재매물</th>
          </tr>
        </thead>
        <tbody>
          <!-- <tr v-for="(item, index) in real_data" :key="index">
                <td>{{ item.rtDealDate }}</td>
                <td>{{ item.rtPrice }}{{ item.rtDealType }}</td>
                <td>{{ item.roomTypeId }}</td>
                <td>{{ item.rtFloor }}</td>
              </tr> -->
        </tbody>
      </template>
    </v-simple-table>
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
  }
}
</style>
