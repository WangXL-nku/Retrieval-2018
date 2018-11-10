<template>
  <div>
    <div v-if="state===0">
      <div class="my-head">
        <img class="head-img-style" src="../../assets/douban.jpg"/>
        <!--<div class="head-div"></div>-->
        <el-input class="input-button" style="position:absolute;" v-model="Query">
          <el-button slot="append" icon="el-icon-search" @click="Search"></el-button>
        </el-input>
        <el-button style="position: absolute;left: 80%;top:30px;" type="success" @click="redoIndex">重建索引</el-button>
      </div>
      <el-container class="main-con" v-for="movie in MovieList" :key="movie.id">
        <el-aside width="150px" style="margin-left: 150px ;">
          <el-button type="text">
          <el-card shadow="hover" :body-style="{ padding: '0px' }" style="width: 135px;" @click="viewInfo(movie.id)">
            <img :src="movie.picture" class="image"/>
          </el-card>
          </el-button>
        </el-aside>
        <el-container>
          <el-main style="padding: 10px 20px;">
            <el-container style="width: 80%;">
              <el-aside>
                <el-col>
                  <div style="height: 30px;font-weight:900;">
                    <span>
                      <el-button type="text" style="font: 20px Extra large;padding: 0px;" @click="viewInfo(movie.id)" v-html="movie.name">
                      </el-button>
                    </span>
                  </div>
                  <el-col style="margin-bottom:10px;">
                    <span class="title-cvi">导演：</span>
                    <span style="color:#37a;" v-html="movie.author"></span>
                  </el-col>
                  <el-col style="margin-bottom: 10px; ">
                    <span class="title-cvi">类型：</span>
                    <span style="color:#37a;" v-html="movie.type"></span>
                  </el-col>
                  <el-col style="margin-bottom: 10px; ">
                    <span class="title-cvi">地区：</span>
                    <span style="color:#37a;" v-html="movie.areas"></span>
                  </el-col>
                  <el-col>
                    <span class="title-cvi">豆瓣评分：</span>
                    <div>
                      <span style="font-size:30px;vertical-align: middle;"><v-html>
                        {{movie.grade}}
                      </v-html></span>
                      <div style="width: 250px;display: inline-block;vertical-align: middle;font-size: 15px ">
                        <el-rate
                          v-model="movie.grade/2"
                          disabled
                          size="medium"
                          text-color="#ff9900">
                        </el-rate>
                      </div>
                    </div>
                  </el-col>
                </el-col>
              </el-aside>
              <el-main style="white-space:pre-wrap;padding: 0px;" v-html="movie.abst"></el-main>
            </el-container>
          </el-main>
        </el-container>
      </el-container>
      <div style="text-align: center;">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage1"
          :page-size="5"
          layout="total, prev, pager, next"
          :total="movieNum">
        </el-pagination>
      </div>
    </div>
    <div v-else>
      <img style="width: 80%;padding-left: 170px;" src="../../assets/aaa.jpg"/>
      <el-button @click = showBack style="position: absolute;top:250px;left:82%;" type="primary">返回搜索列表</el-button>
      <el-container style="margin:0px 250px;">
        <el-header style="height: 25px;font-weight:900;">
          <span style="font: 30px Extra large;" v-html="infoMovie.name"></span>
        </el-header>
        <el-main>
          <el-container>
            <el-aside width="200px">
              <el-card shadow="hover" :body-style="{ padding: '0px' }" style="width:150px;">
                <img :src="infoMovie.picture" class="image2"/>
              </el-card>
            </el-aside>
            <el-main style="padding: 0px;">
              <el-col style="margin-top: 10px;">
                <el-col style="margin-bottom:10px;">
                  <span class="title-cvi">导演：</span>
                  <span style="color:#37a;" v-html="infoMovie.author"></span>
                </el-col>
                <el-col style="margin-bottom: 10px; ">
                  <span class="title-cvi">类型：</span>
                  <span style="color:#37a;" v-html="infoMovie.type"></span>
                </el-col>
                <el-col style="margin-bottom: 10px;">
                  <span class="title-cvi">地区：</span>
                  <span style="color:#37a;" v-html="infoMovie.areas"></span>
                </el-col>
                <el-col style="width: 400px;">
                  <span class="title-cvi">主演：</span>
                  <span v-html="infoMovie.actor"></span>
                </el-col>
                <el-col>
                  <span class="title-cvi">豆瓣评分：</span>
                  <div>
                    <span style="font-size:30px;vertical-align: middle;">
                      {{infoMovie.grade}}
                    </span>
                    <div style="width: 250px;display: inline-block;vertical-align: middle;font-size: 15px ">
                      <el-rate
                        v-model="infoMovie.grade"
                        disabled
                        size="medium"
                        text-color="#ff9900">
                      </el-rate>
                    </div>
                  </div>
                </el-col>
              </el-col>
            </el-main>
          </el-container>
          <el-col style="padding:20px 0px;">
            <div style="color: #007722;font-size: 25px">
              剧情简介
            </div>
            <div style="width: 80%;white-space:pre-wrap;"v-html="infoMovie.abst"></div>
          </el-col>
          <el-col>
            <div style="color: #007722;font-size: 25px">
              影评1
            </div>
            <div style="width: 80%;white-space:pre-wrap;" v-html="infoMovie.comment1"></div>
          </el-col>
          <el-col>
            <div style="color: #007722;font-size: 25px">
              影评2
            </div>
            <div style="width: 80%;white-space:pre-wrap;" v-html="infoMovie.comment2"></div>
          </el-col>
          <el-col>
            <div style="color: #007722;font-size: 25px">
              影评3
            </div>
            <div style="width: 80%;white-space:pre-wrap;"v-html="infoMovie.comment3"></div>
          </el-col>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
    export default {
      data(){
        return{
          state:0,
          Query:'',
          currentPage1:1,
          movieNum:0,
          infoMovie:{
            name:'',
            author:'',
            type:'',
            areas:'',
            grade:'',
            actor:'',
            abst:'',
            picture:'',
            comment1:'',
            comment2:'',
            comment3:'',
            id:'',
          },
          MovieList:[]
        }
      },
      mounted(){
        // this.initData();
        this.Search();
      },
      methods:{
        Search(){
          this.axios.get('/tf-idf_search', {
            params: {
              question:this.Query,
            },
            headers: {
            }
          }).then(res => {
            if (res.data.success) {
              this.MovieList = res.data.data.movies;
              this.Query = res.data.data.query;
              this.movieNum = res.data.data.movieNum;
              this.currentPage1 = 1;
              for(var i=0;i<this.MovieList.length;i++){
                this.MovieList[i].id=i;
              }
              this.infoMovie = this.MovieList[0];
            } else {
              this.$message({
                type:'error',
                message:"无相关电影信息"
              });
            }
          }).catch(error => {
            this.$message({
              message: error,
              type: 'warning'
            });
          })
        },
        viewInfo(Id){
          this.infoMovie = this.MovieList[Id];
          this.state = 1;
        },
        showBack(){
          this.state = 0;
        },
        handleCurrentChange(val) {
          // console.log(`当前页: ${val}`);
          // alert(val);
          this.axios.get('/set-page', {
            params: {
              pageNum:val,
            },
            headers: {
            }
          }).then(res => {
            if (res.data.success) {
              this.MovieList = res.data.data.movies;
              this.Query = res.data.data.query;
              this.movieNum = res.data.data.movieNum;
              for(var i=0;i<this.MovieList.length;i++){
                this.MovieList[i].id=i;
              }
              this.infoMovie = this.MovieList[0];
            } else {
              this.$message({
                type:'error',
                message:"无相关电影信息"
              });
            }
          }).catch(error => {
            this.$message({
              message: error,
              type: 'warning'
            });
          })
        },
        redoIndex(){
          this.axios.get('/redo-index', {
            params: {},
            headers: {
            }
          }).then(res => {
            if(res.data == 'redo Index') {
              alert("已经重新建立了索引")
            }
          }).catch(error => {
            this.$message({
              message: error,
              type: 'warning'
            });
          })
        },
      }
    }
</script>

<style>
  .title-cvi{
    color: #666666;
  }
  .main-ini{
    display: inline-block;
  }
  .main-con{
    margin: 20px;
  }
  .image{
    width:135px;
  }
  .image2{
    width:150px;
  }
  .input-button .el-input-group__append{
    background: #FFF;
    color: #4285f4;
  }
  .my-head{
    background: #fafafa;
    border-bottom:1px solid #ebebeb;
    height:100px;
  }
  .head-img-style{
    text-align: left;
    height: 44px;
    margin:30px 20px 0px 170px;
  }
  .el-input{
    position: absolute;
    width:35%;
    padding-bottom: 50px;
    top:33px;
  }
</style>
