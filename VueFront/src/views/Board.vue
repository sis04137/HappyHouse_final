<template>
  <div class="bv-example-row mt-3 text-center">
    <!-- <h3 class="underline-hotpink"> -->
    <h2>
      <v-icon size="30px">{{ question }}</v-icon
      >[ QnA ]
    </h2>
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  name: "Board",
  data: () => ({
    question: "question-circle",
  }),
};
</script>
<style scoped>
/* .underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.3) 30%
  );
} */
</style>
