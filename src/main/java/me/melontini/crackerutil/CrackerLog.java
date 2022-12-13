package me.melontini.crackerutil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrackerLog {
    private static final Logger LOGGER = LogManager.getLogger("CrackerUtil");
    private static final StackWalker stackWalker = StackWalker.getInstance();

    public static Logger getLogger() {
        return LOGGER;
    }

    public static void error(String msg, Throwable throwable) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.error(name + msg, throwable);
    }

    public static void error(String msg, Object... args) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.error(name + msg, args);
    }

    public static void error(String msg) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.error(name + msg);
    }

    public static void error(Throwable throwable) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.error(name, throwable);
    }

    public static void warn(String msg, Throwable throwable) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.warn(name + msg, throwable);
    }

    public static void warn(String msg, Object... args) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.warn(name + msg, args);
    }

    public static void warn(String msg) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.warn(name + msg);
    }

    public static void info(String msg) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.info(name + msg);
    }

    public static void info(String msg, Object... args) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.info(name + msg, args);
    }

    public static void info(boolean bool) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.info(name + bool);
    }

    public static void info(double d) {
        String name = "[" + getCallerName() + "] ";
        LOGGER.info(name + d);
    }

    // if someone figures out how to call jdk.internal.reflect.Reflection#getCallerClass I will literally like you :)
    public static String getCallerName() {
        return stackWalker.walk(s -> {
            var first = s.skip(2).findFirst().orElse(null);
            return first != null ? first.getClassName() + "#" + first.getMethodName() : "NoClassNameFound";
        });
    }
}
