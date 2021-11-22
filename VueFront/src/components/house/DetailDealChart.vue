<template>
  <div>
    <canvas
      class="statistics-charts-line"
      id="planet-chart"
      ref="lineChart"
      style="height: 30vh; width: 50vw"
    ></canvas>
  </div>
</template>

<script>
import Chart from "chart.js";
export default {
  created() {},
  mounted() {
    this.createChart("planet-chart", this.planetChartData);
  },
  props: ["real_sale"], //여기서 rtDealDate랑 rtPrice를 떼 온다
  data() {
    return {
      rtDealDate: [],
      rtPrice: [],

      planetChartData: {
        type: "line",
        data: {
          labels: this.rtDealDate,
          datasets: [
            {
              label: "매매(만)",
              data: this.rtPrice,
              backgroundColor: ["rgba(71, 183, 132, .5)"],
              borderColor: ["#47b784"],
              borderWidth: 1,
            },
          ],
        },
        options: {
          responsive: true,
          lineTension: 1,
          hover: {
            mode: "nearest",
            intersect: true,
          },
          scales: {
            // yAxes: [{ display: false }],
            xAxes: [
              {
                display: false, //this will remove all the x-axis grid lines
              },
            ],
          },
        },
      },
    };
  },
  methods: {
    createChart(charId, chartData) {
      this.parseRealSale();

      console.log(this.rtPrice);
      console.log(this.rtDealDate);

      const ctx = document.getElementById(charId);
      new Chart(ctx, {
        type: chartData.type,
        data: chartData.data,
        options: chartData.options,
      });
    },
    /*prop으로 받은 real_sale 객체를 돌면서 거래일자와 price를 각각 리스트로*/
    parseRealSale() {
      this.rtDealDate = [];
      this.rtPrice = [];
      this.real_sale.forEach((item) => {
        this.rtDealDate.push(item.rtDealDate);
        this.rtPrice.push(item.rtPrice);
      });
    },
  },
};
</script>
