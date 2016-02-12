package com.siganid.annotation.orm.freemaker.util;

public class ProjectUtil {

    public static ProjectUtil getInstance() {
        if (model == null) {
            model = new ProjectUtil();
        }
        return model;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }


    public String getProjectPath() {
        return projectPath;
    }


    String projectPath;

    private static ProjectUtil model;

    private ProjectUtil() {

    }


}
