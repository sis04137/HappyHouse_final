<template>
  <section class="section-container">
    <v-row class="signin" style="width: 100%; height: 100%">
      <v-col cols="8" class="left">
        <div class="wrapper">
          <div class="card">
            <h1><span class="enclosed">구아랑</span>혜란</h1>
          </div>
        </div>
      </v-col>
      <v-col cols="4" class="right" align-content="center">
        <div style="width: 100%">
          <h2>Sign In</h2>

          <v-text-field
            required
            outlined
            dark
            filled
            dense
            v-model="user.email"
            label="Email"
          ></v-text-field>
          <v-text-field
            v-model="user.password"
            label="Password"
            required
            outlined
            dense
            dark
            filled
            :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPass = !showPass"
            :type="showPass ? 'text' : 'password'"
          ></v-text-field>
          <v-text-field
            required
            outlined
            dark
            filled
            dense
            v-model="user.name"
            label="Name"
          ></v-text-field>
          <v-text-field
            required
            outlined
            dark
            filled
            dense
            v-model="user.address"
            label="Address"
          ></v-text-field>
          <v-text-field
            required
            outlined
            dark
            filled
            dense
            v-model="user.tel"
            label="Tel"
          ></v-text-field>

          <div class="text-center">
            <v-btn
              class="signin-btn"
              rounded
              color="white"
              dark
              @click="confirm"
            >
              Sign In
            </v-btn>
            <v-btn
              class="signin-btn"
              rounded
              color="white"
              dark
              href="http://localhost:9999/vue/oauth2/authorization/google"
            >
              Google SignIn
            </v-btn>
          </div>
        </div>
      </v-col>
    </v-row>
  </section>
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
      showPass: false,
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
    .left {
      padding: 30px;
      justify-content: center;
      align-items: center;
      box-sizing: border-box;
      display: flex;
      color: #30ac7c;
      background-color: #ffffff;
    }
    .right {
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
<style scoped>
.wrapper {
  position: relative;
  perspective: 40em;
  display: grid;
  transform-style: preserve-3d;
}

.card {
  grid-area: 1 / 1;
  height: 200px;
  width: 400px;
  transform: translateX(10px) rotateY(25deg) rotateX(10deg);
  background: rgba(249, 198, 26, 0.88);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 30px;
  color: #000;
  text-transform: uppercase;
  font-size: 60px;
  font-weight: 900;
  backface-visibility: hidden;
  box-shadow: 0 10px 30px -3px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 60px;
  font-weight: 900;
}

.card .enclosed {
  background: #000;
  line-height: 1;
  color: rgba(249, 198, 26, 1);
  padding: 0 5px;
  display: inline-block;
  transform: translate(-1px, 1px) scale(0.75);
  transform-origin: right center;
}

.wrapper:before {
  --bw: 9px;
  grid-area: 1 / 1;
  content: "";
  backface-visibility: hidden;
  height: 100%;
  width: 100%;
  margin-top: calc(-1 * var(--bw));
  margin-left: calc(-1 * var(--bw));
  background: transparent;
  transform: translateX(-60px) rotateY(-30deg) rotateX(15deg) scale(1.03);
  pointer-events: none;
  border: var(--bw) solid #000;
  box-sizing: content-box;
}

.wrapper:hover > div,
.wrapper:hover:before {
  transform: none;
}

.wrapper > div,
.wrapper:before {
  will-change: transform;
  transition: 0.3s transform cubic-bezier(0.25, 0.46, 0.45, 1);
}
</style>
