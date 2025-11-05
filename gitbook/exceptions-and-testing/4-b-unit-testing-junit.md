# 4-b Unit Testing - JUnit

Source code for this chapter is available at [https://github.com/Engin1980/7tetl-unit-testing-tutorial](https://github.com/Engin1980/7tetl-unit-testing-tutorial).

Perfect — here’s a draft for **Section 1: “What is a Unit Test and Why We Write Them”** written in clear, student-friendly English, suitable for a GitBook-style tutorial.\
I’ve aimed for 4 concise paragraphs that introduce the topic, motivate it, and explain the key idea without jargon.

***

## Unit Testing

### What and Why

When we write code, we usually focus on making it _work_. But how do we know that it really works — not only today, but also next week, after we change something? This is where _unit testing_ comes in. A **unit test** is a small piece of code that checks whether a specific part of your program (a _unit_) behaves correctly. Usually, a unit means one class or one method.

Unit testing is like having a safety net. Every time you modify your program, you can run your tests to make sure that the changes didn’t break anything that was already working. Without tests, bugs can easily appear in unexpected places, and you might not notice until much later — often when it’s much harder to fix them.

Another important reason for writing tests is **confidence**. When your program is covered by unit tests, you can experiment and refactor code more freely, because you’ll quickly know if something stops working. This helps you become a better developer: testing makes you think about how your code is structured, what it depends on, and how to design it so that it’s easier to test and maintain.

Finally, testing is a key part of _professional_ software development. In real projects, teams use automated tests to ensure that every new feature or bug fix doesn’t break the rest of the system. Learning how to write good unit tests now will save you a lot of time and frustration later — and it’s one of the most valuable habits a programmer can develop.

### JUnit

JUnit is the most popular framework for writing and running unit tests in Java. It provides a simple way to create tests, organize them, and automatically check if your code behaves as expected. Instead of manually running your program and checking the output, JUnit allows you to write small test methods that verify specific functionality. It’s fast, reliable, and widely used in both learning and professional environments.

Here is a very simple example. Suppose we have a class `Calculator` with a method `add`:

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

Using JUnit, we can write a test to check if `add` works correctly:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void testAdd() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }
}
```

In this example, the `@Test` annotation marks the method as a test, and `assertEquals` checks that the result of `calc.add(2, 3)` is `5`. If it is, the test passes; if not, it fails. This is the basic idea of unit testing with JUnit: small, automated checks that give you confidence your code works correctly.

#### When a Test Passes or Fails

A unit test **passes** when all assertions inside the test method are satisfied — that means every `assertEquals`, `assertThrows`, or other check returns the expected result without throwing an unexpected exception. For example, if you expect `2 + 3` to equal `5`, and the method actually returns `5`, the test passes.

A test **fails** when at least one assertion does not match the expected result or an unexpected exception is thrown. For instance, if a method returns `6` instead of `5`, or if a division by zero occurs but your test didn’t expect it, the test fails. When a test fails, JUnit provides a clear output showing the expected value versus the actual value, or the exception that occurred. This feedback helps you quickly identify what went wrong in your code.

## Setting Up a Java Project

Before writing unit tests, you need to create a Maven project in IntelliJ IDEA and add the JUnit library. Maven helps you manage dependencies automatically, which makes adding JUnit very simple.

#### Step 1: Create a Maven Project in IntelliJ IDEA

1. Open IntelliJ IDEA → **New Project** → **Maven** → **Next**.
2. Fill in **GroupId** (e.g., `com.example`) and **ArtifactId** (e.g., `myapp`) → **Next** → **Finish**.
3. When selecting the Maven **archetype**, choose **maven-archetype-quickstart**. This is a simple archetype for a console application with the standard Java project structure.
4. IntelliJ will create the project with a `pom.xml` file.

#### Step 2: Add JUnit to `pom.xml`

Open `pom.xml` and add the following dependency inside the `<dependencies>` section:

```xml
<dependencies>
    <!-- JUnit 5 dependency -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Save the file. IntelliJ will automatically download the JUnit library. If it doesn’t, right-click the project → **Maven** → **Reload Project**.

#### Step 3: Create a Test Folder

If the `src/test/java` folder doesn’t exist, you need to create it manually:

1. Right-click on `src` → **New → Directory** → name it `test/java`.
2. Mark it as **Test Sources Root**: Right-click → **Mark Directory as → Test Sources Root**.

Now your project has a proper place for all unit test classes.

#### Step 4: Configure and Run Tests

To run tests in IntelliJ:

