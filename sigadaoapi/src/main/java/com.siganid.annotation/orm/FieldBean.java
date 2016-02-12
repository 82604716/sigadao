package com.siganid.annotation.orm;

/**
 * Created by Administrator on 2016/2/3.
 */
public class FieldBean {

    boolean isId = false;
    String fieldType;
    String fieldName;
    String tableFieldName;
    String tableFieldType;

    public String getFieldType() {
        return fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public String getTableFieldType() {
        return tableFieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public void setTableFieldType(String tableFieldType) {
        this.tableFieldType = tableFieldType;
    }

    public boolean isId() {
        return isId;
    }

    public void setIsId(boolean isId) {
        this.isId = isId;
    }

    @Override
    public String toString() {
        String result = getFieldType() + " --" + getFieldName() + " table ->" +
                getTableFieldType() + getTableFieldName();
        return result;
    }
}
