package com.vst.common.tools.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author weiwei
 * @date 2015-9-30 下午05:51:05
 * @description 
 * @version	
 */
public class ToolsIO {

	/**
	 * 把输入流转换成字符串
	 * @param is
	 * @return
	 */
	public static String toString(InputStream is, String encoding){
		byte[] array = toArray(is);
		if(array != null){
			try {
				return new String(array, encoding);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}
	
	/**
	 * 把输入流转换成字符串
	 * @param is
	 * @return
	 */
	public static String toString(InputStream is){
		return toString(is, "utf-8");
	}
	
	/**
	 * 转换成字节数组
	 * @param is
	 * @return
	 */
	public static byte[] toArray(InputStream is){
		if(is != null){
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				byte[] buf = new byte[2 << 8];
				int len = -1;
				
				while((len = is.read(buf)) != -1){
					baos.write(buf, 0, len);
					baos.flush();
				}
				return baos.toByteArray();
			} catch (UnsupportedEncodingException e) {
			} catch (IOException e) {
			} finally {
				closeStream(baos, is);
			}
		}
		return null;
	}
	
	/**
	 * 关流
	 * @param close
	 */
	public static void closeStream(Closeable... close){
		if(close != null){
			try {
				for(Closeable c : close){
					if(c != null){
						c.close();
					}
				}
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 解压字符串
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static ByteArrayOutputStream uncompress(byte[] byteArray) throws IOException {
		if (byteArray == null || byteArray.length == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[1024];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
			out.flush();
		}
		out.close();
		return out;
	}
	
	/**
	 * 解压成字符串
	 * @param byteArray
	 * @return
	 * @throws IOException
	 */
	public static String uncompressToString(byte[] byteArray, String charsetName) throws IOException{
		ByteArrayOutputStream bos = uncompress(byteArray);
		if(bos != null){
			return bos.toString(charsetName);
		}
		return null;
	}
	
	/**
	 * 压缩字符串
	 * @param str
	 * @return 返回byteArrayOutputStream
	 * @throws IOException
	 */
	public static ByteArrayOutputStream compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out;
	}

	
	/**
	 * 压缩字符串
	 * @param str
	 * @return 返回byteArray
	 * @throws IOException
	 */
	public static byte[] compressToByteArray(String str) throws IOException {
		ByteArrayOutputStream out = compress(str);
        return out == null ? null : out.toByteArray();
    }
}
