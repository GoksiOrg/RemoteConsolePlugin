package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SpigotLoggerFactory implements ILoggerFactory {
    private final Map<String, Logger> loggerMap;

    public SpigotLoggerFactory() {
        loggerMap = new HashMap<>();
    }

    @Override
    public Logger getLogger(String name) {
        return loggerMap.computeIfAbsent(name, SpigotAdapter::new);
    }
}
