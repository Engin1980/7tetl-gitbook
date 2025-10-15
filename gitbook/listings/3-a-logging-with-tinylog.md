# 3-a Logging with TinyLog

## TinyLog Logging Tutorial for Beginners (Java)

In this tutorial, you will learn how to use **TinyLog**, a simple logging library for Java. We will show how to log messages, use different log levels, include variables in logs, and format them.

We will:

* Create a small Java project using Maven.
* Add TinyLog as a dependency.
* Write a few examples that show how to log messages.

### 2. Setting Up the Project

We will use **Maven** to manage our dependencies. If you already have a `pom.xml` file, open it and add TinyLog.

If not, create a new Maven project and include this in your `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>tinylog-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- TinyLog 2 dependency -->
        <dependency>
            <groupId>org.tinylog</groupId>
            <artifactId>tinylog-api</artifactId>
            <version>2.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.tinylog</groupId>
            <artifactId>tinylog-impl</artifactId>
            <version>2.6.2</version>
        </dependency>
    </dependencies>
</project>
```

### 3. Your First Log Message

Now, let’s write a simple program.

Create a file called `Main.java` in the `src/main/java/com/example` folder.

```java
package com.example;

import org.tinylog.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.info("Hello from TinyLog!");

        // Using different log levels
        Logger.trace("Starting program...");
        Logger.debug("Debugging some details.");
        Logger.info("Everything is working fine.");
        Logger.warn("Something might be wrong.");
        Logger.error("Something went wrong!");

        // Using variables in log messages
        String name = "Alice";
        int score = 42;
        Logger.info("Player {} scored {} points!", name, score);
    }
}
```

When you run your program, you should see output like:

```
INFO: Hello from TinyLog!
TRACE: Starting program...
DEBUG: Debugging some details.
INFO: Everything is working fine.
WARN: Something might be wrong.
ERROR: Something went wrong!
INFO: Player Alice scored 42 points!
```

### 4. Configuring TinyLog

You can control TinyLog using a simple text file called `tinylog.properties`. Put it in your `src/main/resources` folder.

Example configuration:

```properties
# Set the minimum level of messages to show
level = info

# Choose where to write logs
writer = console
writer.format = {level}: {message}
```

You can change `writer` to write to a file instead:

```properties
writer = file
writer.file = logs/app.log
writer.format = {date} [{level}] {message}
```

Now your logs will be saved in `logs/app.log`.

### 5. Understanding Formatters

TinyLog allows you to control how each log message looks using **formatters**. Formatters use placeholders wrapped in `{}` to represent information.

Here are the most common placeholders you can use in `writer.format`:

| Placeholder   | Description                           | Example                        |
| ------------- | ------------------------------------- | ------------------------------ |
| `{date}`      | Current date and time                 | 2025-10-15 14:32:01            |
| `{level}`     | Log level (INFO, WARN, etc.)          | INFO                           |
| `{thread}`    | Thread name                           | main                           |
| `{tag}`       | Name of the custom logger/tag         | MyApp                          |
| `{class}`     | Class name                            | com.example.Main               |
| `{package}`   | Package name                          | com.example                    |
| `{method}`    | Method name                           | main                           |
| `{line}`      | Line number of the log call           | 15                             |
| `{message}`   | The actual log message                | Player Alice scored 42 points! |
| `{exception}` | Stack trace if an exception is logged | java.lang.Exception...         |

Example custom format:

```
writer.format = {date} [{thread}] {class}.{method}(): {level} - {message}
```

Output example:

```
2025-10-15 14:32:01 [main] com.example.Main.main(): INFO - Player Alice scored 42 points!
```

You can combine any placeholders you like to create your own log format.

### 6. Creating and Using Custom Loggers

In TinyLog, you can create custom loggers by using **tags**. Tags act as logger names.

```java
import org.tinylog.Logger;

public class CustomLoggerExample {
    public static void main(String[] args) {
        // Default logger
        Logger.info("This is a default log message.");

        // Custom logger with tag "MyApp"
        Logger.tag("MyApp").info("This is a custom logger message.");

        // Another custom logger with tag "Database"
        Logger.tag("Database").warn("Database connection slow.");
    }
}
```

Output:

```
INFO: This is a default log message.
INFO [MyApp]: This is a custom logger message.
WARN [Database]: Database connection slow.
```

### 7. Study Sources for TinyLog

Here are useful resources to learn more about TinyLog:

* **Official Documentation:** [https://tinylog.org/v2/](https://tinylog.org/v2/)
* **Maven Central (dependency info):** [https://search.maven.org/artifact/org.tinylog/tinylog-api](https://search.maven.org/artifact/org.tinylog/tinylog-api)

