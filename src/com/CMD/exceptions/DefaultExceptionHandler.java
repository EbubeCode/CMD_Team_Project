package com.CMD.exceptions;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//This class implements the UncaughtExceptionHandler interface and prints the errors in the log
public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(DefaultExceptionHandler.class.getName());

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LOGGER.log(Level.ERROR, "Exception occurred {}", ex);
    }
}