1. Open the **Run/Debug Configurations** (top-right dropdown → **Edit Configurations**).
2. Click **+** → **JUnit**.
3. Set a name for the configuration (e.g., `All Tests`), and choose the **Test kind**:
   * `All in package` to run all tests in a package, or
   * `Class` to run a specific test class.
4. Apply and save the configuration.
5. Click **Run** to execute the tests. A green bar means all tests passed, a red bar indicates failures.

## Simple JUnit tests

Let’s have a simple **Calculator** class with addition and division, including protection against division by zero:

```java
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }
        return a / b;
    }
}
```

Now, we would like to validate, if the behavior is correct.

#### Simple asserting - `assertEquals`

Add `CalculatorTest` class into the test folder.

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void testAddition() {
        Calculator calc = new Calculator();

        // assertEquals(expected, actual, message)
        // "expected" must always be the first parameter!
        assertEquals(5, calc.add(2, 3), "2 + 3 should equal 5");
    }
}
```

Explanation of `assertEquals`:

* `expected`: the value you expect the method to return. **Always first** — this ensures that if the test fails, the output clearly shows the expected vs actual value.
* `actual`: the value returned by your method.
* `message` (optional): a string shown if the test fails, helping you understand the problem.

You can now run the test configuration to see the result.

### Testing Multiple Input Combinations

When writing tests, we should not focus on just a single combination of inputs. Instead, the goal is to cover as much of the **input domain** as possible — that is, all meaningful types of values that a method might receive. This includes typical, boundary, and exceptional cases. For example, when testing a division method, we should check not only regular values but also zero, negative numbers, and very large or very small values. Covering the full input domain increases our confidence that the method behaves correctly in all situations, not just under ideal conditions. To do so, we can use several techniques.

{% hint style="info" %}
Note that approaches mentioned below are the simple ones and used only for initial test set up. More advanced techniques will be explained later, in [#more-complex-techniques](4-b-unit-testing-junit.md#more-complex-techniques "mention") section.
{% endhint %}

When you want to test a method with **different combinations of input values**, you have two main approaches:

1. **One test method with multiple assertions**
   * You can put several `assertEquals` (or other assertions) in a single test method.
   * This is simple but has a downside: if one assertion fails, the rest of the test may not run, so you won’t see all failing cases at once.

```java
@Test
void testAdditionMultipleCases() {
    Calculator calc = new Calculator();

    assertEquals(5, calc.add(2, 3));
    assertEquals(0, calc.add(0, 0));
    assertEquals(-3, calc.add(-1, -2));
}
```

2. **Separate test methods for each case**
   * You can write a separate test method for each combination.
   * This approach is usually better for readability and for quickly identifying which specific case failed.

```java
@Test
void testAdditionPositiveNumbers() {
    Calculator calc = new Calculator();
    assertEquals(5, calc.add(2, 3));
}

@Test
void testAdditionZero() {
    Calculator calc = new Calculator();
    assertEquals(0, calc.add(0, 0));
}

@Test
void testAdditionNegativeNumbers() {
    Calculator calc = new Calculator();
    assertEquals(-3, calc.add(-1, -2));
}
```

When testing multiple input combinations, you can either put all assertions in a single test method or create separate test methods for each case. Using a single test method is quick and compact, making it suitable for very simple cases, but if one assertion fails, the remaining cases in that method might not run. Writing separate test methods for each combination is usually better for readability and maintainability, as it clearly shows which specific case fails and makes the tests easier to debug and extend in larger projects.

**Summary:**

* **Single test method**: quick and compact, good for very simple cases.
* **Multiple test methods**: preferred in real projects because it makes tests easier to read, maintain, and debug.

### Testing float-point results - \`assertEquals\` vs `double`

When working with `double` values in Java, you should never compare them using exact equality (`==`) because floating-point arithmetic can introduce tiny rounding errors. For example, a calculation that should mathematically equal `2.5` might actually produce `2.4999999997` or `2.5000000001` due to how numbers are represented in binary. To handle this, JUnit allows you to specify a **delta** value in `assertEquals(expected, actual, delta)` when asserting float-point numbers. The delta defines the maximum allowed difference between the expected and actual values for the test to still pass. In other words, JUnit checks whether the two numbers are “close enough” rather than exactly equal — the test passes if the absolute difference between them is smaller than or equal to the delta. This makes floating-point comparisons reliable even when minor precision errors occur.

```java
@Test
void testDivision() {
    Calculator calc = new Calculator();

    // For double values, provide a small delta due to rounding errors
    assertEquals(2.5, calc.divide(5, 2), 0.0001, "5 / 2 should be approximately 2.5");
}
```

* The **delta** parameter allows a small margin of error for floating-point comparisons.
* Using `==` with doubles can lead to false negatives due to tiny rounding differences.

#### Testing Exceptions&#x20;

In JUnit, exceptions are tested using the `assertThrows` method. It checks that a specific block of code throws the expected exception type. You provide two arguments: the expected exception class and a lambda expression containing the code that should throw it. For example:

```java
assertThrows(
  IllegalArgumentException.class, 
  () -> calculator.divide(5, 0));
