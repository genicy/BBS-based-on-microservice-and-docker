/*记录数组里面有数据但显示长度却为零的问题*/
import axios from 'axios'
export function getAllLabel(userid) {
    var item  = new Set()
    var _this = this
    var array=[]
    axios.post("http://localhost:8013/qa/problem/userLabel/"+userid,{
    }).then(function (res) {
      res.data.forEach((item2, index)=>{
        array.push(item2.data.labelname)
        item.add(item2.data.labelname)
        /*alllabels.set(item2.data.labelname,item2.data.labelname)*/
      })
    })
    axios.get("http://localhost:8013/base/label")
      .then(function (res) {
        res.data.data.forEach((item2, index)=>{
          /* alllabels.set(item2.labelname,item2.labelname)*/
          item.add(item2.labelname)
          array.push(item2.labelname)
        })
      })

  var hash=[];
  array.length=10
  console.log(array)
  item.add('aaa')
  console.log(Array.from(item))



}
