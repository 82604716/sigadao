package com.siganid.annotation.orm.freemaker.base;


import java.util.ArrayList;
import java.util.List;

public class ImportModel {
	public enum ImportType {
		Holder, AndroidView, Vo;
	}

	List<Import> list = new ArrayList<Import>();
	
	

	public List<Import> getList() {
		return list;
	}

	private static ImportModel model;

	private ImportModel() {

	}

	public static ImportModel getInstance() {
		if (model == null) {
			model = new ImportModel();
		}
		return model;
	}

	public void add(String string) {
		Import import1 = new Import();
		import1.setImportClassName(string);
		list.add(import1);
	}

}
