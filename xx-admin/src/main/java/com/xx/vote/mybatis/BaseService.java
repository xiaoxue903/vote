package com.xx.vote.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.utils.ColumnUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/7 14:26
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 检查该字段集合组成的数据是否重复
     *
     * @param id     更新时所用的主键值，新增时传入空
     * @param fields 不能重复的字段集
     * @param values 需要检查重复的字段值集
     * @return 重复返回true, 不重复返回false
     */
    boolean checkDuplicate(Long id, String[] fields, String[] values);

    /**
     * 检查该字段集合组成的数据是否重复
     *
     * @param id     更新时所用的主键值，新增时传入空
     * @param fn 不能重复的字段
     * @param value 需要检查重复的字段值
     * @return 重复返回true, 不重复返回false
     */
    boolean checkDuplicate(Long id, ColumnUtil.SFunction<T, ?> fn, String value);

    /**
     * 根据多个主键查询
     * @param ids 主键几个
     * @return 对象对象集合
     */
    List<T> getByIds(Collection<? extends Serializable> ids);
}
