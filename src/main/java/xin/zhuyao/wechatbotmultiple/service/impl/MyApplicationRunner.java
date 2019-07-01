package xin.zhuyao.wechatbotmultiple.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import xin.zhuyao.wechatbotmultiple.entity.SingletonClient;
import xin.zhuyao.wechatbotmultiple.entity.TokenEntity;
import xin.zhuyao.wechatbotmultiple.entity.entityvm.SendMessageVm;
import xin.zhuyao.wechatbotmultiple.entity.wechat.WeChatClient;
import xin.zhuyao.wechatbotmultiple.repository.TokenRepository;
import xin.zhuyao.wechatbotmultiple.utils.PropertiesUtil;

import java.util.Optional;

/**
 * @ClassName MyApplicationRunner
 * @Description: TODO
 * author zy
 * @date 2019/6/30 4:24
 **/
@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private WeChatListenerImpl weChatListener;
    @Autowired
    private TokenRepository tokenRepository;
    // 在需要停止的类注入
    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        Optional<TokenEntity> byId = tokenRepository.findById(1);
        if (byId.isPresent()) {
            try {
                TokenEntity tokenEntity = byId.get();
                SendMessageVm sendMessageVm = PropertiesUtil.getProperties();
                if (tokenEntity.getToken().equals(sendMessageVm.getKey())) {
                    //新建一个模拟微信客户端
                    WeChatClient wechatClient = SingletonClient.getInstance();
                    //为模拟微信客户端设置监听器
                    wechatClient.setListener(weChatListener);
                    //启动模拟微信客户端
                    wechatClient.startup();
                }else {
                    log.warn("++++++++++++++++key不对,请退出，填写正确key，然后重启+++++++++++++++++");
                    context.close();
                }
            } catch (Exception e) {
                log.warn("++++++++++++++++key不对,请退出，填写正确key，然后重启+++++++++++++++++");
                context.close();
                e.printStackTrace();
            }
        }

    }
}
