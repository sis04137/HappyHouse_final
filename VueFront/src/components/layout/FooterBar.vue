<template>
  <v-card height="400px">
    <v-footer app v-bind="localAttrs" :padless="padless">
      <v-card flat tile width="100%" class="red lighten-1 text-center">
        <v-card-text>
          <v-btn icon @click="home()">
            <v-icon size="24px">
              {{ mdihome }}
            </v-icon>
          </v-btn>
          <v-btn icon @click="email()">
            <v-icon size="24px">
              {{ mdiemail }}
            </v-icon>
          </v-btn>
          <v-btn icon @click="phone()">
            <v-icon size="24px">
              {{ mdiphone }}
            </v-icon>
          </v-btn>
          <!-- <v-btn v-for="icon in icons" :key="icon" class="mx-4" icon>
            <v-icon size="24px">
              {{ icon }}
            </v-icon>
          </v-btn> -->
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="white--text">
          {{ new Date().getFullYear() }} — <strong>(SSAFY)</strong> 서울시
          강남구 테헤란로 멀티 스퀘어
        </v-card-text>
      </v-card>
    </v-footer>
  </v-card>
</template>

<script>
//mailbox, telephone-fill, envelope-fill
// import Vue from "Vue";
// import { mailbox, telephone, envelope } from "bootstrap-vue";

export default {
  name: "FooterBar",
  // components: {
  //   mailbox,
  //   telephone,
  //   envelope,
  // },
  data: () => ({
    // icons: ["mdi-home", "mdi-email", "mdi-calendar", "mdi-delete"],
    mdihome: "mdi-home",
    mdiemail: "mdi-email",
    mdiphone: "mdi-phone",
    padless: false,
    variant: "fixed",
  }),
  computed: {
    localAttrs() {
      const attrs = {};

      if (this.variant === "fixed") {
        attrs.absolute = false;
        attrs.fixed = true;
      } else {
        attrs[this.variant] = true;
      }
      return attrs;
    },
  },
  methods: {
    home() {
      this.$router.push({ name: "Home" });
    },
    email() {
      alert("E-mail : admin@ssafy.com");
    },
    phone() {
      alert("Phone : 1544-9001");
    },
  },
};
</script>

<style scoped>
footer {
  border-top: 1px solid #35495e;
  text-align: left;
  font-size: 16px;
  color: #535bcc;
  margin: 100px 0 0 0;
}
.address {
  color: #101018;
  float: left;
}
.copyright {
  color: #101018;
  text-align: right;
  float: right;
  display: inline-block;
}
</style>
