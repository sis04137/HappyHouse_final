<template>
  <div id="app" style="width: 100%; height: 100%">
    <!-- <navi-bar /> -->
    <header-bar />
    <router-view />
    <!-- <footer-bar /> -->
  </div>
</template>

<script>
// import NaviBar from "./components/layout/NaviBar.vue";
// import FooterBar from "./components/layout/FooterBar.vue";
import HeaderBar from "./components/layout/Header.vue";

export default {
  name: "App",
  components: {
    // NaviBar,
    // FooterBar,
    HeaderBar,
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
  /* font-family: "NotoSerifKR"; */
  /* font-family: "Dongle", sans-serif; */
  /* font-family: "Gamja Flower", cursive; */
  /* font-family: "Yeon Sung", cursive; */
  /* font-family: "Permanent Marker", cursive; */
  font-family: "Jua", sans-serif;
  /* font-family: "Stylish", sans-serif; */
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