```

In this case, the test passes if `calculator.divide(5, 0)` throws an `IllegalArgumentException`; otherwise, it fails. This allows you to verify that your methods handle invalid or exceptional inputs correctly.

In JUnit, the `assertThrows` method not only checks that an exception of the expected type is thrown, but it also **returns** that exception object, allowing you to perform additional checks — for example, verifying the exception message. This is useful when you want to ensure that the correct error message is displayed to the user or logged properly.

Here’s an example:

```java
IllegalArgumentException exception = assertThrows(
  IllegalArgumentException.class, 
  () -> calculator.divide(5, 0));

assertEquals("Division by zero is not allowed", exception.getMessage());
```

In this case, the test first verifies that the method throws an `IllegalArgumentException`. Then, it retrieves the exception object returned by `assertThrows` and checks that the message is exactly what you expect. This provides an extra level of precision in your tests and helps confirm that both the type and content of the exception are correct.

With this knowledge, we can implement test for our method `Calculator.divide(...)`:

```java
import static org.junit.jupiter.api.Assertions.assertThrows;

@Test
void testDivisionByZero() {
    Calculator calc = new Calculator();

    // assertThrows checks that the code throws the expected exception
    assertThrows(IllegalArgumentException.class, () -> {
        calc.divide(5, 0);
    }, "Division by zero should throw IllegalArgumentException");
}
```

* Now `divide()` throws an `IllegalArgumentException` instead of letting Java throw `ArithmeticException`.
* `assertThrows` verifies that the exception occurs as expected, which is important for validating your program’s error handling.

***

This example shows:

1. How `assertEquals` works and why **expected** comes first.
2. How to test integers and floating-point numbers.
3. How to test that a method correctly throws exceptions.

### Test Lifecycle Annotations

When writing multiple tests, it’s common that each test needs the same initial setup — for example, creating a new instance of a class to test. Instead of repeating this code inside every test method, we can use special JUnit annotations that control what happens **before and after** each test runs. The most commonly used is `@BeforeEach`, which marks a method that runs before every single test. This is a perfect place to initialize objects or prepare the test environment.

For example, instead of creating the `Calculator` object inside every test, we can prepare it once before each test runs:

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator(); // runs before every test
    }

    @Test
    void testAddition() {
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    void testDivision() {
        assertEquals(2.5, calc.divide(5, 2), 0.0001);
    }
}
```

Here, the `setUp()` method is called automatically by JUnit before each test, ensuring that `calc` is always initialized in a clean state. This keeps the tests independent — no test can accidentally affect another one.

***

| Annotation      | Runs When                   | Typical Use                                 | Notes              |
| --------------- | --------------------------- | ------------------------------------------- | ------------------ |
| **@BeforeEach** | Before **each** test method | Initialize objects or reset state           | Runs once per test |
| **@AfterEach**  | After **each** test method  | Clean up resources, close files, reset data | Runs once per test |
| **@BeforeAll**  | Once **before all** tests   | Set up shared resources, configuration      | Must be `static`   |
| **@AfterAll**   | Once **after all** tests    | Release shared resources, clean up          | Must be `static`   |

These lifecycle annotations make your tests cleaner, more readable, and easier to maintain, especially as your test suite grows.&#x20;

## More complex techniques

For more complex techniques, we will use simple following classes in the project:

```java
package cz.osu.prf.kip.validators;

public class ArgumentAsserts {
  public static void isNotNull(Object value, String argumentName){
    if (value == null)
      throw new IllegalArgumentException(argumentName + " is null.");
  }
  public static void isNotNullOrWhitespace(String value, String argumentName){
    if (value == null || value.trim().isEmpty())
      throw new IllegalArgumentException(argumentName + " is empty string.");
  }
}
```

