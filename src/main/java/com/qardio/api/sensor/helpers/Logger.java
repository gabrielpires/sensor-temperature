/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qardio.api.sensor.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The type Logger.
 */
final public class Logger {

    /**
     * The constant ANSI_RESET.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * The constant ANSI_RED.
     */
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * The constant ANSI_GREEN.
     */
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * The constant ANSI_YELLOW.
     */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * The constant ANSI_WHITE.
     */
    public static final String ANSI_WHITE = "\u001B[37m";

    private static String loggerName = "main";


    /**
     * Sets logger name.
     *
     * @param loggerName the logger name
     */
    public static void setLoggerName(String loggerName) {
        Logger.loggerName = loggerName;
    }

    /**
     * Info.
     *
     * @param msg the msg
     * @param obj the obj
     */
    public static void info(String msg, Object obj) {
        Logger.log("INFO", msg + " " + obj.toString());
    }

    /**
     * Error.
     *
     * @param msg the msg
     * @param obj the obj
     */
    public static void error(String msg, Object obj) {
        Logger.log("ERROR", msg + " " + obj.toString());
    }

    /**
     * Debug.
     *
     * @param msg the msg
     * @param obj the obj
     */
    public static void debug(String msg, Object obj) {
        Logger.log("DEBUG", msg + " " + obj.toString());
    }

    /**
     * Info.
     *
     * @param msg the msg
     */
    public static void info(String msg) {
        Logger.log("INFO", msg);
    }

    /**
     * Error.
     *
     * @param msg the msg
     */
    public static void error(String msg) {
        Logger.log("ERROR", msg);
    }

    /**
     * Debug.
     *
     * @param msg the msg
     */
    public static void debug(String msg) {
        Logger.log("DEBUG", msg);
    }

    /**
     * Exception.
     *
     * @param exception the exception
     */
    public static void exception(Throwable exception) {
        exception(null, exception);
    }

    /**
     * Exception.
     *
     * @param message   the message
     * @param exception the exception
     */
    public static void exception(String message, Throwable exception) {

        String exceptionMessage = "";
        if (message != null) {
            exceptionMessage = message + " \n ";
        }

        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));

        exceptionMessage += String.format(
                "%s %s %s",
                exception.getMessage(),
                ANSI_RESET,
                stringWriter.toString()
        );

        error(exceptionMessage);
    }

    /**
     * Log.
     *
     * @param type the type
     * @param msg  the msg
     */
    public static void log(String type, String msg) {
        System.out.printf("%s[%s][%s.%s]: %s%s%n",
                Logger.getColor(type),
                Logger.getTimestamp(),
                Logger.loggerName,
                type,
                msg,
                ANSI_RESET
        );
    }

    private static String getTimestamp() {
        return new Timestamp((new Date()).getTime()).toString();
    }

    private static String getColor(String type) {
        return switch (type) {
            case "DEBUG" -> ANSI_YELLOW;
            case "INFO" -> ANSI_GREEN;
            case "ERROR" -> ANSI_RED;
            default -> ANSI_WHITE;
        };
    }
}
