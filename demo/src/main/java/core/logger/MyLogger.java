package core.logger;

import java.util.logging.*;

import core.logger.handler.MyFileHandler;
import core.logger.handler.WebSocketClient;
import core.utils.Constants;
import core.utils.Records;

public class MyLogger extends Logger {

    protected MyLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    private static final Logger logger = Logger.getLogger(MyLogger.class.getName());
    private static final Level level = Level.ALL;

    static {
        configureLogger();
    }

    private static void configureLogger() {
        logger.setLevel(level);
        if (Constants.ONLINE)
            WebSocketClient.configureWebSocketClient(logger);
        else
            MyFileHandler.configureHandler(logger);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void logMethodEntry(String methodName, Object thisObject, Object[] params, Class<?>[] paramsType) {
        logger.logp(level, thisObject.getClass().getName(), methodName, Constants.ENTRY,
                new Records.Enter(params, paramsType, thisObject));
    }

    public static <T> void logMethodExit(String methodName, Object thisObject, Class<T> returnType, T result,
            Object[] params, Class<?>[] paramsType) {
        logger.logp(level, thisObject.getClass().getName(), methodName, Constants.RETURN,
                new Records.Exit<>(returnType, result, params, paramsType, thisObject));
    }

    public static void logStaticMethodEntry(String className, String methodName, Object[] params,
            Class<?>[] paramsType) {
        logger.logp(level, className, methodName, Constants.ENTRY,
                new Records.Enter(params, paramsType, null));
    }

    public static <T> void logStaticMethodExit(String className, String methodName, Class<T> returnType, T result,
            Object[] params, Class<?>[] paramsType) {
        logger.logp(level, className, methodName, Constants.RETURN,
                new Records.Exit<>(returnType, result, params, paramsType, null));
    }
}
