<template>
  <div id="road" style="width: 100%; height: 300px"></div>
</template>

<script>
export default {
  props: ["danjiLat", "danjiLng"],
  data() {
    return {};
  },
  mounted() {
    if (!(window.kakao && window.kakao.maps)) {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      //autoload false로 설정해줘야 뜸 왜 그런지는 모르겠음
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&&appkey=140fc52b610505ff65a221d6b9eabd09";
      document.head.appendChild(script);
    }

    var roadviewContainer = document.getElementById("road"); //로드뷰를 표시할 div
    var roadviewObject = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
    var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
    console.log(this.roadviewLatLng);
    var position = new kakao.maps.LatLng(this.danjiLat, this.danjiLng);

    // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
    roadviewClient.getNearestPanoId(position, 50, function (panoId) {
      roadviewObject.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
    });
  },
  // watch: {
  //   name() {
  //     this.danjiLat = this.danjiLat;
  //     this.danjiLng = this.danjiLng;
  //   },
  // },
};
</script>
