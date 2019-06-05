<!--专栏类似公众号，媒体号，官方等等，只有专栏能够发文章，具体参考https://xiaozhuanlan.com/-->
<template>
  <div>

    <el-col :span="24" class="toolbar" style="padding-bottom: 0px; height: 60px;margin-top: 10px">
      <el-form :inline="true" style="float: left">
        <el-form-item>
          <el-input placeholder="姓名" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createNew">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-table
      :data="tableData"
      border


      @selection-change="handleSelectionChange"
      style="width: 100%;">
      <el-table-column type="selection" width="45"/>

      <el-table-column
        prop="id"
        label="企业id"
        sortable
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="企业名称"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="summary"
        label="企业简介"
        sortable

        width="150">
      </el-table-column>
      <el-table-column
        prop="address"
        label="企业地址"
        sortable
        width="100">
      </el-table-column>
      <el-table-column
        prop="labels"
        label="企业标签"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="coordinate"
        label="坐标"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="logo"
        label="LOGO"
        width="50">
        <template slot-scope="scope">
          <img :src="scope.row.logo" alt="LOGO" width="100%"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="url"
        label="URL"
        sortable
        width="50">
      </el-table-column>
      <el-table-column
        prop="ishot"
        label="热门"
        sortable
        width="50">
      </el-table-column>

      <el-table-column
        fixed="right"
        label="操作"

        width="100">
        <template slot-scope="scope">
          <el-button @click="handleEditClick(scope.$index,scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleRecruitClick(scope.$index,scope.row)" type="text" size="small">职位</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
   <!-- <el-col style="padding: 20px;height: 40px"><el-button style="float: left" type="danger" @click="multiDelete">批量删除</el-button>-->
      <el-pagination
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        style="float: right;height: 40px"
        :page-size="10"
        :total="50">
      </el-pagination></el-col>

    <!--编辑界面-->
    <el-dialog title="企业编辑"  :visible.sync="editFormVisible" width="60%">
      <el-form :model="editEnterprise" label-width="80px" >
        <el-form-item label="企业名称" >
          <el-input v-model="editEnterprise.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="LOGO">
          <el-upload
            class="upload-demo"
            name="img"
            :on-success="uploadCoverSuccess"
            :action="coverServerUrl"
            :headers="headers"
            limit=1
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="企业地址" >
          <el-input  v-model="editEnterprise.address" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="企业坐标" >

          <div class="amap-page-container">
            <el-amap-search-box class="search-box"  :on-search-result="onSearchResult"></el-amap-search-box>
            <el-amap vid="amapDemo" :center="mapCenter" :zoom="12" class="amap-demo" :events="events">
              <el-amap-marker v-for="marker in markers" :position="marker" ></el-amap-marker>

            </el-amap>
          </div>
          <el-input :disabled="true" v-model="editEnterprise.coordinate" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="企业简介" >
          <el-input type="textarea" v-model="editEnterprise.summary" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="企业标签" >
          <el-input type="textarea" v-model="editEnterprise.labels" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="企业链接" >
          <el-input v-model="editEnterprise.URL" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="热门">
          <el-radio-group v-model="editEnterprise.state" style="float: left;margin-top: 10px">
            <el-radio class="radio" :label="1">是</el-radio>
            <el-radio class="radio" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editEnterpriseFunc">提交</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {

  data () {
    let self = this
    return {

      lng: 0,
      lat: 0,
      address: '',
      markers: [
        [121.5273285, 31.21515044]
      ],
      /*      searchOption: {
        city: '上海',
        citylimit: true
      }, */
      mapCenter: [121.455739, 31.249563],
      coverServerUrl: 'http://localhost:8012/article/article/uploadImg/2',
      imgCoverPath: 'http://localhost:8004/cover/',
      headers: {
        'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
      },
      editFormVisible: false,
      editEnterprise: {},
      pageSize: 5,
      pageCount: 1,
      tableData: [],
      multipleSelection: [],
      events: {
        click (e) {
          let { lng, lat } = e.lnglat
          self.lng = lng
          self.lat = lat
          /*          //坐标地址转换
          var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: 'all'
          })
          geocoder.getAddress([lng, lat], function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
              if (result && result.regeocode) {
                self.address = result.regeocode.formattedAddress
              }
            }
          }) */
          console.log(self.lng + ',' + self.lat)
          self.editEnterprise.coordinate = self.lng + ',' + self.lat
        }
      }
    }
  },
  methods: {
    addMarker: function () {
      let lng = 121.5 + Math.round(Math.random() * 1000) / 10000
      let lat = 31.197646 + Math.round(Math.random() * 500) / 10000
      this.markers.push([lng, lat])
    },
    onSearchResult (pois) {
      console.log(pois)
      let latSum = 0
      let lngSum = 0

      if (pois.length > 0) {
        pois.forEach(poi => {
          let {lng, lat} = poi
          lngSum += lng
          latSum += lat
          this.markers.push([poi.lng, poi.lat])
        })
        let center = {
          lng: lngSum / pois.length,
          lat: latSum / pois.length
        }
        this.mapCenter = [center.lng, center.lat]
      }
    },
    uploadCoverSuccess (res, file) {
      var filePath = this.imgCoverPath + res.data
      this.editEnterprise.logo = filePath
      // res为后端返回的结果集，code为自定义
      if (res.code == '20000') {
        this.$message.success('图片插入成功')
      } else {
        this.$message.info('图片已传输后台，但前台显示错误')
      }
    },
    multiDelete () {
      var _this = this
      console.log(this.multipleSelection.length)
      for (var i = 0; i < this.multipleSelection.length; i++) {
        this.$axios.delete('http://localhost:8012/recruit/enterprise/' + this.multipleSelection[i].id, {
          headers: {
            'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
          }
        }).then(function (res) {
          if (res.data.code == '20000') {
            _this.$message.success('删除成功')
          } else {
            _this.$message.error('删除失败')
          }
        })
      }
    },
    // 多选框点击函数
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    createNew () {
      this.editFormVisible = true
      this.editEnterprise = {}
    },
    editEnterpriseFunc () {
      /* 判断是修改还是新增 */
      if (this.editEnterprise.id) {
        var _this = this
        this.$axios.put('http://localhost:8012/recruit/enterprise/' + this.editEnterprise.id, this.editEnterprise, {
          headers: {
            'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
          }
        }).then(function (res) {
          if (res.data.code == '20000') {
            _this.$message.success('修改成功')
          } else {
            _this.$message.error('修改失败')
          }
        })
      } else {
        var _this = this
        this.$axios.post('http://localhost:8012/recruit/enterprise/', this.editEnterprise, {
          headers: {
            'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
          }
        }).then(function (res) {
          if (res.data.code == '20000') {
            _this.$message.success('新增成功')
          } else {
            _this.$message.error('新增失败')
          }
        })
      }
      this.editFormVisible = false
    },
    /* 改变当前页 */
    handleCurrentChange (val) {
      this.pageCount = val
      this.getEnterprise()
    },
    /* 点击编辑 */
    handleEditClick (index, row) {
      this.editEnterprise = row
      this.editFormVisible = true
      if (this.editEnterprise.coordinate) {
        var location = this.editEnterprise.coordinate.split(',')
        this.markers[0] = [location[0], location[1]]
        this.mapCenter = [location[0], location[1]]
        console.log(this.markers + '---' + this.mapCenter)
      }

      console.log(this.editEnterprise + this.editFormVisible)
    },
    handleRecruitClick (index, row) {
      this.$router.push({name: 'recruit', params: {id: row.id}})
    },
    /* 点击查看 */
    handleShowClick (index, row) {
      this.editEnterprise = row
      console.log(this.editEnterprise)
    },
    /* 分页获取频道 */
    getEnterprise () {
      var _this = this
      this.$axios.post('http://localhost:8012/recruit/enterprise/search/' + this.pageCount + '/' + this.pageSize, {

      }, {
        headers: {
          'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
        }
      }).then(function (res) {
        if (res.data.flag === true) {
          _this.tableData = res.data.data.rows
          console.log(_this.tableData)
        }
      })
    }

  },
  mounted () {
    this.getEnterprise()
  }

}

</script>

<style scoped>

  element.style {
  }
  user agent stylesheet
  div {
    display: block;
  }
  .el-dialog--center .el-dialog__footer {
    text-align: inherit;
    margin-top: -60px;
  }
  .quill-editor {
    min-height: 200px;
    max-height: 400px;
    overflow-y: auto;
  }
  .amap-demo {
    height: 300px;
  }

  .search-box {
    position: absolute;
    top: 25px;
    left: 20px;
  }

  .amap-page-container {
    position: relative;
  }
</style>
