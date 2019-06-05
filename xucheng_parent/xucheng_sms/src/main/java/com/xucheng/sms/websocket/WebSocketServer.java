package com.xucheng.sms.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import com.alibaba.fastjson.JSONObject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.xucheng.sms.pojo.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/5/6 10:54
 * @Version: 1.0
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    @Autowired
    private RedisTemplate redisTemplate;

    //静态变量，用来记录当前在线连接数。线程安全。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid="";
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
        this.sid=sid;
/*        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            System.out.println("websocket IO异常");
        }*/
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自窗口"+sid+"的信息:"+message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     * */
    public void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        String[] strings = message.split(";");
        //info为定义的消息格式，分别为昵称 动作 对象；如张三 评论了你的文章 如何学习...
        Info info = new Info(strings[1], strings[2], strings[3]);
        message = JSONObject.toJSONString(info);
        System.out.println("推送消息到窗口"+sid+"，推送内容:"+message);
        int i=0;
        for (WebSocketServer item : webSocketSet) {
            ++i;
            try {
/*                //这里可以设定只推送给这个sid的，为null则全部推送
                *//*if(sid==null) {
                    item.sendMessage(message);
                }else*//* */if(item.sid.equals(sid)){
                    item.sendMessage(message);
                    break;
                }
                else if(i==webSocketSet.size()){
                    //接收的用户可能不在线，先放在redis里面，用户登录时就扫一遍
                    //可能不止一条消息，故一个用户的消息对应一个map，放在redis中
                    Map<String, String> map = new HashMap<>();
                    try {
                        map = redisTemplate.opsForHash().entries("ws="+sid);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    map.put(message,message);
                    redisTemplate.opsForHash().putAll("ws="+sid, map);
                    System.out.print("发送失败，先放在redis里面");
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
