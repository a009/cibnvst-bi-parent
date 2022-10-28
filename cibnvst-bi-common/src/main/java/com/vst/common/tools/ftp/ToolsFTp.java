package com.vst.common.tools.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;

import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpProtocolException;

/**
 * @author weiwei
 * @date 2014-9-19 下午03:51:36
 * @description 
 * @version	
 */
@SuppressWarnings("restriction")
public class ToolsFTp {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(ToolsFTp.class);
	
	/**
	 * ftp服务地址
	 */
	private String _ftpServer;
	
	/**
	 * ftp服务器端口
	 */
	private int _ftpPort;
	
	/**
	 * ftp服务器用户名
	 */
	private String _ftpUser;
	
	/**
	 * ftp服务器密码
	 */
	private String _ftpPassword;

	/**
	 * ftp客户端实例
	 */
	private static FTPClient _ftpClient;
	
	/**
	 * 
	 */
	private static FtpClient ftpClient = null;
	
	/**
	 * 构造器私有化
	 */
	public ToolsFTp(){
		URL url = Thread.currentThread().getContextClassLoader().getResource("common.properties");
		if(url != null){
			File file = new File(url.getPath());
			if(file.exists()){
				FileInputStream fis = null;
				Properties p = new Properties();
				try {
					fis = new FileInputStream(file);
					p.load(fis);
				} catch (Exception e) {
				}finally{
					ToolsIO.closeStream(fis);
				}
				_ftpServer = p.getProperty("ftp_server");
				_ftpPort = ToolsNumber.parseInt(p.getProperty("ftp_port"), 21);
				_ftpUser = p.getProperty("ftp_user");
				_ftpPassword = p.getProperty("ftp_pwd");
			}
		}
	}
	
	/**
	 * 带参构造器
	 * @param ftpServer
	 * @param ftpPort
	 * @param ftpUser
	 * @param ftpPassword
	 */
	public ToolsFTp(String ftpServer, int ftpPort, String ftpUser,
			String ftpPassword) {
		_ftpServer = ftpServer;
		_ftpPort = ftpPort;
		_ftpUser = ftpUser;
		_ftpPassword = ftpPassword;
	}


	/**
	 * 建立ftp连接
	 */
	private FTPClient connect(){
		_ftpClient = new FTPClient();
		try {
			_ftpClient.connect(_ftpServer, _ftpPort);
			_ftpClient.login(_ftpUser, _ftpPassword);
		} catch (SocketException e) {
			logger.error("Ftp socket error. ERROR:" + e.getMessage());
		} catch (IOException e) {
			logger.error("Ftp IO error. ERROR:" + e.getMessage());
		}
		return _ftpClient;
	}
	
	/**
	 * 设置ftp相关配置
	 * @param ftp
	 * @throws IOException 
	 */
	private void setFtpClientConfig(FTPClient ftp) throws IOException{
		FTPClientConfig conf = new FTPClientConfig("UNIX");
		ftp.configure(conf);
		// 被动传输模式
		ftp.enterLocalPassiveMode();
		// 二进制传输模式
		ftp.setFileType(FTP.BINARY_FILE_TYPE);	
	}
	
	
	/**
	 * 上传文件的ftp方法
	 * @param destDir
	 * @param remoteHost
	 * @param filePath
	 * @param fileType
	 * @return
	 */
	public String uploadFile(String destDir, String remoteHost, String filePath, String fileType){
		// 返回标志
		String result = null;
		// 输入流
		FileInputStream fis = null;
		// 输出流
		TelnetOutputStream tos = null;
		try {
			// 只有当目的地文件路径存在并且待上传的文件路径不为空时，才可能允许上传
			if(!ToolsString.isEmpty(destDir) && !ToolsString.isEmpty(filePath)){
				File file = new File(filePath);
				// 待上传文件存在，才继续上传
				if(file.exists()){
					// 建立ftp连接
					FTPClient ftp = connect();
					int reply = ftp.getReplyCode();
					
					if(!FTPReply.isPositiveCompletion(reply)){
						logger.error("FTP server refused connection.");
					}else{
						ftp.setControlEncoding("gbk");
						// 设置ftp相关设置
						setFtpClientConfig(ftp);
						
						String fileName = ToolsRandom.getRandom(20) + fileType;
						ftp.makeDirectory(destDir);
						ftp.changeWorkingDirectory(destDir);
						fis = new FileInputStream(file);
						boolean flag = ftp.appendFile(fileName, fis);
						// 如果上传成功，才返回路径
						if(flag) result = destDir + "/" + fileName;
						logger.info("upload file "+ flag +"!" + result);
					}
					
					ftp.logout(); // 退出登录
					ftp.disconnect();// 关闭连接
				}
			}
		} catch (Exception e) {
			logger.error("ERROR:" + e.getMessage());
		} finally {
			// 关流
			ToolsIO.closeStream(fis, tos);
		}
		return result;
	}
	
