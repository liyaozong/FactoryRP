package tech.yozo.factoryrp.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 反射工具类
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/3
 * @description
 */
public class ReflectUtil {


    /**
     * 返回某个类的字段
     * @param object
     * @param clazz
     * @return
     * @throws Exception
     */
    private static List<Field> returnClassField(Object object, List<Field> fieldList, Class<?> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldList.add(field);
        }
        return fieldList;
    }


    /**
     * 拿到某个类的字段
     * @param object
     * @param fieldList
     * @param clazz
     * @throws Exception
     */
    private static void exceClass(Object object, List<Field> fieldList, Class<?> clazz) throws Exception {
        if (clazz != Object.class) {
            returnClassField(object, fieldList, clazz);
            Class<?> clazzs = clazz.getSuperclass();
            exceClass(object, fieldList, clazzs);
        }
    }

    /**
     * 通过反射获取各个属性名称和属性值封装成类
     *
     * @param object
     * @return
     */
    public static List<Field> returnFieldList(Object object) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> clazz = object.getClass();
        try {
            exceClass(object, fieldList, clazz);
        } catch (Exception e) {

        }
        return fieldList;
    }

    /**
     * 获取对象中空值的属性
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
