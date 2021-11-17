<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-steelblue">
      <b-icon icon="house"></b-icon> HappyHouse
    </h3>
    <b-carousel
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
        <h1>Happy House!!</h1>
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
        <p>서울 12반 3조 입니다!! <br /></p>
      </b-carousel-slide>
    </b-carousel>

    <!-- 지도 자리 -->
    <div class="mapDiv">
      <div class="bigClass">
        <h3>근처에 얼마나 많은 매물들이 있을까요?</h3>
        <h5>Apart Info 메뉴에서 자세한 위치를 알아보세요!</h5>
        <br />
        <b-button pill size="lg" variant="outline-secondary" @click="goApart"
          >ApartInfo로</b-button
        >
      </div>
      <div>
        <div id="map"></div>
        <div>
          <b-button pill variant="outline-secondary" @click="setMapInfo"
            >지도 위치에서 매물 확인하기</b-button
          >
        </div>
        <div>
          <label for="apartSearch">아파트검색으로 이동 </label>
          <input
            v-on:input.stop="searchArea"
            id="apartSearch"
            name="apartSearch"
          />
          <br />
          <label for="entireSearch">통합검색으로 이동 </label>
          <input
            v-on:input.stop="entireSearchArea"
            id="entireSearch"
            name="entireSearch"
          />
        </div>
        <ul id="searchResult">
          <li v-for="item in items" :key="item.id">
            <button
              @click="
                moveToPosition(
                  item.id,
                  item.lat,
                  item.lng,
                  item.zoom_level_v2.web
                )
              "
            >
              {{ item.name }}
            </button>
            : {{ item.description }} : {{ item._source.신주소 }}
          </li>
        </ul>
      </div>
    </div>
  </b-container>
</template>

<script>
import axios from "axios";
export default {
  name: "Main",
  props: {
    msg: String,
  },
  data() {
    return {
      slide: 0,
      sliding: null,

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
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
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
        });
        var infowindow = new kakao.maps.InfoWindow({
          content: pos.name, // 인포윈도우에 표시할 내용
        });
        (function (marker, infowindow) {
          // 마커에 mouseover 이벤트를 등록하고 마우스 오버 시 인포윈도우를 표시합니다
          kakao.maps.event.addListener(marker, "mouseover", function () {
            infowindow.open(this.map, marker);
          });

          // 마커에 mouseout 이벤트를 등록하고 마우스 아웃 시 인포윈도우를 닫습니다
          kakao.maps.event.addListener(marker, "mouseout", function () {
            infowindow.close();
          });
        })(marker, infowindow);

        marker.setMap(this.map); //이거 해줘야 함
      });
    },
    printLevel() {
      console.log(
        `mapLevel: ${this.map.getLevel()}, geolevel: ${this.geolevel}`
      );
    },
    //아파트검색 v-on
    searchArea(event) {
      axios
        .get(
          `https://apis.zigbang.com/v2/search?leaseYn=N&q=${event.target.value}&serviceType=아파트`
        )
        .then(({ data }) => {
          this.items = data.items;
          console.log(data);
        });
    },
    //통합검색 v-on
    entireSearchArea(event) {
      axios
        .get(`https://apis.zigbang.com/search?q=${event.target.value}`)
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
    },
    goApart() {
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
#map {
  width: 100%;
  height: 500px;
}
.mapDiv {
  font-family: "NotoSerifKR";
  color: black;
}
.bigClass {
  padding: 5%;
}
div {
  padding: 3%;
}
</style>
