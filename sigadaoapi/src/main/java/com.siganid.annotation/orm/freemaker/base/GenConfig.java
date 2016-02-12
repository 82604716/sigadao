package com.siganid.annotation.orm.freemaker.base;


import com.siganid.annotation.orm.freemaker.util.ProjectUtil;

import java.io.File;
import java.util.Map;

public class GenConfig {


    // 模板路径
    public final static String templeUsePath = "/temple";
    // 输出路径
    final String outPutBasePath = ProjectUtil.getInstance().getProjectPath();
    static String outPutPackageName = "";

    String templeName;
    String outClassName;
    public Map<String, Object> root;

    String templeContent;

    String daoSavePath;


    public String getTempleContent() {
        return templeContent;
    }

    public void setTempleContent(String templeContent) {
        this.templeContent = templeContent;
    }

    public String getOutClassName() {
        return outClassName;
    }

    public static void setOutPutPackageName(String outPutPackageName) {
        GenConfig.outPutPackageName = outPutPackageName;
    }

    public String getDaoSavePath() {
        return daoSavePath;
    }

    public void setDaoSavePath(String daoSavePath) {
        this.daoSavePath = daoSavePath;
    }

    public GenConfig(String templeName, String outClassName) {
        this.templeName = templeName;
        this.outClassName = outClassName;
    }


    public GenConfig(String outClassName) {
        this.outClassName = outClassName;
    }

    public String getTempleName() {
        return templeName;
    }

    public String getOutPackageName() {
        return outPutPackageName;
    }


    /**
     * 获取保存的类的绝对路径
     *
     * @return
     */
    public String getClassSavePath() {
        String path = outPutBasePath + "\\" + outPutPackageName;
        path = path.replaceAll("\\.", "\\\\");
        File file = new File(path);
        if (!(file.exists() && file.isDirectory())) {
            file.mkdirs();
        }
        path = path + "\\" + outClassName + ".java";
        return path;
    }


    public String getTemplePath() {
        String filePath = getClass().getClassLoader().getResource("/resources/temple/dao.ftl").getFile();
        System.out.println("filePath:" + filePath);
        return filePath;
    }

    public Map<String, Object> getRoot() {
        return root;
    }

    public void setRoot(Map<String, Object> root) {
        this.root = root;
    }


}
