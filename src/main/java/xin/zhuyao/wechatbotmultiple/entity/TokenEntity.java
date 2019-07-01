package xin.zhuyao.wechatbotmultiple.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName TokenEntity
 * @Description: TODO
 * author zy
 * @date 2019/7/1 23:41
 **/
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_token")
public class TokenEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * token
     */
    String token;
}