The `ArgumentAsserts` class provides simple static methods for validating input arguments. The `isNotNull` method checks whether an object is not `null` and throws an `IllegalArgumentException` with the argument name if it is. The `isNotNullOrWhitespace` method additionally checks whether a string is not empty or composed only of whitespace, throwing an exception for invalid input as well. It serves as a quick way to protect methods from invalid arguments.

```java
package cz.osu.prf.kip.model;

import cz.osu.prf.kip.validators.ArgumentAsserts;

public class AppUser {
  private final String userName;
  private final String password;
  private final boolean isAdmin;

  public AppUser(String userName, String password, boolean isAdmin) {
    ArgumentAsserts.isNotNullOrWhitespace(userName, "userName");
    ArgumentAsserts.isNotNull(password, "password");

    this.userName = userName;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public boolean isAdmin() {
    return isAdmin;
  }
}
```

The `AppUser` class represents an application user with immutable fields `userName`, `password`, and `isAdmin`. The constructor uses `ArgumentAsserts` to ensure that the username is not empty and the password is not `null`, guaranteeing a valid user instance. It provides standard getters for all three attributes, and the `isAdmin` flag indicates whether the user has administrative rights. Overall, it is a simple user model with input validation.

```java
package cz.osu.prf.kip.repositories;
import cz.osu.prf.kip.model.AppUser;
import java.util.List;

public interface AppUserRepository {
  void addUser(AppUser user);

  void deleteUser(String userName);

  List<AppUser> getUsers();

  boolean isUserNameTaken(String userName);
}
```

The `AppUserRepository` interface defines basic operations for managing application users. It allows adding users (`addUser`), deleting them by username (`deleteUser`), retrieving a list of all users (`getUsers`), and checking whether a username is already taken (`isUserNameTaken`). It serves as an abstraction of user storage without specifying a concrete implementation.

```java
package cz.osu.prf.kip.services;

import cz.osu.prf.kip.model.AppUser;
import cz.osu.prf.kip.repositories.AppUserRepository;
import cz.osu.prf.kip.validators.ArgumentAsserts;

import java.util.Optional;

public class AppUserService {
  private final AppUserRepository appUserRepository;

  public AppUserService(AppUserRepository appUserRepository) {
    ArgumentAsserts.isNotNull(appUserRepository, "appUserRepository");
    this.appUserRepository = appUserRepository;
  }

  Optional<AppUser> tryGetUserByCredentials(String username, String password) {
    Optional<AppUser> ret = appUserRepository.getUsers().stream()
        .filter(q -> q.getUserName().equals(username) && q.getPassword().equals(password))
        .findFirst();
    return ret;
  }

  public void createUser(String username, String password) {
    AppUser appUser = new AppUser(username, password, false);
    if (appUserRepository.isUserNameTaken(username)) {
      throw new IllegalArgumentException("Username is already taken");
    }
    appUserRepository.addUser(appUser);
  }
}
```

The `AppUserService` class provides logic for managing users on top of the `AppUserRepository` abstraction.

* The constructor ensures that the repository is not `null`.
* The `tryGetUserByCredentials` method searches for a user by username and password and returns it as an `Optional`.
* The `createUser` method creates a new user with default administrative rights set to `false` and ensures that the username is not already taken; otherwise, it throws an exception.

Overall, the class ensures safe and consistent operations on users.

### Simple tests

We wil start with the `ArgumentAsserts` tests. The basic simple tests may look like this:

```java
package cz.osu.prf.kip.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentAssertsTest {

  @Test
  void isNotNull_Success() {
    ArgumentAsserts.isNotNull(1, "number");
  }

  @Test
  void isNotNull_Failure() {
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      ArgumentAsserts.isNotNull(null, "param");
    });
    assertTrue(ex.getMessage().contains("param"));
    assertTrue(ex.getMessage().toLowerCase().contains("null"));
  }

  @Test
  void isNullOrWhitespace_Success() {
    ArgumentAsserts.isNotNullOrWhitespace("dudla", "param");
  }
}
```

### Parametrized Tests

From the example above, we can see that to validate multiple inputs, we need to write all of them into one unit test method, having multiple asserts, or we need to create multiple testing methods, one for every input combination.

Or, we can use parametrized test.

To do so, we will need one more dependency:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>5.14.0</version>
    <scope>test</scope>
