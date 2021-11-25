<template>
  <div
    class="text-center fill-width fill-height"
    style="margin: 0px; height: 100%"
  >
    <div id="map" style="z-index: 1; position: absolute">
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
        <v-card-title>
          매물 검색
          <v-tooltip right>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon v-bind="attrs" v-on="on">
                <i class="far fa-question-circle"></i>
              </v-btn>
            </template>
            <span>지명/시/군/구/동/건물명/지하철역으로 검색해보세요</span>
          </v-tooltip></v-card-title
        >
        <v-card-text style="height: 50px">
          <div class="map_wrap">
            <ul id="category">
              <li id="BK9" data-order="0">
                <span class="category_bg bank"></span>
                은행
              </li>
              <li id="MT1" data-order="1">
                <span class="category_bg mart"></span>
                마트
              </li>
              <li id="PM9" data-order="2">
                <span class="category_bg pharmacy"></span>
                약국
              </li>
              <li id="OL7" data-order="3">
                <span class="category_bg oil"></span>
                주유소
              </li>
              <li id="CE7" data-order="4">
                <span class="category_bg cafe"></span>
                카페
              </li>
              <li id="CS2" data-order="5">
                <span class="category_bg store"></span>
                편의점
              </li>
            </ul>
          </div>
          <!-- <i class="fas fa-child"></i>
          <v-icon> mdi-subway-variant</v-icon>
          <v-icon>mdi-bell-outline</v-icon>
          <v-icon>mdi-bell-off-outline</v-icon> -->
          <!-- <slider v-model="range"></slider> -->
          <!-- <v-range-slider max="50" min="0"></v-range-slider> -->
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
    </div>
    <!-- 왼쪽 상세사항 창 -->
    <v-card
      v-if="showDetail"
      class="float-md-right scroll mapModal"
      max-width="400"
      max-height="90%"
      style="z-index: 70; margin: 20px; overflow-y: scroll; max-height: 1200px"
      flat
    >
      <v-card-title>
        <v-btn icon class="mx-2" small @click="CloseDetail">
          <v-icon> mdi-arrow-left-thin </v-icon>
        </v-btn>
        <v-spacer></v-spacer>
        {{ this.danji.name }}
        <v-spacer></v-spacer>
        <toggle-favorite
          :favorited="favorited"
          :apt_id="danji.id"
        ></toggle-favorite>
      </v-card-title>
      <v-card-text>
        <h5>
          {{ this.danji.구주소 }}
        </h5>
        <p>{{ this.danji.총세대수 }}세대, 별점 {{ this.danji.review_score }}</p>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-text>
        <h6>로드뷰</h6>
        <!-- <div id="road" style="width: 100%; height: 200px"></div> -->
        <detail-road-view
          v-if="showChartandRoad"
          :danjiLat="danji.lat"
          :danjiLng="danji.lng"
        ></detail-road-view>
      </v-card-text>

      <v-divider></v-divider>
      <v-card-text>
        <h6>
          실거래가<v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon v-bind="attrs" v-on="on">
                <i class="far fa-question-circle"></i>
              </v-btn>
            </template>
            <span
              >최근 50건에 대한 계약일과 실거래가, 층수를 확인 가능합니다. 룸
              타입은 정부제공 룸 타입을 지원합니다.</span
            >
          </v-tooltip>
        </h6>
        <v-tabs grow>
          <v-tab>매매</v-tab>
          <v-tab>전월세</v-tab>
          <v-tab-item>
            <detail-deal-chart
              :real_sale="real_sale"
              :chart_label="chart_label_sale"
              v-if="showChartandRoad"
            ></detail-deal-chart>
            <detail-deal-table
              v-if="showChartandRoad"
              :real_data="real_sale"
            ></detail-deal-table>
          </v-tab-item>
          <v-tab-item>
            <detail-deal-chart-rent
              v-if="showChartandRoad"
              :real_sale="real_rent"
              :chart_label="chart_label_rent"
            ></detail-deal-chart-rent>
            <detail-deal-table
              v-if="showChartandRoad"
              :real_data="real_rent"
            ></detail-deal-table>
          </v-tab-item>
        </v-tabs>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-text>
        <h6>평가</h6>
        <v-expansion-panels accordion flat>
          <v-expansion-panel>
            <v-expansion-panel-header>총평 보기</v-expansion-panel-header>
            <v-expansion-panel-content>
              <p class="danji" v-text="this.danji.desc"></p>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card-text>

      <v-divider></v-divider>
      <v-card-text>
        <h6>
          학군<v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon v-bind="attrs" v-on="on">
                <i class="far fa-question-circle"></i>
              </v-btn>
            </template>
            <span>해당 아파트의 학군과 주변 학교 정보를 알 수 있습니다.</span>
          </v-tooltip>
        </h6>
        <v-tabs grow>
          <v-tab>초등학교</v-tab>
          <v-tab>중학교</v-tab>
          <v-tab>고등학교</v-tab>
          <v-tab-item>
            <detail-school :schoolList="school_data.elementary"></detail-school>
          </v-tab-item>
          <v-tab-item>
            <detail-school :schoolList="school_data.middle"></detail-school>
          </v-tab-item>
          <v-tab-item>
            <detail-school :schoolList="school_data.high"></detail-school>
          </v-tab-item>
        </v-tabs>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn text> 더 많은 정보 보기 </v-btn>
      </v-card-actions>
    </v-card>

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
import DetailDealChartRent from "@/components/house/DetailDealChartRent.vue";
import DetailDealTable from "@/components/house/DetailDealTable.vue";
import DetailSchool from "@/components/house/DetailSchool.vue";
import ToggleFavorite from "@/components/house/favorite/ToggleFavorite.vue";
import http from "@/util/http-common.js";
import { mapState } from "vuex";
// import Slider from "@vueform/slider/dist/slider.vue2";
import DetailRoadView from "@/components/house/DetailRoadView.vue";

