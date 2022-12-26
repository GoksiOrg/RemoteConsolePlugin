package org.slf4j.impl;

import org.slf4j.Logger;
import org.slf4j.Marker;
import tech.goksi.remoteconsole.RemoteConsole;

import java.util.logging.Level;

public class SpigotAdapter implements Logger {
    private final String name;
    private final java.util.logging.Logger logger;

    public SpigotAdapter(String name) {
        this.name = name;
        this.logger = RemoteConsole.getInstance().getLogger();
    }

    private String format(String msg, Object... objects) {
        for (Object object : objects) {
            msg = msg.replaceFirst("\\{}", object.toString());
        }
        return msg;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {
        logger.fine(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        logger.fine(format(format, arg));
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        logger.fine(format(format, arg1, arg2));
    }

    @Override
    public void trace(String format, Object... arguments) {
        logger.fine(format(format, arguments));
    }

    @Override
    public void trace(String msg, Throwable t) {
        logger.log(Level.FINER, msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {

    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {
        logger.fine(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        logger.fine(format(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        logger.fine(format(format, arg1, arg2));
    }

    @Override
    public void debug(String format, Object... arguments) {
        logger.fine(format(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        logger.log(Level.FINE, msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {
        if (msg.contains("Listening on")) logger.info("HTTP server " + msg.toLowerCase());
        else logger.info(msg);
    }

    @Override
    public void info(String msg, Object arg) {
        logger.info(format(msg, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        logger.info(format(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        logger.info(format(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        logger.log(Level.INFO, msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override
    public void info(Marker marker, String msg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {
        logger.warning(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        logger.warning(format(format, arg));
    }

    @Override
    public void warn(String format, Object... arguments) {
        logger.warning(format(format, arguments));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        logger.warning(format(format, arg1, arg2));
    }

    @Override
    public void warn(String msg, Throwable t) {
        logger.log(Level.WARNING, msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override
    public void warn(Marker marker, String msg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {
        logger.severe(msg);
    }

    @Override
    public void error(String format, Object arg) {
        logger.severe(format(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        logger.severe(format(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        logger.severe(format(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        logger.log(Level.SEVERE, msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return false;
    }

    @Override
    public void error(Marker marker, String msg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {

    }
}