</dependency>
```

There are three basic approaches:

* simple parametized test;
* csv-value-based parametrized test;
* parametrized test based on method as a data generator.

#### Simple Parametrized Test

To do so, the only thing we need is to define a set of simple values, which will be passed into the parametrized test:

```java
// ...
class ArgumentAssertsTest {
  // ...
  @ParameterizedTest
  @ValueSource(strings = {"a", "bas", "asef", "byuoasmef", " fasef af fasef"})
  void isNullOrWhitespace_SuccessMulti(String param) {
    ArgumentAsserts.isNotNullOrWhitespace(param, "param");
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\t \t", "     "})
  void isNullOrWhitespace_FailureMulti(String param) {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> ArgumentAsserts.isNotNullOrWhitespace(param, "param"));
  }
}
```

To do so, we:

* need to change the annotation from `@Test` to `` @ParametrizedTest` ``;
* add an annotation for values - `@ValueSource` with defined values of the required type.

Once executed, such method will be invoked separately for every input value in the `@ValueSource` annotation.

#### Csv-Source Parametrized Test

If more values need to be specified for the test run, we can define them in the CSV format using `@CsvSource` annotation:

```java
// ...
class ArgumentAssertsTest {
  // ...
  @ParameterizedTest
  @CsvSource({
      "a, true",
      "aa, true",
      " , false",
      "\t, false",
      "   , false"
  })
  void isNullOrWhitespace_FailureMultipleWithCsv(String param, boolean result) {
    if (!result)
      isNullOrWhitespace_FailureMulti(param);
    else
      isNullOrWhitespace_SuccessMulti(param);
  }
}
```

Every line is a set of values separated by comma (`,`) in the CSV format. The order and data type of values must match the method parameters.

Then, in the implementation, we can adjust test behavior w.r.t. to the input data.

#### Parametrized test based on a method as a data generator

One powerful way to supply test data is by using a **method source** that returns a stream of arguments. You mark the test with `@ParameterizedTest` and use `@MethodSource` to reference the data provider method. That method must be `static` and return a `Stream<Arguments>`. Each `Arguments` object represents one set of parameters passed to the test.

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    private final Calculator calc = new Calculator();

    static Stream<Arguments> additionData() {
        return Stream.of(
            Arguments.of(2, 3, 5),
            Arguments.of(0, 0, 0),
            Arguments.of(-1, -2, -3)
        );
    }

    @ParameterizedTest
    @MethodSource("additionData")
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }
}
```

### Property Based Testing -- jqwik

Property-based testing (PBT) is a type of testing that differs from classic _example-based testing_ (like JUnit), where you write specific inputs and expected outputs. Instead, you define **properties** that should hold for **all possible inputs**, and the testing framework generates many random inputs to check these properties.

In Java, there are several frameworks providing this behavior. We will introduce **jqwik**. The main framework properties are:

1.  **Defining a property:**\
    In `jqwik`, you mark a method with the `@Property` annotation. This method contains assertions that should hold for all inputs.

    ```java
    import net.jqwik.api.*;

    class ExampleTest {
        @Property
        boolean reverseTwice(@ForAll String str) {
            return str.equals(new StringBuilder(str).reverse().reverse().toString());
        }
    }
    ```

    * `@ForAll` tells jqwik to generate random values for the argument `str`.
    * The test checks the property: “If you reverse a string twice, you get the original string.”
2. **Input generation:**\
   Jqwik automatically generates various values for each type (e.g., int, String, List) and tests whether the property holds.
3. **Failure minimization (shrinking):**\
   If jqwik finds an input that violates the property, it tries to simplify it to the **smallest counterexample** to make reproducing the issue easier.
4. **Advantages:**
   * Finds edge cases you might not think of when writing specific tests.
   * Covers a wider range of inputs.
   * Works well for functions that must preserve invariants or satisfy certain general properties.

In short, jqwik lets you write property-based tests in Java, generate random inputs automatically, and discover scenarios where your logic might fail.

To work with **jqwik**, the dependency needs to be added to `pom.xml`:

```xml
<dependency>
    <groupId>net.jqwik</groupId>
    <artifactId>jqwik</artifactId>
    <version>1.9.3</version>
    <scope>test</scope>
