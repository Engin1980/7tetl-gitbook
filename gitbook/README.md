---
description: >-
  The chapter presents the issue of application debugging and the motivation for
  it. It explains two types of errors and the modes, how the app can be started.
---

# 1 A basic introduction to testing and debugging

Application testing is an integral part of any program development. Both during the creation and after the completion of the application, the programmer verifies that the result corresponds to the expectations, that the application performs the operations that are expected of it, and that it correctly performs them. Therefore, every programmer must master ways to find and eliminate errors, inconsistencies, or incorrect behavior in applications. Since a programmer is a person who typically makes mistakes, he manages to place errors in every application, and it is therefore important to be able to find and eliminate them.

Errors can generally be divided into two groups:&#x20;

* **Syntactic (syntax errors)** - are errors caused by incorrect writing of the commands of the given programming language. Every programming language has specific formal rules that say how statements must be written in it - for example, that variables are declared with the keyword dim, or that individual statements are separated by semicolons, what is the exact order of expressions when declaring a function, how form the body of the method and the like. Syntax errors (from the word syntax) therefore refer to the wrong writing of the source code. These errors can usually be found relatively easily (it depends on the capabilities of the given programming language), the compiler performing the translation of the source code from the programming language for execution is not able to understand the incorrectly written command and therefore reports an error. The programmer thus has information about the error quickly available and is typically able to correct it. A simple example demonstrates a syntax error when there are no semicolons inside the parentheses in the for loop `for (int i = 0 i < 10 i++)` - see the full listing below. Thus, the only problem area may be understanding the type of error. Simple errors like “; expected" can be easily detected and the missing semicolon can be found. However, for more complex constructions, you need to know exactly the syntax of the language, its individual commands, and capabilities, so that it is obvious what the given error means. If even this is not enough, it is advisable to use the Internet for help, enter the error in the search engine, and try to find out more detailed information and repair options there.&#x20;
* **Semantic** - are errors that are caused by the programmer writing the commands incorrectly so that they do not make logical sense. These commands can be (and in the vast majority of cases are) written syntactically correctly, i.e. the compiler can translate and execute them, but their execution does not make sense from the point of view of the program - the program will perform a wrong operation, an incorrect calculation (or the calculation will return an incorrect value), or it will do nothing or end up with an error at runtime. These errors are difficult to find and remove because the programmer cannot see them at first glance (as in the case of syntactic errors, where the compiler will show them) and has to search for where and what is being done incorrectly by repeatedly going through the written commands. An example can be the for loop, where the third part, working with the control variable, is badly written `for (int i = 0; i < 10; i--)` - see the full listing below. The cycle should run from the value 0 to the value 9, because the value of the control variable i does not increase but decreases (i--), the cycle will not be executed even once, which was certainly not intended by the programmer.&#x20;

{% code title="Example of syntax error" %}
```java
for (int i = 0 i < 10 i++) { 
  System.out.println("Value is " + i); 
}
```
{% endcode %}

{% code title="Example of semantic error" %}
```java
for (int i = 0; i < 10; i--) { 
  System.out.println("Value is " + i); 
}
```
{% endcode %}

The next part of the study support will therefore be devoted to semantic errors.

Since every programmer writes with errors, the technique of finding errors is crucial for him. The better, simpler, and faster he is able to find and correct errors, the more efficient and beneficial the resulting programming activity will be. That's why programmers use different tools to find bugs.

As part of the study support, they can be included in two groups:

* Ad-hoc tools – i.e. anything that helps the programmer find a bug. Typically, this includes various auxiliary statements that the programmer prints on the screen and in a file, where he writes down where the program shuffled, what methods it called, what the values ​​of the variables were, and the like. The advantage is that no more sophisticated knowledge is required for these techniques, but on the other hand, the programmer typically significantly interferes with the created code them, and their use can cause additional errors in the program.
* Debugging tools (debug) – are tools that directly support programming languages ​​and are used for analysis, management, and browsing of source code. The vast majority of these tools do not require intervention in the original source code, moreover, they offer comfortable behavior (for example, going through the program by individual commands) and thus facilitate the search for errors, along with minimizing the probability that the programmer will cause other, new errors by using them. These techniques will be presented in more detail in the following subsection.&#x20;

In the end, it doesn't matter what tools the programmer uses, the goal is to create a functional, correct program serving the purpose for which it was created.&#x20;

The debugging techniques and procedures used in this tutorial are not targeted to a specific programming language, but the demonstrations are created and shown using the Java programming language and the IntelliJ Idea development environment.

### Debugging

Basic application debugging techniques are operations that do not force the programmer to intervene in the created source code, but at the same time provide a sufficient amount of information about the executed code. In general, debugging in this study guide will be divided into three basic techniques:

* Breakpoint – stopping points&#x20;
* Stepping&#x20;
* Watching – monitoring of variable values.&#x20;

Depending on the techniques presented, the issue of the call stack will also be explained. The first technique introduced will be breakpoints. However, before introducing them, it should be emphasized that debugging techniques do not work always everywhere. It generally depends on whether debugging is supported by the Java Virtual Machine (true for the most cases) and whether the development environment supports it (true for any current IDE). However, in general, it can be said that debugging techniques may not always be available everywhere.

### Startup modes

It is also important to mention that it depends on how the application is launched. For the following explanation, the following simple diagram will be considered, presenting the techniques of launching the application and **the states** in which the application will be.

TODO figure 1 here

From the point of view of the running application, we will distinguish three states:

* Run – is a classically launched application performing its function. None of the debugging techniques will work in this mode. The application to Run mode is started with the F6 key.&#x20;
* DebugRun – is the state when the application is running, i.e. executing its code. However, debugging techniques (see below) will work in this code. The application in DebugRun mode is started with the keyboard shortcut Ctrl+F5.
* Stepping – is a state when the application is suspended and the programmer sees the current command being executed in the application. Debugging techniques work in this mode.&#x20;

From the point of view of debugging, the application can therefore be in a running (debugRun) or stepping (stepping) state. The running state does not show the programmer anything, while the stepping state allows the programmer to continuously monitor the values ​​of variables, execute individual statements or their sequences, and follow the flow of the program. So the application can be launched in three ways:

1. Start the application in a common way – with the F6 key, using the Run Run project menu, or using the arrow on the toolbar (see image).
2. Start the application in the debug run - with the Ctrl+F5 key, using the DebugDebug project menu, or using the arrow on the toolbar (see picture).
3. By starting the application into stepping - with the F7 key or using the menu DebugStep into.

TODO figure 2 here

You can switch between individual states. For completeness, a graph representing the states and transitions between them in the NetBeans environment will now be presented. We recommend you return to this diagram and the following table after studying all relevant chapters.

TODO figure 3

The following table describes how to switch between individual application states.

<table><thead><tr><th width="137">State</th><th>Description</th><th>Entry action</th><th>Exit action</th></tr></thead><tbody><tr><td>Run</td><td>Executed application</td><td>Start the application</td><td>End of the application, or fatal error</td></tr><tr><td>DebugRun</td><td>Executed application for debugging</td><td>Start the application in debug mode, or resume from Stepping mode</td><td>At the breakpoint, on the end of the application, or at fatal error</td></tr><tr><td>Stepping</td><td>Executed application ready for processing command-by-command</td><td>By breakpoint hit, when app is paused (to be in stepping mode), or after one/multiple step(s) execution</td><td>At the end of the application, or when resumed to DebugRun mode</td></tr></tbody></table>

At the end of the introductory presentation, it is necessary to justify again that **the debugging techniques will not work when the application is started in the classic way** (Run, F6).
