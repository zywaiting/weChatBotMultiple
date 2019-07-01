package xin.zhuyao.wechatbotmultiple.entity.entityvm;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName SendMessageVm
 * @Description: TODO
 * author zy
 * @date 2019/7/1 23:02
 **/
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendMessageVm implements Serializable {

    /**
     * 描述
     */
    String description;

    /**
     * 连接列表
     */
    List<String> urlList;

    /**
     * 群列表
     */
    List<String> groupList;

    /**
     * 秘钥
     */
    String key;

    /**
     * 条数
     */
    Integer num;

    public SendMessageVm(String description, String urlStr, String groupStr, String key, String num) {
        this.description = description.replace("description=", "");
        this.urlList = Arrays.asList(urlStr.replace("url=", "").split(","));
        this.groupList = Arrays.asList(groupStr.replace("group=","").split(","));
        this.key = key.replace("key=", "");
        this.num = Integer.valueOf(num.replace("num=", ""));
    }

}
