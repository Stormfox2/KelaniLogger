/*
 *
 *  Copyright (c) 2020.
 *  This system ist developed by Jes Müller and Quirin Brändli!
 *  All rights reserved!
 *
 *  Please read the licence for more information.
 *
 */

package de.kelanisystem.kelanilogger.config;

import de.kelanisystem.kelanilogger.handler.KelaniConsoleHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Level;

public class KelaniLoggerConfig {
    //Name
    private String loggerName;
    private String logo;

    //Files
    private boolean fileLogActive;
    private File directory;
    private String logName;

    //Format
    private Formatter formatter;

    //Two log levels
    private Level logLevelConsole;
    private Level logLevelFile;

    //Handlers
    private boolean deactivateDefaultConsoleHandler = false;
    private List<KelaniConsoleHandler> kelaniConsoleHandlers;

    public KelaniLoggerConfig(String loggerName) {
        kelaniConsoleHandlers = new ArrayList<>();

        this.loggerName = loggerName;
        logLevelConsole = Level.INFO;
        logLevelFile = Level.INFO;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public String getLogo() {
        return logo;
    }

    /**
     * Logo printed out after the start of the logger.
     *
     * @param logo String of the logo.
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isFileLogActive() {
        return fileLogActive;
    }

    /**
     * Activate logging into file.
     * To be able to use this function, you need to add the following attributes:
     *
     * - logDirectory
     * - logFileName
     *
     * @param fileLogActive
     */
    public void setFileLogActive(boolean fileLogActive) {
        this.fileLogActive = fileLogActive;
    }

    public File getDirectory() {
        return directory;
    }

    /**
     * Sets the main directory of the log files.
     * @param directory directory of log files
     */
    public void setDirectory(File directory) {
        this.directory = directory;
    }

    /**
     * Returns name of the config file.
     * If name is not set, returns internal name.
     *
     * @return name of logger file
     */
    public String getLogFileName() {
        return (loggerName == null) ? logName : loggerName;
    }

    /**
     * Sets the name of the log file.
     * You can these placeholders:
     *
     * %LOGGER_NAME% => Internal name of the logger.
     * %LOG_CREATE_TIME% => Time of the creation of logger.
     *
     * @param logName name of the log
     */
    public void setLogFileName(String logName) {
        this.logName = logName;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    /**
     * Set the class for formatting the log messages.
     *
     * @param formatter formatter for logs
     */
    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public Level getLogLevelConsole() {
        return logLevelConsole;
    }

    /**
     * Set the log level of the console and the log file.
     *
     * @param logLevel level of the console and the log file
     */
    public void setLogLevels(Level logLevel) {
        logLevelConsole = logLevel;
        logLevelFile = logLevel;
    }

    /**
     * Set the log level of the console.
     *
     * @param logLevelConsole level of the console
     */
    public void setLogLevelConsole(Level logLevelConsole) {
        this.logLevelConsole = logLevelConsole;
    }

    public Level getLogLevelFile() {
        return logLevelFile;
    }

    /**
     * Set the log level of the log files.
     *
     * @param logLevelFile level of the the log files
     */
    public void setLogLevelFile(Level logLevelFile) {
        this.logLevelFile = logLevelFile;
    }

    public boolean isDefaultConsoleHandlerDeactivated() {
        return deactivateDefaultConsoleHandler;
    }

    public void deactivateDefaultConsoleHandler() {
        this.deactivateDefaultConsoleHandler = true;
    }

    public List<KelaniConsoleHandler> getKelaniConsoleHandlers() {
        return kelaniConsoleHandlers;
    }

    /**
     * Adds new console handlers for the logger.
     *
     * @param consoleHandlers added console handlers
     */
    public void setKelaniConsoleHandlers(KelaniConsoleHandler... consoleHandlers) {
        kelaniConsoleHandlers.addAll(Arrays.asList(consoleHandlers));
    }
}
