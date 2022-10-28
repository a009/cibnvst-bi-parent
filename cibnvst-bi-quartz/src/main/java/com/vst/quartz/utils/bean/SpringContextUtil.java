package com.vst.quartz.utils.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author kai 
 * 获取所有 bean
 * TODO: 2018/4/23
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
    	SpringContextUtil.applicationContext = context;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

	public static <T> T  getBean(Class<T> c){
        return applicationContext.getBean(c);
    }
}
