<template>
  <v-card-text>
    {{ this.schoolList.zoneCode }} {{ this.schoolList.zoneName }}

    <v-simple-table>
      <template v-slot:default>
        <tbody>
          <tr v-for="(item, index) in schoolList.list" :key="index">
            <td>{{ item.distance }}</td>
            <td>{{ item.name }}</td>
            <!-- <td>{{ item.gender }}</td>
            <td>{{ item.sulib }}</td> -->
          </tr>
        </tbody>
      </template>
    </v-simple-table>

    <v-expansion-panels accordion flat>
      <v-expansion-panel>
        <v-expansion-panel-header>비학군보기</v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-simple-table>
            <template v-slot:default>
              <tbody>
                <tr v-for="(item, index) in schoolList.etcList" :key="index">
                  <td>{{ item.distance }}</td>
                  <td>{{ item.name }}</td>
                  <!-- <td>{{ item.gender }}</td>
                  <td>{{ item.sulib }}</td> -->
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card-text>
</template>

<script>
export default {
  created() {},
  mounted() {},
  props: ["schoolList"],
  data() {
    return {};
  },
  methods: {},
};
</script>
