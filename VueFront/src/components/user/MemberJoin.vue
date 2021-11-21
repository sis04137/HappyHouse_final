<template>
  <section class="section-container">
    <v-row class="signin" style="width: 100%; height: 100%">
      <v-col cols="8" class="left">
        <div class="container">
          <div class="card">
            <div class="imgBx">
              <h4><strong>name + real_type</strong></h4>
              <h6>주소 어쩌고 저쩌고</h6>
            </div>
            <div class="contentBx">
              <h5>price.sales.avg</h5>
              <div class="size">
                <h3></h3>
              </div>
              <div class="size">
                <h3>평점 : score</h3>
              </div>
              <div class="color">
                <h3>households : itemCnt</h3>
              </div>
              <div class="color">
                <h3>price.sales.min ~ price.sales.max</h3>
              </div>
            </div>
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
@import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");

.container {
  position: relative;
}

.container .card {
  position: relative;
  width: 200px;
  height: 300px;
  background: #232323;
  border-radius: 20px;
  overflow: hidden;
}

.container .card:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #30ac7c;
  clip-path: circle(150px at 80% 20%);
  transition: 0.5s ease-in-out;
}

.container .card:hover:before {
  clip-path: circle(300px at 80% -20%);
}

.container .card:after {
  content: "대원";
  position: absolute;
  top: 30%;
  left: -20%;
  font-size: 12em;
  font-weight: 800;
  font-style: italic;
  color: rgba(255, 255, 25, 0.05);
}

.container .card .imgBx {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 10000;
  width: 100%;
  height: 220px;
  transition: 0.5s;
}

.card:hover .container {
  top: 0%;
  transform: translateY(0%);
}
.card:hover .imgBx {
  top: 5%;
  transform: translateY(0%);
  color: #fff;
}

.container .card .imgBx img {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(-25deg);
  width: 270px;
}

.container .card .contentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 100px;
  text-align: center;
  transition: 1s;
  z-index: 10;
}

/* hover하면 얼마나 올라가는지 */
.container .card:hover .contentBx {
  height: 180px;
}

.container .card .contentBx h2 {
  position: relative;
  font-weight: 600;
  letter-spacing: 1px;
  color: #fff;
  margin: 0;
}

.container .card .contentBx .size,
.container .card .contentBx .color {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 20px;
  transition: 0.5s;
  opacity: 0;
  visibility: hidden;
  padding-top: 0;
  padding-bottom: 0;
}

.container .card:hover .contentBx .size {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.5s;
}

.container .card:hover .contentBx .color {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.6s;
}

.container .card .contentBx .size h3,
.container .card .contentBx .color h3 {
  color: #fff;
  font-weight: 300;
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 2px;
  margin-right: 10px;
}

.container .card .contentBx .size span {
  width: 26px;
  height: 26px;
  text-align: center;
  line-height: 26px;
  font-size: 14px;
  display: inline-block;
  color: #111;
  background: #fff;
  margin: 0 5px;
  transition: 0.5s;
  color: #111;
  border-radius: 4px;
  cursor: pointer;
}

.container .card .contentBx .size span:hover {
  background: #30ac7c;
}

.container .card .contentBx .color span {
  width: 20px;
  height: 20px;
  background: #30ac7c;
  border-radius: 50%;
  margin: 0 5px;
  cursor: pointer;
}

.container .card .contentBx a {
  display: inline-block;
  padding: 10px 20px;
  background: #fff;
  border-radius: 4px;
  margin-top: 10px;
  text-decoration: none;
  font-weight: 600;
  color: #111;
  opacity: 0;
  transform: translateY(50px);
  transition: 0.5s;
  margin-top: 0;
}

.container .card:hover .contentBx a {
  opacity: 1;
  transform: translateY(0px);
  transition-delay: 0.75s;
}
</style>
