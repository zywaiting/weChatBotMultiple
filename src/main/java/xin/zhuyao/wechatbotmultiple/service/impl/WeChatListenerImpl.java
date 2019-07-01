package xin.zhuyao.wechatbotmultiple.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xin.zhuyao.wechatbotmultiple.entity.contact.WXContact;
import xin.zhuyao.wechatbotmultiple.entity.contact.WXGroup;
import xin.zhuyao.wechatbotmultiple.entity.entityvm.SendMessageVm;
import xin.zhuyao.wechatbotmultiple.entity.message.WXMessage;
import xin.zhuyao.wechatbotmultiple.entity.wechat.WeChatClient;
import xin.zhuyao.wechatbotmultiple.utils.GuavaCacheUtils;
import xin.zhuyao.wechatbotmultiple.utils.PropertiesUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @ClassName WeChatListenerImpl
 * @Description: TODO
 * author zy
 * @date 2019/6/30 8:00
 **/
@Service
@Slf4j
public class WeChatListenerImpl extends WeChatClient.WeChatListener {

    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    @Override
    public void onQRCode(@Nonnull WeChatClient client, @Nonnull String qrCode) {
//        System.out.println("onQRCode：" + qrCode);
    }

    @Override
    public void onLogin(@Nonnull WeChatClient client) {
        System.out.println(String.format("onLogin：您有%d名好友、活跃微信群%d个", client.userFriends().size(), client.userGroups().size()));
    }

    @Override
    public void onMessage(@Nonnull WeChatClient client, @Nonnull WXMessage message) {
        if (message.getFromGroup() != null) {
            try {
                SendMessageVm sendMessageVm = PropertiesUtil.getProperties();
                WXGroup fromGroup = message.getFromGroup();
                log.info("接收到群 [{}] 的消息: {}", fromGroup.getName(), message.getContent());
                List<String> groupList = sendMessageVm.getGroupList();
                if (StringUtils.isNotEmpty(fromGroup.getName()) && groupList.stream().anyMatch(group -> fromGroup.getName().contains(group)) && !message.getContent().contains("标志位:")) {
                    Object object = GuavaCacheUtils.get(fromGroup.getName());
                    if (object != ObjectUtils.NULL) {
                        GuavaCacheUtils.put(fromGroup.getName(), (Integer) object + 1);
                    } else {
                        GuavaCacheUtils.put(fromGroup.getName(), 1);
                    }
                    int num = sendMessageVm.getNum() < 20 ? 20 : sendMessageVm.getNum();
                    if ((Integer) GuavaCacheUtils.get(fromGroup.getName()) % num == 0) {
//                        String sendMessage = "标志位:" + sendMessageVm.getDescription() + "\r\n";
                        String sendMessage = "微信自动发送机器人\r\n百度网盘地址:https://pan.baidu.com/s/1fMtVH8Cd5AMJFmDSQNfknA,\r\n提取码:bs5v" + "\r\n";
                        List<String> urlList = sendMessageVm.getUrlList();
                        for (String url : urlList) {
                            sendMessage = sendMessage + "\r\n\r\n" + url;
                        }
                        sendMessage(client, fromGroup, sendMessage);

                    }
                    if (message.getContent().contains("加入群聊")) {
                        String sendMessage = sendMessageVm + "\r\n";
                        List<String> urlList = sendMessageVm.getUrlList();
                        sendMessage = sendMessage + "\r\n" + urlList.get(0);
                        sendMessage(client, fromGroup, sendMessage);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(@Nonnull WeChatClient client, @Nonnull WXGroup fromGroup, String sendMessage) {
        WXContact contact = client.userContact(fromGroup.getId());
        if (contact != null) {
            Object objectNum = GuavaCacheUtils.get("num");
            if (objectNum != ObjectUtils.NULL) {
                GuavaCacheUtils.put("num", (Integer) objectNum + 1);
            } else {
                GuavaCacheUtils.put("num", 1);
            }
            log.info("\r\n\r\n已发送" + GuavaCacheUtils.get("num") + "次");
            log.info("success:" + GSON.toJson(client.sendText(contact, sendMessage)));
        } else {
            log.info("联系人未找到");
        }
    }


    @Override
    public void onContact(@Nonnull WeChatClient client, @Nullable WXContact oldContact, @Nullable WXContact newContact) {
        System.out.println(String.format("检测到联系人变更:旧联系人名称：%s:新联系人名称：%s", (oldContact == null ? "null" : oldContact.name), (newContact == null ? "null" : newContact.name)));
    }
}
