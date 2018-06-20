package com.sda.library.log;

import com.sda.library.model.LogStatus;
import org.apache.log4j.Logger;

public class AppLogger {

    public static void log(LogStatus status, Class<?> clazz, String msg){
        final Logger logger = Logger.getLogger(clazz);
        if (logger.isDebugEnabled()) {
            msg = "DEBUG - " + msg;
        }
        switch (status) {
            case INFO:
                logger.info(msg);
                break;
            case WARN:
                logger.warn(msg);
                break;
            case ERROR:
                logger.error(msg);
                break;
            default:
                logger.info(msg);
                break;
        }
    }
}