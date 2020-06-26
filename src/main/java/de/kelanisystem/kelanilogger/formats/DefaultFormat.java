/*
 *
 *  Copyright (c) 2020.
 *  This system ist developed by Jes Müller and Quirin Brändli!
 *  All rights reserved!
 *
 *  Please read the licence for more information.
 *
 */

package de.kelanisystem.kelanilogger.formats;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static java.util.logging.Level.*;

public class DefaultFormat extends Formatter {
    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("windows");

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_CYAN = "\u001B[36m";

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        Level level = record.getLevel();

        if (!IS_WINDOWS)
            builder.append(getLevelColor(record.getLevel()));


        if (level != OFF) {
            builder.append("[");
            builder.append(calcDate(record.getMillis()));
            builder.append("]");

            builder.append(" [");
            builder.append(record.getLevel().getName());
            builder.append("]");

            if (!IS_WINDOWS)
                builder.append(ANSI_WHITE);
            builder.append(" - ");
        }

        builder.append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null) {
            builder.append("\t");
            for (int i = 0; i < params.length; i++) {
                builder.append(params[i]);
                if (i < params.length - 1)
                    builder.append(", ");
            }
        }

        builder.append(getAnsiReset());

        builder.append("\n");

        return builder.toString();
    }

    private String getAnsiReset() {
        return !IS_WINDOWS ? ANSI_RESET : "";
    }

    private String getLevelColor(Level level) {
        if (INFO.equals(level)) {
            return ANSI_CYAN;
        } else if (SEVERE.equals(level)) {
            return ANSI_RED;
        } else if (WARNING.equals(level)) {
            return ANSI_YELLOW;
        } else if (FINE.equals(level)) {
            return ANSI_GREEN;
        }
        return ANSI_WHITE;
    }

    private String calcDate(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(milliseconds);
        return dateFormat.format(date);
    }
}
