package com.github.xia.security.admin.biz;

import com.github.xia.security.admin.entity.GateClient;
import com.github.xia.security.admin.mapper.GateClientMapper;
import com.github.xia.security.common.biz.BaseBiz;
import com.github.xia.security.common.constant.UserConstant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     网关客户service
 * </p>
 *
 * @author: xia
 * @date: 2020-03-20
 * @since: JDK 1.8
 * @version: 1.0
 */
@Service
public class GateClientBiz extends BaseBiz<GateClientMapper, GateClient> {

    @Override
    public void insertSelective(GateClient entity) {
        String secret = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getSecret());
        entity.setSecret(secret);
        super.insertSelective(entity);
    }

    @Override
    public void updateById(GateClient entity) {
        GateClient old = mapper.selectOne(entity);
        if(!entity.getSecret().equals(old.getSecret())){
            String secret = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getSecret());
            entity.setSecret(secret);
        }
        super.updateById(entity);
    }

}

