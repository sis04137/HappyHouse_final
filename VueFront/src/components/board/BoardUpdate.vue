<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <!-- <b-alert show><h3>글수정</h3></b-alert> -->
        <!-- <v-banner outlined rounded shaped single-line tile color="#CEF6E3" -->
        <v-banner outlined rounded shaped single-line tile color="#30ac7c"
          ><h3>글수정</h3></v-banner
        >
      </v-col>
    </v-row>
    <board-write-form type="modify" />
  </v-container>
</template>

<script>
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardUpdate",
  components: {
    BoardWriteForm,
  },
};
</script>

<style></style>
