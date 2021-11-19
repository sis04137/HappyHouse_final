<template>
  <v-container class="text-center">
    <!-- <v-container class="bv-example-row mt-3"> -->
    <b-row>
      <b-col>
        <b-alert show><h3>QnA</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-form-input
          v-model="keyword"
          v-on:input="search"
          placeholder="Search Sth"
        ></b-form-input>
      </b-col>
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="articles.length">
        <!-- <b-table-simple hover responsive> -->
        <v-data-table hover responsive>
          <thead head-variant="light">
            <tr>
              <th>글번호</th>
              <th>제목</th>
              <th>조회수</th>
              <th>작성자</th>
              <th>작성일</th>
            </tr>
          </thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <board-list-row
              v-for="(article, index) in articles"
              :key="index"
              v-bind="article"
            />
          </tbody>
        </v-data-table>
      </b-col>
    </b-row>
    <v-pagination v-model="page" :length="6"></v-pagination>
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
