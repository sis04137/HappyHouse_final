<template>
  <div class="text-center fill-width fill-height" style="margin: 0px">
    <div id="map" style="z-index: 1">
      <v-card
        class="float-md-left"
        style="z-index: 70; margin: 20px"
        flat
        floating
      >
        <v-card-title> 매물 검색 </v-card-title>
        <v-card-text> 지명/시/군/구/동/이름을 검색해보세요. </v-card-text>
        <v-card-text>
          <v-autocomplete
            :search-input.sync="entireSearchArea"
            @input="entireSearchArea"
            v-model="message1"
            label="통합검색"
            auto-select-first
            clearable
          >
          </v-autocomplete>
        </v-card-text>
        <v-divider></v-divider>
        <v-expand-transition>
          <v-list>
            <v-list-item v-for="item in items" :key="item.id">
              <v-list-item-content>
                <v-list-item-title>{{ item.source.신주소 }}</v-list-item-title>
                <v-list-item-subtitle>{{
                  item.description
                }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-expand-transition>
      </v-card>

      <v-btn style="z-index: 90" @click="showDetail = !showDetail"
        >디테일</v-btn
      >
      <v-card
        v-if="showDetail"
        class="float-md-right scroll"
        max-width="400"
        max-height="90%"
        style="z-index: 70; margin: 20px"
        flat
        floating
      >
        <v-card-title> {{ this.danji.name }} </v-card-title>
        <v-card-text>
          <h5>
            {{ this.danji.구주소 }}
          </h5>
          <p>
            {{ this.danji.총세대수 }}세대, 별점 {{ this.danji.review_score }}
          </p>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <h6>평가</h6>
          {{ this.danji.desc }}
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <h6>실거래가</h6>
          {{ this.real_sale }}
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <h6>학군</h6>
          {{ this.danjischool }}
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn text> 더 많은 정보 보기 </v-btn>
        </v-card-actions>
      </v-card>

      <!-- <v-toolbar
        class="float-md-left"
        style="z-index: 70; margin: 10px"
        dense
        floating
      >
        <v-text-field
          hide-details
          prepend-icon="mdi-magnify"
          single-line
        ></v-text-field>
        <v-btn icon>
          <v-icon>mdi-crosshairs-gps</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </v-toolbar> -->
    </div>

    <!-- <div>
      <v-btn color="error" dark large @click="setMapInfo"
        >지도 위치에서 매물 확인하기</v-btn
      >
    </div> -->

    <!-- <v-expansion-panels>
      <v-expansion-panel>
        <v-expansion-panel-header> 총평 </v-expansion-panel-header>
        <v-expansion-panel-content>
          {{ details }}
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header> 매물리뷰 </v-expansion-panel-header>
        <v-expansion-panel-content>
          {{ details2 }}
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header> 학군 </v-expansion-panel-header>
        <v-expansion-panel-content>
          <ul>
            <li v-for="item in schools" :key="item.id">
              {{ item.name }} : {{ item.distance }}
            </li>
          </ul>
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header> 비학군 </v-expansion-panel-header>
        <v-expansion-panel-content>
          <ul>
            <li v-for="item in schools2" :key="item.id">
              {{ item.name }} : {{ item.distance }}
            </li>
          </ul>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels> -->

    <!-- <div>
      <v-text-field
        v-on:input="searchArea"
        @input="searchArea"
        v-model="message0"
        label="아파트검색"
        clearable
      ></v-text-field>
      <v-text-field
        v-on:input="entireSearchArea"
        @input="entireSearchArea"
        v-model="message1"
        label="통합검색"
        clearable
      ></v-text-field>
    </div>
    <v-btn
      v-for="item in items"
      :key="item.id"
      @click="
        moveToPosition(item.id, item.lat, item.lng, item.zoom_level_v2.web)
      "
    >
      {{ item.name }} : {{ item.description }} : {{ item._source.신주소 }}
      <v-icon dark right> mdi-checkbox-marked-circle </v-icon>
    </v-btn> -->
  </div>
</template>
<script>
import axios from "axios";
// import LeftDetail from "@/components/house/child/LeftDetail.vue";

export default {
  name: "House",
  components: {
    // LeftDetail,
  },
  data() {
    return {
      /*for map*/
      map: null, //mount되면 여기다가 kakaomap 설정해놓고 다른 method들에서 막 호출해 씀
      maplevel: null,
      center: null,
      bounds: null,
      apartlist: null,
      geolevel: null, //몇단계의 geohash를 구할 건지
      geohash: null,

      items: [],
      positions: [],

      /*상세페이지 */
      showDetail: true,
      detailDrawer: null,
      detailMenu: [
        { icon: "mdi-currency-usd" },
        { icon: "mdi-account-voice" },
        { icon: "mdi-town-hall" },
        { icon: "mdi-account-group" },
      ],
      danji_id: 3684,
      danji: {},
      danjischool: {},
      real_sale: {},

      details: "",
      details2: "",
      schools: [],
      schools2: [],

      /*search form 두개랑 bind */
      message0: "",
      message1: "",
    };
  },
  mounted() {
    //load 되기 전에 initMap()해 버리면 commonStyle인가 뭔가 찾을 수 없다고 뜬다
    //window.kakao 어쩌구 변수 쓸라면 index.html에도 script 넣어줘야 함
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      //autoload false로 설정해줘야 뜸 왜 그런지는 모르겠음
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&&appkey=140fc52b610505ff65a221d6b9eabd09";
      document.head.appendChild(script);
    }

    //테스트!!!!!!! 상세페이지에 쓰려고 단지아이디 임의로 박아놓음
    axios
      .get(`https://apis.zigbang.com/v2/danjis/${this.danji_id}`)
      .then(({ data }) => {
        this.danji = data;
      });
    axios
      .get(
        `https://apis.zigbang.com/property/apartments/school/info?apartmentId=${this.danji_id}`
      )
      .then(({ data }) => {
        this.danjischool = data.elementary.list;
      });
    axios
      .get(
        `https://apis.zigbang.com/v2/apartments/real_sale/list/${this.danji_id}/0?limit=10&offset=0&transactionType=s`
      )
      .then(({ data }) => {
        this.real_sale = data;
      });
  },
  methods: {
    showDe() {
      console.log("clicked");
      this.showDetail = !this.showDetail;
    },
    /*for map */
    async initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.5013068, 127.0396597), //역삼 멀티캠퍼스
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);

      /*map event handler*/
      // 영역변경 이벤트 핸들러
      kakao.maps.event.addListener(this.map, "bounds_changed", () => {
        console.log("boundChange");
        //bounds_changed 너무 많이 호출됨 사용자가 멈추면 로드해야 하는데
        /*
          지연시간 주고 지도 설정하기 근데 어케 줌
        */
        // setTimeout(() => console.log('안녕하세요.'), 1000);
        //  this.setMapInfo();
      });

      //줌 레벨변경 이벤트 핸들러
      kakao.maps.event.addListener(this.map, "zoom_changed", () => {
        console.log("levelChange");
        this.setMapInfo();
      });

      this.setMapInfo();
    },
    //Map info 초기화하는 걸 initMap() 안에서 하면 이상하게 에러뜸
    async setMapInfo() {
      //얘를 기반으로 geohash 계산해서 api로 날려야 매물리스트 불러옴

      //test
      this.details = "";
      this.details2 = "";

      console.log("Init MapInfo");
      this.maplevel = this.map.getLevel();
      this.center = this.map.getCenter();
      this.bounds = this.map.getBounds();
      this.geolevel = 5;
      // if(this.maplevel > 5){
      //   this.geolevel = 6;
      // }
      //this.geolevel = this.maplevel;  //임시로 geolevel 이렇게 설정해둠 :  maplevel 높아질 수록 geolevel은 낮아져야 함
      // console.log("남서좌표: " + this.bounds.getSouthWest());
      // console.log("북동좌표: " + this.bounds.getNorthEast());

      /*Get GeoHash*/
      // await this.getGeohash();
      await axios
        .get(
          `http://geohash.world/v1/encode/${this.center.getLat()},${this.center.getLng()}?pre=${
            this.geolevel
          }`
        )
        .then(({ data }) => {
          this.geohash = data.geohash;
          console.log(this.geohash);
        });
      /*

          setGeolevel
          todo

      */
      var requestUrl = `https://apis.zigbang.com/property/apartments/location/v3?e=&geohash=${this.geohash}`;
      if (this.maplevel <= 5) {
        this.geolevel = 5;
      } else {
        this.geolevel = 4;
      }

      /*GetApartList*/
      await axios.get(requestUrl).then(({ data }) => {
        console.log("getApartList");
        this.positions = data.filtered;
        console.log(data);
      });

      //마커찍어야 함
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      this.positions.forEach((pos) => {
        var imageSize = new kakao.maps.Size(24, 35);
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        var latlng = new kakao.maps.LatLng(pos.lat, pos.lng);
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: latlng, // 마커를 표시할 위치
          title: pos.name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
          clickable: true,
        });
        var iwContent = `<div class="wrap">
            <div class="info">
            <div class="title">
            ${pos.name} 
                    <div class="close" onclick="closeOverlay()" title="닫기"></div>
              </div>
             <div class="body">
                        <div class="desc">
                            <div class="ellipsis">${pos.sido}${pos.gugun} ${pos.dong}43-205</div>
                            <div class="jibun ellipsis"> 매매가 평균 ${pos.price.sales.avg} </div>
                        </div>
                  </div>
                </div>
            </div>`,
          iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
        var infowindow = new kakao.maps.InfoWindow({
          content: iwContent, // 인포윈도우에 표시할 내용
          removable: iwRemoveable,
        });

        /* marker event listner */
        kakao.maps.event.addListener(marker, "click", () => {
          axios
            .get(`https://apis.zigbang.com/v2/danjis/${pos.id}`)
            .then(({ data }) => {
              // this.items = data.items;
              // console.log(data);
              this.details = data.desc;
              this.details2 = data.review_recent;
              console.log(this.details);
              console.log(this.details2);

              axios
                .get(
                  `https://apis.zigbang.com/property/apartments/school/info?apartmentId=${pos.id}`
                )
                .then(({ data }) => {
                  // this.items = data.items;
                  // console.log(data);
                  this.schools = data.elementary.list;
                  this.schools2 = data.elementary.etcList;
                });
            });
          // 마커 위에 인포윈도우를 표시합니다
          //console.log(pos.name);

          infowindow.open(this.map, marker);
        });

        // (function (marker, infowindow) {
        //   // 마커에 mouseover 이벤트를 등록하고 마우스 오버 시 인포윈도우를 표시합니다
        //   kakao.maps.event.addListener(marker, "mouseover", function () {
        //     infowindow.open(this.map, marker);
        //   });

        //   // 마커에 mouseout 이벤트를 등록하고 마우스 아웃 시 인포윈도우를 닫습니다
        //   kakao.maps.event.addListener(marker, "mouseout", function () {
        //     infowindow.close();
        //   });
        // })(marker, infowindow);
        marker.setMap(this.map); //이거 해줘야 함
      });
    },
    printLevel() {
      console.log(
        `mapLevel: ${this.map.getLevel()}, geolevel: ${this.geolevel}`
      );
    },
    //아파트검색 v-on
    searchArea() {
      axios
        .get(
          `https://apis.zigbang.com/v2/search?leaseYn=N&q=${this.message0}&serviceType=아파트`
        )
        .then(({ data }) => {
          this.items = data.items;
          console.log(data);
        });
    },
    //통합검색 v-on
    entireSearchArea() {
      console.log("detected");
      axios
        .get(`https://apis.zigbang.com/search?q=${this.message1}`)
        .then(({ data }) => {
          this.items = data.items;
          console.log(data);
        });
    },
    moveToPosition(key, lat, lng, zoom_level) {
      console.log(key);
      var moveLatLon = new kakao.maps.LatLng(lat, lng);
      this.map.setLevel(zoom_level);
      this.map.panTo(moveLatLon);
      this.setMapInfo();
    },
    goApart() {
      this.$router.push({ name: "House" });
    },
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
}

#map {
  width: 100%;
  height: 900px;
}
.wrapper {
  float: left;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 30px;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  color: #888;
  margin-top: -2px;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
}
.info .link {
  color: #5085bb;
}

.scroll {
  overflow-y: scroll;
}
</style>
