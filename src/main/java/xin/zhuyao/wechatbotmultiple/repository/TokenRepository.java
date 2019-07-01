package xin.zhuyao.wechatbotmultiple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import xin.zhuyao.wechatbotmultiple.entity.TokenEntity;

/**
 * @ClassName TokenRepository
 * @Description: TODO
 * author zy
 * @date 2019/7/1 23:43
 **/
@Repository
public interface TokenRepository  extends JpaRepository<TokenEntity, Integer>, JpaSpecificationExecutor<TokenEntity> {

}
