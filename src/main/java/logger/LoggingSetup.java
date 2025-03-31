package logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingSetup {
    public static void configureGlobalLogging() {
        LogManager.getLogManager().reset();
        Logger rootLogger = Logger.getLogger("");

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.WARNING);
        rootLogger.addHandler(handler);
        rootLogger.setLevel(Level.WARNING);
    }
}
