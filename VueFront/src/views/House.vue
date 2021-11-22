<template>
  <div
    class="text-center fill-width fill-height"
    style="margin: 0px; height: 100%"
  >
    <div id="map" style="z-index: 1">
      <!-- <div class="container" style="z-index: 103">
        <div class="card">
          <div class="imgBx">
            <h4><strong>name + real_type</strong></h4>
            <h6>주소 어쩌고 저쩌고</h6>
          </div>
          <div class="contentBx">
            <h5>price.sales.avg</h5>
            <div class="size">
              <h3></h3>
            </div>
            <div class="size">
              <h3>평점 : score</h3>
            </div>
            <div class="color">
              <h3>households : itemCnt</h3>
            </div>
            <div class="color">
              <h3>price.sales.min ~ price.sales.max</h3>
            </div>
          </div>
        </div>
      </div> -->
      <v-card
        class="float-md-left mapModal"
        style="z-index: 70; margin: 20px"
        flat
        floating
        width="200px"
      >
        <v-card-title> 매물 검색 </v-card-title>
        <v-card-text>
          지명/시/군/구/동/건물명으로 확인해보세요
          <i class="fas fa-child"></i>
          ><v-icon> mdi-subway-variant</v-icon>
          <v-icon>mdi-bell-outline</v-icon>
          <v-icon>mdi-bell-off-outline</v-icon>
        </v-card-text>
        <v-text-field
          v-on:input="entireSearchArea"
          @input="entireSearchArea"
          v-model="entireKeyword"
          label="통합검색"
          clearable
          style="margin-left: 5%; margin-right: 5%"
        ></v-text-field>

        <v-divider></v-divider>
        <v-expand-transition dense>
          <v-list class="overflow-y-auto" max-height="600">
            <v-list-item
              v-for="item in items"
              :key="item.id"
              @click="
                moveToPosition(
                  item.id,
                  item.lat,
                  item.lng,
                  item.zoom_level_v2.web
                )
              "
            >
              <v-list-item-content>
                <v-list-item-title>{{ item.name }}</v-list-item-title>
                <v-list-item-subtitle>
                  {{ item._source.신주소 }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-expand-transition>
      </v-card>

      <!-- 왼쪽 상세사항 창 -->
      <v-card
        v-if="showDetail"
        class="float-md-right scroll mapModal"
        max-width="400"
        max-height="90%"
        style="z-index: 70; margin: 20px"
        flat
        floating
      >
        <v-card-title>
          <v-btn text class="mx-2" small @click="CloseDetail">
            <v-icon> mdi-arrow-left-thin </v-icon>
          </v-btn>
          <v-spacer></v-spacer>
          {{ this.danji.name }}
          <v-spacer></v-spacer>
          <v-icon> mdi-pin </v-icon>
        </v-card-title>
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
          <v-tooltip v-model="show" top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn class="no-text-transform" text v-bind="attrs" v-on="on">
                <i class="far fa-question-circle"></i>
              </v-btn>
              <!-- <v-btn icon v-bind="attrs" v-on="on">
                <v-icon small> mdi-help-circle-outline </v-icon>
              </v-btn> -->
            </template>
            계약일과 매매 실거래가, 층수를 확인 가능합니다. 룸 타입은 정부제공
            룸 타입을 지원합니다.
          </v-tooltip>
          <h6>실거래가</h6>
          <detail-deal-chart :real_sale="real_sale"></detail-deal-chart>
          <v-expansion-panels accordion flat>
            <v-expansion-panel>
              <v-expansion-panel-header
                >자세한 실거래가 보기</v-expansion-panel-header
              >
              <v-expansion-panel-content>
                <v-simple-table>
                  <template v-slot:default>
                    <thead>
                      <tr>
                        <th>계약일</th>
                        <th>가격</th>
                        <th>타입</th>
                        <th>층수</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(item, index) in real_sale" :key="index">
                        <td>{{ item.rtDealDate }}</td>
                        <td>{{ item.rtPrice }}{{ item.rtDealType }}</td>
                        <td>{{ item.roomTypeId }}</td>
                        <td>{{ item.rtFloor }}</td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <h6>평가</h6>
          <p v-text="this.danji.desc"></p>
        </v-card-text>

        <v-divider></v-divider>
        <v-card-text>
          <h6>학군</h6>
          {{ this.schools }}
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn text> 더 많은 정보 보기 </v-btn>
        </v-card-actions>
      </v-card>
    </div>

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
        v-model="entireKeyword"
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
import DetailDealChart from "@/components/house/DetailDealChart.vue";

export default {
  name: "House",
  components: {
    // LeftDetail,
    DetailDealChart,
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

      /*지도에 마커 띄우는 친구들*/
      apartRequestUrl: null,
      items: [],
      positions: [], //API 결과 저장할 배열
      markers: [],
      overlays: [],

      detailOverlay: new kakao.maps.CustomOverlay({
        position: null,
        content: "",
        xAnchor: 0.3,
        yAnchor: 0.91,
      }),

      /*상세페이지 */
      showDetail: false,
      detailDrawer: null,
      detailMenu: [
        { icon: "mdi-currency-usd" },
        { icon: "mdi-account-voice" },
        { icon: "mdi-town-hall" },
        { icon: "mdi-account-group" },
      ],
      danji_id: 0,
      danji: {},
      danjischool: {},
      real_sale: {}, //실거래가
      // details: "", //총평
      // details2: "", //최근리뷰
      schools: [], //학군초등학교
      schools2: [], //학군중학교

      /*search form 두개랑 bind */
      message0: "",
      entireKeyword: "",
      min: 0,
      max: 200,
      range: [0, 300],
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
  },
  methods: {
    /*for map */
    async initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.5013068, 127.0396597), //역삼 멀티캠퍼스
        level: 3,
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
        this.setMapInfo();
      });

      this.setMapInfo();
    },
    //Map info 초기화하는 걸 initMap() 안에서 하면 이상하게 에러뜸
    async setMapInfo() {
      //얘를 기반으로 geohash 계산해서 api로 날려야 매물리스트 불러옴

      console.log("Init MapInfo");
      this.maplevel = this.map.getLevel();
      this.center = this.map.getCenter();
      this.bounds = this.map.getBounds();
      this.geolevel = 5;

      /*Get GeoHash*/
      /*geohash 구해오는 친구 */
      var getGeohashUrl = `http://geohash.world/v1/encode/${this.center.getLat()},${this.center.getLng()}?pre=`;
      if (this.maplevel < 5) {
        this.geolevel = 5;
        getGeohashUrl += this.geolevel;
      } else {
        this.geolevel = 4;
        getGeohashUrl += this.geolevel;
      }
      await axios.get(getGeohashUrl).then(({ data }) => {
        this.geohash = data.geohash;
        console.log(this.geohash);
      });

      //주변 아파트 리스트를 불러올 때 쓰는 함수. geolevel에 따라서 달라진다.
      if (this.maplevel < 5) {
        this.setMarkers(null);
        this.setOverlays(null);
        this.markers = [];
        this.overlays = [];
        /* 지도에 세부매물 찍을 때 사용하는 부분 */
        this.apartRequestUrl = `https://apis.zigbang.com/property/apartments/location/v3?e=&geohash=${this.geohash}`; //뒤에 geohash 붙여서 사용
        await axios.get(this.apartRequestUrl).then(({ data }) => {
          this.positions = data.filtered;
        });
        await this.getPropertyMap(); //데이터로 세부매물 마커찍기
        // await this.getSchoolMap(); //setMarkers로 합류됨
        this.setMarkers(this.map);
      } else if (5 <= this.maplevel && this.maplevel <= 6) {
        this.apartRequestUrl = `https://apis.zigbang.com/v2/local/price?geohash=${this.geohash}&local_level=3&period=3&transaction_type_eq=s`;
        await axios.get(this.apartRequestUrl).then(({ data }) => {
          this.positions = data.datas;
        });
        await this.getLevel23Map(); //데이터로 마커찍기
      } else if (7 <= this.maplevel && this.maplevel <= 8) {
        this.apartRequestUrl = `https://apis.zigbang.com/v2/local/price?geohash=${this.geohash}&local_level=2&period=3&transaction_type_eq=s`;
        await axios.get(this.apartRequestUrl).then(({ data }) => {
          this.positions = data.datas;
        });
        await this.getLevel23Map(); //데이터로 마커찍기
      } else {
        this.apartRequestUrl = `https://apis.zigbang.com/v2/local/price?geohash=${this.geohash}&local_level=1&period=3&transaction_type_eq=s`;
        await axios.get(this.apartRequestUrl).then(({ data }) => {
          this.positions = data.datas;
        });
        await this.getLevel23Map(); //데이터로 마커찍기
      }
    },
    /*level2랑 level3에서 커스텀 오버레이 찍는 부분*/
    async getLevel23Map() {
      //기존 마커를 널로 비워준다 이거 두 줄 순서대로 같이 가야함
      this.setMarkers(null);
      this.setOverlays(null);
      this.markers = [];
      this.overlays = [];

      this.positions.forEach((pos) => {
        //마커 아니라 커스텀 오버레이로 생성해야 함
        //가격이 null, 들어간 게 있어서 v-if 걸었는데 안 됨
        var content = `<v-card dark id="donggucard">${pos.name}<br/>${pos.price.sales.avg}</v-card>`;
        var latlng = new kakao.maps.LatLng(pos.lat, pos.lng);
        // 커스텀 오버레이를 생성합니다
        var customOverlay = new kakao.maps.CustomOverlay({
          position: latlng,
          content: content,
        });
        this.overlays.push(customOverlay);
        // customOverlay.setMap(this.map);
      });
      this.setOverlays(this.map);
    },

    /*property-> 세부매물 찍을 때 마커 찍는 부분, 학교도 같이 찍음*/
    async getPropertyMap() {
      //기존 마커랑 오버레이를 널로 비워준다 이거 두 줄 순서대로 같이 가야함

      //새로 마커찍는 부분
      var imageSize = new kakao.maps.Size(24, 35);
      var markerImage = new kakao.maps.MarkerImage(
        require("@/assets/map/apart_marker.png"),
        imageSize
      );
      // "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      this.positions.forEach((pos) => {
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
                    <div class="close"" title="닫기"></div>
              </div>
             <div class="body">
                        <div class="desc">
                            <div class="jibun ellipsis"> 매매가 평균 ${pos.price.sales.avg} </div>
                        </div>
                  </div>
                </div>
            </div>`;
        var iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // var iwContent = `<div class="container" style="z-index: 103">
        //   <div class="card">
        //     <div class="imgBx">
        //       <h4><strong>${pos.name}</br>${pos.real_type}</strong></h4>
        //       <h6>${pos.gugun} ${pos.dong} ${pos.bunji}</h6>
        //     </div>
        //     <div class="contentBx">
        //       <h5>${pos.price.sales.avg}</h5>
        //       <div class="size">
        //         <h3></h3>
        //       </div>
        //       <div class="size">
        //         <h3>평점 : ${pos.score}</h3>
        //       </div>
        //       <div class="color">
        //         <h3>households : ${pos.households}</h3>
        //       </div>
        //       <div class="color">
        //         <h3>${pos.price.sales.min} ~ ${pos.price.sales.max}</h3>
        //       </div>
        //     </div>
        //   </div>
        // </div>`;
        var infowindow = new kakao.maps.InfoWindow({
          content: iwContent, // 인포윈도우에 표시할 내용
          removable: iwRemoveable,
        });

        /* marker event listner */
        kakao.maps.event.addListener(marker, "mouseover", () => {
          infowindow.open(this.map, marker);
        });
        kakao.maps.event.addListener(marker, "mouseout", () => {
          infowindow.close();
          // overlay.setMap(null);
        });

        //클릭시 단지세부(실거래가랑 평점), 초등학교, 실거래가 10개를 불러와서 데이터에 세팅. 해당 데이터는 오픈된 상세설명창에 뿌려진다.
        kakao.maps.event.addListener(marker, "click", () => {
          infowindow.close();
          axios
            .get(`https://apis.zigbang.com/v2/danjis/${pos.id}`)
            .then(({ data }) => {
              this.danji = data;
              axios
                .get(
                  `https://apis.zigbang.com/property/apartments/school/info?apartmentId=${pos.id}`
                )
                .then(({ data }) => {
                  this.schools = data.elementary.list;
                  this.schools2 = data.elementary.etcList;
                  axios
                    .get(
                      `https://apis.zigbang.com/v2/apartments/real_sale/list/${pos.id}/0?limit=50&offset=0&transactionType=s`
                    )
                    .then(({ data }) => {
                      console.log("50개까지의 실거래가");
                      console.log(data);
                      this.real_sale = data.data;
                      this.OpenDetail();
                    });
                });
            });
          //console.log(pos.name);
        });
        this.markers.push(marker); //만든 마커를 마커 배열에 전부 넣기
      });

      //학교
      imageSize = new kakao.maps.Size(24, 35);
      markerImage = new kakao.maps.MarkerImage(
        require("@/assets/map/school_marker.png"),
        imageSize
      );
      await axios
        .get(
          `https://apis.zigbang.com/v2/schools?genderNullable=true&geohash=${this.geohash}`
        )
        .then(({ data }) => {
          var schools = data.schools;
          schools.forEach((school) => {
            var latlng = new kakao.maps.LatLng(school.lat, school.lng);
            var marker = new kakao.maps.Marker({
              map: this.map, // 마커를 표시할 지도
              position: latlng, // 마커를 표시할 위치
              title: school.name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
              image: markerImage, // 마커 이미지
              clickable: true,
            });
            kakao.maps.event.addListener(marker, "click", () => {
              window.open(`${school.homepage}`);
            });
            this.markers.push(marker);
          });
        });
    },
    //마커 세팅하는 함수
    setMarkers(map) {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(map);
      }
    },
    //커스텀 오버레이 세팅하는 함수
    setOverlays(map) {
      for (var i = 0; i < this.overlays.length; i++) {
        this.overlays[i].setMap(map);
      }
    },
    setDetailOverlay(map) {
      this.detailOverlay.setMap(map);
    },
    closeDetailOverlay() {
      this.detailOverlay.setMap(null);
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
        .get(`https://apis.zigbang.com/search?q=${this.entireKeyword}`)
        .then(({ data }) => {
          this.items = data.items;
          console.log(data);
        });
    },
    // 검색 결과 누르면 해당 위치로 이동하고 setMapInfo()
    moveToPosition(key, lat, lng, zoom_level) {
      console.log(key);
      console.log(this.map);
      var moveLatLon = new kakao.maps.LatLng(lat, lng);
      this.map.setLevel(zoom_level);
      this.map.panTo(moveLatLon);
      this.setMapInfo();
    },

    /* 상세페이지 열고 닫는 함수 */
    OpenDetail() {
      if (!this.showDetail) {
        // this.closeDetailOverlay();
        this.showDetail = !this.showDetail;
      }
    },
    CloseDetail() {
      if (this.showDetail) {
        this.showDetail = !this.showDetail;
      }
    },
  },
};
</script>

<style>
.mapModal {
  top: 5%;
  bottom: 5%;
}
#donggucard {
  background-color: #fff;
  color: #30ac7c;
  font-size: 15px;
}
#map {
  width: 100%;
  height: 100%;
}
</style>

<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
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
  overflow-y: auto;
}
</style>
