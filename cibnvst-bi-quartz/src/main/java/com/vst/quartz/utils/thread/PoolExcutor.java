package com.vst.quartz.utils.thread;

import com.vst.quartz.utils.thread.pool.AbstractPool;


/**
 * @author kai
 * 线程池操作类
 */
public class  PoolExcutor extends AbstractPool {


    public PoolExcutor(Runnable thread) {

        super(thread);
        System.out.println(super.getExecutorService().isShutdown());
        if (super.getExecutorService().isShutdown()){
            this.getExecutorService().shutdown();
        }

    }
}
