package com.vst.defend.communal.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

public class ExportUtil {
	public static File ExportExcel(List<Map<String,Object>> data, String position, String fileName){
		File directory = new File(position);
        if(!directory.exists()){
        	directory.mkdirs();
        }
	    File file = new File(position+ "/" + fileName + ".xlsx");
	    try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(data!=null && !data.isEmpty()){
			XSSFWorkbook wb = new XSSFWorkbook();  //第一步，创建一个webbook，对应一个Excel文件
			XSSFSheet sheet = wb.createSheet();  //第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			XSSFRow row = sheet.createRow(0);  //第三步,在sheet中添加表头第0行
			XSSFCellStyle style = wb.createCellStyle(); //第四步，创建单元格，并设置值表头，设置表头居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //创建一个居中格式
			
			XSSFCell cell;
			List<String> titleList = new ArrayList<String>();//第一行标题
			for(String title : data.get(0).keySet()){ 
				titleList.add(title);
			}
			
			for(int i = 0; i < titleList.size(); i++){ //设置第一行标题
				cell = row.createCell(i); 
				cell.setCellValue(titleList.get(i));
			    cell.setCellStyle(style);
			    
			    sheet.setColumnWidth(i, 24 * 256); //设置单元格宽度；
			}
	
			//第五步，将数据库中查询到的数据写入到excel   
			for(int i = 0; i < data.size(); i++)  
	        {  
	            row = sheet.createRow(i + 1); 
	            Map<String, Object> map = data.get(i);
	            for(int j = 0; j < titleList.size(); j++){
			        cell = row.createCell(j);
			        
			        if("包名".equals(titleList.get(j))){
			        	cell.setCellValue(getPckName(map.get(titleList.get(j))+""));  //创建单元格，并设置值  
			        }else{
			        	// cell.setCellValue(getValue(map.get(titleList.get(j))+""));  //创建单元格，并设置值
			        	cell.setCellValue(map.get(titleList.get(j))+"");  //创建单元格，并设置值
			        }
			        cell.setCellStyle(style);  	//设置单元格居中
	            }
	        }
			 //第六步，将文件存到指定位置  
			try   
	        {  
	            FileOutputStream fileOut = new FileOutputStream(file);  
	            wb.write(fileOut);
	            fileOut.close();  
	        } catch (Exception e){  
	            e.printStackTrace();  
	        }  
		}
		return file;
	}
	
	public static File ExportExcel2(Map<Integer, List<Map<String, Object>>> data, String position, String fileName){
		File directory = new File(position);
        if(!directory.exists()){
        	directory.mkdirs();
        }
	    File file = new File(position+ "/" + fileName + ".xlsx");
	    try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(data!=null && !data.isEmpty()){
			XSSFWorkbook wb = new XSSFWorkbook();  //第一步，创建一个webbook，对应一个Excel文件
			for(Map.Entry<Integer, List<Map<String, Object>>> entry : data.entrySet()){
				String sheetTitle = entry.getKey().toString();
				List<Map<String, Object>> detailData = entry.getValue();
				
				XSSFSheet sheet = wb.createSheet(sheetTitle);  //第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				XSSFRow row = sheet.createRow(0);  //第三步,在sheet中添加表头第0行
				XSSFCellStyle style = wb.createCellStyle(); //第四步，创建单元格，并设置值表头，设置表头居中
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  //创建一个居中格式
				
				XSSFCell cell;
				List<String> titleList = new ArrayList<String>();//第一行标题
				for(String title : detailData.get(0).keySet()){ 
					titleList.add(title);
				}
				
				for(int i = 0; i < titleList.size(); i++){ //设置第一行标题
					cell = row.createCell(i); 
					cell.setCellValue(titleList.get(i));
				    cell.setCellStyle(style);
				    
				    sheet.setColumnWidth(i, 30 * 256); //设置单元格宽度；
				}
		
				//第五步，将数据库中查询到的数据写入到excel   
				for(int i = 0; i < detailData.size(); i++){  
		            row = sheet.createRow(i + 1); 
		            Map<String, Object> map = detailData.get(i);
		            for(int j = 0; j < titleList.size(); j++){
				        cell = row.createCell(j);
				        if("统计维度".equals(titleList.get(j))){
				        	cell.setCellValue(getDimesionName(ToolsNumber.parseInt(map.get(titleList.get(j))+"")));  //创建单元格，并设置值  
				        }else if("包名".equals(titleList.get(j))){
				        	cell.setCellValue(getPckName(map.get(titleList.get(j))+""));  //创建单元格，并设置值  
				        }else{
				        	cell.setCellValue(getValue(map.get(titleList.get(j))+""));  //创建单元格，并设置值  
				        }
				        cell.setCellStyle(style);  	//设置单元格居中
		            }
		        }
			}
			 //第六步，将文件存到指定位置  
			try  
	        {  
	            FileOutputStream fileOut = new FileOutputStream(file);  
	            wb.write(fileOut);
	            fileOut.close();  
	        } catch (Exception e){  
	            e.printStackTrace();  
	        }  
		}
		return file;
	}
	