	/**
	 * 上传文件的ftp方法
	 * @param destDir
	 * @param remoteHost
	 * @param filePath
	 * @param fileName 格式：文件名.后缀名
	 * @param mark
	 * @return
	 */
	public String uploadFile(String destDir, String remoteHost, String filePath, String fileName, boolean mark){
		// 返回标志
		String result = null;
		// 输入流
		FileInputStream fis = null;
		// 输出流
		TelnetOutputStream tos = null;
		try {
			// 只有当目的地文件路径存在并且待上传的文件路径不为空时，才可能允许上传
			if(!ToolsString.isEmpty(destDir) && !ToolsString.isEmpty(filePath)){
				File file = new File(filePath);
				// 待上传文件存在，才继续上传
				if(file.exists()){
					// 建立ftp连接
					FTPClient ftp = connect();
					int reply = ftp.getReplyCode();
					
					if(!FTPReply.isPositiveCompletion(reply)){
						logger.error("FTP server refused connection.");
					}else{
						ftp.setControlEncoding("gbk");
						// 设置ftp相关设置
						setFtpClientConfig(ftp);
						
						// 上传新文件
						ftp.makeDirectory(destDir);
						ftp.changeWorkingDirectory(destDir);
						fis = new FileInputStream(file);
						// 文件名转码(防止中文乱码)
						String newFileName = new String(fileName.getBytes("GBK"),"iso-8859-1");
						// 替换源文件
						boolean flag = ftp.storeFile(newFileName, fis);
						
						// 如果上传成功，才返回路径
						if(flag) result = destDir + "/" + fileName;
						logger.info("upload file "+ flag +"!" + result);
					}
					
					ftp.logout(); // 退出登录
					ftp.disconnect();// 关闭连接
				}
			}
		} catch (Exception e) {
			logger.error("ERROR:" + e.getMessage());
		} finally {
			// 关流
			ToolsIO.closeStream(fis, tos);
		}
		return result;
	}
	
	/**
	 * 上传文件的ftp方法
	 * @param destDir
	 * @param remoteHost
	 * @param fileStream
	 * @param fileType
	 * @return
	 */
	public String uploadFile(String destDir, String remoteHost, InputStream fileStream, String fileType){
		// 返回标志
		String result = null;
		// 输入流
		FileInputStream fis = null;
		// 输出流
		TelnetOutputStream tos = null;
		try {
			// 只有当目的地文件路径存在并且待上传的文件路径不为空时，才可能允许上传
			if(!ToolsString.isEmpty(destDir)){
				// 建立ftp连接
				FTPClient ftp = connect();
				int reply = ftp.getReplyCode();
				
				if(!FTPReply.isPositiveCompletion(reply)){
					logger.error("FTP server refused connection.");
				}else{
					ftp.setControlEncoding("gbk");
					// 设置ftp相关设置
					setFtpClientConfig(ftp);
					
					String fileName = ToolsRandom.getRandom(20) + fileType;
					ftp.makeDirectory(destDir);
					ftp.changeWorkingDirectory(destDir);
					boolean flag = ftp.appendFile(fileName, fileStream);
					// 如果上传成功，才返回路径
					if(flag) result = destDir + "/" + fileName;
					logger.info("upload file "+ flag +"!" + result);
					
					ftp.logout(); // 退出登录
					ftp.disconnect();// 关闭连接
				}
			}
		} catch (Exception e) {
			logger.error("ERROR:" + e.getMessage());
		} finally {
			// 关流
			ToolsIO.closeStream(fis, tos);
		}
		return result;
	}
	
