package xin.zhuyao.wechatbotmultiple.utils;

import lombok.extern.slf4j.Slf4j;
import xin.zhuyao.wechatbotmultiple.entity.entityvm.SendMessageVm;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName PropertiesUtil
 * @Description: TODO
 * author zy
 * @date 2019/7/1 22:48
 **/
@Slf4j
public class PropertiesUtil {
    public static SendMessageVm getProperties() throws Exception {
        //参数为空
        File directory = new File("");
        List<String> stringList = Files.readAllLines(Paths.get(directory.getCanonicalPath() + "/properties.txt"));
        HashMap<Integer,String> hashMap = new HashMap<>();
        for (String str : stringList) {
            if (str.contains("description=")) {
                hashMap.put(0,str);
            }
            if (str.contains("url=")) {
                hashMap.put(1,str);
            }
            if (str.contains("group=")) {
                hashMap.put(2,str);
            }
            if (str.contains("key=")) {
                hashMap.put(3,str);
            }
            if (str.contains("num=")) {
                hashMap.put(4,str);
            }
        }
        if (hashMap.keySet().size() == 5) {
            return new SendMessageVm(hashMap.get(0), hashMap.get(1), hashMap.get(2), hashMap.get(3), hashMap.get(4));
        }
        log.warn("配置文件有问题");
        throw new Exception("配置文件有问题");
    }
}
