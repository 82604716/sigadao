package com.siganid.annotation;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Administrator on 2016/2/9.
 */
public interface Processor {
    void onProcesst(Set<? extends TypeElement> annotations, RoundEnvironment env,  ProcessingEnvironment processingEnv);

}
