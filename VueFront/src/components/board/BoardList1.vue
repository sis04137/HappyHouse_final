<template>
  <v-container class="text-center">
    <v-row>
      <!-- <col> -->
      <div class="qna">
        <alert show><h3>QnA</h3></alert>
      </div>
      <!-- </col> -->
    </v-row>
    <v-row class="mb-1">
      <!-- <col class="text-left"> -->
      <div class="text-right">
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
      <!-- <template v-slot:activator="{ on, attrs }"> -->
      <div class="cbtn">
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
      <!-- </template> -->
      <!-- <div class="text-right">
        <v-btn
          class="ma-2"
          outlined
          color="indigo"
          variant="outline-primary"
          @click="moveWrite()"
          >글쓰기</v-btn
        > -->
      <!-- color="normal"
          depressed
          elevation="2" -->
      <!-- <button variant="outline-primary" @click="moveWrite()">글쓰기</button> -->
      <!-- </div> -->
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
          <tr v-for="article in articles" :key="article.name">
            <td class="text-center">{{ article.articleno }}</td>
            <th class="text-left">
              <router-link
                :to="{ name: 'BoardView', params: { articleno: articleno } }"
                >{{ article.subject }}</router-link
              >
            </th>
            <td>{{ article.hit }}</td>
            <td>{{ article.username }}</td>
            <td>{{ article.regtime }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <v-pagination v-model="page" :length="6"></v-pagination>
  </v-container>
</template>

<script>
import http from "@/util/http-common";
// import BoardListRow from "@/components/board/BoardListRow";

export default {
  name: "BoardList",
  // components: {
  //   // BoardListRow,
  // },
  components: {},
  props: {
    articleno: Number,
    userid: String,
    subject: String,
    hit: Number,
    regtime: String,
  },
  // vuetify: new Vuetify(),
  data() {
    return {
      articles: [],
      keyword: "",
      // articles: [
      //   {
      //     articleno: "",
      //     subject: "",
      //     hit: "",
      //     username: "",
      //     regtime: "",
      //   },
      // ],
      username: "",
      id: this.userid,
      rows: 10,
      page: 1,
    };
  },
  created() {
    http.get(`/board`).then(({ data }) => {
      this.articles = data;
    });
    http
      .get(`/api/member/${this.id}`)
      .then(({ data }) => {
        this.username = data.name;
      })
      .catch((error) => console.log(error));
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "BoardView",
        params: { articleno: article.articleno },
      });
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
.qna {
  text-align: center;
}
.cbtn {
  text-align: right;
}
</style>
