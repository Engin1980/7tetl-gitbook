# 5 Breakpoints

**A breakpoint** is a program interruption point. Its goal is to interrupt the program at a specific point. So if we place a breakpoint somewhere in the source code of the program, the application will stop juste before executing this command.

An important note is that a breakpoint can only be placed on a statement. For example, variable declarations are not statements (they only say that a variable will exist, but the program does not perform any "operation" regarding this code. On the other side, if a value assignment is a part of variable declaration, it is already a valid statement (the operation is "assigning a value to a variable").

```java
int a; // not a statement

int b = 0; // is a statement
```

Similarly, method declarations/headers are not statements. However, the body of methods typically contains commands:

```java
public String getA(){	// not a statement, but a breakpoint can be placed here
  String ret;         	// not a statement
  ret = "A";        	// is a statement
  return ret;         	// is a statement
}         		// not a statement
```

Note that although the method signature line is not a statement, you can place a breakpoint here. Once set, the environment will automatically place a breakpoint on the first statement line of the method.

{% hint style="info" %}
A special case is the closing bracket of the "}" block of a method. It is not a statement, but in some development tools, the breakpoint can be placed here so that the programmer can catch the exiting of the method - so it is an auxiliary tool for the programmer.
{% endhint %}

## Basic Breakpoint Operations

A breakpoint can be placed in two simple ways:

* Ctrl+F8 on a specific line
* By clicking on the desired line in the line header before the line number (gray column).

{% hint style="info" %}
Note that the exact keyboard shorcuts can be changed. Always refer to your Idea settings to check for the correct keyboard shortcut.
{% endhint %}

![Example of a breakpoint](Imgs/5-demo-breakpoint.jpg)

The purple circle shows where to click to insert a breakpoint. Insertion of a breakpoint is reflected by coloring the line with a reddish color and a circle icon of the same color. The following figure shows the insertion of a breakpoint on all command lines.

![Visualisation of breakpoints](Imgs/5-where-breakpoint.jpg)

In the figure, you can notice:

* At line 10 - It was mentioned above that a breakpoint cannot be placed on a method declaration. However, Idea allows you to prepare a breakpoint here. At startup, the breakpoint is not placed here, but placed at the first statement of the method and the run is interrupted once the method is called. This state is represented by a change in icon - a reddish diamond (instead of a circle).
* At line 11 - As mentioned, a variable declaration is not a statement. Therefore, you cannot set the breakpoint here. Or, more precisely - you can set a breakpoint here; however, once executed, the invalid breakpoints are ignored and represented by the respective icon (see lines 11 or 14).
* At line 14 - You cannot defined a breakpoint at the method end. To do so, you need to place a breakpoint at the `return` statement.

Removing a breakpoint is done in the same way as adding one.

**Example**

A demonstration of behavior follows. Let's have the following simple code in the main() method.

```java
public static void main(String[] args) { 
  int a = 5;  
  int b = 7;  
  int c = a + b;  
  System.out.println(c);  
} 
```

The method adds two numbers and prints the result. We place a breakpoint on the addition line.

![Example 1 - a prepared breakpoint](Imgs/5-example-prepared.jpg)

Now let's run the application.

If we start the application classically – F6 – without debug mode, the application will run to the end, it will terminate and the sum statement obtained by line 13 – i.e. the value 12 – will appear on the console.

However, if we start the application in debug mode - Ctrl+F5, the application will start in _DebugRun_ mode and then gets interrupted in _Stepping_ mode (see next chapter) on line 12 after reaching the breakpoint. This will be signaled by a blue colored line:

![Example 1 - executed code, breakpoint hit](Imgs/5-example-run.jpg)

The blue-colored line says that the application is in a suspended state, in stepping debugging mode. The highlighted line indicates a command that is yet to be executed.

In the stepping mode, you can dynamically add or remove additional breakpoints to the application: just place (or remove) them again.

To resume the application run from suspended mode, use F9 key or the green arrow in the _Debug_ toolbar:

![Example 1 - 'resume' button location](Imgs/5-example-how-resume.jpg)

{% hint style="info" %}
Do not use the common _Run_ or _Debug-Run_ commands from the toolbar. Those commands will try to start another instance of your application. Once the application is started in the _Debug-Run_ mode, all related stuff is available using the _Debug_ window, typically located at the bottom of the screen.
{% endhint %}

**Another example**

If we place two breakpoints in the program, the application will stop when the first one is reached. Consider a program that generates an array of numbers and then sums them. Finally it prints the result.

```java
public static void main(String[] args) {
 
  double [] data = new double[1_000_000]; 		// 1. BP
  for (int i = 0; i < data.length; i++) {
    data[i] = Math.random();
  }
  
  double sum = 0;   					// 2. BP
  int index = 0;
  for (int i = 0; i < data.length; i++) {
    sum += data[i];
  }
  
  System.out.println("Result is " + sum);
} 
```

Place two breakpoints in the program and then start the program. The program first stops at the first breakpoint.

![Example 2 - Prepared breakpoints, first one hit](Imgs/5-example2-A.jpg)

If the programmer now wants to continue the calculation, he presses the F9 key and the program runs until it hits the second breakpoint:

![Example 2 - Prepared breakpoings, second one hit](Imgs/5-example2-B.jpg)

If the programmer now starts the program again, the program will reach the end (because there is no more breakpoint in the run path) and will exit.

General rules using breakpoints for transitions between debug-run and stepping states can be subsequently formulated as follows. If the programmer starts a program in debug-run mode, the program will run in this mode until:

* does not end (by successfully completing the program);
* there is no fatal error in the application terminating the running of the application (unsuccessful termination of the program);
* does not encounter a breakpoint (switches to the stepping state).

## Using a breakpoint to monitor the application's progress

Breakpoints are primarily used to stop an application from running at a certain point. The programmer can then monitor the variables at this point or browse the application continuously (it will be explained in the next chapters).

**Conditional commands – if, switch**

However, the programmer can also use breakpoints to monitor the progress of the code. An illustrative example can be a simple code snippet with an "if-else" statement.

TODO 5-if-else.jpg

If the programmer places breakpoints in both branches, after starting the application, it must necessarily (that is, if the program passes through the condition on line 9) reach one of these branches. Depending on which of the branches the program stops, the programmer can determine whether the above condition has been evaluated as true or false.

TODO 5-if-else-run.jpg

So in the example above, the condition was not met, the value of `a` was less than or equal to 10, and the program execution jumped to line 12.

**Function calls**

In a similar way, for example, you can test whether the created function is called. In this way, the programmer can easily find out whether he forgot to call a certain function (or, using other mechanisms, find out where the function was called from). It is enough if the programmer inserts a breakpoint into the function somewhere (ideally at the beginning). If the application stops at a breakpoint, the program calls the given function. If the application does not stop at the breakpoint, the function is not called (or the application terminates with an error before the function is called).

**Iterations**

Breakpoints can also be used in iterations. It is important to note that a breakpoint is hit repeatedly in in every iteration!

TODO 5-iter.jpg

In this case, after starting, the application will be stopped at line 5 in the first iteration (when `i` is 0), after restart (F9) in the second iteration (when `i` is 1), etc. This breakpoint will therefore be encountered in each iteration; in total, the program stops here 1,000,000 times.

## Breakpoint properties

Certain behavior can also be set to the created breakpoint through its properties.

### Breakpoint conditions

As mentioned, when running an application in debug-run mode, the application will always stop when a breakpoint is reached.

However, sometimes, typically in cycles, such processing is disadvantageous. Consider the following example where we are checking data read from a large text file.

```java
int linesCount = getLinesCount();
for (int i = 0; i < linesCount; i++) {  
  String line = readLineFromFile();  
  double d = convertLineToDouble(line);
  data.add(d);  
} 
```

In this case, we know how many lines there are in the file (for example, 10,000). However, one line of this large file ends up with an error when calling the `convertLineToDouble(line)` method. When executed, the code returns an error:

```
Exception in thread "main" java.lang.RuntimeException: Invalid data.
	at breakpoints.Program.convertLineToDouble(ToDelete58432.java:58)
	at breakpoints.Program.main(ToDelete58432.java:32)
Java Result: 1
```

However, this error only says that the data is not correct, but does not say what data at all. The programmer would need to find out which are the erroneous data, and most importantly, on which line of the input file they appear.

Now, if the programmer tries to find the error, he must place a breakpoint in the loop handling the reading from the file:

TODO 5-cond-example-1.jpg

The programmer can now check the value in the variable d (it will be explained in the following chapters) as well as see what line of the file it is on (this is shown in the variable i). But since we said that the file has 10,000 lines, it may happen that the very first line is not the one with the error. Therefore, if the programmer restarts the application (F5), one turn of the cycle is performed, and in the next turn of the cycle, the application stops again at a breakpoint:

TODO the same

One can notice the difference from stopping at the breakpoint in the previous case - there is no difference. The program simply stops at a breakpoint every time, every iteration. So the programmer can now repeatedly run the program (F9) and perform more and more iterations. When a certain iteration fails and the program crashes, then the programmer will be on the wrong line. If he knows the iteration number, he can look in the source file and see what the problem line of the input file contains.

But repeatedly pressing the F9 key will not help the programmer - what if the error is as far as line 8,574? Such checking and gradually going through 8 thousand iterations of the cycle would be time consuming and mentally draining. Fortunately, the programmer can help with the technique of conditional breakpoints.

A programmer can define a condition - once this condition is true, the breakpoint is hit and a program is suspended. If the condition is false, the breakpoint is skipped.

The breakpoint condition is defined on the breakpoint context menu (see figure). The Properties window opens.

TODO 5-break-condition.jpg

There is a _Condition_ text box in the breakpoint properties window. Here you can now specify any condition indicating when the breakpoint should stop. If we are in the for cycle and we want to stop, if the control variable also has the value 7, all we have to do is enter:

TODO 5-break-condition-set.jpg

Attention! This is a value evaluating conditions, so the rules apply&#x20;

* The condition does not end with a semicolon.
* Conditions can be chained arbitrarily (as if, for example, you would write the condition in the parentheses of the if statement, i.e. (a > 7 && a < 10) || b = 3.
* For comparison, the comparison sign must be used correctly (two equals: a == b). They don't forget to use the function x.equals(…) to compare strings.

If a condition is set on a breakpoint, the breakpoint will change its icon:

TODO 5-break-condition-icon.jpg

Using this knowledge and the technique of bisecting intervals, the problem of finding a wrong iteration in a cycle can be easily solved.

{% hint style="info" %}
The method of bisecting the intervals is based on the principle that I divide the area into two parts and find out which is the problem. I then subdivide the problem area again and continue until I have a small enough area to examine one by one.

For example, if I have a value range of 0-1,000 and my unknown error value is 300. I set the stop breakpoint to 500. When the application starts, it crashes, so I know that the error value is less than 500. So I set the breakpoint to 250. Then the application it reaches the breakpoint - so I know that the problem area is in the range of 250 - 500. So I set the breakpoint to 333 - the application crashes. So I limited the range to 250-333. I can continue iteratively until I have a small enough area to go through one at a time using individual iterations.
{% endhint %}

### Enabled/Disabled breakpoints

Sometimes it is advisable not to delete the breakpoint directly, but only to turn it off for a while and turn it on again later. Both can be done again via the context menu of the breakpoint and the _Enabled_ option.

TODO 5-breakpoint-suspended.jpg

Once the breakpoint is disabled, it cannot be hit. However, you can re-enable the breakpoint at any time.

This is useful if you have a breakpoint with a condition or other additional settings. By deleting/reseting the breakpoint, you have to set the settings again. By disabling/enabling the breakpoint, you preserve the settings.

### Additional breakpoint settings

Using the _More..._ button, you can expand full breakpoint settings. Then, you can adjust several other properties. The most significant are explained in the table below.

TODO 5-breakpoint-all.jpg

<table><thead><tr><th width="217">Property</th><th>Meaning</th></tr></thead><tbody><tr><td>Enabled</td><td>Enabled breakpoint can be hit. Disabled breakpoint preservers its configuration, but cannot be hit.</td></tr><tr><td>Suspend</td><td>Defines, how the suspendation behaves in multithreaded environment. All - means all application threads are suspended. Thread - means only the thread hitting the breakpoint is suspended. </td></tr><tr><td>Log ...</td><td>Prints a log (message) into the console window when the breakpoint is hit.</td></tr><tr><td>Log "Breakpoint hit" message</td><td>Logs a message only saying that the breakpoint was hit.</td></tr><tr><td>Log Stack trace</td><td>Logs a full stack trace when a breakpoint is hit. <em>Stack trace</em> is explained later in this course.</td></tr><tr><td>Evaluate and log</td><td>By setting a custom text in the text field, you can print a custom log message to a console. This message can also contain a value of variables or a result of a function calls.</td></tr><tr><td>Remove once hit</td><td>Breakpoint is removed once it is hit. Therefore, the breakpoint is always hit only once.</td></tr><tr><td>Pass count</td><td>You can specify a count, how many times a breakpoint is run through until its hit.</td></tr><tr><td>Instance/Class/Caller filters</td><td>You can specify, which instance/class/caller can invoke the breakpoint. More advanced technique.</td></tr></tbody></table>

## Exception as a breakpoint

Very powerful feature in Idea (and some other development environments) is handling exceptions as breakpoints.

Why? If an exception occures in the application, it causes imminent application crash. Once the application has crashed, you have only a limited tools to diagnose why and when did the application crash. On the other side, if **an exception is handled as a breakpoint**, the application run is suspended in the case of exception. It means, you have the application calculation frozen in the moment of exception invocation. You can analyse stack trace, variable values and other important properties (see the following chapters).

In general Idea is set to handle any exception as a breakpoint. You can adjust this behavior using _Breakpoints_ window, in the section _Java Exception Breakpoints_.

TODO again same image as above

You can add a custom exception-breakpoint handling using the `+` button in the top left corner. Morever, for every exception-breakpoint, you can adjust the similar properties as above; moreover, you can distinquish between caught and uncaught exception handling.

{% hint style="info" %}
Caught excepiont is exception handled by a programmer. You probably don't want to handle this exception raising as a breakpoint, if your code is correctly handling the exception and recovering the state.

Unhandled exceptions are not handled by any mechanism and causing application crash. In this case, you typically want to handle such cases as breakpoints, as you can see, what went wrong.
{% endhint %}

{% hint style="warning" %}
Again note, that exception-breakpoint handling is available only in the application _Debug-Run_ state.
{% endhint %}

## Exception window

To overview and simple adjust all the breakpoints in the current project, use the _Breakpoint_ window.

This window can be opened using "Run -> View Breakpoints" menu, from the _Debug_ bottom menu and the breakpoints icon, or by a key shortcut Ctrl+Shift+F8 (by default).

## Interrupting calculations

Sometimes there is a situation where the current program needs to be suspended at the current point of the calculation, even though we do not have a breakpoint at that point. Typically, it is at times when the program performs some complex, longer-lasting calculation, and the programmer wants to find out, for example, what state the calculation is in, if the program continues correctly, or if there is an error somewhere in the program.

At such a moment, the programmer can pause the program using the toolbar and the pause icon.

TODO 5-pause.jpg

When the program is suspended, the application is interrupted **wherever** the current calculation is performed and marks the next example to be executed. The difference from a breakpoint is that a breakpoint determines the exact line where the program should stop, while a pause stops the program wherever it is.&#x20;

{% hint style="info" %}
Note that the current executed code may not necessary be your code - you may end up in some third-party-library or in original Java class implemention. To get to your code, use the techniques described in the _Stepping_ chapter.
{% endhint %}

{% hint style="info" %}
Sometimes it can happen that the calculation pauses somewhere "between" the commands being executed. Then you only need to press F7 once (see the chapter on stepping) and the program continues to the next command to be executed - it will be marked in green.
{% endhint %}









