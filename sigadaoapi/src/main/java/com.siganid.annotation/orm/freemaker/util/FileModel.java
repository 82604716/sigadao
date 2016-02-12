package com.siganid.annotation.orm.freemaker.util;

import java.io.File;
import java.io.FilenameFilter;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class FileModel {

    List<File> allFile = new ArrayList<File>();

    public List<File> getAllFile() {
        return allFile;
    }

    public void setAllFile(List<File> allFile) {
        this.allFile = allFile;
    }

    public long getLastBuildTime(String projectPathString) {
        getAllFile(projectPathString);
        for (File file : allFile) {
            if (file.getName().endsWith(".apk")) {
                return file.lastModified();
            }
        }
        return 0;
    }

    public List<File> getLastEditRes(String projectPathString) {
        List<File> result = new ArrayList<File>();
        long lastBuildTime = getLastBuildTime(projectPathString);
        for (File file : allFile) {
            if ((file.getName().endsWith(".xml") || file.getName().endsWith(".png") || file.getName().endsWith(".jpg")) && file.lastModified() > lastBuildTime) {
                result.add(file);
                System.out.println("修改的资源文件：" + file.getName());
            }
        }
        return result;
    }

    public List<File> getLastEditSrc(List<String> projectPaths) {
        List<File> result = new ArrayList<File>();
        for (String projectPath : projectPaths) {
            result.addAll(getLastEditSrc(projectPath));
        }
        return result;
    }

    public List<File> getLastEditSrc(String projectPathString) {
        List<File> result = new ArrayList<File>();
        long lastBuildTime = getLastBuildTime(projectPathString);
        for (File file : allFile) {
            if (file.getName().endsWith(".java") && file.lastModified() > lastBuildTime) {
                result.add(file);
                System.out.println("修改的类文件：" + file.getName());
            }
        }
        return result;
    }

    public void soket() {
    }

    public void getAllFile(String projectPath) {
        File file = new File(projectPath);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String arg1) {
                if (arg0.isDirectory()) {
                    return true;
                }
                return false;
            }
        });
        File result = null;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                getAllFile(files[i].getAbsolutePath());
            } else {
                allFile.add(files[i]);
            }
        }
    }

    public List<File> getAllFile(List<String> projectPaths, String basePath, String endString) {
        List<File> result = new ArrayList<>();
        for (String projectPath : projectPaths) {
            allFile.clear();
            getAllFile(projectPath + "/" + basePath, endString);
            result.addAll(allFile);
        }
        return result;
    }

    public void getAllFile(String projectPath, String endString) {
        File file = new File(projectPath);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String arg1) {
                if (arg0.isDirectory()) {
                    return true;
                }
                return false;
            }
        });
        File result = null;
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                getAllFile(files[i].getAbsolutePath(), endString);
            } else if (files[i].getAbsolutePath().endsWith(endString)) {
                allFile.add(files[i]);
            }
        }
    }

    public void getAllFolder(String projectPath, String endString) {
        File file = new File(projectPath);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String arg1) {
                if (arg0.isDirectory()) {
                    return true;
                }
                return false;
            }
        });
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                if (files[i].getAbsolutePath().endsWith(endString)) {
                    allFile.add(files[i]);
                } else {
                    getAllFolder(files[i].getAbsolutePath(), endString);
                }
            }
        }
    }

    public String getAsResPath(String projectPath) {
        List<File> resFile = new ArrayList<>();
        allFile.clear();
        getAllFolder(projectPath + "../", "\\res");
        for (int i = 0; i < allFile.size(); i++) {
            File file = allFile.get(i);
            if (!file.getAbsolutePath().contains("build")) {
                resFile.add(file);
            }
        }
        String result = parseFilePathToSringDevideBy(" ", resFile);
        return result;
    }

    public String parseFilePathToSringDevideBy(String deviceChar, List<File> files) {
        String resultString = "";
        for (int i = 0; i < files.size(); i++) {
            if (i == 0) {
                resultString = files.get(i).getAbsolutePath();
            } else {
                resultString = resultString + deviceChar + files.get(i).getAbsolutePath();
            }
        }
        return resultString;
    }

    public String getAsAndroidManifestFile(String projectPath) {
        allFile.clear();
        getAllFile(projectPath, "AndroidManifest.xml");

        for (int i = 0; i < allFile.size(); i++) {
            File file = allFile.get(i);
            if (file.getAbsolutePath().contains("build")) {
                continue;
            } else {
                return file.getAbsolutePath();
            }
        }
        return "";
    }

    public String getAsAttetsPath(String projectPath) {
        allFile.clear();
        getAllFolder(projectPath, "assets");
        for (int i = 0; i < allFile.size(); i++) {
            File file = allFile.get(i);
            if (file.getAbsolutePath().contains("build")) {
                continue;
            } else {
                return file.getAbsolutePath();
            }
        }
        return "";

    }

    public static void main(String[] args) {
        new FileModel().getAsResPath("I:\\quickmain\\src\\repositories\\quickpic-dev\\");
        // System.out.println(new MainModel().getLastEditRes());
        // System.out.println(new MainModel().getLastEditSrc());
//		System.out.println(Msg.init(MsgFlag.StartAcceptFile));
//		System.out.println(Msg.getMsg("{\"msgFlag\":\"StartAcceptFile\"}").getMsgFlag());
    }
}
