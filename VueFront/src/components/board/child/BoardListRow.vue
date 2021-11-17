<template>
  <b-tr>
    <b-td>{{ articleno }}</b-td>
    <b-th class="text-left">
      <router-link
        :to="{ name: 'BoardView', params: { articleno: articleno } }"
        >{{ subject }}</router-link
      >
    </b-th>
    <b-td>{{ hit }}</b-td>
    <b-td>{{ username }}</b-td>
    <b-td>{{ regtime }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";
import http from "@/util/http-common.js";

export default {
  name: "BoardListRow",
  props: {
    articleno: Number,
    userid: String,
    subject: String,
    hit: Number,
    regtime: String,
  },
  data() {
    return {
      username: "",
      id: this.userid,
    };
  },
  created() {
    http
      .get(`/api/member/${this.id}`)
      .then(({ data }) => {
        this.username = data.name;
      })
      .catch((error) => console.log(error));
  },
  computed: {},
};
</script>

<style></style>
