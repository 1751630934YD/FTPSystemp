<template>
  <div id="app">

    <el-dialog title="FTP连接登录" :visible.sync="centerDialogVisible" width="45%" center :close-on-click-modal="false"
      :show-close=false>
      <el-input placeholder="请输入内容" v-model="FTPurl">
        <template slot="prepend">FTPURL</template>
      </el-input>
      <el-input style="margin-top: 15px;" placeholder="请输入内容" v-model="user">
        <template slot="prepend">账号</template>
      </el-input>
      <el-input style="margin-top: 15px;" placeholder="请输入内容" v-model="password">
        <template slot="prepend">密码</template>
      </el-input>
      <span slot="footer" class="dialog-footer">

        <el-button type="primary" @click="butn()">确 定</el-button>
      </span>
    </el-dialog>

    <el-container>
      <el-header>FTP站内搜索</el-header>
      <el-main>
        <FTPindex :mes="centerDialogVisible"/>
      </el-main>
    </el-container>

  </div>
</template>

<script>
import FTPindex from './components/FTPidnex.vue'

export default {
  name: 'App',
  components: {
    FTPindex
  },
  data() {
    return {
      FTPurl: "",
      password: "",
      user: "",
      centerDialogVisible: true
    }
  },

  mounted() {
    if (this.$cookies.get('value') != null) {
      this.$data.centerDialogVisible = false;
    }




  }
  ,
  methods: {
    butn() {
      const Login = {
        FTPurl: this.$data.FTPurl,
        user: this.$data.user,
        password: this.$data.password

      }
      this.$cookies.set('value', JSON.stringify(Login), { expires: 1 / 24 });
      this.$data.centerDialogVisible = false;



    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;

}


.el-header,
.el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
  height: 100%;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {

  color: #333;
  text-align: center;

}

body>.el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
