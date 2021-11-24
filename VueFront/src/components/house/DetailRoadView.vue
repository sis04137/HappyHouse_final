<template>
  <div id="detailRoadView" style="width: 100%; height: 400px">구아구</div>
</template>

<script>
export default {
  mounted() {},
  props: ["danji", "map"],
  data() {
    return {};
  },
  created() {
    //로드뷰 세팅
    // var roadviewContainer = document.getElementById("detailRoadView"); //로드뷰를 표시할 div
    // var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
    // var roadviewClient = new kakao.maps.RoadviewClient();
    // var position = new kakao.maps.LatLng(33.450701, 126.570667);
    // roadviewClient.getNearestPanoId(position, 50, function (panoId) {
    //   roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
    // });
  },
  methods: {},
};
</script>
