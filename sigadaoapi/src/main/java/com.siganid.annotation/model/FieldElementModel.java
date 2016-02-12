package com.siganid.annotation.model;

import com.siganid.annotation.util.ElementUtil;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2016/2/3.
 */
public class FieldElementModel {

    private ProcessingEnvironment processingEnv;
    Messager messager;

    public FieldElementModel(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        messager = processingEnv.getMessager();
    }

    public void printFiled(Element fieldElement) {
        if (!ElementUtil.IsFieldElement(fieldElement)) {
            return;
        }
    }


}
