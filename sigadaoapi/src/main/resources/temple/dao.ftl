package ${packageName};

import android.content.Context;
import android.database.Cursor;
import com.siganid.sigadaobase.siga.BaseDao;
import java.util.ArrayList;
import java.util.List;

<#list FieldBean as fieldBean>
  <#if fieldBean.isId == "true" >
   <#assign idField = "${fieldBean.tableFieldName}" >
  </#if>
</#list>

public class ${voName}Dao extends BaseDao {

    Context context;

    public ${voName}Dao(Context context) {
        super(context);
    }

    public ${voName} find(String whereStr) {
        checkAndCreatTable();
        Cursor cursor = db.findBySql(getFindSql(whereStr));

        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        if (!cursor.moveToNext()) {
            return null;
        }
        return get${voName}From(cursor);
    }

    public List<${voName}> findAll(String whereStr) {
        checkAndCreatTable();
        Cursor cursor = db.findBySql(getFindSql(whereStr));
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        List<${voName}> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ${voName} ${voName?uncap_first} = get${voName}From(cursor);
            list.add(${voName?uncap_first});
        }
        return list;
    }

    private ${voName} get${voName}From(Cursor cursor) {
        ${voName} ${voName?uncap_first} = new ${voName}();

        <#list FieldBean as fieldBean>
        ${fieldBean.fieldType} ${fieldBean.fieldName} = cursor.get${fieldBean.fieldType?cap_first}(cursor.getColumnIndex("${fieldBean.tableFieldName}"));
        ${voName?uncap_first}.set${fieldBean.fieldName?cap_first}(${fieldBean.fieldName});
        </#list>

        return ${voName?uncap_first};
    }

    public void save(Object object) {
        checkAndCreatTable();
        db.exeSql(getInsertSql(get${voName}From(object)));
    }

    private ${voName} get${voName}From(Object object) {
        if (object instanceof ${voName}) {
            return (${voName}) object;
        }
        throw new RuntimeException("has no this object dao");
    }


    public void update(Object object, String whereStr) {
        checkAndCreatTable();
        db.exeSql(getUpdateSql(get${voName}From(object), whereStr));
    }


    public void delete(String whereStr) {
        checkAndCreatTable();
        db.exeSql(getDeleteSql(whereStr));
    }


    public String getCreatSql() {
     <#assign otherField = "" />
        <#list FieldBean as fieldBean>
         <#if fieldBean.isId == "false" >
            <#assign otherField = otherField + ",\\\"${fieldBean.fieldName}\\\"" />
         </#if>
        </#list>

        String creatSql = "CREATE TABLE IF NOT EXISTS ${voName?uncap_first} ( \"${idField}\" INTEGER PRIMARY KEY AUTOINCREMENT${otherField})";
        return creatSql;
    }

    public String getInsertSql(${voName} ${voName?uncap_first}) {
     <#assign otherField = "" />
     <#assign setFieldValue = "" />
      <#assign sField = "" />
      <#list FieldBean as fieldBean>
       <#if fieldBean.isId == "false" >
        <#assign otherField = otherField + ",${fieldBean.fieldName}" />
        <#assign setFieldValue = setFieldValue + ",${voName?uncap_first}.get${fieldBean.fieldName?cap_first}()" />
        <#assign sField = sField + ",%s" />
       </#if>
      </#list>

        String insertSql = "INSERT INTO ${voName?uncap_first} (${otherField?substring(1)}) VALUES (${sField?substring(1)})";
        Object[] objects = new Object[]{${setFieldValue?substring(1)}};
        String result = String.format(insertSql, objects);
        return result;
    }

    public String getDeleteSql(String whereStr) {
        String deletesql = "delete from '${voName?uncap_first}' " + whereStr;
        return deletesql;
    }

    public String getUpdateSql(${voName} ${voName?uncap_first}, String whereStr) {
     <#assign otherField = "" />
     <#assign setFieldValue = "" />
            <#list FieldBean as fieldBean>
             <#if fieldBean.isId == "false" >
                <#assign otherField = otherField + ",${fieldBean.fieldName} = %s" />
                <#assign setFieldValue = setFieldValue + ",${voName?uncap_first}.get${fieldBean.fieldName?cap_first}()" />
             </#if>
            </#list>

        String updateSql = "UPDATE '${voName?uncap_first}' SET ${otherField?substring(1)} " + whereStr;
        Object[] objects = new Object[]{${setFieldValue?substring(1)}};
        String result = String.format(updateSql, objects);
        return result;
    }

    public String getFindSql(String whereStr) {
        String findSql = "SELECT * FROM '${voName?uncap_first}' " + whereStr;
        return findSql;
    }

    public String getCheckSql() {
        String checksql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='${voName?uncap_first}'";
        return checksql;
    }

}
