package com.github.xia.security.common.biz;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @explain：mapper基本操作类
 * @author: XIA
 * @date: 2020-02-02
 * @since: JDK 1.8
 * @version: 1.0
 */
public abstract class BaseBiz<M extends Mapper<T>, T> {

    @Autowired
    private M mapper;

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    public void insert(T entity) {
        mapper.insert(entity);
    }

    public void insertSelective(T entity) {
        mapper.insertSelective(entity);
    }

    public void delete(T entity) {
        mapper.delete(entity);
    }

    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void updateById(T entity) {
        mapper.updateByPrimaryKey(entity);
    }

    public void updateSelectiveById(T entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }
}
