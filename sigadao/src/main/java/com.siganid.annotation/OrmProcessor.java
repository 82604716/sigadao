package com.siganid.annotation;

import com.siganid.annotation.util.MessagerUtil;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by Administrator on 2016/2/2.
 */
@SupportedAnnotationTypes("com.siganid.annotation.inter.Table")
public class OrmProcessor extends AbstractProcessor {
    private Elements elementUtils;
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        elementUtils = processingEnv.getElementUtils();
        try {
            Class clazz = Class.forName("com.siganid.annotation.SigormProcessor");
            ProcessorManager.getInstance().setProcessor((Processor) clazz.newInstance());
        } catch (Exception e) {
            MessagerUtil.Log(processingEnv, "e:" + e);
            e.printStackTrace();
        }
        super.init(processingEnv);
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        try {
            Processor processor = ProcessorManager.getInstance().getProcessor();
            if (processor != null) {
                processor.onProcesst(annotations, env, processingEnv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
