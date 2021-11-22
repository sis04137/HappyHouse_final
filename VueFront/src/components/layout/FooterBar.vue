<template>
  <v-footer v-bind="localAttrs" :padless="padless" width="100%">
    <v-card flat tile width="100%" class="red lighten-1 text-center">
      <v-card-text>
        <v-btn icon @click="home()">
          <v-icon size="24px">
            {{ mdihome }}
          </v-icon>
        </v-btn>

        <!-- <v-dialog v-model="dialog">
            <v-btn icon @click="email()">
              <v-icon size="24px">
                {{ mdiemail }}
              </v-icon>
            </v-btn>
            <v-card>
              <v-card-title class="text-h5 grey lighten-2">
                Email Address
              </v-card-title>

              <v-card-text> E-mail : admin@ssafy.com </v-card-text>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="dialog = false">
                  closed
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog> -->

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
        {{ new Date().getFullYear() }} — <strong>(SSAFY)</strong> 서울시 강남구
        테헤란로 멀티 스퀘어
      </v-card-text>
    </v-card>
  </v-footer>
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
    variant: "default",
    dialog: false,
  }),
  computed: {
    localAttrs() {
      const attrs = {};

      if (this.variant === "default") {
        attrs.absolute = false;
        attrs.fixed = false;
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
