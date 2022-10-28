package com.vst.quartz.utils.thread.pool;

import com.vst.quartz.constant.ApiMagic;

import java.util.concurrent.*;

/**
 * @author kai
 * 线程池创建父类
 * TODO: 2018/4/24
 */
public class AbstractPool {

    private ExecutorService executorService;

    {
        ThreadFactory factory = Executors.defaultThreadFactory();

        executorService = new ThreadPoolExecutor(10,15,0L, TimeUnit.MILLISECONDS
        ,new LinkedBlockingQueue<Runnable>(ApiMagic.API_MAGIC_1024),factory,new ThreadPoolExecutor.AbortPolicy());
    }

    public AbstractPool(Runnable thread) {
        this.executorService.execute(thread);
        this.executorService.shutdown();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
