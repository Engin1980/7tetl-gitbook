# 3 Logging

## Introduction & Motivation

A higher level of continuous listings used for debugging is constant monitoring of the application state. In this case, we do not add some additional outputs when trying to find a bug. Instead, during the application development, we are adding the output checkpoints reporting the current state and ensuring, that everything is ok.

This technique is called **logging**.

In logging, we record events, messages, or other information to track the operation and performance of the applications. This practice is widely used in various fields, including software development, system administration, and cybersecurity, to help monitor, troubleshoot, and analyze the behavior and performance of systems.

### How does it differ from continuous listings?

Continuous listing outpus are placed in the code only for debugging purposes. They pollute the code, and are supposed to be deleted once the bug is found.

Logging, on the other side, is placed in the code for the application lifetime. Different log levels ensure that only the appropriate information is visible when neccessary.

### Basic terms

Before we show some simple examples, let's clear out the basic terms:

* Logger - it is an object responsible for obtaining logged messages and their forwarding to the handlers. The instance of the logger is typically the only object a programmer is using directly by invoking and passing logged messages.
* Handler / Appender - is an object delivering the log message to its destination. This destination is typically a console output, file, database, or web service.
* Formatter/Layout - is an object responsible for formatting the log message into the target format processed by a handler.
* Filter - is an object responsible for filtering the appropriate messages (typically by log level, invoker, or target). Commonly, they are used together with handlers to process a specific message with a specific handler only.

### Log Levels

For logging, you can use different levels representing various significance. Simply say, you can use several basic levels:

<table><thead><tr><th width="115">Level</th><th>Description</th></tr></thead><tbody><tr><td>TRACE</td><td>For a very low level reporting, used to monitor the flow of the codes and variables. Example: printing the values of variable in the iteration.</td></tr><tr><td>DEBUG</td><td>For a low level reporting, used for debugging and code analysis as a verification everything goes well. Example: printing a value of a variable with suspicous behavior, or for validating a correct variable value during development/debug.</td></tr><tr><td>INFO</td><td>For a standard reports of the progress and/or success messages of operations. Example: reporting that some operation has started, or has been completed succesfully (+ the result).</td></tr><tr><td>WARN</td><td>For reporting an unexpected values or issues not directly causing the issue in the application. Example: reporting that some variable has unexpected/null value and the default value will be used instead.</td></tr><tr><td>ERROR</td><td>For reporting an error in the application. Application typically cannot continue in the requested operation, but can abort it and continue to run different tasks. Example: Failed to save the work to the file. You can continue your work and try it again later, but for now, your work is not safe.</td></tr><tr><td>FATAL</td><td>For reporting fatal error causing the application to quit. It is used to store as much information as possible before the applcation is exited. Example: An object required to complete the operation is null. App cannot continue.</td></tr></tbody></table>

Everytime a programmer sends a log message, she/he must include the log level. On the otherside, every logger/appender/filter has its predefined log level. For simplicity, it can be said that if logger's log level is lower than the message's log level, the message will be processed. E.g., if logger has a level `info`, it will process all info/warn/error/fatal messages, but will ignore trace/debug messages.

### Implementations

There are multiple implementations for logging frameworks in Java. Here is an overview of the most common ones along with a brief comparison:

| Framework               | Description                                                  | Advantages                                                                                      | Disadvantages                                                      | Typical Use Case                          |
| ----------------------- | ------------------------------------------------------------ | ----------------------------------------------------------------------------------------------- | ------------------------------------------------------------------ | ----------------------------------------- |
| Java Util Logging (JUL) | Built-in framework directly in JDK (`java.util.logging`)     | No external library needed, simple to use                                                       | Limited configuration options, poor formatting, weaker performance | Small or internal projects                |
| Log4j 2                 | Modern version of the popular Apache Log4j                   | Excellent performance (asynchronous logging), flexible configuration (XML, JSON, YAML), plugins | Requires external library                                          | Larger enterprise applications            |
| SLF4J                   | Abstraction layer over various logging frameworks            | Allows easy switching of implementation (Logback, Log4j, JUL)                                   | Does not log by itself – requires a backend                        | Standard API for libraries and frameworks |
| Logback                 | Successor to Log4j by the same author, often used with SLF4J | Fast, flexible configuration (XML, Groovy), good integration with Spring                        | Slightly more complex configuration than JUL                       | Spring applications, enterprise systems   |
| TinyLog                 | Lightweight, simple framework with minimal dependencies      | Easy configuration, small footprint, fast startup                                               | Fewer features than Log4j/Logback                                  | Microservices, small applications         |
| Log4j (v1.x)            | Original implementation of Apache Log4j                      | Historically widespread                                                                         | Obsolete and insecure (CVE-2021-44228)                             | Do not use – replace with Log4j 2         |

In bigger project, logging architecture is commonly divided into two parts:

* a common interface providing basic logging capabilities
* an underlying implementation doing the physical operations.

For interface, SFL4J2 or Log4J-api are commonly used ensuring the basic operations available. To those, many common logging implementations are connectible using appropriate references.
