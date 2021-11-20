<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <v-banner outlined rounded shaped single-line tile color="#CEF6E3"
          ><h3>글작성</h3></v-banner
        >
      </v-col>
    </v-row>
    <board-write-form type="register" />
  </v-container>
</template>

<script>
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardWrite",
  components: {
    BoardWriteForm,
  },
};
</script>

<style></style>
