package com.geetion.job138.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.concurrent.TimeoutException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * @author 80work
 * @version 0.1
 */
public class FileUtil {

	/**
	 * 判断是否存在 SDcard
	 * 
	 * @return boolean
	 */
	public static boolean hasSdCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取保存路径(绝对路径)
	 * 
	 * @param path
	 *            要保存的子路径
	 * @return
	 */
	public static String getSavePath(String path) {
		String savePath = Environment.getExternalStorageDirectory().toString() + path;
		File file = new File(savePath);
		if (!file.exists())
			file.mkdirs();
		return savePath;
	}

	/**
	 * 保存文件到储存器
	 * 
	 * @param uri
	 *            传入文件的URI
	 * @param subPath
	 *            保存文件的子路径
	 * @param fileName
	 *            保存文件的名称
	 * @param context
	 *            当前Activity活动
	 * @return File 保存后的File文件
	 */
	public static File saveFile(Uri uri, String subPath, String fileName, Activity context) {
		ContentResolver cr = context.getContentResolver();
		File resultFile = null;
		try {
			InputStream in = cr.openInputStream(uri);
			BufferedInputStream bis = new BufferedInputStream(in);
			byte[] btye = new byte[1024 * 1024];
			int read = -1;
			resultFile = new File(getSavePath(subPath), fileName);
			FileOutputStream fos = new FileOutputStream(resultFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			try {
				while ((read = bis.read(btye)) != -1) {
					bos.write(btye, 0, read);
				}
				bos.close();
				bis.close();
				fos.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return resultFile;
	}

	/**
	 * 保存文件到储存器
	 * 
	 * @param uri
	 *            传入文件的URI
	 * @param subPath
	 *            保存文件的子路径
	 * @param fileName
	 *            保存文件的名称
	 * @param context
	 *            当前Activity活动
	 * @return File 保存后的File文件
	 */
	public static File saveFile(InputStream in, String subPath, String fileName) {
		File resultFile = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(in);
			byte[] btye = new byte[1024 * 1024];
			int read = -1;
			resultFile = new File(getSavePath(subPath), fileName);
			FileOutputStream fos = new FileOutputStream(resultFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			try {
				while ((read = bis.read(btye)) != -1) {
					bos.write(btye, 0, read);
				}
				bos.close();
				bis.close();
				fos.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return resultFile;
	}

	/**
	 * 保存网络文件到储存器
	 * 
	 * @param url
	 *            传入文件的URL地址
	 * @param subPath
	 *            保存文件的子路径
	 * @param fileName
	 *            保存文件的名称
	 * @param context
	 *            当前Activity活动
	 * @return File 保存后的File文件
	 */
	public static File saveNetwordFile(URL url, String subPath, String fileName, Context context) throws MyHttpException {
		File resultFile = null;
		URLConnection conntection = null;
		InputStream in = null;
		try {

			conntection = url.openConnection();
			conntection.setConnectTimeout(5000);
			conntection.setReadTimeout(20000);
			in = conntection.getInputStream();

			BufferedInputStream bis = new BufferedInputStream(in);
			byte[] btye = new byte[1024 * 1024];
			int read = -1;
			resultFile = new File(getSavePath(subPath), fileName);
			FileOutputStream fos = new FileOutputStream(resultFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			while ((read = bis.read(btye)) != -1) {
				bos.write(btye, 0, read);
			}
			bos.close();
			bis.close();
			fos.close();
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new MyHttpException("500", "网络异常请稍后再试");
		}
		return resultFile;
	}

	/**
	 * 删除储存器文件
	 * 
	 * @param file
	 *            傳入要刪除的文件
	 * @return boolean 是否刪除成功
	 */
	public static boolean deleteFile(File file) {
		return file.delete();

	}

	/**
	 * 获取储存器指定路径的所有文件
	 * 
	 * @param path
	 *            文件所有的绝对路径
	 * @return File[] 路径下的所有文件
	 */
	public static File[] getPathFiles(String path) {
		File file = new File(path);
		File[] resultFiles = null;
		if (file.exists()) {
			resultFiles = file.listFiles();
		}
		return resultFiles;
	}

	/**
	 * 获取文件里面所有字符串
	 * 
	 * @param file
	 *            目标文件
	 * @return
	 */
	public static String getFileText(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			StringBuffer sb = new StringBuffer();
			while ((text = reader.readLine()) != null) {
				sb.append(text);
			}
			reader.close();
			return sb.toString();
		} catch (Exception e) {
			Log.e("Exception", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解压缩zip文件
	 * 
	 * @param sourceFileName
	 *            要解压缩的zip文件
	 * @param desDir
	 *            解压缩到的目录
	 * @throws IOException
	 *             压缩文件的过程中可能会抛出IO异常，请自行处理该异常。
	 */
	public static void unZIP(String sourceFileName, String desDir) throws IOException {
		// 创建压缩文件对象
		ZipFile zf = new ZipFile(new File(sourceFileName));

		// 获取压缩文件中的文件枚举
		Enumeration<? extends ZipEntry> en = zf.entries();
		int length = 0;
		byte[] b = new byte[1028 * 8];

		// 提取压缩文件夹中的所有压缩实例对象
		while (en.hasMoreElements()) {
			ZipEntry ze = en.nextElement();
			// System.out.println("压缩文件夹中的内容："+ze.getName());
			// System.out.println("是否是文件夹："+ze.isDirectory());
			// 创建解压缩后的文件实例对象
			File f = new File(desDir + ze.getName());
			System.out.println("解压后的内容：" + f.getPath());
			System.out.println("是否是文件夹：" + f.isDirectory());
			// 如果当前压缩文件中的实例对象是文件夹就在解压缩后的文件夹中创建该文件夹
			if (ze.isDirectory()) {
				f.mkdirs();
			} else {
				// 如果当前解压缩文件的父级文件夹没有创建的话，则创建好父级文件夹
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
					System.out.println("创建文件夹：" + f.getParentFile().getPath());
				}

				// 将当前文件的内容写入解压后的文件夹中。
				OutputStream outputStream = new FileOutputStream(f);
				InputStream inputStream = zf.getInputStream(ze);
				while ((length = inputStream.read(b)) > 0)
					outputStream.write(b, 0, length);

				inputStream.close();
				outputStream.close();
			}
		}
		zf.close();
	}

	/**
	 * 删除文件夹或者文件
	 * 
	 * @param folderPath
	 *            String 文件夹路径或者文件的绝对路径 如：/mnt/sdcard/test/1.png
	 */
	public static void deleteDirectory(String folderPath) {
		try {
			// 删除文件夹里所有的文件及文件夹
			deleteAllFile(folderPath);
			File lastFile = new File(folderPath);
			if (lastFile.exists()) {
				// 最后删除空文件夹
				lastFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径或者文件的绝对路径 如：/mnt/sdcard/test/1.png
	 */
	public static void deleteAllFile(String path) {
		// 在内存开辟一个文件空间，但是没有创建
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			String[] tempList = file.list();
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				if (path.endsWith(File.separator)) {
					temp = new File(path + tempList[i]);
				} else {
					temp = new File(path + File.separator + tempList[i]);
				}
				if (temp.isFile()) {
					temp.delete();
				}
				if (temp.isDirectory()) {
					// 先删除文件夹里面的文件
					deleteAllFile(path + "/" + tempList[i]);
					// 再删除空文件夹
					deleteDirectory(path + "/" + tempList[i]);
				}
			}
		}
	}

}
