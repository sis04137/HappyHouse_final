<template>
  <v-container style="height: 700px">
    <v-btn @click="handleClickGetAuth"> getauthorization code</v-btn>
    <v-btn @click="authKakao"> 카카오로그인</v-btn>
    <a href="http://localhost:9999/vue/oauth2/authorization/google"
      >서버 구글로그인</a
    >
    <!-- <div>
      <detail-deal-chart></detail-deal-chart>
    </div> -->
    <toggle-favorite :favorited="favorited"></toggle-favorite>
    <div class="map_wrap">
      <div
        id="map"
        style="width: 100%; height: 100%; position: relative; overflow: hidden"
      ></div>
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
    <div id="map" style="width: 100%; height: 300px"></div>
  </v-container>
</template>

<script>
import http from "@/util/http-common.js";
// import DetailDealChart from "@/components/house/DetailDealChart.vue";
import ToggleFavorite from "@/components/house/favorite/ToggleFavorite.vue";

export default {
  name: "TestG",
  components: {
    // DetailDealChart,
    ToggleFavorite,
  },
  data() {
    return {
      favorited: true,

      /*service */
      placeMarkers: [],
      currCategory: "",
      placeOverlay: null,
      map: null,
      ps: null,
      contentNode: null,
    };
  },
  mounted() {
    if (!(window.kakao && window.kakao.maps)) {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      //autoload false로 설정해줘야 뜸 왜 그런지는 모르겠음
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&&appkey=140fc52b610505ff65a221d6b9eabd09Y&libraries=services";
      document.head.appendChild(script);
    }

    /*service */
    this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
    this.contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다

    var mapContainer = document.getElementById("map"), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
      };
    this.map = new kakao.maps.Map(mapContainer, mapOption);
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
  },
  methods: {
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
            console.log("dispalyplaceinfo");
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

    handleClickGetAuth() {
      this.$gAuth
        .getAuthCode()
        .then((authCode) => {
          //on success
          console.log("authCode");
          console.log(authCode);
          http.post("/google", authCode).then(({ data }) => {
            console.log(data);
          });
          // return this.$http.post("http://localhost:9999/vue/google", authCode); //이 uri를 redierct uri로 등록해줘야 함 아니면 에러
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log("error");
          console.log(error);
        });
    },
    authGoogle() {
      http.get("/oauth2/authorization/google").then(({ data }) => {
        console.log(data);
      });
    },
    signOut() {
      const authInst = window.gapi.auth2.getAuthInstance();
      authInst.signOut().then(() => {
        // eslint-disable-next-line
        console.log("User Signed Out!!!");
      });
    },
    authKakao() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?client_id=44ddf9f04018e79ad5791e558f7dafb5&redirect_uri=http://localhost:8080&response_type=code`
      );
      // axios
      //   .get(
      //     `https://kauth.kakao.com/oauth/authorize?client_id=44ddf9f04018e79ad5791e558f7dafb5&redirect_uri=http://localhost:8080&response_type=code`
      //   )
      //   .then(({ data }) => {
      //     console.log(data);
      //   });
    },
  },
};
</script>

<style>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
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