</dependency>
```

Then, the test needs to be annotated with `@Property` annotation; every input parameter with `@ForAll` annotation.

For example, let we check if the constructor of `AppUser` is correctly entering values into the respective fields. We don't want to write examples manually, not even specify corner cases. Let **jqwik** do this for us:

<pre class="language-java" data-line-numbers><code class="lang-java">package cz.osu.prf.kip.model;

import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;

public class AppUserTest {

  @Property
  public void ctorInitializingCorrectly(
      @ForAll String userName, 
      @ForAll String password, 
      @ForAll boolean isAdmin) {
<strong>    Assume.that(userName != null &#x26;&#x26; !userName.trim().isEmpty());
</strong><strong>    Assume.that(password != null);
</strong><strong>
</strong><strong>    AppUser appUser = new AppUser(userName, password, isAdmin);
</strong><strong>
</strong><strong>    Assertions.assertEquals(userName, appUser.getUserName());
</strong><strong>    Assertions.assertEquals(password, appUser.getPassword());
</strong><strong>    Assertions.assertEquals(isAdmin, appUser.isAdmin());
</strong><strong>  }
</strong><strong>}
</strong></code></pre>

Note that:

* at line 10 the method is annotated;
* at lines 12-14 the arguments are annotated.

Moreover, the `AppUser` constructor does not accepts null/whitespace usernames and null passwords. These cases must be cut out from the testing, otherwise the test will fail. To do so, we will use `Assume.that(...)` construct — see lines 15-16.

The output of such test may look like:

```
timestamp = 2025-10-14T23:36:29.324964400, AppUserTest:ctorInitializingCorrectly = 
                              |-----------------------jqwik-----------------------
tries = 1000                  | # of calls to property
checks = 902                  | # of not rejected calls
generation = RANDOMIZED       | parameters are randomly generated
after-failure = SAMPLE_FIRST  | try previously failed sample, then previous seed
when-fixed-seed = ALLOW       | fixing the random seed is allowed
edge-cases#mode = MIXIN       | edge cases are mixed in
edge-cases#total = 18         | # of all combined edge cases
edge-cases#tried = 18         | # of edge cases tried in current run
seed = 7364070255207688824    | random seed to reproduce generated values
```

Here you can see that:

* the test was executed 1000 times;
* the checks (`Assume.that(...)`) were valid for 902 cases;

Alternatively, you can specify the parameter behavior using the annotations, like:

```java
package cz.osu.prf.kip.model;

import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;

public class AppUserTest {

  @Property
  public void ctorInitializingCorrectly(
      @ForAll @AlphaChars @StringLength(min = 1, max = 30) String userName, 
      @ForAll @AlphaChars @StringLength(min = 8, max = 30) String password, 
      // @ForAll @IntRange(min = 0, max = 120) int age) - an example for int
      @ForAll boolean isAdmin) {
    // Assume.that(userName != null && !userName.trim().isEmpty());
    // Assume.that(password != null);
    // ...
  }
}
```

However, for more detailed explanation, see documentation.

{% embed url="https://jqwik.net/docs/current/user-guide.html" %}

### Mocking — Mockito

**Mocking** in unit testing is a technique used to simulate the behavior of real objects or components that a piece of code depends on. Instead of using the actual implementation — which might be slow, unpredictable, or have side effects like database access or network calls — a _mock object_ is created to mimic that dependency in a controlled way. This allows developers to isolate the unit under test and verify its behavior independently of external systems.

By using mocking, tests become faster, more reliable, and focused solely on the logic of the component being tested. Mocks can be configured to return specific values, throw exceptions, or record how they were used (e.g., which methods were called and with what arguments). This makes it possible to assert not only the results of the code but also its interactions with other components, leading to more precise and maintainable unit tests.

**Mockito** is a popular Java framework used for creating and managing mock objects in unit tests. It provides a clean and simple API that allows developers to define how mocked dependencies should behave — such as what values to return or which exceptions to throw — without writing complex boilerplate code. Mockito also enables verification of interactions, so you can check whether specific methods were called, how many times, and with what arguments. Because of its intuitive syntax and integration with testing frameworks like JUnit, Mockito has become one of the most widely used mocking tools in the Java ecosystem.

#### Dependencies

To add Mockito into the project, two (or three) more dependencies needs to be added into Maven:

```xml
<!-- Mockito Core -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.5.0</version>
    <scope>test</scope>
</dependency>

<!-- Mockito Jupiter integrace (for @ExtendWith(MockitoExtension.class)) -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.5.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.14.0</version>
    <scope>test</scope>
</dependency>
```

Dependencies are:

* mockito-core — for basic Mockito functionality;
* mockito-junit-jupiter — for Mockito integration with JUnit;
* junit-jupiter-engine — to enable JUnit engine support in JUnit. This dependency may already be in your `pom.xml` file as it is tightly integrated to JUnit testing. However, if missing, Mockito will not work.

#### Context

In our project, we have `AppUserService` providing operations over `AppUser` and using `AppUserRepository` to load/save users. However, we do not have the implementation for `AppUserRepository`, but still, we want to test `AppUserService`. Therefore, we will **mock** the repository instance into the service.

{% hint style="info" %}
Note that a real project will be probably based on `SpringBoot` framework using _dependency injection_ (DI) to inject repository instance into the service. In our case, we have a simple project without DI support, so we will inject the mock manually.\
However, in `SpringBoot` project you will also need to know `@InjectMock` annotation telling that mocks should be injected into the target object.
{% endhint %}

#### Implementation

Let's now build the `AppUserServiceTest` class piece by piece. The skeleton:

{% code lineNumbers="true" %}
```java
package cz.osu.prf.kip.services;