export default {
  name: "House",
  components: {
    // LeftDetail,
    DetailDealChart,
    DetailDealChartRent,
    DetailDealTable,
    DetailSchool,
    ToggleFavorite,
    DetailRoadView,
    // Slider,
  },
  computed: {
    ...mapState(["user"]),
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

      /*categoryMarker */
      placeMarkers: [],
      currCategory: "",
      placeOverlay: null,
      // map: null,
      ps: null,
      contentNode: null,

      roadView: null,
      roadviewClient: null,

      /*지도에 마커 띄우는 친구들*/
      apartRequestUrl: null,
      items: [],
      positions: [], //API 결과 저장할 배열
      markers: [],
      overlays: [],
      latitude: "",
      longitude: "",
      textContent: "",

      detailOverlay: new kakao.maps.CustomOverlay({
        position: null,
        content: "",
        xAnchor: 0.3,
        yAnchor: 0.91,
      }),

      /*상세페이지 */
      showDetail: false,
      showChartandRoad: false,
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
      real_sale: {}, //실거래가 매매
      real_rent: {}, //실거래가 전월세
      chart_label_sale: "매매(만)",
      chart_label_rent: "전월세(만)",
      // details: "", //총평
      // details2: "", //최근리뷰
      schools: [], //학군초등학교
      schools2: [], //학군중학교
      school_data: null,
      favorited: false,

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
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=140fc52b610505ff65a221d6b9eabd09&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    /*for map */
    async initMap() {
      let latlng = new kakao.maps.LatLng(37.5013068, 127.0396597);
      // const container = document.getElementById("map");
      const options = {
        center: latlng, //new kakao.maps.LatLng(37.5013068, 127.0396597), //역삼 멀티캠퍼스
        level: 3,
      };
      this.map = new kakao.maps.Map(document.getElementById("map"), options);

      if (!("geolocation" in navigator)) {
        this.textContent = "Geolocation is not available.";
        return;
      }
      this.textContent = "Locating...";
      // get position
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.latitude = pos.coords.latitude;
          this.longitude = pos.coords.longitude;
          this.textContent =
            "Your location data is " + this.latitude + ", " + this.longitude;
          console.log(this.textContent);
          latlng = new kakao.maps.LatLng(this.latitude, this.longitude);
          this.map.setCenter(latlng);
          this.setMapInfo();
        },
        (err) => {
          this.textContent = err.message;
        }
      );

      var imageSize = new kakao.maps.Size(32, 35);
      var markerImage = new kakao.maps.MarkerImage(
        require("@/assets/map/location.png"),
        imageSize
      );
      //H 37.526139, 126.864492
      // "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      var markerH = new kakao.maps.Marker({
        map: this.map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(37.526139, 126.864492), // 마커를 표시할 위치
        title: "❤개발진❤", // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage, // 마커 이미지
        clickable: true,
      });
      //G 37.5583483, 126.9083209
      markerH.setMap(this.map);
      markerH = new kakao.maps.Marker({
        map: this.map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(37.5583483, 126.9083209), // 마커를 표시할 위치
        title: "❤개발진❤", // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage, // 마커 이미지
        clickable: true,
      });
      markerH.setMap(this.map);

      /*map event handler*/
      // 영역변경 이벤트 핸들러
      kakao.maps.event.addListener(this.map, "bounds_changed", () => {
        // console.log("boundChange");
        //bounds_changed 너무 많이 호출됨 사용자가 멈추면 로드해야 하는데
        /*
          지연시간 주고 지도 설정하기 근데 어케 줌
        */
        // setTimeout(() => console.log('안녕하세요.'), 1000);
        // this.setMapInfo();
      });

      //줌 레벨변경 이벤트 핸들러
      kakao.maps.event.addListener(this.map, "zoom_changed", () => {
        this.setMapInfo();
      });

      /*service */
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
      this.contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
      // var mapContainer = document.getElementById("map"), // 지도를 표시할 div
      //   mapOption = {
      //     center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      //     level: 5, // 지도의 확대 레벨
      //   };
      // this.map = new kakao.maps.Map(mapContainer, mapOption);
      this.ps = new kakao.maps.services.Places(this.map);
      // console.log(this.ps);
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);
      this.contentNode.className = "placeinfo_wrap";
      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(
        this.contentNode,
        "mousedown",
        kakao.maps.event.preventMap
      );
      this.addEventHandle(
        this.contentNode,
        "touchstart",
        kakao.maps.event.preventMap
      );

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      var category = document.getElementById("category"),
        children = category.children;

      for (var i = 0; i < children.length; i++) {
        // children[i].onclick = this.onClickCategory(children[i]);//이래버리니까 바로 FOR문 돌면서 실행됨
        children[i].onclick = this.onClickCategory;
      }

      //원래 있던 애
      this.setMapInfo();
    },

    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },
    searchPlaces() {
      console.log("searchPlaces");
      if (!this.currCategory) {
        console.log("not a currCate");
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },
    placesSearchCB(data, status, pagination) {
      console.log("placesSearchCB");
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
        console.log(pagination);
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },
    displayPlaces(places) {
      console.log("displayplaces");
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document
        .getElementById(this.currCategory)
        .getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(
          new kakao.maps.LatLng(places[i].y, places[i].x),
          order
        );

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        ((marker, place) => {
          kakao.maps.event.addListener(marker, "click", () => {
            console.log("displayplaceinfo");
            var content =
              '<div class="placeinfo">' +
              '   <a class="title" href="' +
              place.place_url +
              '" target="_blank" title="' +
              place.place_name +
              '">' +
              place.place_name +
              "</a>";

            if (place.road_address_name) {
              content +=
                '    <span title="' +
                place.road_address_name +
                '">' +
                place.road_address_name +
                "</span>" +
                '  <span class="jibun" title="' +
                place.address_name +
                '">(지번 : ' +
                place.address_name +
                ")</span>";
            } else {
              content +=
                '    <span title="' +
                place.address_name +
                '">' +
                place.address_name +
                "</span>";
            }

            content +=
              '    <span class="tel">' +
              place.phone +
              "</span>" +
              "</div>" +
              '<div class="after"></div>';
            console.log(this.contentNode);
            this.contentNode.innerHTML = content;
            this.placeOverlay.setPosition(
              new kakao.maps.LatLng(place.y, place.x)
            );
            this.placeOverlay.setMap(this.map);
          });
        })(marker, places[i]);
      }
    },
    addMarker(position, order) {
      console.log("addmarker");
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.placeMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    removeMarker() {
      console.log("removemarker");
      for (var i = 0; i < this.placeMarkers.length; i++) {
        this.placeMarkers[i].setMap(null);
      }
      this.placeMarkers = [];
    },
    onClickCategory(e) {
      console.log("onclickcategory");
      var id = e.currentTarget.id,
        className = e.currentTarget.className;
      console.log(id);

      this.placeOverlay.setMap(null);

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker();
      } else {
        this.currCategory = id;
        this.changeCategoryClass(this);
        this.searchPlaces();
      }
    },
    changeCategoryClass(el) {
      console.log("changecategoryclass");
      var category = document.getElementById("category"),
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (el) {
        el.className = "on";
      }
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
        var content;
        if (pos.price.sales.avg == null) {
          content = `<div dark id="donggucard" style="back-ground:white">${pos.name}</div>`;
        } else {
          content = `<div dark id="donggucard">${pos.name}<br/>${pos.price.sales.avg}</div>`;
        }
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
      var imageSize = new kakao.maps.Size(35, 35);
      var markerImage = new kakao.maps.MarkerImage(
        require("@/assets/map/apart_marker4.png"),
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

        //클릭시 단지세부(실거래가랑 평점), 초등학교, 실거래가 50개를 불러와서 데이터에 세팅. 해당 데이터는 오픈된 상세설명창에 뿌려진다.
        kakao.maps.event.addListener(marker, "click", () => {
          //이전 상세페이지 내용 없애려고 얘를 null로 돌렸더니 다음 단지 내용 받아오기 전에 빨간에러 뜨긴하는데 보기에는 잘 돌아감
          this.danji = null;
          this.favorited = false; //일단 false로 돌려준다
          this.showChartandRoad = false;
          infowindow.close();
          //단지 세부
          axios
            .get(`https://apis.zigbang.com/v2/danjis/${pos.id}`)
            .then(({ data }) => {
              this.danji = data;
              //학교
              axios
                .get(
                  `https://apis.zigbang.com/property/apartments/school/info?apartmentId=${pos.id}`
                )
                .then(({ data }) => {
                  console.log(data);
                  this.schools = data.elementary.list;
                  this.schools2 = data.elementary.etcList;
                  this.school_data = data;

                  // 매매
                  axios
                    .get(
                      `https://apis.zigbang.com/v2/apartments/real_sale/list/${pos.id}/0?limit=50&offset=0&transactionType=s`
                    )
                    .then(({ data }) => {
                      console.log("50개까지의 매매");
                      console.log(data);
                      this.real_sale = data.data;
                      //전월세
                      axios
                        .get(
                          `https://apis.zigbang.com/v2/apartments/real_sale/list/${pos.id}/0?limit=50&offset=0&transactionType=r`
                        )
                        .then(({ data }) => {
                          console.log("50개까지의 전월세");
                          console.log(data);
                          this.real_rent = data.data;
                          //해당 매물번호가 유저관심사에 있다면 true로 바꿔줌
                          http.get(`fav/${this.user.id}`).then(({ data }) => {
                            console.log(data);
                            data.forEach((item) => {
                              if (item.apt_id == pos.id) {
                                this.favorited = true;
                              }
                            });
                          });
                          console.log(this.danji.lat);
                          console.log(this.danji.lng);
                          //상세창 오픈
                          this.showChartandRoad = true;
                          this.OpenDetail();
                        });
                    });
                });
            });
          //console.log(pos.name);
        });
        this.markers.push(marker); //만든 마커를 마커 배열에 전부 넣기
      });

      //학교
      imageSize = new kakao.maps.Size(30, 35);
      markerImage = new kakao.maps.MarkerImage(
        require("@/assets/map/s_marker.png"),
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
        this.showChartandRoad = true;
      }
    },
    CloseDetail() {
      if (this.showDetail) {
        this.showDetail = !this.showDetail;
        this.showChartandRoad = false;
        //상세전부 전부 null로 돌려야 함
        this.danji = null;
        this.favorited = false;
      }
    },
  },
};
</script>

