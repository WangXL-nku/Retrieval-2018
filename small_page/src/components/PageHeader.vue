<template>
  <div class="page-head">
    <img class="header-img-style" src="../assets/pageHead1.png" >
    <img class="header-img-style head-img-hight-style" src="../assets/images (4).jpg">

<!-- <navigate-bar @logOutChild="logOutChild_"></navigate-bar> -->
  </div>
</template>

<script>
export default {
  name: 'PageHeader',
  data () {
    return {}
  },
  methods: {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.navigatebar-separate-line {
  background-color: rgba(176, 196, 222, 0.6);
  height: 2px;
}
.header-img-style {
  width: 100%;
  vertical-align: middle;

}
  .head-img-hight-style{
    height: 200px;
  }
</style>
