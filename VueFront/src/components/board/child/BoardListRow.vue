<template>
  <!-- <tr>
    <td>{{ articleno }}</td>
    <th class="text-left">
      <router-link
        :to="{ name: 'BoardView', params: { articleno: articleno } }"
        >{{ subject }}</router-link
      >
    </th>
    <td>{{ hit }}</td>
    <td>{{ username }}</td>
    <td>{{ regtime }}</td>
  </tr> -->
  <tr>
    <td class="text-center">{{ articleno }}</td>
    <th class="text-left">
      <router-link
        :to="{ name: 'BoardView', params: { articleno: articleno } }"
        >{{ subject }}</router-link
      >
    </th>
    <td>{{ hit }}</td>
    <td>{{ username }}</td>
    <td>{{ regtime }}</td>
  </tr>
</template>

<script>
// import moment from "moment";
import http from "@/util/http-common.js";

export default {
  name: "BoardListRow",
  props: {
    articleno: Number,
    userid: Number,
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
