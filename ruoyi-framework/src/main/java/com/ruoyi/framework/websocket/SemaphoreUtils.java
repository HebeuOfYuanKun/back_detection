package com.ruoyi.framework.websocket;

import java.util.concurrent.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.framework.websocket
 * @Project：ruoyi-vue-service
 * @name：SemaphoreUtils
 * @Date：2024/4/22 20:27
 * @Filename：SemaphoreUtils
 */
public class SemaphoreUtils
{
    /**
     * SemaphoreUtils 日志控制器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreUtils.class);

    /**
     * 获取信号量
     *
     * @param semaphore
     * @return
     */
    public static boolean tryAcquire(Semaphore semaphore)
    {
        boolean flag = false;

        try
        {
            flag = semaphore.tryAcquire();
        }
        catch (Exception e)
        {
            LOGGER.error("获取信号量异常", e);
        }

        return flag;
    }

    /**
     * 释放信号量
     *
     * @param semaphore
     */
    public static void release(Semaphore semaphore)
    {

        try
        {
            semaphore.release();
        }
        catch (Exception e)
        {
            LOGGER.error("释放信号量异常", e);
        }
    }
}
