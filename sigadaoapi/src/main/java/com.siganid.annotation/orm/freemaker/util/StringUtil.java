package com.siganid.annotation.orm.freemaker.util;

public class StringUtil {

	public static String makeFirstCharUp(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1)
				.toUpperCase());
	}

	public static String makeFirstCharLow(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1)
				.toLowerCase());
	}

	public static String getShortClassName(String modelClassName) {
		String str[] = modelClassName.split("\\.");
		return str[str.length - 1];

	}
}
