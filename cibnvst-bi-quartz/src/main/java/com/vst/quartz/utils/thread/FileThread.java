package com.vst.quartz.utils.thread;

import com.vst.common.pojo.VstQuartzConfig;
import com.vst.common.tools.string.ToolsString;
import com.vst.quartz.constant.Constants;
import com.vst.quartz.enumerate.QuartzEnum;
import com.vst.quartz.utils.file.FileTools;
import com.vst.quartz.utils.file.util.GzipTools;
import com.vst.quartz.utils.file.util.JarTools;
import com.vst.quartz.utils.file.util.TarTools;
import com.vst.quartz.utils.file.util.ZipTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 异步处理文件备份
 * TODO: 2018/4/19 19:30:45
 */
public class FileThread implements Runnable {

    private Log logger = LogFactory.getLog(FileThread.class);

    /**
     * 备份的数据 sql语句 用户导出sql文件
     */
    private List<String> list;

    /**
     * 备份的数据，用于生产json数据和Excel数据
     */
    private List<Map<String,Object>> maps;

    /**
     * 列名，导出excel是需要用到每列的列名
     */
    private Map<String,String> map;


    /**
     * 表名
     * 用表名组成文件名
     */
    private VstQuartzConfig config;

    public FileThread(List<String> list,List<Map<String,Object>> maps,Map<String,String> map, VstQuartzConfig config) {
        this.list = list;
        this.maps = maps;
        this.map = map;
        this.config = config;
    }

    @Override
    public void run() {

        try {
            //获取文件成功之后的路径
            String url = "";
            if (ToolsString.isEmpty(config.getVst_qc_file_address())){
                config.setVst_qc_file_address(Constants.URL);
            }
            //获取文件备份格式,1:json格式，2:excel格式文件，3:sql文件格式
            int format = config.getVst_qc_file_format();
            if (format == QuartzEnum.VST_FILE_TYPE.getOneStatus()){
                url =  FileTools.exportJson(maps,config);
            }else if (format == QuartzEnum.VST_FILE_TYPE.getTowStatus()){
                url =  FileTools.exportExcel(maps,config,map);
            }else if (format == QuartzEnum.VST_FILE_TYPE.getTreeStatus()){
                url =  FileTools.exportSql(list,config);
            }

            File file = new File(url);

            if (!file.exists()){
                logger.info("文件路径不存在");
                return;
            }

            //判断是否进行文件压缩1:压缩,2:不压缩
            if (config.getVst_qc_is_compression().equals(QuartzEnum.VST_IS_COMPRESSION.getOneStatus())){
                //获取文件名:
                String name = file.getName();
                //获取文件压缩路径
                String destPath = config.getVst_qc_compression_address() ;

                //任务名
                String jobName = config.getVst_qc_name();

                //如果压缩路径为空，则直接压缩在该文件目录下面
                if (ToolsString.isEmpty(destPath)){
                    destPath = url.substring(0,url.lastIndexOf("/")+1);
                }

                //判断压缩成什么格式 1:zip格式,2:jar格式,3:gzip格式(不支持目录压缩）,4:tar格式
                switch (config.getVst_qc_compression_format()){
                    case 1:
                        ZipTools.toZip(url,destPath,name.substring(name.lastIndexOf(".") + 1));
                        break;
                    case 2:
                        JarTools.toJar(url,destPath,name.substring(name.lastIndexOf(".")+1));
                        break;
                    case 3:
                        GzipTools.toGzip(url,destPath,jobName,name.substring(name.lastIndexOf(".")+1));
                        break;
                    case 4:
                        TarTools.toTar(url,destPath,name.substring(name.lastIndexOf(".")+1));
                        break;
                    default:
                        break;
                }
            }
        }catch (Exception e){
            maps.clear();
            list.clear();
            e.printStackTrace();
        }finally {
            list.clear();
            maps.clear();
        }



    }
}
