package xin.zhuyao.wechatbotmultiple.protocol;

public class ReqRevokeMsg {
    public xin.zhuyao.wechatbotmultiple.protocol.BaseRequest BaseRequest;
    public String ClientMsgId;
    public String SvrMsgId;
    public String ToUserName;

    public ReqRevokeMsg(xin.zhuyao.wechatbotmultiple.protocol.BaseRequest baseRequest, String clientMsgId, String serverMsgId, String toUserName) {
        this.BaseRequest = baseRequest;
        this.ClientMsgId = clientMsgId;
        this.SvrMsgId = serverMsgId;
        this.ToUserName = toUserName;
    }
}
