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
        class="float-md-left"
        style="z-index: 70; margin: 20px"
        flat
        floating
        width="200px"
      >
        <v-card-title> 매물 검색 </v-card-title>
        <v-card-text>
          지명/시/군/구/동/건물명을 검색하고, 아파트 매물을 확인하세요.
        </v-card-text>
        <v-text-field
          v-on:input="entireSearchArea"
          @input="entireSearchArea"
          v-model="entireKeyword"
          label="통합검색"
          clearable
        ></v-text-field>

        <v-divider></v-divider>
        <v-expand-transition dense>
          <v-list>
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
        class="float-md-right scroll"
        max-width="400"
        max-height="90%"
        style="z-index: 70; margin: 20px"
        flat
        floating
      >
        <v-card-title>
          <v-btn text class="mx-2" small @click="CloseDetail">
            <v-icon> mdi-arrow-left-thin </v-icon> </v-btn
          >{{ this.danji.name }}
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
          <h6>평가</h6>
          <p v-text="this.danji.desc"></p>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <h6>실거래가</h6>
          {{ this.real_sale }}
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

      /*지도에 마커 띄우는 친구들*/
      apartRequestUrl: null,
      items: [],
      positions: [], //API 결과 저장할 배열
      markers: [],
      overlays: [],

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
        console.log("levelChange");
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
      if (this.maplevel <= 5) {
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
      if (this.maplevel <= 5) {
        /* 지도에 세부매물 찍을 때 사용하는 부분 */
        this.apartRequestUrl = `https://apis.zigbang.com/property/apartments/location/v3?e=&geohash=${this.geohash}`; //뒤에 geohash 붙여서 사용
        await axios.get(this.apartRequestUrl).then(({ data }) => {
          console.log("일일이매물List");
          this.positions = data.filtered;
          console.log(data);
        });
        await this.getPropertyMap(); //데이터로 마커찍기
      } else if (6 <= this.maplevel && this.maplevel <= 6) {
        this.apartRequestUrl = `https://apis.zigbang.com/v2/local/price?geohash=${this.geohash}&local_level=3&period=3&transaction_type_eq=s`;
        await axios.get(this.apartRequestUrl).then(({ data }) => {
          console.log("동별매물List");
          this.positions = data.datas;
          console.log(data);
        });
        await this.getLevel23Map(); //데이터로 마커찍기
      } else {
        this.apartRequestUrl = `https://apis.zigbang.com/v2/local/price?geohash=${this.geohash}&local_level=2&period=3&transaction_type_eq=s`;
        await axios.get(this.apartRequestUrl).then(({ data }) => {
          console.log("구별매물List");
          this.positions = data.datas;
          console.log(data);
        });
        await this.getLevel23Map(); //데이터로 마커찍기
      }
    },
    /*level2랑 level3에서 커스텀 오버레이 찍는 부분*/
    async getLevel23Map() {
      console.log("Lv3짜리 동별로 매물갯수 마커찍는 부분");
      //기존 마커를 널로 비워준다 이거 두 줄 순서대로 같이 가야함
      this.setMarkers(null);
      this.setOverlays(null);
      this.markers = [];
      this.overlays = [];

      this.positions.forEach((pos) => {
        //마커 아니라 커스텀 오버레이로 생성해야 함
        //가격이 null, 들어간 게 있어서 v-if 걸었는데 안 됨
        var content = `<div class ="label">${pos.name}<p v-if="pos.price.sales.avg != null">${pos.price.sales.avg}</p></div>`;
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

    /*property-> 세부매물 찍을 때 마커 찍는 부분*/
    async getPropertyMap() {
      //기존 마커랑 오버레이를 널로 비워준다 이거 두 줄 순서대로 같이 가야함
      this.setMarkers(null);
      this.setOverlays(null);
      this.markers = [];
      this.overlays = [];

      //새로 마커찍는 부분
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
        kakao.maps.event.addListener(marker, "mouseover", () => {
          infowindow.open(this.map, marker);
        });
        kakao.maps.event.addListener(marker, "mouseout", () => {
          infowindow.close();
        });

        //클릭시 단지세부(실거래가랑 평점), 초등학교, 실거래가 10개를 불러와서 데이터에 세팅. 해당 데이터는 오픈된 상세설명창에 뿌려진다.
        kakao.maps.event.addListener(marker, "click", () => {
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
                      `https://apis.zigbang.com/v2/apartments/real_sale/list/${pos.id}/0?limit=10&offset=0&transactionType=s`
                    )
                    .then(({ data }) => {
                      console.log("10개까지의 실거래가");
                      console.log(data);
                      this.real_sale = data;
                    });
                });
            });
          //console.log(pos.name);
          this.OpenDetail();
        });
        this.markers.push(marker); //만든 마커를 마커 배열에 전부 넣기
      });
      this.setMarkers(this.map);
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
  overflow-y: auto;
}
</style>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");

