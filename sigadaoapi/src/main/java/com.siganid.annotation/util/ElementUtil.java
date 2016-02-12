package com.siganid.annotation.util;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.ElementFilter;

/**
 * Created by Administrator on 2016/2/3.
 */
public class ElementUtil {


    public static boolean IsClassElement(Element element) {
        return element.getKind() == ElementKind.CLASS;
    }

    public static boolean IsFieldElement(Element element) {
        return element.getKind() == ElementKind.FIELD;
    }

    public static boolean IsMethodElement(Element element) {
        return element.getKind() == ElementKind.METHOD;
    }

    public static List<? extends Element> getAllFieldInElement(Element element) {
        return ElementFilter.fieldsIn(element.getEnclosedElements());
    }

    public static List<? extends Element> getAllMethodInElement(Element element) {
        return ElementFilter.methodsIn(element.getEnclosedElements());
    }


}
