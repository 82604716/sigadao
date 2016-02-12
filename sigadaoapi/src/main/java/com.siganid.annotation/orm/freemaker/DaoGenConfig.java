package com.siganid.annotation.orm.freemaker;


import com.siganid.annotation.orm.FieldBean;

import java.util.List;

public class DaoGenConfig {
    public String packageName = "";
    private String voName;
    List<FieldBean> fieldBeans;

    public DaoGenConfig() {
    }

    public DaoGenConfig(String packageName, String voName) {
        this.packageName = packageName;
        this.voName = voName;
    }

    public List<FieldBean> getFieldBeans() {
        return fieldBeans;
    }

    public void setFieldBeans(List<FieldBean> fieldBeans) {
        this.fieldBeans = fieldBeans;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVoName(String voName) {
        this.voName = voName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVoName() {
        return voName;
    }
}
