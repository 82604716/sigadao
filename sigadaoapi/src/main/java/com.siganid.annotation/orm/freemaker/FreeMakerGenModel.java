package com.siganid.annotation.orm.freemaker;

import com.siganid.annotation.orm.FieldBean;
import com.siganid.annotation.orm.freemaker.base.GenConfig;
import com.siganid.annotation.orm.freemaker.util.FileModel;
import com.siganid.annotation.orm.freemaker.util.ProjectUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMakerGenModel {

    private static FreeMakerGenModel model;

    public FreeMakerGenModel() {

    }

    public static FreeMakerGenModel getInstance() {
        if (model == null) {
            model = new FreeMakerGenModel();
        }
        return model;
    }

    public void gen(GenConfig genConfig) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        Template temp;
        if (genConfig.getTempleContent().length() > 0) {
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("daotemple", genConfig.getTempleContent());
            cfg.setTemplateLoader(stringLoader);
            temp = cfg.getTemplate("daotemple", "utf-8");
        } else {
            cfg.setDirectoryForTemplateLoading(new File(genConfig.getTemplePath()));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            temp = cfg.getTemplate(genConfig.getTempleName());
        }


        File outFile = new File(genConfig.getDaoSavePath());
        System.out.println(genConfig.getDaoSavePath());
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(outFile));
        temp.process(genConfig.getRoot(), out);
        out.flush();
        out.close();
        // �Աȹ���
    }


    public void initDao(DaoGenConfig daoGenConfig) {
        GenConfig genConfig = new GenConfig(daoGenConfig.getVoName() + "Dao");

        genConfig.setTempleContent(getDaoTemple());
        GenConfig.setOutPutPackageName(daoGenConfig.getPackageName());
        DaoRootMapModel daoRootMapModel = new DaoRootMapModel(daoGenConfig);
        daoRootMapModel.init();
        genConfig.setRoot(daoRootMapModel.getRootMap());
        genConfig.setDaoSavePath(getDaoSavePath(daoGenConfig.getVoName(), daoGenConfig.getPackageName()));
        try {
            getInstance().gen(genConfig);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private String getDaoSavePath(String tableName, String packageName) {
        packageName = packageName.replace(".", "\\");
        File file = new File("");
        String root = "";
        try {
            root = file.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileModel fileModel = new FileModel();
        fileModel.getAllFile().clear();
        fileModel.getAllFile(root, tableName + ".java");
        List<File> tableFiles = fileModel.getAllFile();
        for (File tableFile : tableFiles) {
            String path = tableFile.getParent();
            if (path.contains(packageName)) {
                path = path + "\\" + tableName + "Dao.java";
                return path;
            }
        }
        return "";
    }

    private String getDaoTemple() {

        // InputStream is = this.getClass().getClassLoader().getResourceAsStream("temple/dao.ftl");
        InputStream is = null;
        try {
            is = new FileInputStream("C:\\quick465rb\\src\\repositories\\MuliteDexTest\\sigadaoapi\\src\\main\\resources\\temple\\dao.ftl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String result = "";
        String s = "";
        try {
            while ((s = br.readLine()) != null) {
                result = result + s + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static void main(String[] args) {
        ProjectUtil.getInstance().setProjectPath("C:/quick465rb/src/repositories/MuliteDexTest/");
        DaoGenConfig daoGenConfig = new DaoGenConfig();
        daoGenConfig.setPackageName("com.example.androidmsgtest");
        daoGenConfig.setVoName("Stu");
        List<FieldBean> list = new ArrayList<>();
        FieldBean fieldBean = new FieldBean();
        fieldBean.setFieldName("id");
        fieldBean.setFieldType("int");
        fieldBean.setTableFieldName("id");
        list.add(fieldBean);
        daoGenConfig.setFieldBeans(list);

        new FreeMakerGenModel().initDao(daoGenConfig);
    }
}
