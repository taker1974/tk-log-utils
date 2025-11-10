# tk-log-utils

## About the module

Some useful logging methods.

## Using the module

Add dependencies to your _pom.xml_:

```xml
<properties>
    <tk-log-utils.version>2.0.2</tk-log-utils.version>
</properties>

<!-- Add module itself -->
<dependency>
    <groupId>ru.spb.tksoft</groupId>
    <artifactId>tk-log-utils</artifactId>
    <version>${tk-log-utils.version}</version>
</dependency>

<!-- Add preferred implementation of slf4j -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>${logback.version}</version>
</dependency>
```

Add imports to your code:

```Java
import ru.spb.tksoft.utils.log.LogEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

Log your events:

```Java
@Service
public class MyService {
    private static final Logger log = LoggerFactory.getLogger(MyService.class);

    public void myMethod(int param) {
        // Log line: <some text depends on your config>: myMethod: starting
        LogEx.trace(log, LogEx.me(), LogEx.STARTING);

        ...

        // Log line: <some text depends on your config>: myMethod: finished
        LogEx.trace(log, LogEx.me(), LogEx.STOPPED);

        // You can log anything you want:
        LogEx.info(log, LogEx.me(), "I", "don't", "want", "to write method name every time");
    }
}

```

## Build the module

1. Install Java 21 + Maven.
2. Build the module:

```bash
cd tk-log-utils && mvn clean package
```

## Prerequisites

Java >= 17.

**Java**:

- install JDK or JRE version 17 or higher (development is carried out on this version; there are no obvious restrictions on the use of other versions of Java);
- make sure that the installation is correct and that java, javac, and maven (mvn) are available;

## Compile and install

See pom.xml. Change to the module root directory and run:

```bash
mvn clean install
mvn compile javadoc:javadoc
```

## API

See [javadoc](https://github.com/taker1974/tk-log-utils/blob/main/javadoc/) folder for documentation.

## Author

Konstantin Terskikh  
Email: <kostus.online.1974@yandex.ru>, <kostus.online@gmail.com>  
Saint-Petersburg 2025
