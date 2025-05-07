package ru.spb.tksoft.utils.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * Tests were developed using the DeepSeek neural network and R1.
 */
@ExtendWith(MockitoExtension.class)
class LogExTest {

    @Mock
    private Logger logger;

    @Nested
    class GetThisMethodNameTests {

        @Test
        void getThisMethodName_ShouldReturnCallerMethodName() {
            String methodName = testHelper();
            assertThat(methodName).isEqualTo("testHelper");
        }

        private String testHelper() {
            return LogEx.getThisMethodName();
        }
    }

    @Nested
    class LogMethodTests {

        @Test
        void log_ShouldJoinPartsWithColon() {
            // When
            LogEx.log(logger, Level.INFO, new Object[] {"part1", "part2", 42});

            // Then
            verify(logger).info("part1: part2: 42");
            verifyNoMoreInteractions(logger);
        }

        @Test
        void log_WithEmptyParts_ShouldLogEmptyMessage() {
            LogEx.log(logger, Level.WARN, new Object[0]);
            verify(logger).warn("");
        }

        @Test
        void log_WithNullParts_ShouldHandleGracefully() {
            LogEx.log(logger, Level.ERROR, new Object[] {null, "value"});
            verify(logger).error("null: value");
        }
    }

    @Nested
    class LevelSpecificMethodsTests {

        @Test
        void trace_ShouldUseCorrectLevel() {
            LogEx.trace(logger, LogEx.STARTING, "operation");
            verify(logger).trace("starting: operation");
        }

        @Test
        void debug_ShouldUseConstantsCorrectly() {
            LogEx.debug(logger, LogEx.SHORT_RUN, "id=42");
            verify(logger).debug("starting -> finishing: id=42");
        }

        @Test
        void info_ShouldHandleMultipleArgs() {
            LogEx.info(logger, "User", "admin", "performed action");
            verify(logger).info("User: admin: performed action");
        }

        @Test
        void error_ShouldHandleComplexObjects() {
            Object[] complex = {new int[] {1, 2}, java.time.Instant.now()};
            LogEx.error(logger, complex);
            verify(logger).error(anyString());
        }
    }

    @Test
    void constants_ShouldHaveCorrectValues() {
        assertThat(LogEx.STARTING).isEqualTo("starting");
        assertThat(LogEx.STOPPING).isEqualTo("finishing");
        assertThat(LogEx.STOPPED).isEqualTo("finished");
        assertThat(LogEx.SHORT_RUN).isEqualTo("starting -> finishing");
        assertThat(LogEx.EXCEPTION_THROWN).isEqualTo("exception thrown");
    }
}