import cz.osu.prf.kip.model.AppUser;
import cz.osu.prf.kip.repositories.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

  @Mock
  private AppUserRepository appUserRepository;
  
  private AppUserService appUserService = null;

  private List<AppUser> users;
  // ...
}
```
{% endcode %}

Here:

* At line 20, we say that Mockito is extending tests in this class using mocks.&#x20;
* At line 21 we are saying that the `appUserRepository` instance at the following line will be injected with a mock object.
* So far, we are not specifyíng the behavior of the mock.
* `appUserService` and `users` fields will be filled later.

{% hint style="warning" %}
Note annotation at line 20. Without this line, no test will be recognized in the class (even with `@Test` annotation).
{% endhint %}

**Simple method test**

Let as now test a simple method returning an user by the username and password. We expect this user will exist in the repository, so expected result is `isPresent`.

```java
@Test
void tryGetUserByCredentials_success() {
  Optional<AppUser> user = appUserService.tryGetUserByCredentials("marek", "1234");
  Assertions.assertTrue(user.isPresent());
}
```

You can see that we are using `appUserService.tryGetUserByCredentials` method, which is internally calling `appUserRepository.getUsers()`. However, as `appUserRepository` is a mock, it has (so far) no idea what is the behavior of `getUsers()` method. So let us set one — it's called **a stub**:

{% code lineNumbers="true" %}
```java
@BeforeEach
public void setup() {
  users = new ArrayList<>();
  users.add(new AppUser("marek", "1234", true));
  users.add(new AppUser("tereza", "5678", false));
  users.add(new AppUser("petr", "7890", false));

  org.mockito.Mockito
      .doReturn(users)
      .when(appUserRepository).getUsers();

//  alternative way, preferred:
//    org.mockito.Mockito.when(appUserRepository.getUsers())
//        .thenReturn(users);

  this.appUserService = new AppUserService(appUserRepository);
}
```
{% endcode %}

We have used set-up method invoked before every test (see `@BeforeEach` annotation). In this method, we:

* fill up a list of users — lines 3-6;
* define the behavior for `appUserRepository.getUsers()` — lines 8-11 (or alternative style at 13-15);
* create a new instance of `appUserService`.

The command at line(s) 8+:

* do return a value  `users`
* ... when over `appUserRepository` mock
* ... method `getUsers()` is invoked.

{% hint style="info" %}
The above mentioned pattern is universal for every mock methods & behaviors in Mockito.

However, if some return is expected from the method, alternative (and in real life more common) way is used - see lines 13-15. This format cannot be used for methods returning `void`. Therefore, in this tutorial we will use the first approach only.
{% endhint %}

How the test can be invoked and the result can be tested.

Now is a time to do some improvements:

**AutoImplements**: Mockito automatically mocks all the methods with their mocks - even if you do not define any behavior. With no behavior, default implementations return default values from the methods (0 for int/long/double/..., null for classes, etc.).

If you are not ok with this behavior and would like to be notified once non-explicitly-mocked method is invoked, the mocking needs to be customized:

1. Remove the `@Mock` annotation from the repository; the object will not be mocked automatically.
2. Add explicit mock instance in the `setup()` method:

```java
appUserRepository = org.mockito.Mockito.mock(AppUserRepository.class, invocation -> {
  throw new UnsupportedOperationException(
      "Method without stub invoked: AppUserRepository." + invocation.getMethod().getName() + "(...)"
  );
});
```

This behavior will now throw an exception if non-stubbed method is invoked.

**Non-used stubs:** If you are setting up stubs for more test than one (like in `@BeforeEach` method, or globally in `@BeforeAll` ), it may happen that some test is not using all the stubs. In this case, Mockito by default invokes an exception that for performance reasons, unused stubs should be removed. In this case, you can:

1. Define stubs per test
2. Set stubs as lenient. Lenient stubs are not checked for usage.

The second approach can be achieved using `lenient()` method:

{% code lineNumbers="true" %}
```java
org.mockito.Mockito
    .lenient()
    .doReturn(users)
    .when(appUserRepository).getUsers();
