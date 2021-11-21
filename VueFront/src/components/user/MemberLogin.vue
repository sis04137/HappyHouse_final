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
      <v-col cols="4" align-center class="right">
        <div style="width: 100%">
          <h2>LOGIN</h2>
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

          <div class="text-center">
            <v-btn
              class="signin-btn"
              rounded
              color="white"
              dark
              @click="confirm"
            >
              Log in
            </v-btn>
          </div>
        </div>
      </v-col>
    </v-row>
  </section>
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

      showPass: false,
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
      // padding-top: 10%;
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

<style scoped>
/* class에 card만 주면 테두리 무지개 됨 */
/* .card {
  background-color: var(--background);
  display: block;
  width: 300px;
  min-height: 90px;
  cursor: pointer;
  padding: 15px;
  margin: calc(50vh - 30px) auto 0 auto;
  border: 3px solid var(--primary);
  box-shadow: 10px -10px 0 -3px var(--background), 10px -10px var(--green),
    20px -20px 0 -3px var(--background), 20px -20px var(--yellow),
    30px -30px 0 -3px var(--background), 30px -30px var(--orange),
    40px -40px 0 -3px var(--background), 40px -40px var(--red);
}

.card:hover {
  animation: shadow-wave 1s ease infinite;
}

@keyframes shadow-wave {
  0% {
    border: 3px solid var(--primary);
    box-shadow: 10px -10px 0 -3px var(--background), 10px -10px var(--green),
      20px -20px 0 -3px var(--background), 20px -20px var(--yellow),
      30px -30px 0 -3px var(--background), 30px -30px var(--orange),
      40px -40px 0 -3px var(--background), 40px -40px var(--red);
  }
  20% {
    border: 3px solid var(--red);
    box-shadow: 10px -10px 0 -3px var(--background), 10px -10px var(--primary),
      20px -20px 0 -3px var(--background), 20px -20px var(--green),
      30px -30px 0 -3px var(--background), 30px -30px var(--yellow),
      40px -40px 0 -3px var(--background), 40px -40px var(--orange);
  }
  40% {
    border: 3px solid var(--orange);
    box-shadow: 10px -10px 0 -3px var(--background), 10px -10px var(--red),
      20px -20px 0 -3px var(--background), 20px -20px var(--primary),
      30px -30px 0 -3px var(--background), 30px -30px var(--green),
      40px -40px 0 -3px var(--background), 40px -40px var(--yellow);
  }
  60% {
    border: 3px solid var(--yellow);
    box-shadow: 10px -10px 0 -3px var(--background), 10px -10px var(--orange),
      20px -20px 0 -3px var(--background), 20px -20px var(--red),
      30px -30px 0 -3px var(--background), 30px -30px var(--primary),
      40px -40px 0 -3px var(--background), 40px -40px var(--green);
  }
  80% {
    border: 3px solid var(--green);
    box-shadow: 10px -10px 0 -3px var(--background), 10px -10px var(--yellow),
      20px -20px 0 -3px var(--background), 20px -20px var(--orange),
      30px -30px 0 -3px var(--background), 30px -30px var(--red),
      40px -40px 0 -3px var(--background), 40px -40px var(--primary);
  }
  100% {
    border: 3px solid var(--primary);
    box-shadow: 10px -10px 0 -3px var(--background), 10px -10px var(--green),
      20px -20px 0 -3px var(--background), 20px -20px var(--yellow),
      30px -30px 0 -3px var(--background), 30px -30px var(--orange),
      40px -40px 0 -3px var(--background), 40px -40px var(--red);
  }
}

:root {
  --primary: #22d2a0;
  --secondary: #192824;
  --background: #192824;
  --green: #1fc11b;
  --yellow: #ffd913;
  --orange: #ff9c55;
  --red: #ff5555;
}

.card p {
  text-transform: uppercase;
} */
</style>
