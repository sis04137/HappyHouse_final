<template>
  <v-expansion-panels accordion flat>
    <v-expansion-panel>
      <v-expansion-panel-header>자세한 실거래가 보기</v-expansion-panel-header>
      <v-expansion-panel-content>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th>계약일</th>
                <th>가격</th>
                <th>타입</th>
                <th>층수</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in real_data" :key="index">
                <td>{{ item.rtDealDate }}</td>
                <td>{{ item.rtPrice }}{{ item.rtDealType }}</td>
                <td>{{ item.roomTypeId }}</td>
                <td>{{ item.rtFloor }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
export default {
  created() {},
  mounted() {},
  props: ["real_data"], //여기서 rtDealDate랑 rtPrice를 떼 온다
};
</script>
