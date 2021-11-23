<template>
  <div>
    <canvas
      class="statistics-charts-line"
      id="real-rent-chart"
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
    this.createChart("real-rent-chart", "line");
  },
  props: ["real_sale", "chart_label"], //여기서 rtDealDate랑 rtPrice를 떼 온다
  data() {
    return {
      rtDealDate: [],
      rtPrice: [],
      myChart: null,
    };
  },
  methods: {
    createChart(charId, type) {
      this.resetCanvas();
      this.parseRealSale();
      console.log(this.rtDealDate);
      console.log(this.rtPrice);

      const ctx = document.getElementById(charId);
      this.myChart = new Chart(ctx, {
        type: type,
        data: {
          labels: this.rtDealDate,
          datasets: [
            {
              label: this.chart_label,
              data: Array.from(this.rtPrice),
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
    resetCanvas() {
      if (window.myChart != undefined) {
        window.myChart.destroy();
      }
    },
  },
};
</script>
