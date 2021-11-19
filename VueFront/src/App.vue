<template>
  <div id="app">
    <!-- <navi-bar /> -->
    <header-bar />
    <router-view />
    <footer-bar />
  </div>
</template>

<script>
// import NaviBar from "./components/layout/NaviBar.vue";
import FooterBar from "./components/layout/FooterBar.vue";
import HeaderBar from "./components/layout/Header.vue";

export default {
  name: "App",
  components: {
    // NaviBar,
    HeaderBar,
    FooterBar,
  },
};
</script>

<style>
@font-face {
  font-family: "NotoSerifKR";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NotoSerifKR.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
div {
  font-family: "NotoSerifKR";
  color: black;
}
a:hover {
  text-decoration: none;
  font-weight: bold;
}

a.router-link-exact-active {
  color: #42b983;
}
</style>
