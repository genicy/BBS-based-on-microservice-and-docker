

export function initWebSocket(userid) {

  // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
  var websock = new WebSocket("ws://localhost:8009/websocket/"+userid);
  websock.onopen = websocketonopen;
  websock.onerror = websocketonerror;
  websock.onmessage =websocketonmessage;
  websock.onclose = websocketclose;
}
export var info=[];
 export function websocketonopen () {
   console.log("WebSocket连接成功");
 }
 export function websocketonerror(e) {
   console.log("WebSocket连接发生错误");
 }
 export function websocketonmessage (e) {

   info.push(e.data);
   console.log('这是测试:'+info)
   return info

 }
 export function getInfo() {
   return info
 }
 export function websocketclose(e) {
   console.log("connection closed )");
 }
