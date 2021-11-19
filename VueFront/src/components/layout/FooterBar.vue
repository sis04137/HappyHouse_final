<template>
  <div>
    <footer toggleable="lg">
      <b-icon icon="mailbox"></b-icon>
      <h4>Find us</h4>
      <!-- <h4>Find us</h4> -->

      <div class="address">
        (SSAFY) 서울시 강남구 테헤란로 멀티 스퀘어 <br />
        <!-- <strong>Phone:</strong> 1544-9001 || -->
        <!-- <strong>Email:</strong> admin@ssafy.com -->
        <b-icon icon="telephone" />1544-9001 ||
        <b-icon icon="envelope" />admin@ssafy.com
      </div>
      <div class="copyright">
        <br />
        &copy; Copyright <strong><span>zipgoohye</span></strong
        >. All Rights Reserved
      </div>
    </footer>
  </div>
</template>

<script>
//mailbox, telephone-fill, envelope-fill
// import Vue from "Vue";
// import { mailbox, telephone, envelope } from "bootstrap-vue";

export default {
  name: "FooterBar",
  // components: {
  //   mailbox,
  //   telephone,
  //   envelope,
  // },
};
</script>

<style scoped>
footer {
  border-top: 1px solid #35495e;
  text-align: left;
  font-size: 16px;
  color: #535bcc;
  margin: 100px 0 0 0;
}
.address {
  color: #101018;
  float: left;
}
.copyright {
  color: #101018;
  text-align: right;
  float: right;
  display: inline-block;
}
</style>
