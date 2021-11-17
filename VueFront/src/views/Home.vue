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
      <b-carousel-slide>
        <b-img :src="require(`@/assets/gamsil.jpg`)"></b-img>
        <!-- <img :src="require(`@/assets/gamsil.jpg`)" /> -->
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

      <b-carousel-slide caption="made by 정구아">
        <img :src="require('../assets/gp.jpg')" />
        <!-- <b-img :src="require('../assets/gamsil.jpg')"></b-img> -->
        <p>SSAFY 6기 열정 핫식스!! <br /></p>
      </b-carousel-slide>

      <b-carousel-slide caption="made by 김혜란">
        <b-img :src="require(`@/assets/hp.jpg`)"></b-img>
        <p>서울 12반 3조 입니다!! <br /></p>
      </b-carousel-slide>
    </b-carousel>

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
