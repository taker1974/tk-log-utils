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

/**
 * Simplified formatted logging.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public final class LogFx extends LogBase {

    private LogFx() {}

    /**
     * Log the current method with 'trace' severity.
     * 
     * @param logger The logger.
     * @param format The format of the message.
     * @param args The arguments for the message.
     */
    public static void trace(Logger logger, String format, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace(format, args);
        }
    }

    /**
     * Log the current method with 'debug' severity.
     * 
     * @param logger The logger.
     * @param format The format of the message.
     * @param args The arguments for the message.
     */
    public static void debug(Logger logger, String format, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, args);
        }
    }

    /**
     * Log the current method with 'info' severity.
     * 
     * @param logger The logger.
     * @param format The format of the message.
     * @param args The arguments for the message.
     */
    public static void info(Logger logger, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(format, args);
        }
    }

    /**
     * Log the current method with 'warn' severity.
     * 
     * @param logger The logger.
     * @param format The format of the message.
     * @param args The arguments for the message.
     */
    public static void warn(Logger logger, String format, Object... args) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, args);
        }
    }

    /**
     * Log the current method with 'error' severity.
     * 
     * @param logger The logger.
     * @param format The format of the message.
     * @param args The arguments for the message.
     */
    public static void error(Logger logger, String format, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(format, args);
        }
    }
}
