package com.siganid.annotation.orm.freemaker.base;


import com.siganid.annotation.orm.freemaker.util.SigLog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootMap {

    String packageName;
    String className;

    private static RootMap model;

    private RootMap() {

    }

    public static RootMap init() {
        if (model == null) {
            model = new RootMap();
        }
        model.initRootMap();
        return model;
    }

    public void reset() {
        rootMap.clear();
        initRootMap();
    }

    Map<String, Object> rootMap = new HashMap<String, Object>();

    public Map<String, Object> getRootMap() {
        return rootMap;
    }

    public void setRootMap(Map<String, Object> rootMap) {
        this.rootMap = rootMap;
    }

    private RootMap initRootMap() {
        //rootMap.put("class", GenConfig.getInstance().getOutClassName());
        //rootMap.put("package", GenConfig.getInstance().getOutPackageName());
        return this;
    }

    public <T> RootMap addListToMap(List<T> list) {
        if (list.size() > 0) {
            rootMap.put(list.get(0).getClass().getSimpleName(), getMap(list));
        }
        return this;
    }

    public <T> RootMap addListToMap(String keyName, List<T> list) {
        if (list.size() > 0) {
            rootMap.put(keyName, getMap(list));
        }
        return this;
    }

    /**
     * 把一个list的对象转化成map 对应的名字就是temp用到的名字
     *
     * @param importList
     * @return
     */
    private <T> List<Map<String, Object>> getMap(List<T> importList) {
        ArrayList<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < importList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            putVoToMap(importList.get(i), map);
            mapList.add(map);
        }
        return mapList;
    }

    public void add(Object object) {
        if (rootMap != null) {
            putVoToMap(object, rootMap);
        }
    }

    /**
     * 把一个类的属性转化为map的字段属性
     *
     * @param class1
     */
    public void addClassToMap(Class class1) {
        List<Properties> properties = new ArrayList<Properties>();
        Field[] fields = class1.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            try {
                field.setAccessible(true);
                Properties properties2 = new Properties();
                properties2.setName(field.getName());
                properties2.setTypeName(field.getType().getName());
                properties2.setTypeSimpleName(field.getType().getSimpleName());
                properties.add(properties2);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }
        }
        rootMap.put(properties.get(0).getClass().getSimpleName(), getMap(properties));

    }

    /**
     * 把一个对象映射成map
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */

    public void putVoToMap(Object object, Map<String, Object> map) {
        if (object == null) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

            Field field = fields[i];
            try {

                SigLog.d("put的对象类型：" + field.getType());
                field.setAccessible(true);
                if (field.getType().equals(List.class)) {
                    SigLog.d("输出类型是list");
                    List list = (List<? extends Object>) field.get(object);
                    if (list != null && list.size() > 0) {
                        addListToMap(list);
                    }
                }
                SigLog.d("put的对象名和值：" + field.getName(), field.get(object).toString());
                map.put(field.getName(), field.get(object).toString());

            } catch (Exception e) {
                try {
                    if (field == null) {
                        SigLog.d("file为空~~~");
                    }
                    if (field.get(object) == null) {
                        SigLog.d(field.getName(), "值为空~~~");
                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                //e.printStackTrace();
                // TODO: handle exception
            }
        }
    }

    public static void main(String[] args) {
        Import import1 = new Import();
        import1.setImportClassName("12313123");
        new RootMap().putVoToMap(import1, null);
    }
}
