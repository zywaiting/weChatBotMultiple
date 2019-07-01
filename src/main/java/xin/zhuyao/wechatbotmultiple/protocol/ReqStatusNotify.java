package xin.zhuyao.wechatbotmultiple.protocol;

public class ReqStatusNotify {
    public xin.zhuyao.wechatbotmultiple.protocol.BaseRequest BaseRequest;
    public int Code;
    public String FromUserName;
    public String ToUserName;
    public long ClientMsgId;

    public ReqStatusNotify(xin.zhuyao.wechatbotmultiple.protocol.BaseRequest baseRequest, int code, String myName) {
        this.BaseRequest = baseRequest;
        this.Code = code;
        this.FromUserName = myName;
        this.ToUserName = myName;
        this.ClientMsgId = System.currentTimeMillis();
    }
}
