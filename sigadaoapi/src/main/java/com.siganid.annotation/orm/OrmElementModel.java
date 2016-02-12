package com.siganid.annotation.orm;

import com.siganid.annotation.util.ElementUtil;
import com.siganid.annotation.inter.Field;
import com.siganid.annotation.inter.Id;
import com.siganid.annotation.inter.Table;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by Administrator on 2016/2/3.
 */
public class OrmElementModel {


    private ProcessingEnvironment processingEnv;
    Messager messager;
    Elements elementUtils;

    public OrmElementModel(ProcessingEnvironment processingEnv, Elements elementUtils) {
        this.processingEnv = processingEnv;
        messager = processingEnv.getMessager();
        this.elementUtils = elementUtils;
    }

    public String getPackageName(Element classElement) {
        PackageElement packageElement = elementUtils.getPackageOf(classElement);

        return packageElement.getQualifiedName().toString();
    }

    public String getTableName(Element classElement) {
        String name = classElement.getAnnotation(Table.class).name();
        if (name.length() <= 0) {
            name = classElement.getSimpleName().toString();
        }
        return name;
    }


    public List<FieldBean> getFiledBean(Element classElement) {
        List<FieldBean> fieldBeans = new ArrayList<>();
        List<? extends Element> fields = ElementUtil.getAllFieldInElement(classElement);
        for (Element element : fields) {
            fieldBeans.add(getFieldBeanFrom(element));
        }
        return fieldBeans;
    }

    public Element getIdElement(Element classElement) {
        List<? extends Element> fields = ElementUtil.getAllFieldInElement(classElement);
        Element result = null;
        for (Element element : fields) {
            Id id = element.getAnnotation(Id.class);
            String fieldName = element.getSimpleName().toString();
            if (id != null) {
                result = element;
            }
            if ((fieldName.equals("id") || fieldName.equals("id")) && result == null) {
                result = element;
            }
        }
        return result;
    }

    private boolean isIdElement(Element fieldElement) {
        Id id = fieldElement.getAnnotation(Id.class);
        return id != null;
    }

    private FieldBean getFieldBeanFrom(Element fieldElement) {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setIsId(isIdElement(fieldElement));
        fieldBean.setFieldName(fieldElement.getSimpleName().toString());
        VariableElement variableElement = (VariableElement) fieldElement;
        fieldBean.setFieldType(findFieldType(fieldElement));

        String tableFieldType = changeFieldTypeToTableType(fieldBean.getFieldType());
        fieldBean.setTableFieldType(tableFieldType);

        String tableFieldName = getTableFiledNameFrom(fieldElement);
        fieldBean.setTableFieldName(tableFieldName);
        return fieldBean;
    }

    private String findFieldType(Element fieldElement) {
        String fullNameType = fieldElement.asType().toString();
        String[] name = fullNameType.split("\\.");
        if (name.length <= 0) {
            return fullNameType;
        }
        String nameType = name[name.length - 1];
        return nameType;
    }

    private String getTableFiledNameFrom(Element fieldElement) {
        Field field = fieldElement.getAnnotation(Field.class);
        if (field != null && field.value().length() > 0) {
            return field.value();
        }

        Id id = fieldElement.getAnnotation(Id.class);
        if (id != null && id.value().length() > 0) {
            return id.value();
        }
        return fieldElement.getSimpleName().toString();
    }

    private String changeFieldTypeToTableType(String fieldType) {

        String tableType = "";
        return tableType;
    }


}
