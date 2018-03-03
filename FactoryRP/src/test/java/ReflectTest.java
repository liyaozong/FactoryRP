import tech.yozo.factoryrp.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectTest {

    static class ReflectTestVO1 extends ReflectTestVO{
        private String projectName;

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
    }
    public static void main(String[] args) {

        ReflectTestVO test = new ReflectTestVO();

        test.setName("张三");
        test.setPhone("17602870596");
        test.setCertNo("512732322323213213");
        test.setId(1L);

        /*Field[] fs = ReflectTestVO.class.getFields();
        Field[] fs1 = ReflectTestVO.class.getDeclaredFields();
        for (Field f:fs) {
            System.out.println("ReflectTestVO>>>>>>>>>>>>"+f.getName());
        }

        for (Field f:fs1) {
            System.out.println("BaseTestVO>>>>>>>>>>>>"+f.getName());
        }*/

       /* List<Field> fields = reutrnFields(test);

        fields.stream().forEach(f1 ->{
            System.out.println(f1.getName());
        });*/

        SubSubClass cla =new SubSubClass();
        //SuperClass cla =new SubClass();

        ReflectTestVO1 ReflectTestVO1 = new ReflectTestVO1();

        //List<Field> fieldList = sensorDataList(ReflectTestVO1);
        List<Field> fieldList = ReflectUtil.returnFieldList(ReflectTestVO1);
        fieldList.stream().forEach(f1 ->{
            System.out.println(f1.getName());
        });

    }

    /**
     * 返回某个类的字段
     * @param object
     * @param clazz
     * @return
     * @throws Exception
     */
    private static List<Field> returnClassField(Object object,List<Field> fieldList,Class<?> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldList.add(field);
        }
        return fieldList;
    }

    /*private static void returnclassField(Object sensorDataDto, List<SensorData> sensorDatas, Class<?> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            SensorData sensorData = new SensorData();
            sensorData.setSensorId(field.getName().toString());
            sensorData.setSensorValue(field.get(sensorDataDto));
            sensorDatas.add(sensorData);
        }
    }*/


    private static void exceClass(Object object, List<Field> fieldList, Class<?> clazz) throws Exception {
        if (clazz != Object.class) {
            /*System.out.println(clazz);*/
            returnClassField(object, fieldList, clazz);
            Class<?> clazzs = clazz.getSuperclass();
            exceClass(object, fieldList, clazzs);
        }
    }

    /*private static void exceClass(Object object, List<SensorData> sensorDatas, Class<?> clazz) throws Exception {
        if (clazz != Object.class) {
            System.out.println(clazz);
            returnclassF(object, sensorDatas, clazz);
            Class<?> clazzs = clazz.getSuperclass();
            exceClass(object, sensorDatas, clazzs);
        }
    }*/

    /**
     * 通过反射获取各个属性名称和属性值封装成类
     *
     * @param object
     * @return
     */
    public static List<Field> sensorDataList(Object object) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> clazz = object.getClass();
        try {
            exceClass(object, fieldList, clazz);
        } catch (Exception e) {

        }
        return fieldList;
    }

    private static List<Field> reutrnFields(Object object){

        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = Object.class;

        System.out.println(tempClass.getName());

        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类)

            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));

            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }

        return fieldList;

    }

}
