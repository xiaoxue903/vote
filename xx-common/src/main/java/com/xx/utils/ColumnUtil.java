package com.xx.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/7 17:01
 */
public class ColumnUtil {

    private static final String DEFAULT_SPLIT = "";
    private static final Integer DEFAULT_TO_TYPE = 0;

    /**
     * 使Function获取序列化能力
     */
    @FunctionalInterface
    public interface SFunction<T, R> extends Function<T, R>, Serializable {
    }


    /**
     * 获取实体类的字段名称(实体声明的字段名称)
     */
    public static <T> String getFieldName(SFunction<T, ?> fn) {
        return getFieldName(fn, DEFAULT_SPLIT);
    }


    /**
     * 获取实体类的字段名称
     *
     * @param split 分隔符，多个字母自定义分隔符
     */
    public static <T> String getFieldName(SFunction<T, ?> fn, String split) {
        return getFieldName(fn, split, DEFAULT_TO_TYPE);
    }

    /**
     * 获取实体类的字段名称
     *
     * @param split  分隔符，多个字母自定义分隔符
     * @param toType 转换方式，多个字母以大小写方式返回 0.不做转换 1.大写 2.小写
     */
    public static <T> String getFieldName(SFunction<T, ?> fn, String split, Integer toType) {
        SerializedLambda serializedLambda = getSerializedLambda(fn);
        // 从lambda信息取出method、field、class等
        String fieldName = serializedLambda.getImplMethodName().substring("get".length());
        fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "", (fieldName.charAt(0) + "").toLowerCase());
        Field field;
        try {
            field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        // 从field取出字段名，可以根据实际情况调整
        TableField tableField = field.getAnnotation(TableField.class);
        if (tableField != null && tableField.value().length() > 0) {
            return tableField.value();
        } else {
            //0.不做转换 1.大写 2.小写
            switch (toType) {
                case 1:
                    return fieldName.replaceAll("[A-Z]", split + "$0").toUpperCase();
                case 2:
                    return fieldName.replaceAll("[A-Z]", split + "$0").toLowerCase();
                default:
                    return fieldName.replaceAll("[A-Z]", split + "$0");
            }
        }
    }

    private static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        ReflectionUtils.makeAccessible(writeReplaceMethod);

        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        ReflectionUtils.makeAccessible(writeReplaceMethod);
        return serializedLambda;
    }

    public static void main(String[] args) {

        //实体类原字段名称返回
        System.out.println();
        System.out.println("实体类原字段名称返回");
        System.out.println("字段名：" + ColumnUtil.getFieldName(User1::getUserName));
        System.out.println("字段名：" + ColumnUtil.getFieldName(User1::getUserName, "_", 0));
        System.out.println("字段名：" + ColumnUtil.getFieldName(User1::getUserName, "_", 1));
        System.out.println("字段名：" + ColumnUtil.getFieldName(User1::getUserName, "_", 2));
    }
}
@Data
class User1{
    private Long id;

    private String name;

    private String userName;
}