/*
 * Copyright 2025 Konstantin Terskikh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package ru.spb.tksoft.utils.log;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Extended/wrapped logging with thread safety optimizations.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public final class LogEx extends LogBase {

    /** Cache for method names for better performance. */
    private static final ConcurrentMap<String, String> METHOD_NAME_CACHE =
            new ConcurrentHashMap<>();

    /** Thread-local StringBuilder для избежания создания новых объектов. */
    private static final ThreadLocal<StringBuilder> THREAD_LOCAL_BUILDER =
            ThreadLocal.withInitial(() -> new StringBuilder(256));

    private LogEx() {}

    /**
     * Get the current method name with caching for better performance. Thread-safe implementation
     * using ConcurrentHashMap.
     * 
     * @return The name of the method that called this method.
     */
    public static String me() {
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        final int currentFrameIndex = 2; // 0 - getStackTrace(), 1 - getCurrentMethodName()

        if (stackTraceElements.length <= currentFrameIndex) {
            throw new IllegalStateException("Call stack too short");
        }

        final StackTraceElement element = stackTraceElements[currentFrameIndex];
        final String className = element.getClassName();
        final String methodName = element.getMethodName();
        final int lineNumber = element.getLineNumber();

        // Create cache key from class, method and line number
        final String cacheKey = className + "#" + methodName + ":" + lineNumber;

        // Try to get from cache
        String cachedMethodName = METHOD_NAME_CACHE.get(cacheKey);
        if (cachedMethodName != null) {
            return cachedMethodName;
        }

        // If not in cache, add it (may be added multiple times, but this is safe)
        METHOD_NAME_CACHE.putIfAbsent(cacheKey, methodName);
        return methodName;
    }

    /**
     * Log the current method with thread-safe message building. Uses thread-local StringBuilder to
     * avoid object creation overhead.
     * 
     * @param logger - the logger.
     * @param level - the logging level.
     * @param parts - the message parts.
     */
    public static void log(Logger logger, Level level, Object[] parts) {

        if (parts == null || parts.length == 0) {
            processLog(logger, level, "");
            return;
        }

        // Use thread-local StringBuilder to avoid object creation
        StringBuilder builder = THREAD_LOCAL_BUILDER.get();
        builder.setLength(0); // Clear the previous content

        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                builder.append(": ");
            }
            builder.append(String.valueOf(parts[i]));
        }

        String message = builder.toString();
        processLog(logger, level, message);
    }

    /**
     * Thread-safe logging by level.
     * 
     * @param logger The logger.
     * @param level The logging level.
     * @param message The message to log.
     */
    private static void processLog(Logger logger, Level level, String message) {
        switch (level) {
            case TRACE -> logger.trace(message);
            case DEBUG -> logger.debug(message);
            case INFO -> logger.info(message);
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
        }
    }

    /**
     * Log the current method with 'trace' severity
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void trace(Logger logger, Object... parts) {

        log(logger, Level.TRACE, parts);
    }

    /**
     * Log the current method with 'debug' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void debug(Logger logger, Object... parts) {
        log(logger, Level.DEBUG, parts);
    }

    /**
     * Log the current method with 'info' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void info(Logger logger, Object... parts) {
        log(logger, Level.INFO, parts);
    }

    /**
     * Log the current method with 'warn' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void warn(Logger logger, Object... parts) {
        log(logger, Level.WARN, parts);
    }

    /**
     * Log the current method with 'error' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void error(Logger logger, Object... parts) {
        log(logger, Level.ERROR, parts);
    }

    /**
     * Clear the method name cache.
     * 
     * Useful for freeing memory or when changing code during runtime. Thread-safe operation.
     */
    public static void clearMethodCache() {
        METHOD_NAME_CACHE.clear();
    }

    /**
     * Get the size of the method name cache.
     * 
     * @return the size of the method name cache
     */
    public static int getCacheSize() {
        return METHOD_NAME_CACHE.size();
    }

    /**
     * Check if the method name cache is enabled.
     * 
     * @return true if the method name cache is enabled
     */
    public static boolean isCacheEnabled() {
        return !METHOD_NAME_CACHE.isEmpty();
    }

    /**
     * Clear the thread-local variables for the current thread.
     * 
     * Recommended to call when the thread is finished.
     */
    public static void cleanupThreadLocal() {
        THREAD_LOCAL_BUILDER.remove();
    }
}
