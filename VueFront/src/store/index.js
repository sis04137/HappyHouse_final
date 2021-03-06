import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common.js";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    houses: [],
    house: null,
    user: null,
    comments: [],
  },
  getters: {
    comments(state) {
      return state.comments;
    },
  },
  mutations: {
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE(state, house) {
      state.house = house;
    },
    SET_USER(state, user) {
      state.user = user;
    },
    CLEAR_USER(state) {
      state.user = null;
    },
    SET_COMMENTS(state, payload) {
      state.comments = payload;
    },
  },
  actions: {
    getSido({ commit }) {
      http
        .get(`/map/sido`)
        .then((response) => {
          // console.log(data)
          commit("SET_SIDO_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, sidoCode) {
      const params = { sido: sidoCode };
      http
        .get(`/map/gugun`, { params })
        .then((response) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getHouseList({ commit }, gugunCode) {
      const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      // const SERVICE_KEY =
      //   "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
      const SERVICE_URL =
        "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
      const params = {
        LAWD_CD: gugunCode,
        DEAL_YMD: "202110",
        serviceKey: decodeURIComponent(SERVICE_KEY),
      };
      http
        .get(SERVICE_URL, { params })
        .then((response) => {
          console.log(commit, response);
          commit("SET_HOUSE_LIST", response.data.response.body.items.item);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    detailHouse({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
    getUserByUserId({ commit }, user_id) {
      http
        .get(`/api/member/${user_id}`)
        .then((response) => {
          console.log(response.data);
          commit("SET_USER", response.data);
        })
        .catch((error) => console.log(error));
    },
    logout({ commit }) {
      commit("CLEAR_USER");
    },
    getComments({ commit }, payload) {
      http
        .get("/comment/" + payload)
        .then(({ data }) => {
          commit("SET_COMMENTS", data); //data안에 books가 있으니.
        })
        .catch(() => {
          alert("서평조회: 에러 발생!");
        });
    },
  },
  modules: {},
  plugins: [createPersistedState()],
});
