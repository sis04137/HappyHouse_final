<template>
  <section class="section-container">
    <v-row class="signin" style="width: 100%; height: 100%; top: 5vh">
      <v-col cols="2" class="right">
        <v-navigation-drawer permanent dense right style="top: 10vh" flat>
          <!-- <template v-slot:prepend>
            <v-list-item>
              <v-list-item-avatar>
                <img :src="user.picture" />
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title style="left: 10%"
                  ><h4>{{ user.name }}</h4></v-list-item-title
                >
                <v-list-item-subtitle>Logged In</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </template> -->

          <v-list dense>
            <v-list-item link :to="{ name: 'MyPageMain' }">
              <v-list-item-icon>
                <v-icon>mdi-heart</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title><h5>관심매물</h5></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item link :to="{ name: 'MyPageEntre' }">
              <v-list-item-icon>
                <v-icon>mdi-key</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title><h5>회원정보변경</h5></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
      </v-col>
      <v-col cols="10" class="left" align-content="center">
        <v-simple-table style="width: 80%; min-height: 40%">
          <template v-slot:default>
            <thead>
              <tr>
                <th><h5>이름</h5></th>
                <th><h5>주소</h5></th>
                <th><h5>세대수</h5></th>
                <th><h5>현재 매물 수</h5></th>
                <th><h5>찜한 수</h5></th>
                <th><h5></h5></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in fav" :key="index">
                <td>{{ item.name }}</td>
                <td>{{ item.jibunAddress }}</td>
                <td>{{ item.총세대수 }}</td>
                <td>현재매물수 안 나와..?</td>
                <td>sth</td>
                <td>
                  <toggle-favorite
                    favorited="true"
                    :apt_id="item.id"
                  ></toggle-favorite>
                </td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>

        <v-row justify="center">
          <v-card>
            <v-card-title>
              <span class="text-h5">신청서 작성</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field label="성*" required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      label="이름"
                      hint="example of helper text only on focus"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field label="Email*" required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6">
                    <v-select
                      :items="['0-17', '18-29', '30-54', '54+']"
                      label="연령대*"
                      required
                    ></v-select>
                  </v-col>
                  <v-col cols="12" sm="6">
                    <v-autocomplete
                      :items="['학군', '교통', '산뷰', '강뷰', '쇼핑', '직장']"
                      label="관심사항"
                      multiple
                    ></v-autocomplete>
                  </v-col>
                </v-row>
              </v-container>
              <small>* 표시는 필수 입니다.</small>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="dialog = false">
                Close
              </v-btn>
              <v-btn color="blue darken-1" text @click="dialog = false">
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-row>
      </v-col>
    </v-row>
  </section>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState } from "vuex";
import axios from "axios";
import ToggleFavorite from "@/components/house/favorite/ToggleFavorite.vue";
export default {
  name: "MemberJoin",
  components: {
    ToggleFavorite,
  },
  computed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      isLoginError: false,
      showPass: false,
      password: "",
      rawfav: [],
      fav: [],
    };
  },
  created() {
    //fav 목록 가져와서
    http.get(`/fav/${this.user.id}`).then(({ data }) => {
      this.rawfav = data;
      this.rawfav.forEach((element) => {
        var apt_id = element.apt_id; //해당 fav의 apt_id로 직방 요청 날리기
        axios
          .get(`https://apis.zigbang.com/v2/danjis/${apt_id}`)
          .then(({ data }) => {
            console.log(data);
            this.fav.push(data);
          });
      });
    });
  },
  methods: {
    movePage() {
      // save api 날리기
      http.post(`/api/member/save`, this.user).then(() => {
        alert(`${this.user.email}로 로그인해주세요`);
        this.$router.push({ name: "SignIn" });
      });
    },
    confirm() {
      if (this.user.password == this.password) {
        this.$router.push({ name: "MyPage" });
      } else {
        alert("회원 정보 변경을 위해서는 비밀번호가 일치해야 합니다.");
      }
    },
    findPass() {
      http.post(`/api/member/findpass`, this.user.email).then(() => {
        alert(
          `가입하신 이메일로 임시 비밀번호가 발송되었습니다. 임시 비밀번호로 인증하신 후 마이페이지에 접속하셔서 비밀번호를 변경해주세요.`
        );
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.section-container {
  background: #fff;
  width: 100%;
  box-shadow: 0 0 1px 1px rgba($color: #000000, $alpha: 0.1);
  box-sizing: border-box;
  .signin {
    padding: 0;
    min-width: 100%;
    min-height: 1000px;
    box-shadow: 0 0 1px 1px rgba($color: #000000, $alpha: 0.1);
  }
}
</style>
