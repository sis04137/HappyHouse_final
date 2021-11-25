<template>
  <div class="bv-example-row mt-3 text-center">
    <!-- <h3 class="underline-hotpink"> -->
    <h2>
      <!-- <v-icon size="30px">{{ question }}</v-icon -->
      MyPage
      <v-icon large size="30px" color="#30ac7c">mdi-account-circle</v-icon>
    </h2>
    <router-view></router-view>
    <footer-bar></footer-bar>
  </div>
</template>

<script>
import FooterBar from "@/components/layout/FooterBar.vue";
export default {
  name: "Member",
  components: {
    FooterBar,
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}
</style>
