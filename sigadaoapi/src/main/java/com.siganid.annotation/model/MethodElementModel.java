package com.siganid.annotation.model;

import com.siganid.annotation.util.ElementUtil;
import com.siganid.annotation.inter.PrintMe;
import com.siganid.annotation.util.MessagerUtil;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

/**
 * Created by Administrator on 2016/2/3.
 */
public class MethodElementModel {

    private ProcessingEnvironment processingEnv;
    Messager messager;

    public MethodElementModel(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        messager = processingEnv.getMessager();
    }

    public void printMethod(Element methodElement) {
        if (!ElementUtil.IsMethodElement(methodElement)) {
            return;
        }
        ExecutableElement executableElement = (ExecutableElement) methodElement;
        MessagerUtil.Log(processingEnv, "getSimpleName" + executableElement.getSimpleName().toString());
        MessagerUtil.Log(processingEnv, "getReturnType" + executableElement.getReturnType().toString());
        // List<? extends AnnotationMirror> annotationMirrors = executableElement.getAnnotationMirrors();

        printPrintMeAnnotation(methodElement);
    }

    /**
     * @param methodElement
     */
    public void printPrintMeAnnotation(Element methodElement) {
        PrintMe printMe = methodElement.getAnnotation(PrintMe.class);
        if (printMe == null) {
            return;
        }

    }


}
