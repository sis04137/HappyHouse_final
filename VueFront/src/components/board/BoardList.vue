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
      v-model="curpage"
      :length="totalPage"
      @input="onPageChange"
    ></v-pagination>
    <!-- :per-page="perPage" -->
    <!-- :total-rows="rows" -->
    <!-- <v-pagination
      color="#A9E2F3"
      v-model="patination.curpage"
      :length="patination.totalPage"
    ></v-pagination> -->
    <!-- @input="onPageChange" -->
    <!-- :per-page="perPage" -->
    <!-- :total-rows="rows" -->
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
      // patination: {
      //   // rows: 10,
      //   curpage: 1, //
      //   // perPage: 10, //
      //   totalPage: 0, //..
      // },
      // rows: 10,
      curpage: 1, //현재 페이지
      totalPage: 10, //..
      // // perPage: 10, // 한페이지당 몇개
    };
  },
  created() {
    http.get(`/board`).then(({ data }) => {
      this.articles = data;
      console.log("기존: " + this.articles);
    });

    http.get(`/board/list/${this.curpage}`).then(({ data }) => {
      console.log(data.list);
      this.articles = data.list; //글 목록 가져와서 data.list에 담고
      console.log(this.articles);
      this.totalPage = data.totalPage; //total페이지에 값 담고
      console.log("총페이지:  " + this.totalPage); //총 몇페이지가 떠야하는지
    });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
    search() {
      console.log("keyword" + this.keyword);

      if (this.keyword == "") {
        http.get(`/board/list/${this.totalPage}`).then(({ data }) => {
          this.articles = data.list;
          this.totalPage = data.totalPage;
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
    getPage() {
      http.get("/board/list/" + this.curpage).then(({ data }) => {
        //list 뒤에 바로 page 값이 붙어야
        this.articles = data.list;
        // console.log(this.articles);
        // this.patination.curpage = data.current_page;
        this.totalPage = data.totalPage; //데이터변수 이름 맞추기.
      });
    },
    onPageChange() {
      // alert("넘어감" + this.curpage);
      this.getPage();
    },
  },
  // mounted() {
  //   this.getPage();
  // },
  // computed:{
  //   startPage() {
  //     return((this.curpage - 1)*this.perPage);
  //   },
  //   endPage(){
  //     return(this.startPage + this.perPage);
  //   },
  //   totalPage() {
  //     return Math.ceil(this,this.articles.length/this.perPage);
  //   },
  //   calPage(){
  //     return this.articles.slice(this.startPage, this.endPage);
  //   },
  // }
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
