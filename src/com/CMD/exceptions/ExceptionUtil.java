package com.CMD.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.PrintStream;

//This class initializes the log PrintStream with the default System print stream and prints the checked/unchecked exceptions to the log
public class ExceptionUtil {

    public static void init() {
        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());
        System.setOut(createLoggingProxy(System.out));
        System.setErr(createLoggingProxy(System.err));
    }

    public static final Logger LOGGER = LogManager.getLogger(ExceptionUtil.class.getName());

    public static PrintStream createLoggingProxy(final PrintStream realPrintStream){
        return new PrintStream(realPrintStream){
            @Override
            public void print(final String string) {
                LOGGER.info(string);
            }

            @Override
            public void println(final String string) {
                LOGGER.info(string);
            }
        };
    }
}
