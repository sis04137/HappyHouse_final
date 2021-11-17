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
      <b-carousel-slide img-blank img-alt="Blank image">
        <h1>Happy House!!</h1>
        <br /><br /><br /><br />
      </b-carousel-slide>

      <b-carousel-slide
        caption="Happy House"
        text="아파트 상세 정보를 볼 수 있습니다."
        img-src="https://picsum.photos/1024/480/?image=52"
      ></b-carousel-slide>

      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <!-- 이미지 무엇을 넣을까요오 -->
      <b-carousel-slide
        caption="행복한 당신의 집 찾아가세요."
        img-blank
        img-alt="Blank image"
      >
        <p>
          아파트 정보 페이지를 통해 아파트들의 실거래가를 알 수 있으며, <br />
          게시판을 통하여 QnA 또한 가능합니다.
        </p>
      </b-carousel-slide>
    </b-carousel>

    <!-- <p class="mt-4">
      Slide #: {{ slide }}<br />
      Sliding: {{ sliding }}
    </p> -->
    <!-- <b-row>
      <b-col></b-col>
      <b-col cols="10">
        <b-jumbotron
          bg-variant="muted"
          text-variant="dark"
          border-variant="dark"
        >
          <template #header>Happy House</template>

          <template #lead>
            아파트 상세 정보를 볼 수 있습니다. <br />
            행복한 당신의 집 찾아가세요.
          </template>

          <hr class="my-4" />

          <p>Vue + Bootstrap활용.</p>
          <p>Bootstrap-vue는 버전 <b>4.5.3</b>을 권장합니다.</p>
          <p><b>BoardList.vue</b>를 바꿔가면서 테스트하세요.</p>
          <p>Bootstrap의 <b>table</b> 사용법을 익히게됩니다.</p>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row> -->

    <!-- 지도 자리 -->
  </b-container>
</template>

<script>
export default {
  name: "Main",
  props: {
    msg: String,
  },
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
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
</style>
