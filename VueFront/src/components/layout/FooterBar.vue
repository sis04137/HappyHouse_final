<template>
  <div>
    <footer toggleable="lg" type="dark" variant="dark">
      <b-icon icon="mailbox"></b-icon>
      <h4>Find us</h4>
      <!-- <h4>Find us</h4> -->
      <div>
        <h6 class="address">
          (SSAFY) 서울시 강남구 테헤란로 멀티 스퀘어 <br />
          <!-- <strong>Phone:</strong> 1544-9001 || -->
          <!-- <strong>Email:</strong> admin@ssafy.com -->
          <b-icon icon="telephone" />1544-9001 ||
          <b-icon icon="envelope" />admin@ssafy.com
          <!-- 이메일이랑 같은 줄, 오른쪽으로 입력하고 싶... -->
          <h6 class="copyright">
            &copy; Copyright <strong><span>Squadfree</span></strong
            >. All Rights Reserved
          </h6>
        </h6>
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
h6.address {
  color: #101018;
}
h6.copyright {
  color: #101018;
  text-align: right;
}
</style>
