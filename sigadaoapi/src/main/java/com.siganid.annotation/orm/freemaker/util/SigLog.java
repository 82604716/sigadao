package com.siganid.annotation.orm.freemaker.util;

public class SigLog {
	public static boolean isDubug = true;

	/**
	 * �Ѷ��󴫽����Զ���ʾ ��������ֵ
	 * 
	 * Ч�� �� name:fff --- name:fff;
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
