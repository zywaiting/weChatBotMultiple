package xin.zhuyao.wechatbotmultiple.entity;


import xin.zhuyao.wechatbotmultiple.entity.wechat.WeChatClient;

/**
 * @ClassName SingletonClient
 * @Description: TODO
 * author zy
 * @date 2019/7/1 23:55
 **/
public class SingletonClient {
    private static WeChatClient instance = new WeChatClient();
    private SingletonClient (){}
    public static WeChatClient getInstance() {
        return instance;
    }
}
