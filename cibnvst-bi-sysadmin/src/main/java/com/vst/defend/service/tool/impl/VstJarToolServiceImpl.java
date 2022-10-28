package com.vst.defend.service.tool.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.service.tool.VstJarToolService;

/**
 * 
 * @author lhp
 * @date 2019-1-8 下午03:29:22
 * @version
 */
@Service
public class VstJarToolServiceImpl implements VstJarToolService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstJarToolServiceImpl.class);

	/**
	 * 执行SQL
	 */
	@Override
	public List<String> executeSql(String sparkExecParam) throws SystemException {
		List<String> result = new ArrayList<String>();
		try {
			final Process process = Runtime.getRuntime().exec("spark-submit " + sparkExecParam);
			/*new Thread() {
				@Override
				public void run() {
					try {
						System.out.println("process.getErrorStream() -> ");
						outLog(process.getErrorStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();*/
			
			result = outLog(process.getErrorStream());
			process.waitFor();
			System.out.println(process.exitValue());
			process.destroy();
		} catch (Exception e) {
			logger.error("执行SQL失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	private List<String> outLog(InputStream inputStream) throws IOException {
		List<String> result = new ArrayList<String>();
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
			String line;
			try {
				while ((line = lineNumberReader.readLine()) != null) {
					System.out.println(line);
					result.add(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			inputStream.close();
		}
		return result;
	}
	
//	/**
//	 * 执行SQL
//	 */
//	@Override
//	public JSONObject executeSql(String saveType, String sql) throws SystemException {
//		JSONObject result = null;
//		try {
//			String serverName = "10.2.0.187";
//			int port = 9099;
//			
//			Socket client = new Socket(serverName, port);
//			OutputStream outToServer = client.getOutputStream();
//			DataOutputStream out = new DataOutputStream(outToServer);
//			
//			String analysisSql = SqlAnalysisUtils.analysisTableName(sql);
//			out.writeUTF(String.format("sql=%s&saveType=%s", analysisSql, saveType));
//			DataInputStream in = new DataInputStream(client.getInputStream());
//			
//			String read = in.readUTF();
//			if(!ToolsString.isEmpty(read)){
//				result = JSONObject.parseObject(read);
//			}
//			
//			client.close();
//		} catch (Exception e) {
//			logger.error("执行SQL失败: " + SystemException.getExceptionInfo(e));
//			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
//		}
//		return result;
//	}
}
