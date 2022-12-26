package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder {
    public static final StaticLoggerBinder BINDER = new StaticLoggerBinder();
    private final ILoggerFactory loggerFactory;

    private StaticLoggerBinder() {
        loggerFactory = new SpigotLoggerFactory();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return BINDER.getClass().getName();
    }

    public static StaticLoggerBinder getSingleton() {
        return BINDER;
    }
}
