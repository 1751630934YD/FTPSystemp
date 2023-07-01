<template>
  <div>

    <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
      <el-select v-model="select" slot="prepend" placeholder="请选择">
        <el-option label="All" value="All"></el-option>
        <el-option label="user" value="user"></el-option>
      </el-select>
      <el-button slot="append" icon="el-icon-search" @click="() => searchbtn()"></el-button>
    </el-input>

    <el-tree :props="props" :data="data" ref="tree" node-key="id" @node-drag-start="handleDragStart" icon-class="null"
      :load="loadNode" lazy @node-drag-enter="handleDragEnter" @node-drag-leave="handleDragLeave"
      @node-drag-over="handleDragOver" @node-drag-end="handleDragEnd" @node-drop="handleDrop" draggable
      :allow-drop="allowDrop" :allow-drag="allowDrag" v-loading="loading" element-loading-text="搜索中,请稍后">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <i :class="data.icon" style="float: left;line-height: 50px;"></i>
        <span style="font-size: 14px;float: left;align-items: center;">{{ node.label }}</span>

        <span style="float: right;">
          <el-upload class="upload-demo" v-if="data.filetype == 1" action="" :http-request="uploadfun"
            :show-file-list=false multiple><el-button @click="() => append(data)" type="primary" size="mini">上传<i
                class="el-icon-upload el-icon--right"></i></el-button></el-upload>
          <el-button type="primary" v-else size="mini" @click="() => download(node, data)">下载<i
              class="el-icon-download"></i></el-button>
        </span>
      </span>

    </el-tree>
  </div>
