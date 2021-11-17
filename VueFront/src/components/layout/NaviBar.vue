<template>
  <div id="nav">
    <b-navbar toggleable="lg" class="bg-transparent">
      <b-navbar-brand href="#">
        <router-link to="/">
          <img
            src="@/assets/ssafy_logo.png"
            class="d-inline-block align-middle"
            width="90px"
            alt="OurHome"
          />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Home' }" class="link"
              ><b-icon icon="house" font-scale="1"></b-icon> HOME</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'House' }" class="link"
              ><b-icon icon="house-fill" font-scale="1"></b-icon> Apart
              Info</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Board' }" class="link"
              ><b-icon icon="journal" font-scale="1"></b-icon> QnA</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Instargram' }" class="link"
              ><b-icon icon="instagram" font-scale="1"></b-icon>
              Instagram</router-link
            ></b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav v-if="user == null" class="ml-auto">
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'SignUp' }" class="link"
                ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'SignIn' }" class="link"
                ><b-icon icon="key"></b-icon> 로그인</router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>

        <b-navbar-nav v-else class="ml-auto">
          <b-nav-text>{{ user.name }}님 </b-nav-text>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'MyPage' }" class="link"
                ><b-icon icon="person-circle"></b-icon> 마이페이지</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item href="#" @click="logoutBtn"
              ><b-icon icon="person-circle"></b-icon> 로그아웃</b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "NaviBar",
  computed: {
    ...mapState(["user"]),
  },
  methods: {
    ...mapActions(["logout"]),
    logoutBtn() {
      console.log("logout");
      this.logout();
      alert("로그아웃되었습니다.");
      //홈에서 홈으로 가는 경우 redundant error 잡아서 던지려고 넣음
      this.$router.push({ name: "Home" }).catch(() => {});
    },
  },
};
</script>

<style scoped></style>