	/**
	 * 上传文件的ftp方法，往/vst/activity中添加
	 * @param destDir
	 * @param remoteHost
	 * @param filePath
	 * @return
	 */
	public String uploadFile2(String destDir, String remoteHost, String filePath){
		// 返回标志
		String result = null;
		// 输入流
		FileInputStream fis = null;
		// 输出流
		TelnetOutputStream tos = null;
		try {
			// 只有当目的地文件路径存在并且待上传的文件路径不为空时，才可能允许上传
			if(!ToolsString.isEmpty(destDir) && !ToolsString.isEmpty(filePath)){
				File file = new File(filePath);
				// 待上传文件存在，才继续上传
				if(file.exists()){
					// 建立ftp连接
					FTPClient ftp = connect();
					connectFTP();
					int reply = ftp.getReplyCode();
					
					if(!FTPReply.isPositiveCompletion(reply)){
						logger.error("FTP server refused connection.");
					}else{
						ftp.setControlEncoding("gbk");
						// 设置ftp相关设置
						setFtpClientConfig(ftp);
						
						//1、建立日期文件夹
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						String date = sdf.format(new Date());
						// 获取目标文件夹
						String dir = destDir + "/" + date;
						//createDir(dir);
						ftp.mkd(dir);
						System.out.println("创建新目录：" + dir + "成功！");
						
						//2、往日期文件夹中建立子文件夹
						int max = getMaxFileName(dir);
						String subDir = dir + "/" + (max+1);
						//createDir(subDir);
						ftp.mkd(subDir);
						System.out.println("创建子目录：" + subDir + "成功！");
						//3、往子文件夹中放入文件
						String fileName = "t.jpg";
						ftp.changeWorkingDirectory(subDir);
						fis = new FileInputStream(file);
						boolean flag2 = ftp.appendFile(fileName, fis);
						// 如果上传成功，才返回路径
						if(flag2) result = subDir + "/" + fileName;
						logger.info("upload file "+ flag2 +"!" + result);
						//4、复制m.html到子文件夹下
						copyFile("/vst/activity/m.html", subDir + "/m.html");
						//ftp.sendCommand("cp -u -v " + destDir+"/m.html " + subDir+"/m.html");
						System.out.println("复制m.html文件成功！");
					}
					
					ftp.logout(); // 退出登录
					ftp.disconnect();// 关闭连接
					closeFTP();
				}
			}
		} catch (Exception e) {
			logger.error("ERROR:" + e.getMessage());
		} finally {
			// 关流
			ToolsIO.closeStream(fis, tos);
		}
		return result;
	}
	
