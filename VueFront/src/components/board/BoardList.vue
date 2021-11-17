<template>
  <b-container class="bv-example-row mt-3">
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
        <b-table-simple hover responsive>
          <b-thead head-variant="light">
            <b-tr>
              <b-th>글번호</b-th>
              <b-th>제목</b-th>
              <b-th>조회수</b-th>
              <b-th>작성자</b-th>
              <b-th>작성일</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <board-list-row
              v-for="(article, index) in articles"
              :key="index"
              v-bind="article"
            />
          </tbody>
        </b-table-simple>
      </b-col>
    </b-row>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      align="center"
      :per-page="perPage"
      aria-controls="my-table"
    ></b-pagination>
    <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
  </b-container>
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
      perPage: 2,
      currentPage: 1,
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
