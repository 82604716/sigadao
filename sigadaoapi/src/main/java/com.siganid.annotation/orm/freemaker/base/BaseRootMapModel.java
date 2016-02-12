package com.siganid.annotation.orm.freemaker.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseRootMapModel {
	protected abstract BaseRootMapModel init();

	ArrayList<Object> list = new ArrayList<Object>();
	RootMap rootMap = RootMap.init();

	public void add(Object object) {
		rootMap.add(object);
	}

	public void add(Class clazz) {
		rootMap.addClassToMap(clazz);
	}

	public void add(List list) {
		rootMap.addListToMap(list);
	}

	public void add(String keyName, List list) {
		rootMap.addListToMap(keyName, list);
	}

	public void reset() {
		rootMap.reset();
	}

	public Map<String, Object> getRootMap() {

		return rootMap.getRootMap();
	}
}
