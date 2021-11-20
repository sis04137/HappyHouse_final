<template>
  <v-container class="text-center">
    <!-- <v-container class="bv-example-row mt-3"> -->
    <v-row>
      <!-- <v-col>
        <div class="float-md-right">
          <alert show><h3></h3></alert>
        </div>
      </v-col> -->
      <br />
      <br />
    </v-row>
    <v-row class="mb-1">
      <div class="float-md-left">
        <v-text-field
          v-model="keyword"
          v-on:input="search"
          label="Search Sth"
          class="mx-4"
        ></v-text-field>
      </div>
      <!-- </col> -->
      <!-- <col class="text-right"> -->
      <!-- <div class="float-md-right">
        <v-btn right outlined color="#01A9DB" class="mb-2" @click="moveWrite()">
          글쓰기
        </v-btn>
      </div> -->
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
      color="#A9E2F3"
      v-model="page"
      :total-rows="rows"
      :length="1"
      :per-page="perPage"
    ></v-pagination>
    <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
    <div class="float-md-right">
      <v-btn right outlined color="#01A9DB" class="mb-2" @click="moveWrite()">
        글쓰기
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import http from "@/util/http-common";
import BoardListRow from "@/components/board/child/BoardListRow";

export default {
  name: "BoardList",
  components: {
    BoardListRow,
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
    // http.get(`/board`).then(({ data }) => {
    //   this.articles = data;
    //   console.log("기존: " + this.articles);
    // });

    http.get(`/board/list/1`).then(({ data }) => {
      console.log(data.list);
      this.articles = data.list;
      console.log(this.articles);
      this.perPage = data.totalPage;
      console.log("수정페이지:  " + this.perPage);
    });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
    search() {
      console.log("keyword" + this.keyword);

      if (this.keyword == "") {
        http.get(`/board/list/1`).then(({ data }) => {
          // console.log(data.list);
          this.articles = data.list;
          //console.log(data.list);
          this.perPage = data.totalPage;
          //console.log("수정페이지:  " + this.perPage);
        });
      } else {
        http
          .get(`/board/search/${this.keyword}`)
          .then(({ data }) => {
            this.articles = data;
          })
          .catch(() => {});
      }
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
