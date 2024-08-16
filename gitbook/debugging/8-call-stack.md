# 8 Call Stack

Another of the basic concepts when debugging a program is monitoring the **call stack**. When the program starts, the `main()` method is executed first. This method then creates other objects, works with variables and calls their methods. In the object-oriented paradigm (at least from the point of view of a programmer), all code that is executed is in the instance or static methods of classes. No code can run "outside", ie. each command is nested in a particular method.&#x20;

Everything can be illustrated with a seemingly simple function call:

```java
public static void main(String[] args) {
  System.out.println("Test");
} 
```

Here:

1. Method `main()` calls a method...
2. ... `println()` of the `PrintStream`class, which calls a method...
3. ... `writeln()` of the `PrintStream` class, which calls a method...
4. ... `implWriteLn()` of the `PrintStream` class, which calls a method...
5. ... `write()` of the `BufferedWriter` class, which calls a method...
6. ... `implWrite()` of the `BufferedWriter` class, ... etc.

This overview can be achieved simply by repeatedly calling the _Step-into_ step above the above source code.&#x20;

The above action behaves like a _stack_. A stack is a structure that can hold a set of elements, where the elements are removed from the stack in the reverse order in which they were placed in. That is, the last element goes out first, and the first inserted element leaves as the stack last one. It can be noticed that this behavior is completely consistent with the principle of function/method calls. The parent function cannot end before the nesting function call finishes. That's why the technique of tracking function calls is called a **call stack**.

{% hint style="info" %}
Note that the _step-into_ the `PrintStream` and other classes are only possible, if you have disabled the stepping over specific classes - see the section related to the steps. Otherwise, the _step-into_ will step over this method call.
{% endhint %}

## Call Stack Trace in the Debugging

In IDEa, the call stack is visible in the _Debug_ window in the left column:

&#x20;/TODO Imgs/8-stack.jpg

So, in this window you can see the order how the functions were called. If some function(s) appears repeatedly, it is the case of the direct or indirect recursion.

### Navigating through the call stack

You can navigate between the current method calls by clicking on the requested function in the call stack view.&#x20;

By navigating you will not affect the execution state. The IDE will only show you the selected function source code, and changes the "view" context - this affects the Variables and Watches windows. This is important in cases when you have several functions using the same variable names. Variables and Watches windows only show the values w.r.t. to the currently selected call stack level in the calls stack window.

TODO Imgs/8-stack-select.jpg

### Reset Frame

If you click at the method in the call-stack-view via the context menu, you will get an option to _Reset Frame_. This causes aborting the current and all nested method calls and return to the state where the currently selected function were called.

TODO Imgs/8-reset-frame.jpg

Note that this will not rollback any sideeffect changes caused by previous execution of canceled frames.

### **Hiding frames from libraries**

In stepping, you can select classes/packages, which are never stepped in. This is typically used when you are calling some third-party library code and expect there is no issue there. With this behavour, you can aim on what is important and skip over the unrelevant code.

Similar functionality is available in stack trace. There, you can select let the IDE hides the function calls from third-party libraries. This feature is called **Hide frames from Libraries** and is available via the context menu over the call stack view. This behavior is "on" by default and you can see `X hidden frames` label in the case of skipping some frames.

&#x20;/TODO Imgs/8-stack-frames.jpg

## Call Stack Trace in the Exception Handling

The other, more common case when a programmer can see the stack trace, is the raised exception. The raised exception always contains an information about the current method calls tree in the moment of the exception invocation. This stack trace is typically printed together with the exception info:

```
C:\Users\vajgma91\.jdks\openjdk-22.0.2\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.1\lib\idea_rt.jar=58193:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.1\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\vajgma91\source\repos\7tetl-gitbook\sources\StackTracing\target\classes cz.osu.prf.kip.Main
Exception in thread "main" cz.osu.prf.kip.exceptions.BookPersisterException: Failed to read content of file R:\1980.bdb
	at cz.osu.prf.kip.BookPersister.readAllLines(BookPersister.java:141)
	at cz.osu.prf.kip.BookPersister.getByYear(BookPersister.java:108)
	at cz.osu.prf.kip.BookManager.addBook(BookManager.java:13)
	at cz.osu.prf.kip.Main.main(Main.java:10)
Caused by: java.nio.file.NoSuchFileException: R:\1980.bdb
	at java.base/sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:85)
	at java.base/sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:103)
	at java.base/sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:108)
	at java.base/sun.nio.fs.WindowsFileSystemProvider.newByteChannel(WindowsFileSystemProvider.java:234)
	at java.base/java.nio.file.Files.newByteChannel(Files.java:379)
	at java.base/java.nio.file.Files.newByteChannel(Files.java:431)
	at java.base/java.nio.file.spi.FileSystemProvider.newInputStream(FileSystemProvider.java:420)
	at java.base/java.nio.file.Files.newInputStream(Files.java:159)
	at java.base/java.nio.file.Files.newBufferedReader(Files.java:2902)
	at java.base/java.nio.file.Files.readAllLines(Files.java:3397)
	at java.base/java.nio.file.Files.readAllLines(Files.java:3438)
	at cz.osu.prf.kip.BookPersister.readAllLines(BookPersister.java:139)
	... 3 more

Process finished with exit code 1
```

