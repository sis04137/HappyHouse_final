<template>
  <div class="bv-example-row mt-3 text-center">
    <!-- <h3 class="underline-steelblue"> -->
    <h3>
      <v-icon color="#819FF7">mdi-home</v-icon
      ><strong>집, 구해?</strong>(집,GOOHYE?)
    </h3>
    <b-carousel
      class="carousel"
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- Text slides with image -->
      <!-- Slides with custom text -->
      <!-- 이미지 무엇을 넣을까요오 -->
      <!-- <b-carousel-slide img-src="~/assets/gamsil.jpg"> -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=1048">
        <!-- <b-img img-src="https://picsum.photos/id/1/200/300"></b-img> -->
        <!-- <b-img :src="require(`@/assets/gamsil.jpg`)"></b-img> -->
        <h1>집,구해?(집,GOOHYE?)</h1>
        <br /><br /><br /><br />
      </b-carousel-slide>

      <b-carousel-slide
        caption="Happy House"
        text="아파트 상세 정보를 볼 수 있습니다."
        img-src="https://picsum.photos/1024/480/?image=1031"
      ></b-carousel-slide>
      <!-- img-src="https://picsum.photos/1024/480/?image=52" -->

      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <!-- 이미지 무엇을 넣을까요오 -->
      <b-carousel-slide
        caption="행복한 당신의 집 찾아가세요."
        img-src="https://picsum.photos/1024/480/?image=1068"
      >
        <p>
          아파트 정보 페이지를 통해 아파트들의 실거래가를 알 수 있으며, <br />
          게시판을 통하여 QnA 또한 가능합니다.
        </p>
      </b-carousel-slide>

      <b-carousel-slide
        caption="made by 정구아"
        img-src="https://picsum.photos/1024/480/?image=1065"
      >
        <!-- <img :src="require('../assets/gp.jpg')" />
        <b-img :src="require('../assets/gamsil.jpg')"></b-img> -->
        <p>SSAFY 6기 열정 핫식스!! <br /></p>
      </b-carousel-slide>

      <b-carousel-slide
        caption="made by 김혜란"
        img-src="https://picsum.photos/1024/480/?image=1067"
      >
        <!-- <b-img :src="require(`@/assets/hp.jpg`)"></b-img> -->
        <p>서울 12반 2조 입니다!! <br /></p>
      </b-carousel-slide>
    </b-carousel>

    <!-- 지도 자리 -->
    <div class="mapDiv">
      <div class="bigClass">
        <h3>근처에 얼마나 많은 매물들이 있을까요?</h3>
        <h5>Apart Info 메뉴에서 자세한 위치를 알아보세요!</h5>
        <br />
        <b-button pill size="lg" variant="outline-secondary" @click="house()"
          >ApartInfo로</b-button
        >
      </div>
    </div>

    <br />
    <!-- 추천 매물 넣을 자리: 표형식? 카드 형식? -->
    <v-container style="">
      <h5 class="text-left">추천매물</h5>
      <v-simple-table fixed-header height="300px">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">추천 매물</th>
              <th class="text-left">가격</th>
            </tr>
          </thead>
          <tbody class="text-left">
            <tr>
              <td>(서초구)삼환바우스</td>
              <td>매매가 평균: 139,000</td>
            </tr>
            <tr>
              <td>(서초구)서초극동스타클래스2</td>
              <td>매매가 평균: 165,000</td>
            </tr>
            <tr>
              <td>(서초구)강남역우정에쉐르</td>
              <td>매매가 평균: 20,000</td>
            </tr>
            <tr>
              <td>(서초구)서초푸르지오써밋</td>
              <td>매매가 평균: 288,000</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-container>

    <v-divider></v-divider>

    <!-- 공지사항 | 구독 서비스 넣을 자리-->
    <!-- 공지사항 -->
    <v-container>
      <v-row>
        <v-col>
          <v-layout row wrap>
            <v-flex>
              <v-card width="550px">
                <v-expansion-panels width="550px">
                  <h5>공지사항</h5>
                  <v-expansion-panel :key="i">
                    <v-expansion-panel-header>
                      반갑습니다.
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      '집,구해?'에 오신걸 환영합니다. 저희 사이트에서는 지도를
                      통해서 해당 지역의 매물을 알려드립니다. 또한 주변의 학교
                      정보 또한 함께 제공하여 자녀분의 통학 또한 함께 고려할 수
                      있도록 하고 있습니다.
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                  <v-expansion-panel :key="i">
                    <v-expansion-panel-header>
                      House 사용 안내
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      House 메뉴에서는 아파트 매물 정보를 제공합니다. 매물
                      검색을 통해 원하는 지역, 건물을 바로 확인할 수 있으며,
                      해당 매물을 클릭시 상세 정보가 함께 제공됩니다.
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                  <v-expansion-panel :key="i">
                    <v-expansion-panel-header>
                      QnA 사용 안내
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      Board 메뉴에서는 QnA를 통하여 질의응답을 제공하고
                      있습니다. 홈페이지 사용 중 문의사항이 있으시다면 글을
                      등록해주세요. 최대 하루 이내에 답변 드리겠습니다.
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                  <v-expansion-panel :key="i">
                    <v-expansion-panel-header>
                      홈페이지 사용 안내
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      저희 사이트는 회원가입시 사용자 정보를 통해 적절한 메뉴를
                      제공하고 있습니다. 저희 사이트의 회원이 되어 많은 서비스를
                      이용해보세요.
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                  <!-- <v-expansion-panel :key="i">
                    <v-expansion-panel-header> Item </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      Lorem ipsum dolor sit amet, co
                    </v-expansion-panel-content>
                  </v-expansion-panel> -->
                </v-expansion-panels>
              </v-card>
            </v-flex>
          </v-layout>
        </v-col>

        <v-spacer></v-spacer>
        <!-- 구독 서비스 -->
        <!-- <v-layout>
      <v-flex xs12 sm6> -->

        <v-col>
          <!-- <v-layout row wrap> -->
          <!-- <v-flex> -->
          <v-layout row wrap>
            <v-flex>
              <v-card width="550px">
                <v-expansion-panels>
                  <h5>구독 서비스</h5>
                  <v-img
                    src="https://picsum.photos/1024/480/?image=1073"
                    height="100px"
                  >
                    <!-- src="https://picsum.photos/1024/480/?image=1029" -->
                    <!-- src="https://picsum.photos/1024/480/?image=119" -->
                    <!-- width="550px" -->
                  </v-img>

                  <v-card-title primary-title>
                    <div>
                      <div class="headline">지금, 저희 사이트를 구독하세요</div>
                      <span class="grey--text"
                        >관심 매물 이슈를 보내드립니다.</span
                      >
                    </div>
                  </v-card-title>
                  <!-- <v-spacer></v-spacer> -->
                  <v-card-actions>
                    <v-row justify="center">
                      <v-dialog v-model="dialog" persistent max-width="600px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn color="#30ac7c" dark v-bind="attrs" v-on="on">
                            구독 신청하기
                          </v-btn>
                        </template>
                        <v-card>
                          <v-card-title>
                            <span class="text-h5">신청서 작성</span>
                          </v-card-title>
                          <v-card-text>
                            <v-container>
                              <v-row>
                                <v-col cols="12" sm="6" md="4">
                                  <v-text-field
                                    label="성*"
                                    required
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                  <v-text-field
                                    label="이름"
                                    hint="example of helper text only on focus"
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                  <v-text-field
                                    label="Email*"
                                    required
                                  ></v-text-field>
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
                                    :items="[
                                      '학군',
                                      '교통',
                                      '산뷰',
                                      '강뷰',
                                      '쇼핑',
                                      '직장',
                                    ]"
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
                            <v-btn
                              color="blue darken-1"
                              text
                              @click="dialog = false"
                            >
                              Close
                            </v-btn>
                            <v-btn
                              color="blue darken-1"
                              text
                              @click="dialog = false"
                            >
                              Save
                            </v-btn>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                    </v-row>
                    <!-- <div class="text-center">
                      <v-dialog v-model="dialog" width="500">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn color="#30ac7c" dark v-bind="attrs" v-on="on">
                            이메일 입력
                          </v-btn>
                        </template>

                        <v-card>
                          <v-card-title class="text-h5 grey lighten-2">
                            소식 받을 이메일 입력
                          </v-card-title>

                          <v-card-text>
                            <v-form @submit="onSubmit" @reset="onReset">
                            </v-form>
                          </v-card-text>

                          <v-divider></v-divider>

                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="primary" text @click="dialog = false">
                              입력하기.
                            </v-btn>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                    </div> -->
                    <!-- <v-btn flat>Share</v-btn>
                    <v-btn flat color="purple">Explore</v-btn>
                    <v-spacer></v-spacer>
                    <v-btn icon @click="show = !show"> -->
                    <!-- <v-icon>{{
                      show ? "keyboard_arrow_down" : "keyboard_arrow_up"
                    }}</v-icon> 
                    </v-btn> -->
                  </v-card-actions>

                  <!-- <v-slide-y-transition>
                    <v-card-text v-show="show"> I'm a thing. </v-card-text>
                  </v-slide-y-transition> -->
                </v-expansion-panels>
              </v-card>
            </v-flex>
          </v-layout>
        </v-col>
      </v-row>
    </v-container>
    <!-- </v-flex>
    </v-layout> -->
    <footer-bar />
  </div>
</template>

<script>
import FooterBar from "@/components/layout/FooterBar.vue";

export default {
  name: "Main",
  props: {
    msg: String,
  },
  components: {
    FooterBar,
  },
  data() {
    return {
      slide: 0,
      sliding: null,
      dialog: false,
    };
  },

  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
    house() {
      this.$router.push({ name: "House" });
    },
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}

/* .bigClass {
  padding: 5%;
}
  */
/* div {
  padding: 3%;
} */
.carousel {
  max-width: 1500px;
  margin: auto;
}
</style>