</template>
<script>
var notifyelmtemplate;
export default {

  props: {
    mes: Boolean,

  },
  watch: {
    mes: {
      handler: function (newV, oldV) {
        this.InitData();
        // this.$data.first = false

      }
    }
  },
  mounted() {

    // console.log(this.$forceUpdate(this.$refs.tree))

  }
  ,
  data() {
    var data = [];
    return {
      loading: false,
      onclicktree: "",
      percentage: 0,
      showfliemes: true,
      // first: true,
      input1: '',
      input2: '',
      input3: '',
      select: '',
      data: JSON.parse(JSON.stringify(data)),
      props: {
        isLeaf: 'leaf',

      }
    };
  },

  computed: {
    progressTemplate() {
      const progress = this.$createElement('el-progress', {
        props: { percentage: this.$data.percentage },
        style: 'min-width:220px'
      });

      return this.$createElement('div', [progress]);
    },
  },

  methods: {
    searchbtn() {
      this.select = (this.select === null || this.select === undefined || this.select === "") ? "All" : this.select;
      this.loading = true;

      setTimeout(() => {
        if (this.input3 == "" || this.input3 == null || this.input3 == undefined) {
          this.InitData();
          this.loading = false;
          return;
        }
        this.$axios({
          method: 'post', data: { path: this.select + "/" + this.input3 }, url: "/api/FTPSearch"
        }).then(response => {
          if (response.data != "error") {
            this.$data.data = [];
            this.$data.data.push({ icon: 'el-icon-paperclip', leaf: true, filetype: 2, label: response.data })
          }
          else {
            this.$message({
              message: '在' + this.select + '目录下未能搜索到' + this.input3 + '文件',
              type: 'warning'
            });
          }
        }).catch(function (error) {
          // 处理错误情况
          // console.log(e);
        }).then(e => {
          this.loading = false;
        });

      }, 1000);

    },

    download(node, data) {
      const tree = node.parent.data.label == undefined ? this.select : node.parent.data.label
      const children = node.data.label

      this.$axios({ method: 'post', url: "/api/FTPdownload", data: { path: tree + "/" + children }, responseType: 'blob' }).then(res => {
        // 处理成功情况

        //const fileName = res.headers["content-disposition"];
        const url = window.URL.createObjectURL(new Blob([res.data]))
        const a = document.createElement('a');
        a.href = url;
        a.download = node.data.label;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      }).catch(function (error) {
        // 处理错误情况
        // console.log(e);
      }).then(function () {
        // 总是会执行
      });
    },

    append(data) {
      console.log(data);
      this.onclicktree = data.label
    },
    showProgress() {
      notifyelmtemplate = this.$notify({
        title: '上传进度',
        dangerouslyUseHTMLString: true,
        message: this.progressTemplate,
        duration: 0 // 手动关闭

      });


    },

    uploadfun(file) {
      const val = this.$cookies.get('value') == null ? {} : JSON.parse(this.$cookies.get('value'));
      const formData = new FormData();
      const ftpLogin = { hostname: val.FTPurl, username: val.user, password: val.password, path: this.onclicktree + "/" }
      formData.append("Login", JSON.stringify(ftpLogin));
      formData.append("file", file.file);
      this.showProgress()
      this.$axios({
        method: 'post', data: formData, headers: { 'Content-Type': 'multipart/form-data' }, url: "/api/FTPUpload", onUploadProgress: progressEvent => {
          this.$data.percentage = Math.round((progressEvent.loaded / progressEvent.total) * 100)
          console.log('上传进度：' + this.$data.percentage + '%')
          notifyelmtemplate.$children[0].$props.percentage = this.$data.percentage;

        }
      }).then(response => {
        this.$data.data.forEach(item => {
          if (item.label == this.onclicktree) {
            item.children.push({ icon: 'el-icon-paperclip', leaf: true, filetype: 2, label: file.file.name })
          }
        })
      }).catch(function (error) {
        // 处理错误情况
        // console.log(e);
      }).then(function () {
        setTimeout(() => {
          notifyelmtemplate.close();
        }, 5000); // 延迟1秒钟执行
      });

    },




    InitData() {
      this.$data.data = [];
      this.$axios({ method: 'post', url: "/api/FTPFilelist", data: { path: "/" } }).then(res => {
        res.data.forEach(element => {
          this.$data.data.push({ icon: 'el-icon-folder', leaf: false, filetype: element.filetype, label: element.filename, children: [] })
        });
      }).catch(function (error) {
        // 处理错误情况
        // console.log(e);
      }).then(function () {
        // 总是会执行
      });

    },

    loadchildren(node, resolve) {
      this.$axios({ method: 'post', url: "/api/FTPFilelist", data: { path: "/" + node.label } }).then(res => {
        res.data.forEach(element => {
          node.data.children.push({ icon: 'el-icon-paperclip', leaf: true, filetype: element.filetype, label: element.filename })
          return resolve(node.data.children)
        })

      }).catch(function (error) {
        // 处理错误情况
        // console.log(e);
      }).then(function () {
        // 总是会执行

      });

    },


    loadNode(node, resolve) {
      // console.log(data);
      // console.log(node.data.label);
      // console.log(resolve);

      // if (!this.$data.first) {
      this.loadchildren(node, resolve)

      // }
    },

    handleDragStart(node, ev) {
      console.log('drag start', node);


    },
    handleDragEnter(draggingNode, dropNode, ev) {
      console.log('tree drag enter: ', dropNode.label);

    },
    handleDragLeave(draggingNode, dropNode, ev) {
      console.log('tree drag leave: ', dropNode.label);

    },
    handleDragOver(draggingNode, dropNode, ev) {
      console.log('tree drag over: ', dropNode.label);

    },
    handleDragEnd(draggingNode, dropNode, dropType, ev) {
      console.log('tree drag end: ', dropNode && dropNode.label, dropType);

    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log('tree drop: ', dropNode.label, dropType);
      console.log(ev);
    },
    allowDrop(draggingNode, dropNode, type) {
      if (dropNode.data.label === '二级 3-1') {
        return type !== 'inner';
      } else {
        return true;
      }
    },
    allowDrag(draggingNode) {
      return draggingNode.data.label.indexOf('三级 3-2-2') === -1;
    }
  }
};
</script>
<style>
.el-tree-node__content>.el-tree-node__expand-icon {
  padding: 3px;

}

.el-select .el-input {
  width: 130px;
}

.el-tree-node__content {
  display: inline-block;
  line-height: 50px;
  height: 50px;
  min-width: -webkit-fill-available;

}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>