```
{% endcode %}

Note that the second line was added to the previous declaration.&#x20;

Now, let us test the `createUser` method:

```
@Test
void createUser_success() {
  appUserService.createUser("john", "1234");
}
```

To do so, stubs for `addUser()` and also `isUserNameTaken()` methods need to be defined:

{% code lineNumbers="true" %}
```java
@BeforeEach
public void setup() {
  // ...

  org.mockito.Mockito
      .lenient()
      .doNothing()
      .when(appUserRepository).addUser(org.mockito.Mockito.any(AppUser.class));

  org.mockito.Mockito
      .lenient()
      .doAnswer(invocation -> {
        String userName = invocation.getArgument(0);
        boolean ret = users.stream().anyMatch(u -> u.getUserName().equals(userName));
        return ret;
      })
      .when(appUserRepository).isUserNameTaken(org.mockito.Mockito.any(String.class));

  this.appUserService = new AppUserService(appUserRepository);
}
```
{% endcode %}

The first stub is defined as:

1. Do nothing (and return nothing — void)
2. ... when over `appUserRepository` mock
3. ... method `addUser(...)` is invoked
4. ... with `any` parameter with instance of type `AppUser`

Similarly, the second stub says:

1. Do answer (return the value — wil be explained below)
2. ... when over `appUserRepository` mock
3. ... method `isUserNameTaken(...)` is invoked
4. ... with `any` parameter with instance of type `String`

The result - `answer` - will be obtained as:

1. Get the first argument from the method invocation — line 13,
2. Try to find existing user among all users — line 14
3. And return success/failure — line 15.

Basically, 4 basic methods can be used to get the required behavior:

* `doNothing` - is used for `void` function doing nothing and returning nothing as a result.
* `doReturn` - is used to return a simple, single value from the call. By simple value we mean value not dependent on the invocation input parameters.
* `doAnswer` — is used to return a value w.r.t. to the input parameters, or when more complicated stuff needs to be done during the invocation.
* `doThrow` — is used to throw an exception as a result of the invocation.

At the end, the complected code of the testing class, including one bonus test based on Csv-Testing:

```java
package cz.osu.prf.kip.services;

import cz.osu.prf.kip.model.AppUser;
import cz.osu.prf.kip.repositories.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

  //@Mock
  private AppUserRepository appUserRepository;

  private AppUserService appUserService = null;

  private List<AppUser> users;


  @BeforeEach
  public void setup() {
    users = new ArrayList<>();
    users.add(new AppUser("marek", "1234", true));
    users.add(new AppUser("tereza", "5678", false));
    users.add(new AppUser("petr", "7890", false));

    appUserRepository = org.mockito.Mockito.mock(AppUserRepository.class, invocation -> {
      throw new UnsupportedOperationException(
          "Method without stub invoked: AppUserRepository." + invocation.getMethod().getName() + "(...)"
      );
    });

    // alternative way:
//    org.mockito.Mockito.when(appUserRepository.getUsers())
//        .thenReturn(users);
    org.mockito.Mockito
        .lenient()
        .doReturn(users)
        .when(appUserRepository).getUsers();

    org.mockito.Mockito
        .lenient()
        .doNothing()
        .when(appUserRepository).addUser(org.mockito.Mockito.any(AppUser.class));

    org.mockito.Mockito
        .lenient()
        .doAnswer(invocation -> {
          String userName = invocation.getArgument(0);
          boolean ret = users.stream().anyMatch(u -> u.getUserName().equals(userName));
          return ret;
        })
        .when(appUserRepository).isUserNameTaken(org.mockito.Mockito.any(String.class));

    this.appUserService = new AppUserService(appUserRepository);
  }

  @Test
  void tryGetUserByCredentials_success() {
    Optional<AppUser> user = appUserService.tryGetUserByCredentials("marek", "1234");
    Assertions.assertTrue(user.isPresent());
  }

  @Test
  void createUser_success() {
    appUserService.createUser("john", "1234");
  }

  @ParameterizedTest
  @CsvSource({
      "honza, 1234, False",
      "marek, 4543, True",
      "tereza, 1234, True",
      "petr, 2172, True",
      "jonatan, 1141, False"
  })
  void createUser_multiple(String username, String password, boolean shouldFail) {
    if (shouldFail) {
      Assertions.assertThrows(IllegalArgumentException.class, () -> appUserService.createUser(username, password));
    } else {
      appUserService.createUser(username, password);
    }
  }
}
```
