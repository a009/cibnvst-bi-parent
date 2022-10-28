package com.vst.core.communal.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-11 下午09:42:31
 * @decription
 * @version
 */
public class ToolsHdfs implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -640428772419658912L;

	/**
	 * hdfs管理类
	 */
	private FileSystem _fs;
	
	/**
	 * hadoop配置
	 */
	private Configuration _conf;
	
	/**
	 * 空集合
	 */
	private static ArrayList<String> EMPTY_LIST = new ArrayList<String>(0);
	
	/**
	 * 创建hdfs对象
	 * @throws IOException
	 */
	public ToolsHdfs(){
		_conf = new Configuration(true);
		_conf.addResource(new Path("/home/hadoop/hadoop/etc/hadoop/core-site.xml"));
		_conf.addResource(new Path("/home/hadoop/hadoop/etc/hadoop/hdfs-site.xml"));
		_conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		try {
			_fs = FileSystem.get(_conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件
	 * @param filePath
	 * @param data
	 * @throws IOException
	 */
	public void createFile(String filePath, byte[] data) throws IOException {
		FSDataOutputStream fsDataOutputStream = _fs.create(new Path(filePath));
		fsDataOutputStream.write(data);
	}

	/**
	 * 创建文件夹
	 * @param dirPath
	 * @throws IOException
	 */
	public void mkDirs(String dirPath) throws IOException {
		_fs.mkdirs(new Path(dirPath));
	}

	/**
	 * 删除文件,true:递归删除,false:非递归删除
	 * @param filePath
	 * @param isReturn
	 * @throws IOException
	 */
	public boolean deleteFile(String filePath, boolean isReturn) throws IOException {
		return _fs.delete(new Path(filePath), isReturn);
	}

	/**
	 * 上传本地文件到hdfs
	 * @param localFilePath
	 * @param remoteFilePath
	 * @throws IOException
	 */
	public void uploadFile(String localFilePath, String remoteFilePath) throws IOException {
		_fs.copyFromLocalFile(new Path(localFilePath), new Path(remoteFilePath));
	}
	
	/**
	 * 判断hdfs上的某个文件是否存在
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public boolean exist(String filePath) throws IOException {
		return _fs.exists(new Path(filePath));
	}
	
	/**
	 * 列出
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> listAll(String dir) throws IOException {  
        if (ToolsString.isEmpty(dir)) {  
            return EMPTY_LIST;  
        }
        FileStatus[] stats = _fs.globStatus(new Path(dir));  
        ArrayList<String> names = new ArrayList<String>();  
        for (int i = 0; i < stats.length; ++i) {
        	if(stats[i].isDirectory())
            if (stats[i].isFile() && stats[i].getPath().getName().startsWith("part-")) {  
                names.add(stats[i].getPath().toString());  
            }
        }  
        return names;  
    }
	
	/**
	 * 关闭流
	 */
	public void close(){
		ToolsIO.closeStream(_fs);
	}
}
