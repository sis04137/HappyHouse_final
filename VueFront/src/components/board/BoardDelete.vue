<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <!-- <b-alert show><h3>글목록</h3></b-alert> -->
        <v-banner outlined rounded shaped single-line tile color="#30ac7c"
          ><h3>글목록</h3></v-banner
        >
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <!-- <b-alert show variant="danger">삭제처리중...</b-alert> -->
        <!-- <v-banner outlined rounded shaped single-line tile color="#30ac7c" -->
        <v-banner outlined single-line tile color="#FFC2D2"
          ><h3>삭제처리중...</h3></v-banner
        >
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "BoardDelete",
  created() {
    http.delete(`/board/${this.$route.params.articleno}`).then(() => {
      // 현재 route를 /list로 변경.
      this.$router.push({ name: "BoardList" });
    });
  },
};
</script>

<style></style>
