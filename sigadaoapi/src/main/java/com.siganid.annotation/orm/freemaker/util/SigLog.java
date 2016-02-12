package com.siganid.annotation.orm.freemaker.util;

public class SigLog {
	public static boolean isDubug = true;

	/**
	 * 把对象传进来自动显示 对象名和值
	 * 
	 * 效果 ： name:fff --- name:fff;
	 * 
	 */
	public static void d(String... obj) {
		String result = "";
		for (int i = 0; i < obj.length; i++) {
			result = result + "----" + obj[i];
		}
		if (isDubug) {
			System.out.println(result);
		}
	}
}
