package com.siganid.annotation.orm.freemaker;


import com.siganid.annotation.orm.freemaker.base.BaseRootMapModel;
import com.siganid.annotation.orm.freemaker.base.ImportModel;

public class DaoRootMapModel extends BaseRootMapModel {

    DaoGenConfig daoGenConfig = new DaoGenConfig();

    public DaoRootMapModel() {
    }

    public DaoRootMapModel(DaoGenConfig daoGenConfig) {
        this.daoGenConfig = daoGenConfig;
    }

    public DaoRootMapModel init() {
        reset();
        add(daoGenConfig);
        add(ImportModel.getInstance().getList());
        return this;
    }

}
