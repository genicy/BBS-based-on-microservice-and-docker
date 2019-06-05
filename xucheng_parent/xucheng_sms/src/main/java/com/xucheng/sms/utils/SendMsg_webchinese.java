package com.xucheng.sms.utils;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/23 16:39
 * @Version: 1.0
 */
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

@Component
public class SendMsg_webchinese {

    public void sendSms(String mobile, String checkCode)throws Exception
    {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data ={ new NameValuePair("Uid", "yangxucheng"),new NameValuePair("Key", "d41d8cd98f00b204e980"),new NameValuePair("smsMob",mobile),new NameValuePair("smsText","亲爱的用户，感谢您注册YXC个人平台，您的验证码为："+checkCode+"(三分钟内有效)，请勿泄露。")};
        post.setRequestBody(data);
        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:"+statusCode);
        for(Header h : headers)
        {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result); //打印返回消息状态


        post.releaseConnection();

    }

}