	public static File ExportExcel3(List<Map<String,Object>> data, String position, String fileName){
		File directory = new File(position);
        if(!directory.exists()){
        	directory.mkdirs();
        }
	    File file = new File(position+ "/" + fileName + ".xls");
	    try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(data!=null && !data.isEmpty()){
			XSSFWorkbook wb = new XSSFWorkbook();  //第一步，创建一个webbook，对应一个Excel文件
			XSSFSheet sheet = wb.createSheet();  //第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			XSSFRow row = sheet.createRow(0);  //第三步,在sheet中添加表头第0行
			XSSFCellStyle style = wb.createCellStyle(); //第四步，创建单元格，并设置值表头，设置表头居中
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  //创建一个居中格式
			
			XSSFCell cell;
			List<String> titleList = new ArrayList<String>();//第一行标题
			for(String title : data.get(0).keySet()){ 
				titleList.add(title);
			}
			
			for(int i = 0; i < titleList.size(); i++){ //设置第一行标题
				cell = row.createCell(i); 
				cell.setCellValue(titleList.get(i));
			    cell.setCellStyle(style);
			    
			    sheet.setColumnWidth(i, 24 * 256); //设置单元格宽度；
			}
	
			//第五步，将数据库中查询到的数据写入到excel   
			for(int i = 0; i < data.size(); i++)  
	        {  
	            row = sheet.createRow(i + 1); 
	            Map<String, Object> map = data.get(i);
	            for(int j = 0; j < titleList.size(); j++){
			        cell = row.createCell(j);
			        cell.setCellValue(map.get(titleList.get(j))+"");  //创建单元格，并设置值  
			        cell.setCellStyle(style);  	//设置单元格居中
	            }
	        }
			 //第六步，将文件存到指定位置  
			try   
	        {  
	            FileOutputStream fileOut = new FileOutputStream(file);  
	            wb.write(fileOut);
	            fileOut.close();  
	        } catch (Exception e){  
	            e.printStackTrace();  
	        }  
		}
		return file;
	}
	
	/**
	 * 导出txt文件
	 * @param data 数据
	 * @param position 位置
	 * @param fileName 文件名
	 * @return
	 */
	public static File ExportTxt(List<String> data, String position, String fileName){
		File directory = new File(position);
        if(!directory.exists()){
        	directory.mkdirs();
        }
	    File file = new File(position+ "/" + fileName + ".txt");
	    try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(data!=null && !data.isEmpty()){
			StringBuilder sb = new StringBuilder();
			for(String line : data){
				sb.append(line).append("\r\n");
			}
			try {  
	            FileOutputStream fileOut = new FileOutputStream(file);
	            fileOut.write(sb.toString().getBytes());
	            fileOut.close();
	        } catch (Exception e){
	            e.printStackTrace();
	        }
		}
		return file;
	}
	
	
	public static String getValue(String str){
		String value = str;
		if("-1".equals(str)){
			value = "全部";
		}else if("-2".equals(str)){
			value = "未知/暂无";
		}
		return value;
	}
	
	public static String getDimesionName(int dimension){
		String chi = dimension+"";
		switch(dimension){
		 case 1: chi="全部";break;
		 case 2: chi="渠道";break;
		 case 3: chi="显示版本";break;
		 case 4: chi="升级版本";break;
		 case 5: chi="省份";break;
		 case 6: chi="网络运营商";break;
		 case 7: chi="盒子类型";break;
		}
		return chi;
	}
	
	public static String getPckName(String packageName){
		String pckame = packageName;
		if("net.myvst.v2".equals(packageName)){
			pckame = "vst全聚合";
		}else if("com.vst.live".equals(packageName)){
			pckame = "小微直播";
		}else if("com.vst.itv52.v1".equals(packageName)){
			pckame = "全聚合桌面版";
		}else if("com.aili.juhe".equals(packageName)){
			pckame = "阿里版全聚合";
		}else if("com.vst.sunniwell.live".equals(packageName)){
			pckame = "朝阳版直播";
		}else if("com.vst.live.allwinner".equals(packageName)){
			pckame = "全志直播";
		}else if("com.vst.vod.allwinner".equals(packageName)){
			pckame = "全志点播";
		}else if("com.vst.wifianalyze".equals(packageName)){
			pckame = "wifi神器";
		}else if("com.vst.health.allwinner".equals(packageName)){
			pckame = "全志养生";
		}else if("com.vst.LocalPlayer".equals(packageName)){
			pckame = "极清播放器";
		}else if("com.vst.sport".equals(packageName)){
			pckame = "体育";
		}else if("com.vst.health".equals(packageName)){
			pckame = "养生";
		}else if("com.vst.vststudy".equals(packageName)){
			pckame = "公开课";
		}else if("com.vst.children".equals(packageName)){
			pckame = "少儿";
		}else if("com.vst.games".equals(packageName)){
			pckame = "游戏";
		}else if("com.love.tuidan".equals(packageName)){
			pckame = "推单";
		}else if("com.js.litchi".equals(packageName)){
			pckame = "优客直播";
		}else if("net.myvst.v2(CIBN)".equals(packageName)){
			pckame = "vst全聚合(CIBN)";
		}else if("net.myvst.v2(3.0)".equals(packageName)){
			pckame = "vst全聚合(3.0)";
		}else if("com.vst.itv52.v1(CIBN)".equals(packageName)){
			pckame = "全聚合桌面版(CIBN)";
		}else if("com.vst.itv52.v1(3.0)".equals(packageName)){
			pckame = "全聚合桌面版(3.0)";
		}
		return pckame;
	}
	
	/**
	 * 根据地区，截取出省份
	 * @param address
	 * @return
	 */
	public static String getProvinceByAddress(String address){
		String province = "";
		if(!ToolsString.isEmpty(address)){
			int index = address.indexOf("省");
			if(index != -1){	//包含'省'
				province = address.substring(0, index+1);
			}else{
				index = address.indexOf("自治区");	//包含'自治区'
				if(index != -1){
					province = address.substring(0, index+3);
				}else{
					index = address.indexOf("市");	//包含'市'
					if(index != -1){
						province = address.substring(0, index+1);
					}else{
						index = address.indexOf("区");	//包含'区'
						if(index != -1){
							province = address.substring(0, index+1);
						}else{
							province = address;
						}
					}
				}
			}
		}
		return province;
	}
}
