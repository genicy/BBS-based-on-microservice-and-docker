package entity;

/**
 * @introduction: xucheng_parent
 * @Description: 状态码实体类
 * @Author: yangxucheng
 * @Date: 2019/1/16 15:51
 * @Version: 1.0
 */
public class StatusCode {
    public static final int OK=20000;//成功
    public static final int ERROR =20001;//失败
    public static final int LOGINERROR =20002;//用户名或密码错误
    public static final int ACCESSERROR =20003;//权限不足
    public static final int REMOTEERROR =20004;//远程调用失败
    public static final int REPERROR =20005;//重复操作

}
