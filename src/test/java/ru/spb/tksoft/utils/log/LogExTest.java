/*
 * Copyright 2025 Konstantin Terskikh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.spb.tksoft.utils.log;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for LogEx class.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@SuppressWarnings("java:S1192")
@ExtendWith(MockitoExtension.class)
@DisplayName("LogEx Tests")
class LogExTest {

    @Mock
    private Logger mockLogger;

    @Test
    @DisplayName("Should return correct method name")
    void shouldReturnCorrectMethodName() {
        // When
        String methodName = LogEx.me();

        // Then
        Assertions.assertThat(methodName)
                .as("Method name should be correctly retrieved")
                .isEqualTo("shouldReturnCorrectMethodName");
    }

    @Test
    @DisplayName("Should log trace level message correctly")
    void shouldLogTraceLevelMessageCorrectly() {
        // Given
        Object[] parts = {"test", "message", 123};

        // When
        LogEx.trace(mockLogger, parts);

        // Then
        verify(mockLogger).trace("test: message: 123");
    }

    @Test
    @DisplayName("Should log debug level message correctly")
    void shouldLogDebugLevelMessageCorrectly() {
        // Given
        Object[] parts = {"debug", "test"};

        // When
        LogEx.debug(mockLogger, parts);

        // Then
        verify(mockLogger).debug("debug: test");
    }

    @Test
    @DisplayName("Should log info level message correctly")
    void shouldLogInfoLevelMessageCorrectly() {
        // Given
        Object[] parts = {"info", "message"};

        // When
        LogEx.info(mockLogger, parts);

        // Then
        verify(mockLogger).info("info: message");
    }

    @Test
    @DisplayName("Should log warn level message correctly")
    void shouldLogWarnLevelMessageCorrectly() {
        // Given
        Object[] parts = {"warning", "message"};

        // When
        LogEx.warn(mockLogger, parts);

        // Then
        verify(mockLogger).warn("warning: message");
    }

    @Test
    @DisplayName("Should log error level message correctly")
    void shouldLogErrorLevelMessageCorrectly() {
        // Given
        Object[] parts = {"error", "message"};

        // When
        LogEx.error(mockLogger, parts);

        // Then
        verify(mockLogger).error("error: message");
    }

    @Test
    @DisplayName("Should handle single part message")
    void shouldHandleSinglePartMessage() {
        // Given
        Object[] parts = {"single message"};

        // When
        LogEx.info(mockLogger, parts);

        // Then
        verify(mockLogger).info("single message");
    }

    @Test
    @DisplayName("Should handle empty parts array")
    void shouldHandleEmptyPartsArray() {
        // Given
        Object[] parts = {};

        // When
        LogEx.info(mockLogger, parts);

        // Then
        verify(mockLogger).info("");
    }

    @Test
    @DisplayName("Should handle null values in parts")
    void shouldHandleNullValuesInParts() {
        // Given
        Object[] parts = {"test", null, "message"};

        // When
        LogEx.info(mockLogger, parts);

        // Then
        verify(mockLogger).info("test: null: message");
    }

    @Test
    @DisplayName("Should handle different object types")
    void shouldHandleDifferentObjectTypes() {
        // Given
        Object[] parts = {"string", 123, true, 45.67};

        // When
        LogEx.info(mockLogger, parts);

        // Then
        verify(mockLogger).info("string: 123: true: 45.67");
    }

    @Test
    @DisplayName("Should use constants correctly")
    void shouldUseConstantsCorrectly() {
        // Given
        Object[] parts = {LogEx.STARTING, LogEx.STOPPING, LogEx.EXCEPTION_THROWN};

        // When
        LogEx.info(mockLogger, parts);

        // Then
        verify(mockLogger).info("starting: finishing: exception thrown");
    }

    @Test
    @DisplayName("Should handle log method with all levels")
    void shouldHandleLogMethodWithAllLevels() {
        // Given
        Object[] parts = {"test", "message"};

        // When & Then
        LogEx.log(mockLogger, Level.TRACE, parts);
        verify(mockLogger).trace("test: message");

        LogEx.log(mockLogger, Level.DEBUG, parts);
        verify(mockLogger).debug("test: message");

        LogEx.log(mockLogger, Level.INFO, parts);
        verify(mockLogger).info("test: message");

        LogEx.log(mockLogger, Level.WARN, parts);
        verify(mockLogger).warn("test: message");

        LogEx.log(mockLogger, Level.ERROR, parts);
        verify(mockLogger).error("test: message");
    }

    @Test
    @DisplayName("Should handle varargs correctly")
    void shouldHandleVarargsCorrectly() {
        // When
        LogEx.info(mockLogger, "part1", "part2", "part3");

        // Then
        verify(mockLogger).info("part1: part2: part3");
    }

    @Test
    @DisplayName("Should handle complex object conversion")
    void shouldHandleComplexObjectConversion() {
        // Given
        Object[] parts = {
                "String value",
                42,
                true,
                null,
                new StringBuilder("builder"),
                new Object() {
                    @Override
                    public String toString() {
                        return "custom object";
                    }
                }
        };

        // When
        LogEx.info(mockLogger, parts);

        // Then
        verify(mockLogger).info("String value: 42: true: null: builder: custom object");
    }

    @Test
    @DisplayName("Should verify constants values")
    void shouldVerifyConstantsValues() {
        // Then
        Assertions.assertThat(LogEx.STARTING)
                .as("STARTING constant should have correct value")
                .isEqualTo("starting");
        Assertions.assertThat(LogEx.STOPPING)
                .as("STOPPING constant should have correct value")
                .isEqualTo("finishing");
        Assertions.assertThat(LogEx.STOPPED)
                .as("STOPPED constant should have correct value")
                .isEqualTo("finished");
        Assertions.assertThat(LogEx.SHORT_RUN)
                .as("SHORT_RUN constant should have correct value")
                .isEqualTo("starting -> finishing");
        Assertions.assertThat(LogEx.EXCEPTION_THROWN)
                .as("EXCEPTION_THROWN constant should have correct value")
                .isEqualTo("exception thrown");
    }

    @Test
    @DisplayName("Should handle multiple calls correctly")
    void shouldHandleMultipleCallsCorrectly() {
        // When
        LogEx.info(mockLogger, "first", "call");
        LogEx.warn(mockLogger, "second", "call");
        LogEx.error(mockLogger, "third", "call");

        // Then
        verify(mockLogger).info("first: call");
        verify(mockLogger).warn("second: call");
        verify(mockLogger).error("third: call");
    }
}
