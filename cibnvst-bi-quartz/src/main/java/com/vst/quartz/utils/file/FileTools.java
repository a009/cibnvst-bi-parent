package com.vst.quartz.utils.file;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.utils.file.util.CreateFolder;
import com.vst.quartz.utils.mail.Email;
import com.vst.quartz.utils.mail.EmailModule;
import com.vst.quartz.utils.mail.EmailTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author kai 
 * 文件处理类
 * TODO: 2018/3/22
 */
public class FileTools {

    private static Log logger = LogFactory.getLog(FileTools.class);





    /**
     * @author kai
     * 将备份数据导出文件
     * 2017-1-12 10：07：28
     * @param strings 需要备份的数据
     * @param config 任务配置信息
     */
    public static String exportSql(List<String> strings,VstQuartzConfig config){
        //保存地址为保存目录加表名加当前时间

        OutputStreamWriter streamWriter;

        BufferedWriter writer=null;
        String fileUrl = "";
        try {

            //创建文件
            fileUrl = CreateFolder.getFile(config.getVst_qc_file_address(),config.getVst_qc_table(),"sql");
            //保存文件
            OutputStream stream=new FileOutputStream(fileUrl);
            streamWriter=new OutputStreamWriter(stream);
            writer=new BufferedWriter(streamWriter);
            //遍历数据DD
            for (String s:strings){
                //写入数据
                writer.write(s);
                //换行
                writer.newLine();
            }

            writer.flush();

        }catch (FileNotFoundException e) {
            logger.info("写入操作的时候出错");
            e.printStackTrace();

            //发送邮件提醒
            Email email = new Email();
            email.setTitle("文件备份时写入出错。。。:");
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("文件备份时写入出错。。","LiuKai",method,e));
            EmailTools.sendEmail(email);
        }catch (IOException e) {
            logger.info("创建文件夹或者写入的时候出错");
            e.printStackTrace();
            //发送邮件提醒
            Email email = new Email();
            email.setTitle("创建文件夹或者写入的时候出错。。。:");
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("创建文件夹或者写入的时候出错。。","LiuKai",method,e));
            EmailTools.sendEmail(email);
            strings.clear();
        } finally {
            //关闭流
            CloseableUtil.close(writer);
            strings.clear();
        }

        return fileUrl;

    }

    /**
     * kai
     * 导出excel格式的数据
     * TODO: 2018/4/19 20：09：10
     * @param maps 需要导出的数据
     * @param config 任务配置
     * @param map 每条数据对应的列名
     */
    public static String exportExcel(List<Map<String,Object>> maps, VstQuartzConfig config
                                  ,Map<String,String> map){
        //创建一个Excel文件
        HSSFWorkbook book = new HSSFWorkbook();
        int page = 1;
        int total = maps.size();
        int pageSize = 5000;

        if (total >= pageSize){
           page = (pageSize >= total ? 1 : (total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        }

        FileOutputStream stream=null;
        String fileUrl = "";
        //将文件存在指定位置
        try {

            //用于接收分页的数据
            List<Map<String,Object>> list;

            for (int p = 1;p<=page;p++){

                //获取数据
                if (p == page){
                    list = maps.subList((p-1)*pageSize,total);
                }else{
                    list = maps.subList((p-1)*pageSize,pageSize*p);
                }

                if (list.size() <= 0){
                    continue;
                }

                //在HSSFWorkbook中添加一个Sheet，创建一页数据
                HSSFSheet sheet = book.createSheet(config.getVst_qc_table()+"表数据第"+p+"页备份");
                //统一设置列宽
                sheet.setDefaultColumnWidth(25);

                //创建第0行，也就是标题行
                HSSFRow row = sheet.createRow(0);
                //设置标题的高度
                row.setHeightInPoints(50);

                // 第三步创建标题的单元格样式style2以及字体样式headerFont1
                HSSFCellStyle style2 = book.createCellStyle();
                style2.setAlignment(HorizontalAlignment.CENTER);
                style2.setVerticalAlignment(VerticalAlignment.CENTER);
                style2.setFillForegroundColor(IndexedColors.AQUA.index);
                style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                // 创建字体样式
                HSSFFont headerFont1 = book.createFont();
                // 字体加粗
                headerFont1.setBold(true);
                // 设置字体类型
                headerFont1.setFontName("黑体");
                // 设置字体大小
                headerFont1.setFontHeightInPoints((short) 15);
                // 为标题样式设置字体样式
                style2.setFont(headerFont1);



                //创建标题的第一列
                HSSFCell cell = row.createCell(0);
                // 合并列标题
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                        map.size()-1));
                cell.setCellValue(config.getVst_qc_table()+"表数据备份");
                // 设置标题样式
                cell.setCellStyle(style2);

                //创建第一行，也就是数据表表头
                HSSFRow hssfRow = sheet.createRow(1);

                // 设置表头高度
                row.setHeightInPoints(37);

                // 第四步，创建表头单元格样式 以及表头的字体样式
                HSSFCellStyle style = book.createCellStyle();
                // 设置自动换行
                style.setWrapText(true);
                style.setAlignment(HorizontalAlignment.CENTER);
                // 创建一个居中格式
                style.setVerticalAlignment(VerticalAlignment.CENTER);

                HSSFColor color1 = new HSSFColor();
                style.setBottomBorderColor(color1.getIndex());
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);

                // 创建字体样式
                HSSFFont headerFont = book.createFont();
                // 字体加粗
                headerFont.setBold(true);
                // 设置字体类型
                headerFont.setFontName("黑体");
                // 设置字体大小
                headerFont.setFontHeightInPoints((short) 10);
                // 为标题样式设置字体样式
                style.setFont(headerFont);

                //用于标示第几列
                int i = 0;
                for (Map.Entry<String,String> entry : map.entrySet()){
                    HSSFCell hssfCell = hssfRow.createCell(i);
                    hssfCell.setCellValue(entry.getValue());
                    hssfCell.setCellStyle(style);
                    i++;
                }



                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
                HSSFCellStyle z = book.createCellStyle();
                // 设置自动换行
                z.setWrapText(true);
                // 创建一个居中格式
                z.setVerticalAlignment(VerticalAlignment.CENTER);

                // 设置边框
                HSSFColor color2 = new HSSFColor();
                z.setBottomBorderColor(color2.getIndex());
                z.setBorderBottom(BorderStyle.THIN);
                z.setBorderLeft(BorderStyle.THIN);
                z.setBorderRight(BorderStyle.THIN);
                z.setBorderTop(BorderStyle.THIN);

                //创建单元格,从第二行开始，并设置值
                int j = 2;
                for (Map<String,Object> rowMap: list){

                    //创建行
                    HSSFRow r = sheet.createRow(j);

                    //遍历rowMap
                    //遍历列名，给每列赋值
                    int m =0;
                    for (String key: map.keySet()){
                        HSSFCell hssfCell = r.createCell(m);
                        hssfCell.setCellValue(rowMap.get(key) !=null?rowMap.get(key).toString():"");
                        hssfCell.setCellStyle(z);
                        m++;
                    }

                    j++;

                }
            }


            //创建文件,获取保存的路径
            fileUrl = CreateFolder.getFile(config.getVst_qc_file_address(),config.getVst_qc_table(),"xls");


            stream = new FileOutputStream(fileUrl);

            book.write(stream);

            logger.info("导出"+config.getVst_qc_table()+"数据成功");

        }catch (Exception e){
            e.printStackTrace();

            maps.clear();
        }finally {
            //关闭流
            CloseableUtil.close(stream);

            maps.clear();
        }
        return fileUrl;
    }


    /**
     * kai
     * 导出Json格式的数据
     * TODO: 2018/4/19 20：09：10
     * @param maps 需要导出的数据
     * @param config  任务配置
     */
    public static String exportJson(List<Map<String,Object>> maps,VstQuartzConfig config){

        JSONObject jsonObject = new JSONObject();

        // 拼接文件完整路径
        String fileUrl = CreateFolder.getFile(config.getVst_qc_file_address(),config.getVst_qc_table(),"json");

        //生产json文件
        Writer writer = null;

        try {
            if (maps.size() > 0 ){
                JSONArray array = new JSONArray();
                for (Map<String,Object> map: maps) {
                    JSONObject object = new JSONObject(map);
                    array.add(object);
                }

                jsonObject.put("code", ApiCode.API_CODE_200);
                jsonObject.put("msg","数据封装成功");
                jsonObject.put("count",maps.size());
                jsonObject.put("data",array);

                writer = new OutputStreamWriter(new FileOutputStream(fileUrl),"UTF-8");
                writer.write(jsonObject.toJSONString());
                //清空缓冲区并写入文件
                writer.flush();
            }

        }catch (IOException e){
            e.printStackTrace();
            maps.clear();
        }finally {
            //关闭流
            CloseableUtil.close(writer);
            maps.clear();
        }

        return fileUrl;
    }






}