	/**
	 * 上传文件的ftp方法，往/vst/activity中添加
	 * @param destDir
	 * @param remoteHost
	 * @param filePath
	 * @return
	 */
	public String uploadFile2(String destDir, String remoteHost, InputStream fileStream){
		// 返回标志
		String result = null;
		// 输入流
		FileInputStream fis = null;
		// 输出流
		TelnetOutputStream tos = null;
		try {
			// 只有当目的地文件路径存在并且待上传的文件路径不为空时，才可能允许上传
			if(!ToolsString.isEmpty(destDir)){
				// 建立ftp连接
				FTPClient ftp = connect();
				connectFTP();
				int reply = ftp.getReplyCode();
				
				if(!FTPReply.isPositiveCompletion(reply)){
					logger.error("FTP server refused connection.");
				}else{
					ftp.setControlEncoding("gbk");
					// 设置ftp相关设置
					setFtpClientConfig(ftp);
					
					//1、建立日期文件夹
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String date = sdf.format(new Date());
					// 获取目标文件夹
					String dir = destDir + "/" + date;
					//createDir(dir);
					ftp.mkd(dir);
					System.out.println("创建新目录：" + dir + "成功！");
					
					//2、往日期文件夹中建立子文件夹
					int max = getMaxFileName(dir);
					String subDir = dir + "/" + (max+1);
					//createDir(subDir);
					ftp.mkd(subDir);
					System.out.println("创建子目录：" + subDir + "成功！");
					//3、往子文件夹中放入文件
					String fileName = "t.jpg";
					ftp.changeWorkingDirectory(subDir);
					boolean flag = ftp.appendFile(fileName, fileStream);
					// 如果上传成功，才返回路径
					if(flag) result = subDir + "/" + fileName;
					logger.info("upload file "+ flag +"!" + result);
					//4、复制m.html到子文件夹下
					copyFile("/pic/cibnvst/activity/m.html", subDir + "/m.html");
					//ftp.sendCommand("cp -u -v " + destDir+"/m.html " + subDir+"/m.html");
					System.out.println("复制m.html文件成功！");
				}
				
				ftp.logout(); // 退出登录
				ftp.disconnect();// 关闭连接
				closeFTP();
			}
		} catch (Exception e) {
			logger.error("ERROR:" + e.getMessage());
		} finally {
			// 关流
			ToolsIO.closeStream(fis, tos);
		}
		return result;
	}
	
	/**
	 * 连接FTP
	 * @throws FtpProtocolException
	 */
	private void connectFTP() throws FtpProtocolException {
        try {
        	ftpClient = FtpClient.create();
        	ftpClient.connect(new InetSocketAddress(_ftpServer, _ftpPort));
        	ftpClient.login(_ftpUser, _ftpPassword.toCharArray());
        	ftpClient.setBinaryType();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
  
	/**
	 * 关闭FTP
	 */
    private void closeFTP() {
        try{
            if(ftpClient != null) {
                ftpClient.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 检查文件夹是否存在
	 * @param dir
	 * @return
	 */
    @SuppressWarnings("unused")
	private boolean isDirExist(String dir) {
        try{
        	//connectFTP();
        	ftpClient.changeDirectory(dir);
        	closeFTP();
        } catch(Exception e) {
            return false;
        } finally {
        	//closeFTP();
        }
        return true;
    }
	
	/**
	 * 创建文件夹
	 * @param dir
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void createDir(String dir) throws Exception {
		try{
			//connectFTP();
			ftpClient.setAsciiType();
	        StringTokenizer s = new StringTokenizer(dir, "/");
	        s.countTokens();
	        String pathName = "";
	        while(s.hasMoreElements()) {
	            pathName= pathName + "/"+ (String) s.nextElement();
	            try{
	                ftpClient.makeDirectory(pathName);
	            }catch(Exception e) {
	                //e.printStackTrace();
	            }
	        }
	        ftpClient.setBinaryType();
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			//closeFTP();
		}
    }
	
	/**
	 * 获取文件夹下所有的文件
	 * @param dir
	 * @return
	 */
	private Iterator<FtpDirEntry> getSubFiles(String dir){
		Iterator<FtpDirEntry> iter = null;
		try{
			//connectFTP();
			iter = ftpClient.listFiles(dir);
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			//closeFTP();
		}
		return iter;
	}
	
	/**
	 * 获取文件夹名字最大的数值
	 * @param dir
	 * @return
	 */
	private int getMaxFileName(String dir){
		int max = 0;
		Iterator<FtpDirEntry> iter = getSubFiles(dir);
		String reg = "^[0-9]+$";
		while(iter.hasNext()){
			FtpDirEntry file = iter.next();
			String fileName = file.getName();
			if(fileName.matches(reg)){
				int num = Integer.valueOf(fileName);
				if(num > max){
					max = num;
				}
			}
		}
		return max;
	}
	
	/**
	 * 复制文件
	 * @param srcFile 源文件
	 * @param targetFile 目标文件
	 */
	private void copyFile(String srcFile, String targetFile){
		try{
			//connectFTP();
			InputStream input = ftpClient.getFileStream(srcFile);
			ftpClient.putFile(targetFile, input);
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			//closeFTP();
		}
	}
}