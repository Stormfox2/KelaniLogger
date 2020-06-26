/*
 *
 *  Copyright (c) 2020.
 *  This system ist developed by Jes Müller and Quirin Brändli!
 *  All rights reserved!
 *
 *  Please read the licence for more information.
 *
 */

package de.kelanisystem.kelanilogger;

import de.kelanisystem.kelanilogger.config.KelaniLoggerConfig;
import de.kelanisystem.kelanilogger.exceptions.NoFileSpecifiedException;
import de.kelanisystem.kelanilogger.formats.DefaultFormat;
import de.kelanisystem.kelanilogger.handler.KelaniConsoleHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Formatter;
import java.util.logging.*;


public class KelaniLogger {

    private Logger logger;
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private boolean withCommandManager;
    private Formatter formatter;
    private ConsoleHandler consoleHandler;

    private List<ConsoleHandler> consoleHandlers;

    public KelaniLogger(KelaniLoggerConfig kelaniLoggerConfig) {
        consoleHandlers = new ArrayList<>();

        createLogger(kelaniLoggerConfig);
        createFileLogger(kelaniLoggerConfig);
    }

    private void createLogger(KelaniLoggerConfig kelaniLoggerConfig) {
        logger = Logger.getLogger(kelaniLoggerConfig.getLoggerName());
        logger.setUseParentHandlers(false);

        if(!kelaniLoggerConfig.isDefaultConsoleHandlerDeactivated()) {
            consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter((kelaniLoggerConfig.getFormatter() == null) ? new DefaultFormat() : kelaniLoggerConfig.getFormatter());
            consoleHandler.setLevel(kelaniLoggerConfig.getLogLevelConsole());
            logger.addHandler(consoleHandler);
        }

        if(!kelaniLoggerConfig.getKelaniConsoleHandlers().isEmpty()){
            for (KelaniConsoleHandler kelaniConsoleHandler : kelaniLoggerConfig.getKelaniConsoleHandlers()) {
                if (!kelaniConsoleHandler.isFormatSet()) kelaniConsoleHandler.setFormatter(new DefaultFormat());
                if (!kelaniConsoleHandler.isLevelSet()) kelaniConsoleHandler.setLevel(kelaniLoggerConfig.getLogLevelConsole());
                logger.addHandler(kelaniConsoleHandler);
            }
        }
    }

    private void createFileLogger(KelaniLoggerConfig kelaniLoggerConfig) {
        try {

            if(kelaniLoggerConfig.getDirectory() == null || kelaniLoggerConfig.getLogFileName() == null) throw new NoFileSpecifiedException();

            //Date for log file
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy_[HH.mm.ss]");
            Date date = new Date();

            String logFileName = parseLogName(date, kelaniLoggerConfig);

            File logFile = new File(logFileName);
            if (!logFile.getParentFile().exists() && !logFile.getParentFile().mkdirs())
                throw new IOException("Couldn't create parent file: " + logFile.getCanonicalPath());
            if (!logFile.exists() && !logFile.createNewFile())
                throw new IOException("Couldn't create file: " + logFile.getCanonicalPath());
            FileHandler fileHandler;
            fileHandler = new FileHandler(logFileName, true);
            fileHandler.setFormatter(kelaniLoggerConfig.getFormatter());
            fileHandler.setLevel(kelaniLoggerConfig.getLogLevelFile());
            logger.addHandler(fileHandler);
            clearOldLogs(logFile.getParentFile());
        } catch (IOException | NoFileSpecifiedException e) {
            error(e.getMessage());
        }
    }

    private void finish(KelaniLoggerConfig kelaniLoggerConfig) {
        if(kelaniLoggerConfig.getLogo() != null) off(kelaniLoggerConfig.getLogo());
    }

    private String parseLogName(Date date, KelaniLoggerConfig config) throws IOException {
        String filename = config.getLogFileName();
        filename = filename.replace("%LOGGER_NAME%", config.getLogFileName()).replace("%LOG_CREATE_TIME", date.toString());

        String name;
        if(config.getDirectory().getCanonicalPath().endsWith("log") || config.getDirectory().getCanonicalPath().endsWith("logs"))
            name = config.getDirectory().getCanonicalFile() + FILE_SEPARATOR + filename;
        else
            name = config.getDirectory().getCanonicalFile() + FILE_SEPARATOR + "logs" + FILE_SEPARATOR + filename;

        return name;
    }

    private void clearOldLogs(File dir) throws IOException {
        File directory = new File(dir, FILE_SEPARATOR);

        if (directory.exists()) {
            List<File> files = new CopyOnWriteArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));

            files.sort(Comparator.comparingLong(File::lastModified));
            for (File file : files) {
                if (file.lastModified() <= (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7))) {
                    if (!file.delete() && files.remove(file))
                        throw new IOException("Couldn't delete directory: " + file.getCanonicalPath());
                } else if (file.getName().contains(".lck")) {
                    files.remove(file);
                }
            }

            if (files.size() >= 5) {
                for (int i = 0; i < files.size(); i++) {
                    if (files.size() > 5) {
                        if (!files.get(i).delete())
                            throw new IOException("Couldn't delete directory: " + files.get(i).getCanonicalPath());
                        files.remove(i);
                    }

                }
            }
        }
    }

    public void info(Object object) {
        logger.log(Level.INFO, object::toString);
    }

    public void warning(Object object) {
        logger.log(Level.WARNING, object::toString);
    }

    public void error(Object object) {
        logger.log(Level.SEVERE, object::toString);
    }

    public void config(Object object) {
        logger.log(Level.INFO, object::toString);
    }

    public void log(Level level, Object object) {
        logger.log(Level.INFO, object::toString);
    }

    public void fine(Object object) {
        logger.log(Level.INFO, object::toString);
    }

    public void off(Object object) {
        logger.log(Level.OFF, object::toString);
    }

    public void logMany(Level level, List<Object> objects) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < objects.size(); i++) {
            stringBuilder.append(objects.get(i));
            if (i != (objects.size() - 1)) stringBuilder.append("\n");
        }

        log(level, stringBuilder.toString());
    }

    public void addHandlers(ConsoleHandler... handlers){
        for(ConsoleHandler handler : handlers)
            logger.addHandler(handler);
    }

    public void removeHandlers(ConsoleHandler... handlers) {
        for(ConsoleHandler handler : handlers)
            logger.addHandler(handler);
    }
}
