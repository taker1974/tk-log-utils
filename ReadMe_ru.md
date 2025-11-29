# tk-log-utils

## О модуле

Некоторые полезные методы для логирования.

## Использование модуля

Добавьте зависимости в ваш _pom.xml_:

```xml
<properties>
    <tk-log-utils.version>2.0.2</tk-log-utils.version>
</properties>

<!-- Добавьте сам модуль -->
<dependency>
    <groupId>ru.spb.tksoft</groupId>
    <artifactId>tk-log-utils</artifactId>
    <version>${tk-log-utils.version}</version>
</dependency>

<!-- Добавьте предпочитаемую реализацию slf4j -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>${logback.version}</version>
</dependency>
```

Добавьте импорты в ваш код:

```Java
import ru.spb.tksoft.utils.log.LogEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

Логируйте ваши события:

```Java
@Service
public class MyService {
    private static final Logger log = LoggerFactory.getLogger(MyService.class);

    public void myMethod(int param) {
        // Строка лога: <некоторый текст зависит от вашей конфигурации>: myMethod: starting
        LogEx.trace(log, LogEx.me(), LogEx.STARTING);

        ...

        // Строка лога: <некоторый текст зависит от вашей конфигурации>: myMethod: finished
        LogEx.trace(log, LogEx.me(), LogEx.STOPPED);

        // Вы можете логировать всё, что хотите:
        LogEx.info(log, LogEx.me(), "I", "don't", "want", "to write method name every time");
    }
}
```

## Сборка модуля

1. Установите Java 21 + Maven.
2. Соберите модуль:

```bash
cd tk-log-utils && mvn clean package
```

## Требования

Java >= 17.

**Java**:

- установите JDK или JRE версии 17 или выше (разработка ведется на этой версии; нет очевидных ограничений на использование других версий Java);
- убедитесь, что установка выполнена правильно и что java, javac и maven (mvn) доступны;

## Компиляция и установка

См. pom.xml. Перейдите в корневую директорию модуля и выполните:

```bash
mvn clean install
mvn compile javadoc:javadoc
```

## Автор

Константин Терских  
Email: <kostus.online.1974@yandex.ru>, <kostus.online@gmail.com>  
Санкт-Петербург 2025
