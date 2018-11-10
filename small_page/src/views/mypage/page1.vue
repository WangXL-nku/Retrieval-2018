<template>
  <div>
    <el-card>
      <el-collapse v-model="activeName" accordion class="col-head-style">
        <el-collapse-item title="自然查询" name="1">
          <div style="margin-top: 15px" class="col-init-style-1">
            <el-input placeholder="请输入内容" clearable v-model="Search1">
              <el-button type="success" plain slot="append" icon="el-icon-search" @click="SearchInfo1() ">搜索</el-button>
            </el-input>
          </div>
        </el-collapse-item>
        <el-collapse-item title="关键字查询" name="2">
          <div style="margin-left: 40px;">
          <span style="margin-top: 15px;" class="col-init-style-2">
            <el-input placeholder="请输入内容" clearable v-model="Search2.KeyWord1" class="input-with-select">
              <el-select v-model="Search2.Line1" slot="prepend" placeholder="请选择">
                <el-option
                  v-for="item in lineList"
                  :key="item.key"
                  :label="item.name"
                  :value="item.key">
                </el-option>
              </el-select>
              <el-select v-model="Search2.Option1" slot="append" placeholder="请选择">    <el-option
                v-for="item in OptionList"
                :key="item.key"
                :label="item.name"
                :value="item.key">
                </el-option>
              </el-select>
            </el-input>
          </span>
            <span style="margin-top: 15px;" class="col-init-style-2">
            <el-input placeholder="请输入内容" clearable v-model="Search2.KeyWord2" class="input-with-select">
              <el-select v-model="Search2.Line2" slot="prepend" placeholder="请选择">   <el-option
                v-for="item in lineList"
                :key="item.key"
                :label="item.name"
                :value="item.key">
                </el-option>
              </el-select>
              <el-select v-model="Search2.Option2" slot="append" placeholder="请选择">    <el-option
                v-for="item in OptionList"
                :key="item.key"
                :label="item.name"
                :value="item.key">
                </el-option>
              </el-select>
            </el-input>
          </span>
            <span style="margin-top: 15px;" class="col-init-style-2">
            <el-input placeholder="请输入内容" clearable v-model="Search2.KeyWord3" class="input-with-select">
              <el-select v-model="Search2.Line3" slot="prepend" placeholder="请选择">   <el-option
                v-for="item in lineList"
                :key="item.key"
                :label="item.name"
                :value="item.key">
                </el-option>
              </el-select>
              <el-button type="success" plain slot="append" icon="el-icon-search" @click="SearchInfo2">搜索</el-button>
            </el-input>
          </span>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-card>
    <el-card>
      <div style="height: 50px;text-align: left;margin-left: 50px">
      <el-switch
        style="display: block"
        v-model="value4"
        active-color="#409EFF"
        inactive-color="#13ce66"
        active-text="图片展示"
        inactive-text="文字展示">
      </el-switch>
      </div>
      <el-card v-if="value4===false">
        <el-table
          :data="MovieList"
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="电影名称">
                  <span v-html="props.row.name">{{ props.row.name }}</span>
                </el-form-item>
                <el-form-item label="导演">
                  <span v-html="props.row.author">{{ props.row.author }}</span>
                </el-form-item>
                <el-form-item label="类型">
                  <span v-html="props.row.type">{{ props.row.type }}</span>
                </el-form-item>
                <el-form-item label="制片地区">
                  <span v-html="props.row.areas">{{ props.row.areas }}</span>
                </el-form-item>
                <el-form-item label="主演">
                  <span v-html="props.row.actor">{{ props.row.actor }}</span>
                </el-form-item>
                <el-form-item label="豆瓣评分">
                  <span>{{ props.row.grade }}</span>
                </el-form-item>
                <el-form-item label="剧情简介">
                  <span v-html="props.row.abst">{{ props.row.abst }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            label="电影名称"
            prop="name">
          </el-table-column>
          <el-table-column
            label="导演"
            prop="author">
          </el-table-column>
          <el-table-column
            label="地区"
            prop="areas">
          </el-table-column>
          <el-table-column
            label="类型"
            prop="type">
          </el-table-column>
          <el-table-column
            label="豆瓣评分"
            prop="grade">
          </el-table-column>
        </el-table>
      </el-card>
      <el-card v-else>
        <el-row>
          <el-col :span="3" v-for="tue in MovieList" :key="o" :offset="2">
            <el-card :body-style="{ padding: '0px' }" shadow="hover" style="margin-bottom: 20px;">
              <img :src="tue.picture" class="image">
              <div style="padding: 14px;font-size: 15px;height: 40px;">
                <div style="height:22px"><span v-html="tue.name">{{tue.name}}</span></div>
                <el-rate
                v-model="tue.grade"
                disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}">
                </el-rate>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </el-card>
  </div>
</template>

<script>
    export default {
      data(){
        return{

          activeName: '1',
          Search1:'',
          MovieList:'',
          value4: false,
          Search2:{
            Line1:"",
            KeyWord1:'',
            Option1:2,
            Line2:"",
            KeyWord2:'',
            Option2:2,
            Line3:"",
            KeyWord3:''
          },
          OptionList:[{
            name:"AND",
            key:1,
          },{
            name:"OR",
            key:2,
          },{
            name:"AND NOT",
            key:3,
          },{
            name:"OR NOT",
            key:4,
          },{
            name:"XOR",
            key:5,
          }],
          lineList:[{
            name:"所有属性",
            key:"",
          },{
            name:"电影名",
            key:"name",
          },{
            name:"类型",
            key:"type",
          },{
            name:"地区",
            key:"areas",
          },{
            name:"主演",
            key:"actor",
          },{
            name:"剧情简介",
            key:"abst",
          }]
        }
      },
      mounted(){
        this.getAllMovie();
      },
      methods:{
        getAllMovie(){
          this.axios.get('/tf-idf_search', {
            params: {
              question:'他拥有人的心智，却拥有一双剪刀手'
            },
            headers: {
            }
          }).then(res => {
            if (res.data.success) {
              this.MovieList = res.data.data.movies
              // this.teacherDirectingListSearchGraduateValue = '-1'
              // this.getTeacherList()
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
        SearchInfo1(){
          this.axios.get('/common-search', {
            params: {
              question: this.Search1,
            },
            headers: {}
          }).then(res => {
            if(res.data.success) {
              this.MovieList = res.data.data.movies
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
        SearchInfo2(){
          this.axios.get('/key-search', {
            params: {
              Line1:this.Search2.Line1,
              KeyWord1:this.Search2.KeyWord1,
              Option1:this.Search2.Option1,
              Line2:this.Search2.Line2,
              KeyWord2:this.Search2.KeyWord2,
              Option2:this.Search2.Option2,
              Line3:this.Search2.Line3,
              KeyWord3:this.Search2.KeyWord3,
            },
            headers: {}
          }).then(res => {
            if(res.data.success) {
              this.MovieList = res.data.data.movies
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
        }
      },
      comments:{

      }
    }
</script>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
.col-head-style{
  font-size:15px;
  text-align: left;
  font-weight:bold;
}
  .col-init-style-1{
    margin-left: 40px;
    width: 87%;
    text-align: center;
  }
  .col-init-style-2{
    width: 30%;
    display: inline-block;
  }
.el-select .el-input {
  width: 120px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}  .time {
     font-size: 13px;
     color: #999;
   }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
    height:180px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
</style>
