package com.xx.vote.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.utils.ColumnUtil;
import com.xx.utils.ValidationUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/7 15:05
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 检查该字段集合组成的数据是否重复
     *
     * @param id     更新时所用的主键值，新增时传入空
     * @param fields 不能重复的字段集
     * @param values 需要检查重复的字段值集
     * @return 重复返回true, 不重复返回false
     */
    @Override
    public boolean checkDuplicate(Long id, String[] fields, String[] values) {
        if (ValidationUtil.isEmpty(fields) || ValidationUtil.isEmpty(values) || fields.length != values.length) {
            return true;
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (!ValidationUtil.isEmpty(id)) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
            String keyProperty = tableInfo.getKeyProperty();
            wrapper.ne(keyProperty, id);
        }
        for (int pos = 0; pos < fields.length; pos++) {
            wrapper.eq(fields[pos], values[pos]);
        }
        int count = count(wrapper);
        return count > 0;
    }

    /**
     * 检查该字段集合组成的数据是否重复
     *
     * @param id     更新时所用的主键值，新增时传入空
     * @param fn 不能重复的字段
     * @param value 需要检查重复的字段值
     * @return 重复返回true, 不重复返回false
     */
    @Override
    public boolean checkDuplicate(Long id, ColumnUtil.SFunction<T, ?> fn, String value) {
        String field = ColumnUtil.getFieldName(fn,"_", 2);
        if (ValidationUtil.isEmpty(field) || ValidationUtil.isEmpty(value)) {
            return true;
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (!ValidationUtil.isEmpty(id)) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
            String keyProperty = tableInfo.getKeyProperty();
            wrapper.ne(keyProperty, id);
        }
        wrapper.eq(field, value);
        int count = count(wrapper);
        return count > 0;
    }

    /**
     * 根据多个主键查询
     * @param ids 主键几个
     * @return 对象对象集合
     */
    @Override
    public List<T> getByIds(Collection<? extends Serializable> ids) {
        if(ValidationUtil.isEmpty(ids)){
            return Collections.emptyList();
        }
        return listByIds(ids);
    }
}
