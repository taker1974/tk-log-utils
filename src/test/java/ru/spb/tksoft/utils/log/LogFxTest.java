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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Unit tests for LogFx class.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@SuppressWarnings("java:S1192")
@ExtendWith(MockitoExtension.class)
@DisplayName("LogFx Tests")
class LogFxTest {

    @Mock
    private Logger mockLogger;

    @Captor
    private ArgumentCaptor<Object[]> argsCaptor;

    @Nested
    @DisplayName("Trace level tests")
    class TraceLevelTests {

        @Test
        @DisplayName("Should log trace message when trace is enabled")
        void shouldLogTraceMessageWhenEnabled() {
            // Given
            when(mockLogger.isTraceEnabled()).thenReturn(true);

            // When
            LogFx.trace(mockLogger, "Message with {} and {}", "arg1", "arg2");

            // Then
            verify(mockLogger).trace(eq("Message with {} and {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly("arg1", "arg2");
        }

        @Test
        @DisplayName("Should not log trace message when trace is disabled")
        void shouldNotLogTraceMessageWhenDisabled() {
            // Given
            when(mockLogger.isTraceEnabled()).thenReturn(false);

            // When
            LogFx.trace(mockLogger, "Message with {} and {}", "arg1", "arg2");

            // Then
            verify(mockLogger, never()).trace(any(String.class), any(Object[].class));
        }
    }

    @Nested
    @DisplayName("Debug level tests")
    class DebugLevelTests {

        @Test
        @DisplayName("Should log debug message when debug is enabled")
        void shouldLogDebugMessageWhenEnabled() {
            // Given
            when(mockLogger.isDebugEnabled()).thenReturn(true);

            // When
            LogFx.debug(mockLogger, "Debug message: {}", "value");

            // Then
            verify(mockLogger).debug(eq("Debug message: {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly("value");
        }

        @Test
        @DisplayName("Should not log debug message when debug is disabled")
        void shouldNotLogDebugMessageWhenDisabled() {
            // Given
            when(mockLogger.isDebugEnabled()).thenReturn(false);

            // When
            LogFx.debug(mockLogger, "Debug message: {}", "value");

            // Then
            verify(mockLogger, never()).debug(any(String.class), any(Object[].class));
        }
    }

    @Nested
    @DisplayName("Info level tests")
    class InfoLevelTests {

        @Test
        @DisplayName("Should log info message when info is enabled")
        void shouldLogInfoMessageWhenEnabled() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "Info message: {}", "data");

            // Then
            verify(mockLogger).info(eq("Info message: {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly("data");
        }

        @Test
        @DisplayName("Should not log info message when info is disabled")
        void shouldNotLogInfoMessageWhenDisabled() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(false);

            // When
            LogFx.info(mockLogger, "Info message: {}", "data");

            // Then
            verify(mockLogger, never()).info(any(String.class), any(Object[].class));
        }
    }

    @Nested
    @DisplayName("Warn level tests")
    class WarnLevelTests {

        @Test
        @DisplayName("Should log warn message when warn is enabled")
        void shouldLogWarnMessageWhenEnabled() {
            // Given
            when(mockLogger.isWarnEnabled()).thenReturn(true);

            // When
            LogFx.warn(mockLogger, "Warning: {}", "issue");

            // Then
            verify(mockLogger).warn(eq("Warning: {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly("issue");
        }

        @Test
        @DisplayName("Should not log warn message when warn is disabled")
        void shouldNotLogWarnMessageWhenDisabled() {
            // Given
            when(mockLogger.isWarnEnabled()).thenReturn(false);

            // When
            LogFx.warn(mockLogger, "Warning: {}", "issue");

            // Then
            verify(mockLogger, never()).warn(any(String.class), any(Object[].class));
        }
    }

    @Nested
    @DisplayName("Error level tests")
    class ErrorLevelTests {

        @Test
        @DisplayName("Should log error message when error is enabled")
        void shouldLogErrorMessageWhenEnabled() {
            // Given
            when(mockLogger.isErrorEnabled()).thenReturn(true);

            // When
            LogFx.error(mockLogger, "Error occurred: {}", "details");

            // Then
            verify(mockLogger).error(eq("Error occurred: {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly("details");
        }

        @Test
        @DisplayName("Should not log error message when error is disabled")
        void shouldNotLogErrorMessageWhenDisabled() {
            // Given
            when(mockLogger.isErrorEnabled()).thenReturn(false);

            // When
            LogFx.error(mockLogger, "Error occurred: {}", "details");

            // Then
            verify(mockLogger, never()).error(any(String.class), any(Object[].class));
        }
    }

    @Nested
    @DisplayName("Format and arguments tests")
    class FormatAndArgumentsTests {

        @Test
        @DisplayName("Should handle multiple arguments")
        void shouldHandleMultipleArguments() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "Values: {}, {}, {}, {}", "a", "b", "c", "d");

            // Then
            verify(mockLogger).info(eq("Values: {}, {}, {}, {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly("a", "b", "c", "d");
        }

        @Test
        @DisplayName("Should handle no arguments")
        void shouldHandleNoArguments() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "Simple message without arguments");

            // Then
            verify(mockLogger).info(eq("Simple message without arguments"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).isEmpty();
        }

        @Test
        @DisplayName("Should handle null argument")
        void shouldHandleNullArgument() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "Value is: {}", (Object) null);

            // Then
            verify(mockLogger).info(eq("Value is: {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly((Object) null);
        }

        @Test
        @DisplayName("Should handle different argument types")
        void shouldHandleDifferentArgumentTypes() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "String: {}, int: {}, boolean: {}, double: {}",
                    "text", 42, true, 3.14);

            // Then
            verify(mockLogger).info(eq("String: {}, int: {}, boolean: {}, double: {}"),
                    argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly("text", 42, true, 3.14);
        }

        @Test
        @DisplayName("Should handle exception as argument")
        void shouldHandleExceptionAsArgument() {
            // Given
            when(mockLogger.isErrorEnabled()).thenReturn(true);
            Exception testException = new RuntimeException("Test error");

            // When
            LogFx.error(mockLogger, "Exception occurred: {}", testException);

            // Then
            verify(mockLogger).error(eq("Exception occurred: {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue()).containsExactly(testException);
        }
    }

    @Nested
    @DisplayName("Constants tests")
    class ConstantsTests {

        @Test
        @DisplayName("Should have correct STARTING constant")
        void shouldHaveCorrectStartingConstant() {
            Assertions.assertThat(LogFx.STARTING)
                    .as("STARTING constant should have correct value")
                    .isEqualTo("starting");
        }

        @Test
        @DisplayName("Should have correct STOPPING constant")
        void shouldHaveCorrectStoppingConstant() {
            Assertions.assertThat(LogFx.STOPPING)
                    .as("STOPPING constant should have correct value")
                    .isEqualTo("finishing");
        }

        @Test
        @DisplayName("Should have correct STOPPED constant")
        void shouldHaveCorrectStoppedConstant() {
            Assertions.assertThat(LogFx.STOPPED)
                    .as("STOPPED constant should have correct value")
                    .isEqualTo("finished");
        }

        @Test
        @DisplayName("Should have correct SHORT_RUN constant")
        void shouldHaveCorrectShortRunConstant() {
            Assertions.assertThat(LogFx.SHORT_RUN)
                    .as("SHORT_RUN constant should have correct value")
                    .isEqualTo("starting -> finishing");
        }

        @Test
        @DisplayName("Should have correct EXCEPTION_THROWN constant")
        void shouldHaveCorrectExceptionThrownConstant() {
            Assertions.assertThat(LogFx.EXCEPTION_THROWN)
                    .as("EXCEPTION_THROWN constant should have correct value")
                    .isEqualTo("exception thrown");
        }

        @Test
        @DisplayName("Should use constants in formatted message")
        void shouldUseConstantsInFormattedMessage() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "Status: {} -> {}", LogFx.STARTING, LogFx.STOPPED);

            // Then
            verify(mockLogger).info(eq("Status: {} -> {}"), argsCaptor.capture());
            Assertions.assertThat(argsCaptor.getValue())
                    .containsExactly(LogFx.STARTING, LogFx.STOPPED);
        }
    }

    @Nested
    @DisplayName("Multiple calls tests")
    class MultipleCallsTests {

        @Captor
        private ArgumentCaptor<String> formatCaptor;

        @Test
        @DisplayName("Should handle multiple calls with different levels")
        void shouldHandleMultipleCallsWithDifferentLevels() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);
            when(mockLogger.isWarnEnabled()).thenReturn(true);
            when(mockLogger.isErrorEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "First call: {}", "info");
            LogFx.warn(mockLogger, "Second call: {}", "warn");
            LogFx.error(mockLogger, "Third call: {}", "error");

            // Then
            verify(mockLogger).info(eq("First call: {}"), any(Object[].class));
            verify(mockLogger).warn(eq("Second call: {}"), any(Object[].class));
            verify(mockLogger).error(eq("Third call: {}"), any(Object[].class));
        }

        @Test
        @DisplayName("Should handle multiple calls to same level")
        void shouldHandleMultipleCallsToSameLevel() {
            // Given
            when(mockLogger.isInfoEnabled()).thenReturn(true);

            // When
            LogFx.info(mockLogger, "Message {}", 1);
            LogFx.info(mockLogger, "Message {}", 2);
            LogFx.info(mockLogger, "Message {}", 3);

            // Then
            verify(mockLogger, times(3)).info(formatCaptor.capture(), argsCaptor.capture());
            Assertions.assertThat(formatCaptor.getAllValues())
                    .containsExactly("Message {}", "Message {}", "Message {}");
            Assertions.assertThat(argsCaptor.getAllValues())
                    .containsExactly(
                            new Object[] {1},
                            new Object[] {2},
                            new Object[] {3});
        }

        @Test
        @DisplayName("Should handle mixed enabled and disabled levels")
        void shouldHandleMixedEnabledAndDisabledLevels() {
            // Given
            when(mockLogger.isTraceEnabled()).thenReturn(false);
            when(mockLogger.isDebugEnabled()).thenReturn(false);
            when(mockLogger.isInfoEnabled()).thenReturn(true);
            when(mockLogger.isWarnEnabled()).thenReturn(true);
            when(mockLogger.isErrorEnabled()).thenReturn(true);

            // When
            LogFx.trace(mockLogger, "Trace: {}", "value");
            LogFx.debug(mockLogger, "Debug: {}", "value");
            LogFx.info(mockLogger, "Info: {}", "value");
            LogFx.warn(mockLogger, "Warn: {}", "value");
            LogFx.error(mockLogger, "Error: {}", "value");

            // Then - trace and debug should not be logged
            verify(mockLogger, never()).trace(any(String.class), any(Object[].class));
            verify(mockLogger, never()).debug(any(String.class), any(Object[].class));

            // info, warn, error should be logged
            verify(mockLogger).info(eq("Info: {}"), any(Object[].class));
            verify(mockLogger).warn(eq("Warn: {}"), any(Object[].class));
            verify(mockLogger).error(eq("Error: {}"), any(Object[].class));
        }
    }
}
