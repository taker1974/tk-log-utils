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

/**
 * Base class for logging.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
abstract class LogBase {

    protected LogBase() {}

    /** Common phrase 'starting'. */
    public static final String STARTING = "starting";

    /** Common phrase 'finishing'. */
    public static final String STOPPING = "finishing";

    /** Common phrase 'finished'. */
    public static final String STOPPED = "finished";

    /** Common phrase 'starting -> finishing'. */
    public static final String SHORT_RUN = "starting -> finishing";

    /** Common phrase 'exception thrown'. */
    public static final String EXCEPTION_THROWN = "exception thrown";
}