<style>
.mapModal {
  top: 0.5%;
  bottom: 7%;
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
.danji-desc {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 300px;
  height: 20px;
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

<style>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-size: 12px;
}
/*service 강제로 맞춤 */
.map_wrap {
  position: relative;
  width: 100%;
  left: 180px;
  bottom: 80px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>

<style lang="scss" scoped>
// Colors
$color-primary: #0c8040;
$color-secondary: #626262;
$color-border: #ccd7d7;

// Media Queries breakpoints
$small: 480px;
$medium: 768px;
$large: 992px;
$x-large: 1200px;

@mixin hide-menu-items($index) {
  &:nth-child(n + #{$index + 1}) {
    display: none;
  }

  &:nth-child(#{$index}) .menu__link {
    border-right: 0;
  }
}

body {
  display: flex;
  align-items: center;
  height: 100vh;
  background: #fefefe;
}

a {
  outline: 0;
  text-decoration: none;
}

.navigation {
  width: 100%;
  border-bottom: 3px solid $color-border;
  // font-family: "Pathway Gothic One", sans-serif;
  font-size: 22px;
}

.menu {
  display: flex;
  justify-content: center;
  max-width: 1150px;
  margin: 0 auto;
  padding-left: 0;
}

.menu__item {
  display: inline-block;
  white-space: nowrap;

  @media screen and (max-width: $small) {
    @include hide-menu-items(2);
  }

  @media screen and (max-width: $medium) {
    @include hide-menu-items(3);
  }

  @media screen and (max-width: $large) {
    @include hide-menu-items(4);
  }

  &:last-child .menu__link {
    border-right: 0;
  }
}

.menu__link {
  display: block;
  padding: 6px 30px 3px;
  border-right: 2px dotted $color-border;

  &:hover,
  &:focus {
    .menu__first-word {
      transform: translate3d(0, -105%, 0);
    }

    .menu__second-word {
      transform: translate3d(0, 105%, 0);
    }
  }

  @media screen and (min-width: $medium) {
    padding: 6px 40px 3px;
  }
}

.menu__title {
  display: inline-block;
  overflow: hidden;
}

.menu__first-word,
.menu__second-word {
  display: inline-block;
  position: relative;
  transition: transform 0.3s;

  &::before {
    position: absolute;
    content: attr(data-hover);
  }
}

.menu__first-word {
  color: $color-primary;

  &::before {
    top: 105%;
    color: $color-secondary;
  }
}

.menu__second-word {
  color: $color-secondary;

  &::before {
    bottom: 105%;
    color: $color-primary;
  }
}
</style>
