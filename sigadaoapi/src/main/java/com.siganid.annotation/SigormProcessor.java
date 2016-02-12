package com.siganid.annotation;

import com.siganid.annotation.inter.Table;
import com.siganid.annotation.orm.FieldBean;
import com.siganid.annotation.orm.OrmElementModel;
import com.siganid.annotation.orm.freemaker.DaoGenConfig;
import com.siganid.annotation.orm.freemaker.FreeMakerGenModel;
import com.siganid.annotation.orm.freemaker.util.FileModel;
import com.siganid.annotation.orm.freemaker.util.ProjectUtil;
import com.siganid.annotation.util.MessagerUtil;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by Administrator on 2016/2/9.
 */
public class SigormProcessor implements Processor {
    ProcessingEnvironment processingEnv;
    private Elements elementUtils;

    @Override
    public void onProcesst(Set<? extends TypeElement> annotations, RoundEnvironment env, ProcessingEnvironment processingEnv) {
        MessagerUtil.Log(processingEnv, "log in api~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        this.processingEnv = processingEnv;
        elementUtils = processingEnv.getElementUtils();

        for (Element e : env.getElementsAnnotatedWith(Table.class)) {
            dealTable(e);
            genDao(e);
        }
    }


    private void dealTable(Element tableElement) {
        OrmElementModel ormElementModel = new OrmElementModel(processingEnv, elementUtils);
        String tableName = ormElementModel.getPackageName(tableElement);
        MessagerUtil.Log(processingEnv, "包名 " + tableName);
        List<FieldBean> fieldBeans = ormElementModel.getFiledBean(tableElement);
        for (FieldBean fieldBean : fieldBeans) {
            MessagerUtil.Log(processingEnv, fieldBean.toString());
        }
    }

    private void genDao(Element tableElement) {
        OrmElementModel ormElementModel = new OrmElementModel(processingEnv, elementUtils);
        String tableName = ormElementModel.getTableName(tableElement);
        String packageName = ormElementModel.getPackageName(tableElement);
        MessagerUtil.Log(processingEnv, " packagename: " + packageName);
        MessagerUtil.Log(processingEnv, " tableName: " + tableName);
        DaoGenConfig daoGenConfig = new DaoGenConfig();
        daoGenConfig.setPackageName(packageName);
        daoGenConfig.setVoName(tableName);
        List<FieldBean> fieldBeans = ormElementModel.getFiledBean(tableElement);
        daoGenConfig.setFieldBeans(fieldBeans);
        new FreeMakerGenModel().initDao(daoGenConfig);
    }


}
