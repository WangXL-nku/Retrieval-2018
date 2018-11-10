

<template>

<div>
  <!--<page-header></page-header>-->

  <!--<div class="line"></div>-->
    <router-view id="main"></router-view>

  <!--<page-footer></page-footer>-->
</div>

</template>

<script>

import PageFooter from '@/components/PageFooter'
import PageHeader from '@/components/PageHeader'
export default {
    data() {
            return {
              activeIndex:'/student-postgraduate',
                user: {},
                menus: []
            }
        },
        components: {
            PageFooter,
            PageHeader
        },
        methods: {
            handleSelect(key, keyPath) {
          if(key==="logout"){
              this.logout()
          }else{
              this.$router.push({
                  path: key
              })
          }

            },
          logout: function() {
            this.$confirm("确定退出?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "info"
            }).then(() => {
              this.$emit("logout");
            }).catch(() => {});
          },

        }

}

</script>
<style>

a{
    text-decoration: none;
    color: #36648B;
}
.el-menu--popup-bottom-start{
    background: #909399;
    width:35px;

}
.navigate-bar-style {
    margin-top: 10px;
    margin-bottom: 10px;
}

.bread-crumb-separate-line {
  border-top: 2px solid #87ceeb;
  border-bottom: 2px solid #87ceeb;
  height: 40px;
  line-height: 40px;
  margin-bottom: 30px;
}
.nav-btn-font {
    color: #3D3D3D;
}

.nav-btn-font:hover {
    color: #FCFCFC;
    font-weight: bold;
}

.bread-crumb-separate-line {
    border-top: 2px solid #87ceeb;
    border-bottom: 2px solid #87ceeb;
    height: 40px;
    line-height: 40px;
    margin-bottom: 30px;
}

.bread-crumb-font {
    font-size: 17px;
    margin-top: 10px;
    margin-left: 10px;
    width: 80%;
    margin-right: 0;
}

@media (max-width: 768px) {
    .el-button--primary.is-plain {
        width: 9%;
        margin-left: 0;
        font-size: 1px;
    }
    .el-button--medium.is-round {
        padding: 5px 2px;
    }
    .el-icon-d-arrow-right {
        display: none;
    }
    .el-popover {
        width: 90%;
    }
}


/* 小屏幕（平板，大于等于 768px） */

@media (min-width: 768px) and (max-width: 991px) {
    .el-button--primary.is-plain {
        margin-left: 0;
        font-size: 12px;
    }
    .el-button--medium.is-round {
        padding: 10px 10px;
    }
    .el-icon-d-arrow-right {
        display: none;
    }
    .el-popover {
        width: 90%;
    }
}


/* 中等屏幕（桌面显示器，大于等于 992px） */

@media (min-width: 992px) and (max-width: 1367px) {
    .el-button--primary.is-plain {
        margin-left: 0;
        font-size: 12px;
    }
    .el-button--medium.is-round {
        padding: 10px 10px;
    }
    .el-popover {
        width: 70%;
    }
}


/* 中等屏幕（桌面显示器，大于等于 992px） */

@media (min-width: 1368px) and (max-width: 2500px) {
    .el-button--primary.is-plain {
        margin-left: 2px;
        font-size: 14px;
    }
    .el-popover {
        width: 900px;
    }
    /* .el-button--medium.is-round {
    padding: 10px 10px;
  } */
}

</style>
