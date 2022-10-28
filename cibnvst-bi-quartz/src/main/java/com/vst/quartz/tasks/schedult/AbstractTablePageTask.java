package com.vst.quartz.tasks.schedult;

import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.constant.Constants;
import com.vst.quartz.utils.page.PageBean;

/**
 * @author kai
 * 处理需要分页时的数据
 * TODO: 2018/3/15 10:35:50
 */
public abstract class AbstractTablePageTask extends AbstractTableMulti {

    @Override
    protected void noTableMulti(VstQuartzConfig config, String dbSite) {
        tablePage(config,dbSite);
    }

    /**
     * 当数据大于2000的时候，需要进行分页处理
     */
    private void tablePage(VstQuartzConfig config,String dbSite){
        PageBean pageBean = new PageBean(Constants.COUNT);
        do {
            exePageTask(config,dbSite,pageBean);
        }while (pageBean.getPageNum() >= pageBean.getCurrentPage());
    }

    /**
     * kai
     * 抽象方法
     * @param config 任务配置
     * @param dbSite 数据源
     * @param pageBean 分页类
     */
    protected abstract void exePageTask(VstQuartzConfig config,String dbSite,PageBean pageBean);
}
