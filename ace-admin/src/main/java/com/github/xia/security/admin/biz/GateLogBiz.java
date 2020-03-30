package com.github.xia.security.admin.biz;

import com.github.xia.security.admin.entity.GateLog;
import com.github.xia.security.admin.mapper.GateLogMapper;
import com.github.xia.security.common.biz.BaseBiz;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     网关登录日志
 * </p>
 *
 * @author: xia
 * @date: 2020-03-22
 * @since: JDK 1.8
 * @version: 1.0
 */
@Service
public class GateLogBiz extends BaseBiz<GateLogMapper, GateLog> {

    @Override
    public void insert(GateLog entity) {
        mapper.insert(entity);
    }

    @Override
    public void insertSelective(GateLog entity) {
        mapper.insertSelective(entity);
    }

}
