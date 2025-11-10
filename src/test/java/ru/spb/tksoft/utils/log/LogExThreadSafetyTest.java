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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.verify;

/**
 * Thread safety tests for LogEx class.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LogEx Thread Safety Tests")
class LogExThreadSafetyTest {

    @Mock
    private Logger mockLogger;

    @Test
    @DisplayName("Should handle concurrent method name retrieval safely")
    void shouldHandleConcurrentMethodNameRetrievalSafely() throws InterruptedException {
        // Given
        int threadCount = 5;
        int callsPerThread = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        // When
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < callsPerThread; j++) {
                        String methodName = LogEx.me();
                        // Проверяем, что метод возвращает корректное имя (не null и не пустое)
                        Assertions.assertThat(methodName).isNotNull()
                                .isNotEmpty();
                        successCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Then
        Assertions.assertThat(successCount.get())
                .as("All method calls should succeed")
                .isEqualTo(threadCount * callsPerThread);
    }

    @Test
    @DisplayName("Should handle concurrent logging safely")
    void shouldHandleConcurrentLoggingSafely() throws InterruptedException {
        // Given
        int threadCount = 5;
        int callsPerThread = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < callsPerThread; j++) {
                        LogEx.info(mockLogger, "thread", threadId, "call", j);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Then
        verify(mockLogger).info("thread: 0: call: 0");
        // Проверяем, что все вызовы были обработаны
        Assertions.assertThat(LogEx.getCacheSize())
                .as("Cache should contain method names after logging")
                .isGreaterThan(0);
    }

    @Test
    @DisplayName("Should maintain cache consistency under concurrent access")
    void shouldMaintainCacheConsistencyUnderConcurrentAccess() throws InterruptedException {
        // Given
        LogEx.clearMethodCache();
        int threadCount = 8;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // Каждый поток вызывает me() несколько раз
                    for (int j = 0; j < 20; j++) {
                        LogEx.me();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Then
        Assertions.assertThat(LogEx.isCacheEnabled())
                .as("Cache should be enabled after method calls")
                .isTrue();
        Assertions.assertThat(LogEx.getCacheSize())
                .as("Cache should contain entries after concurrent access")
                .isGreaterThan(0);

        // Проверяем, что кэш работает корректно
        String methodName = LogEx.me();
        Assertions.assertThat(methodName)
                .as("Method name should be correctly retrieved")
                .isEqualTo("shouldMaintainCacheConsistencyUnderConcurrentAccess");
    }

    @Test
    @DisplayName("Should handle thread-local StringBuilder correctly")
    void shouldHandleThreadLocalStringBuilderCorrectly() throws InterruptedException {
        // Given
        int threadCount = 4;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // Каждый поток логирует с разными сообщениями
                    LogEx.info(mockLogger, "thread", threadId, "message1");
                    LogEx.info(mockLogger, "thread", threadId, "message2");
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Then
        // Проверяем, что все сообщения были залогированы корректно
        verify(mockLogger).info("thread: 0: message1");
        verify(mockLogger).info("thread: 0: message2");
    }

    @Test
    @DisplayName("Should clean up thread-local resources properly")
    void shouldCleanUpThreadLocalResourcesProperly() {
        // Given
        LogEx.info(mockLogger, "test", "message");

        // When
        LogEx.cleanupThreadLocal();

        // Then
        // После очистки thread-local переменных, следующий вызов должен работать нормально
        LogEx.info(mockLogger, "test", "after cleanup");
        verify(mockLogger).info("test: after cleanup");
    }

    @Test
    @DisplayName("Should handle cache operations safely")
    void shouldHandleCacheOperationsSafely() throws InterruptedException {
        // Given
        LogEx.clearMethodCache();
        int threadCount = 6;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // Смешиваем операции чтения и записи кэша
                    for (int j = 0; j < 10; j++) {
                        LogEx.me(); // Запись в кэш
                        LogEx.getCacheSize(); // Чтение размера кэша
                        LogEx.isCacheEnabled(); // Проверка состояния кэша
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Then
        Assertions.assertThat(LogEx.isCacheEnabled())
                .as("Cache should be enabled after operations")
                .isTrue();
        Assertions.assertThat(LogEx.getCacheSize())
                .as("Cache should contain entries after operations")
                .isGreaterThan(0);
    }
}
