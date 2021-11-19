<template>
  <v-app-bar
    data-app
    fixed
    dense
    flat
    absolute
    dark
    style="position: sticky; z-index: 999"
  >
    <router-link to="/">
      <img
        src="@/assets/ssafy_logo.png"
        class="d-inline-block align-middle"
        width="50px"
        alt="OurHome"
      />
    </router-link>
    <v-spacer></v-spacer>

    <v-btn @click="$router.push({ name: 'Home' })" text color="black">
      <strong>HOME</strong>
    </v-btn>
    <v-btn @click="$router.push({ name: 'House' })" text color="black">
      <strong>HOUSE</strong>
    </v-btn>
    <v-btn @click="$router.push({ name: 'Board' })" text color="black">
      <strong>BOARD</strong>
    </v-btn>

    <v-spacer></v-spacer>

    <v-menu open-on-hover offset-y flat>
      <template v-slot:activator="{ on, attrs }">
        <v-btn text class="mx-2" small v-bind="attrs" v-on="on">
          <v-icon> mdi-account-multiple </v-icon>
        </v-btn>
      </template>

      <v-list v-if="user == null" shaped>
        <v-list-item
          @click="headerAction(item.title)"
          v-for="(item, index) in items2"
          :key="index"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>

      <v-list v-else shaped flat>
        <v-list-item
          @click="headerAction(item.title)"
          v-for="(item, index) in items"
          :key="index"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "Header",
  computed: {
    ...mapState(["user"]),
  },
  data: () => ({
    items: [
      {
        title: "회원가입",
        icon: "person-circle",
      },
      {
        title: "로그인",
        icon: "key",
      },
    ],
    items2: [
      {
        title: "마이페이지",
        icon: "person-circle",
      },
      { title: "로그아웃", icon: "key" },
    ],
    // items: [
    //   { title: "Click Me" },
    //   { title: "Click Me" },
    //   { title: "Click Me" },
    //   { title: "Click Me 2" },
    // ],
  }),
  methods: {
    ...mapActions(["logout"]),
    logoutBtn() {
      console.log("logout");
      this.logout();
      alert("로그아웃되었습니다.");
      //홈에서 홈으로 가는 경우 redundant error 잡아서 던지려고 넣음
      this.$router.push({ name: "Home" }).catch(() => {});
    },
    headerAction(title) {
      if (title === "회원가입") {
        this.$router.push({ name: "SignUp" }).catch(() => {});
      } else if (title === "로그인") {
        this.$router.push({ name: "SignIn" }).catch(() => {});
      } else if (title === "마이페이지") {
        this.$router.push({ name: "MyPage" }).catch(() => {});
      } else if (title === "로그아웃") {
        this.logoutBtn();
      }
    },
  },
};
</script>

<style scoped></style>
