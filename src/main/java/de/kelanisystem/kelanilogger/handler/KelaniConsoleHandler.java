/*
 *
 *  Copyright (c) 2020.
 *  This system ist developed by Jes Müller and Quirin Brändli!
 *  All rights reserved!
 *
 *  Please read the licence for more information.
 *
 */

package de.kelanisystem.kelanilogger.handler;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public abstract class KelaniConsoleHandler extends ConsoleHandler {
    private boolean formatSet;
    private boolean levelSet;

    public KelaniConsoleHandler(Level logLevel) { this(logLevel, null ); }

    public KelaniConsoleHandler(Formatter formatter) { this(null, formatter); }

    public KelaniConsoleHandler(Level logLevel, Formatter formatter) {
        if(!(formatter == null)) this.setFormatter(formatter);
        if(!(logLevel == null)) this.setLevel(logLevel);
    }

    private void setFormatFirst(Formatter formatFirst) {
        if(!(formatFirst == null)) {
            formatSet = true;
            setFormatter(formatFirst);
        }
    }

    private void setLevelFirst(Level levelFirst) {
        if(!(levelFirst == null)) {
            formatSet = true;
            setLevel(levelFirst);
        }
    }

    public boolean isFormatSet() {
        return formatSet;
    }

    public boolean isLevelSet() {
        return levelSet;
    }

    @Override
    public abstract void publish(LogRecord record);
}
