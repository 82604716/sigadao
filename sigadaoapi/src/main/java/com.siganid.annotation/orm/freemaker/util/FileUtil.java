package com.siganid.annotation.orm.freemaker.util;


import com.siganid.annotation.orm.freemaker.base.GenConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {

	private static FileUtil model;

	private FileUtil() {
	}

	public static FileUtil getInstance() {
		if (model == null) {
			model = new FileUtil();
		}
		return model;
	}

	/**
	 * ʹ���ļ�ͨ���ķ�ʽ�����ļ�
	 * 
	 * @param s
	 *            Դ�ļ�
	 * @param t
	 *            ���Ƶ������ļ�
	 */
	public static void fileCopy(File inFile, File outFile) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(inFile);
			fo = new FileOutputStream(outFile);
			in = fi.getChannel();// �õ���Ӧ���ļ�ͨ��
			out = fo.getChannel();// �õ���Ӧ���ļ�ͨ��
			in.transferTo(0, in.size(), out);// ��������ͨ�������Ҵ�inͨ����ȡ��Ȼ��д��outͨ��
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