The output above tells us that the program crashed:

* Due to exception `BookPersisterException`
* ... with message "Failed to read content of file R:\1980.bdb"
* ... in class `BookPersister`, method `` readAllLines()` ``
* ... invoked by the method `getByYear()` of the same class
* ... invoked by the method of `addBook()` of the `BookManager` class
* ... invoked by the `Main.main()` method. Moreover, there is&#x20;
* ... a nested exception of type `NoSuchFileException` with the message "R:\1980.bdb"
* ... and the (more complex) stack trace of the inner exception.

{% hint style="info" %}
Note that the exception chaining is a very important mechanism and affects how the call stack is displayed.&#x20;
{% endhint %}

Whenever an exception is thrown (via the `throw` keyword), it collects its current call stack. If you are catching an exception exA using `catch` keyword to produce your new, encapsulating exception exB, you will have two stack traces - exA will keep its original call stack trace at the moment of its throw, exB will have a call stack trace w.r.t. to the encapsulating throw.

```java
try {
  doSomething();
} catch (Exception exA) {
  Exception exB = new Exception("Message", exA);
  throw exB;
}
```

If you simply throw your custom exception or rethrow the captured exception without encapsulation, you will lose the original stack trace. Therefore, you should strictly avoid this technique.

```java
try {
  doSomething();
} catch (Exception exA) {
  Exception exB = new Exception("Message");
  throw exB; // ! ! ! don't do this, exA info is lost ! ! !
}
```

```java
try {
  doSomething();
} catch (Exception exA) {
  throw exA; // dont do this, exA original call stack trace is lost ! ! !
}
```

## Custom Stack Trace Print

Sometimes it may be convenient to separately log function calls including the call stack.&#x20;

For example, a programmer may be interested in all the ways in which the `z()` method from the above example has ever been called. Unfortunately, there is no easy way to find this out. However, exceptions can be made. First, we modify the call to the `z()` method:

```java
private static int z(int i) {
  printTrace(new Throwable());
  int a = i * 4;
  a = a + z(a);
  return a;
} 
```

The method creates a new exception (but **does not throw it with the throw keyword!**) and passes it as a parameter to the `printTrace()` method. This method retrieves the call stack from the exception. However, since the call stack exception cannot return as a `String` instance, a stream must be created into which the value is written and only then the text is read from the stream as a `String` and can then be written/saved/whatever:

```java
private static void printTrace(Throwable throwable) {
  StringWriter sw = new StringWriter();
  java.io.PrintWriter pw = new java.io.PrintWriter(sw);
  throwable.printStackTrace(pw);
  
  String stackTrace = sw.toString();
  
  System.out.println(stackTrace);
} 
```

The disadvantage of this procedure is that the exception information is displayed at the beginning of the stack dump:

```
run:
java.lang.Throwable
	at callstackdemoapp.CallStackDemoApp.z(CallStackDemoApp.java:38)
	at callstackdemoapp.CallStackDemoApp.y(CallStackDemoApp.java:34)
	at callstackdemoapp.CallStackDemoApp.x(CallStackDemoApp.java:29)
	at callstackdemoapp.CallStackDemoApp.main(CallStackDemoApp.java:25)

BUILD SUCCESSFUL (total time: 0 seconds)

```

The second variant is access via individual items of the stack - the user thus has much more control over the generated code, but on the other hand, this approach is more laborious:

<pre class="language-java"><code class="lang-java">private static void printTrace2(Throwable throwable) {
    
  StackTraceElement [] arr = throwable.getStackTrace();
    
  StringBuilder sb = new StringBuilder();
  for (StackTraceElement ste : arr){
    sb.append(ste.getClassName());
    sb.append(".");
    sb.append(ste.getMethodName());
    sb.append("(...)\r\n\t/in ");
    sb.append(ste.getFileName());
    sb.append(":");
    sb.append(ste.getLineNumber());
    sb.append("\r\n");
  }
    
  String stackTrace = sb.toString();
  
  System.out.println(stackTrace);
<strong>} 
</strong></code></pre>

... and the output:

```
run:
callstackdemoapp.CallStackDemoApp.z(...)
	/in CallStackDemoApp.java:38
callstackdemoapp.CallStackDemoApp.y(...)
	/in CallStackDemoApp.java:34
callstackdemoapp.CallStackDemoApp.x(...)
	/in CallStackDemoApp.java:29
callstackdemoapp.CallStackDemoApp.main(...)
	/in CallStackDemoApp.java:25

BUILD SUCCESSFUL (total time: 0 seconds)
```









