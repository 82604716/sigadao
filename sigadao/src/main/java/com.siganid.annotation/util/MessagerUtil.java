package com.siganid.annotation.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Created by Administrator on 2016/2/3.
 */
public class MessagerUtil {
    public static void Log(ProcessingEnvironment processingEnv, String str) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, str);
    }
}
