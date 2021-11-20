<template>
  <v-container class="text-center">
    <!-- <v-container class="bv-example-row mt-3"> -->
    <v-row>
      <!-- <col> -->
      <div class="float-md-left">
        <alert show><h3>QnA</h3></alert>
      </div>
      <!-- </col> -->
    </v-row>
    <v-row class="mb-1">
      <!-- <col class="text-left"> -->
      <div class="float-md-left">
        <!-- <v-form-input
          v-model="keyword"
          v-on:input="search"
          placeholder="Search Sth"
        ></v-form-input> -->
        <v-text-field
          v-model="keyword"
          v-on:input="search"
          label="Search Sth"
          class="mx-4"
        ></v-text-field>
      </div>
      <!-- </col> -->
      <!-- <col class="text-right"> -->
      <div class="float-md-right">
        <v-btn
          color="primary"
          dark
          class="mb-2"
          variant="outline-primary"
          @click="moveWrite()"
        >
          글쓰기
        </v-btn>
      </div>
      <!-- </col> -->
    </v-row>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr class="text-center">
            <th>글번호</th>
            <th class="text-left">제목</th>
            <th>조회수</th>
            <th>작성자</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <board-list-row
            v-for="(article, index) in articles"
            :key="index"
            v-bind="article"
          />
        </tbody>
      </template>
    </v-simple-table>

    <v-pagination
      v-model="page"
      :total-rows="rows"
      :length="1"
      :per-page="perPage"
    ></v-pagination>
    <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
  </v-container>
</template>

<script>
import http from "@/util/http-common";
import BoardListRow from "@/components/board/child/BoardListRow";

export default {
  name: "BoardList",
  components: {
    BoardListRow,
    // rows() {
    //   return this.items.length;
    // },
  },

  data() {
    return {
      articles: [],
      keyword: "",
      rows: 10,
      page: 1,
      perPage: 2,
    };
  },
  created() {
    http.get(`/board`).then(({ data }) => {
      this.articles = data;
    });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
    search() {
      console.log("keyword" + this.keyword);
      var url = "";
      if (this.keyword == "") {
        url = "board";
      } else {
        url = `/board/search/${this.keyword}`;
      }
      http
        .get(url)
        .then(({ data }) => {
          this.articles = data;
        })
        .catch(() => {});
    },
  },
};
// <style lang="scss">
// @import "../node_modules/vue-good-table/src/styles/style.scss";
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
