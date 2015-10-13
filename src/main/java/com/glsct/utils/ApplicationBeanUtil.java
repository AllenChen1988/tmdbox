package com.glsct.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class ApplicationBeanUtil implements ApplicationContextAware {
    private final static Logger log = Logger.getLogger(ApplicationBeanUtil.class);
    private static ApplicationContext applicationcontext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationcontext = applicationContext;
    }

    public static Object getBean(String beanname){
        try {
            return applicationcontext.getBean(beanname);
        } catch (NullPointerException | NoSuchBeanDefinitionException e) {
            if(log.isDebugEnabled()){
                log.debug("====================获取bean失败："+ beanname + "====================");
            }
            return null;
        }
    }

    public static <T> T getBean(String beanname,Class<T> clazz){
        try {
            return applicationcontext.getBean(beanname,clazz);
        } catch (NullPointerException |NoSuchBeanDefinitionException  e) {
            if(log.isDebugEnabled()){
                log.debug("====================获取bean失败，beanName：" + beanname + " Class:"+ clazz.getName()+"====================");
            }
            return null;
        }
    }



}
