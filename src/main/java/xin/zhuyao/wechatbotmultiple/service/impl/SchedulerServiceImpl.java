package xin.zhuyao.wechatbotmultiple.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xin.zhuyao.wechatbotmultiple.entity.SingletonClient;
import xin.zhuyao.wechatbotmultiple.entity.TokenEntity;
import xin.zhuyao.wechatbotmultiple.entity.entityvm.SendMessageVm;
import xin.zhuyao.wechatbotmultiple.entity.wechat.WeChatClient;
import xin.zhuyao.wechatbotmultiple.repository.TokenRepository;
import xin.zhuyao.wechatbotmultiple.utils.PropertiesUtil;

import java.util.Optional;

/**
 * @ClassName SchedulerServiceImpl
 * @Description: TODO
 * author zy
 * @date 2019/7/1 23:49
 **/
@Slf4j
@Service
public class SchedulerServiceImpl {

    // 在需要停止的类注入
    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private TokenRepository tokenRepository;

    //每30分钟执行一次
    @Scheduled(cron="0 0/1 * * * ?")
    public void statusCheck() {
        Optional<TokenEntity> byId = tokenRepository.findById(1);
        if (byId.isPresent()) {
            try {
                TokenEntity tokenEntity = byId.get();
                SendMessageVm sendMessageVm = PropertiesUtil.getProperties();
                if (!tokenEntity.getToken().equals(sendMessageVm.getKey())) {
                    WeChatClient wechatClient = SingletonClient.getInstance();
                    log.warn("++++++++++++++++key不对,请退出，填写正确key，然后重启+++++++++++++++++");
                    if (wechatClient != null) {
                        wechatClient.shutdown();
                        context.close();
                    }
                }
            } catch (Exception e) {
                log.warn("++++++++++++++++key不对,请退出，填写正确key，然后重启+++++++++++++++++");
                context.close();
            }
        }
    }

}