.container {
  position: relative;
}

.container .card {
  position: relative;
  width: 200px;
  height: 300px;
  background: #232323;
  border-radius: 20px;
  overflow: hidden;
}

.container .card:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #30ac7c;
  clip-path: circle(150px at 80% 20%);
  transition: 0.5s ease-in-out;
}

.container .card:hover:before {
  clip-path: circle(300px at 80% -20%);
}

.container .card:after {
  content: "대원";
  position: absolute;
  top: 30%;
  left: -20%;
  font-size: 12em;
  font-weight: 800;
  font-style: italic;
  color: rgba(255, 255, 25, 0.05);
}

.container .card .imgBx {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 10000;
  width: 100%;
  height: 220px;
  transition: 0.5s;
}

.card:hover .container {
  top: 0%;
  transform: translateY(0%);
}
.card:hover .imgBx {
  top: 5%;
  transform: translateY(0%);
  color: #fff;
}

.container .card .imgBx img {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(-25deg);
  width: 270px;
}

.container .card .contentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 100px;
  text-align: center;
  transition: 1s;
  z-index: 10;
}

/* hover하면 얼마나 올라가는지 */
.container .card:hover .contentBx {
  height: 180px;
}

.container .card .contentBx h2 {
  position: relative;
  font-weight: 600;
  letter-spacing: 1px;
  color: #fff;
  margin: 0;
}

.container .card .contentBx .size,
.container .card .contentBx .color {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 20px;
  transition: 0.5s;
  opacity: 0;
  visibility: hidden;
  padding-top: 0;
  padding-bottom: 0;
}

.container .card:hover .contentBx .size {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.5s;
}

.container .card:hover .contentBx .color {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.6s;
}

.container .card .contentBx .size h3,
.container .card .contentBx .color h3 {
  color: #fff;
  font-weight: 300;
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 2px;
  margin-right: 10px;
}

.container .card .contentBx .size span {
  width: 26px;
  height: 26px;
  text-align: center;
  line-height: 26px;
  font-size: 14px;
  display: inline-block;
  color: #111;
  background: #fff;
  margin: 0 5px;
  transition: 0.5s;
  color: #111;
  cursor: pointer;
}

.container .card .contentBx .size span:hover {
  background: #30ac7c;
}

.container .card .contentBx .color span {
  width: 20px;
  height: 20px;
  background: #30ac7c;
  border-radius: 50%;
  margin: 0 5px;
  cursor: pointer;
}

.container .card .contentBx a {
  display: inline-block;
  padding: 10px 20px;
  background: #fff;
  margin-top: 10px;
  text-decoration: none;
  font-weight: 600;
  color: #111;
  opacity: 0;
  transform: translateY(50px);
  transition: 0.5s;
  margin-top: 0;
}

.container .card:hover .contentBx a {
  opacity: 1;
  transform: translateY(0px);
  transition-delay: 0.75s;
}
</style>
