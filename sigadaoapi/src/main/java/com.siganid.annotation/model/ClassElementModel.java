package com.siganid.annotation.model;

import com.siganid.annotation.util.ElementUtil;
import com.siganid.annotation.inter.Table;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2016/2/3.
 */
public class ClassElementModel {
    ProcessingEnvironment processingEnv;
    Messager messager;
    MethodElementModel methodElementModel;
    FieldElementModel fieldElementModel;

    public ClassElementModel(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        messager = processingEnv.getMessager();
        methodElementModel = new MethodElementModel(processingEnv);
        fieldElementModel = new FieldElementModel(processingEnv);
    }

    public void print(Element classElement) {
        if (!ElementUtil.IsClassElement(classElement)) {
            return;
        }
        printMethod(classElement);
        printField(classElement);
    }

    private void printMethod(Element classElement) {
        List<? extends Element> methodList = ElementUtil.getAllMethodInElement(classElement);
        for (Element methodElement : methodList) {
            methodElementModel.printMethod(methodElement);
        }
    }

    private void printField(Element classElement) {

        List<? extends Element> fieldList = ElementUtil.getAllFieldInElement(classElement);
        for (Element fieldElement : fieldList) {
            fieldElementModel.printFiled(fieldElement);
        }
    }

    public String getTableName(Element classElement) {

        String name = classElement.getAnnotation(Table.class).name();
        if (name.length() <= 0) {
            name = classElement.getSimpleName().toString();
        }
        return name;
    }


}
