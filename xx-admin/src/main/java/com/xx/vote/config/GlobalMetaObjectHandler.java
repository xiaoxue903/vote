package com.xx.vote.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: xueqimiao
 * @Date: 2022/9/2 02:09
 */
public class GlobalMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        Date now = new Date();
        this.setInsertField("createDate", now, metaObject);
        this.setInsertField("updateDate", now, metaObject);
    }

    private void setInsertField(String name, Object val, MetaObject metaObject) {
        if (Objects.isNull(this.getFieldValByName(name, metaObject))) {
            this.setInsertFieldValByName(name, val, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = 0L;
        try {
            //userId = SystemContext.getCurrentMember().getId();
        }catch (Exception e){
            userId = 0L;
        }
        this.setUpdateFieldValByName("updateDate", new Date(), metaObject);
    }
}
