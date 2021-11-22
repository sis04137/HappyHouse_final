<template>
  <v-container style="height: 700px">
    <v-btn @click="handleClickGetAuth"> getauthorization code</v-btn>
    <v-btn @click="authKakao"> 카카오로그인</v-btn>
    <a href="http://localhost:9999/vue/oauth2/authorization/google"
      >서버 구글로그인</a
    >
    <!-- <div>
      <detail-deal-chart></detail-deal-chart>
    </div> -->
  </v-container>
</template>

<script>
import http from "@/util/http-common.js";
// import DetailDealChart from "@/components/house/DetailDealChart.vue";
export default {
  name: "TestG",
  components: {
    // DetailDealChart,
  },
  methods: {
    handleClickGetAuth() {
      this.$gAuth
        .getAuthCode()
        .then((authCode) => {
          //on success
          console.log("authCode");
          console.log(authCode);
          http.post("/google", authCode).then(({ data }) => {
            console.log(data);
          });
          // return this.$http.post("http://localhost:9999/vue/google", authCode); //이 uri를 redierct uri로 등록해줘야 함 아니면 에러
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log("error");
          console.log(error);
        });
    },
    // handleClickGetAuth() {
    //   this.$gAuth
    //     .getAuthCode()
    //     .then((authCode) => {
    //       //on success
    //       console.log("authCode");
    //       console.log(authCode);
    //       return this.$http.get("/google", {
    //         code: authCode,
    //         redirect_uri: "postmessage",
    //       });
    //     })
    //     .then((response) => {
    //       console.log(response);
    //     })
    //     .catch((error) => {
    //       console.log("error");
    //       console.log(error);
    //     });
    // },
    authGoogle() {
      http.get("/oauth2/authorization/google").then(({ data }) => {
        console.log(data);
      });
    },
    signOut() {
      const authInst = window.gapi.auth2.getAuthInstance();
      authInst.signOut().then(() => {
        // eslint-disable-next-line
        console.log("User Signed Out!!!");
      });
    },
    authKakao() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?client_id=44ddf9f04018e79ad5791e558f7dafb5&redirect_uri=http://localhost:8080&response_type=code`
      );
      // axios
      //   .get(
      //     `https://kauth.kakao.com/oauth/authorize?client_id=44ddf9f04018e79ad5791e558f7dafb5&redirect_uri=http://localhost:8080&response_type=code`
      //   )
      //   .then(({ data }) => {
      //     console.log(data);
      //   });
    },
  },
};
</script>

<style lang="scss">
// Colors
$color-primary: #0c8040;
$color-secondary: #626262;
$color-border: #ccd7d7;

// Media Queries breakpoints
$small: 480px;
$medium: 768px;
$large: 992px;
$x-large: 1200px;

@mixin hide-menu-items($index) {
  &:nth-child(n + #{$index + 1}) {
    display: none;
  }

  &:nth-child(#{$index}) .menu__link {
    border-right: 0;
  }
}

body {
  display: flex;
  align-items: center;
  height: 100vh;
  background: #fefefe;
}

a {
  outline: 0;
  text-decoration: none;
}

.navigation {
  width: 100%;
  border-bottom: 3px solid $color-border;
  // font-family: "Pathway Gothic One", sans-serif;
  font-size: 22px;
}

.menu {
  display: flex;
  justify-content: center;
  max-width: 1150px;
  margin: 0 auto;
  padding-left: 0;
}

.menu__item {
  display: inline-block;
  white-space: nowrap;

  @media screen and (max-width: $small) {
    @include hide-menu-items(2);
  }

  @media screen and (max-width: $medium) {
    @include hide-menu-items(3);
  }

  @media screen and (max-width: $large) {
    @include hide-menu-items(4);
  }

  &:last-child .menu__link {
    border-right: 0;
  }
}

.menu__link {
  display: block;
  padding: 6px 30px 3px;
  border-right: 2px dotted $color-border;

  &:hover,
  &:focus {
    .menu__first-word {
      transform: translate3d(0, -105%, 0);
    }

    .menu__second-word {
      transform: translate3d(0, 105%, 0);
    }
  }

  @media screen and (min-width: $medium) {
    padding: 6px 40px 3px;
  }
}

.menu__title {
  display: inline-block;
  overflow: hidden;
}

.menu__first-word,
.menu__second-word {
  display: inline-block;
  position: relative;
  transition: transform 0.3s;

  &::before {
    position: absolute;
    content: attr(data-hover);
  }
}

.menu__first-word {
  color: $color-primary;

  &::before {
    top: 105%;
    color: $color-secondary;
  }
}

.menu__second-word {
  color: $color-secondary;

  &::before {
    bottom: 105%;
    color: $color-primary;
  }
}
</style>
