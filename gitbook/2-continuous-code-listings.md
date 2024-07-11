---
description: >-
  This page explains a technique of continuous code listings and its pros and
  cons.
---

# 2 Continuous Code Listings

he technique of continuous listings is one of the simplest techniques in which a programmer can follow the flow of a program and the values ​​of variables. It is an approach that requires a minimum of knowledge of the programming language under investigation, does not make great demands on the development environment, and is therefore particularly suitable in cases of:&#x20;

* When the programmer follows foreign source code in which he does not normally work;
* When the programmer examines the source code in a programming language that he does not directly know (but we assume that it is a language similar in terms of syntax and used paradigms (for example, in the Java - C# - C++ - ... group);
* When the programmer does not have a suitable development environment available, or cannot work with it (he must only be able to perform basic operations – write a command and then compile and run the program after writing the command).&#x20;

These situations occur when editing the source codes of colleagues who are not present and for which it is necessary to suddenly correct a simple error. It is not suitable for complex source code examination because:&#x20;

* By embedding continuous statements in the code, I am changing it and there is a risk that:&#x20;
  * By inserting the statement, we cause a new error in the program that was not there before;&#x20;
  * We forget to remove all inserted statements, and after removing the error, the program fails to return to its original (albeit corrected) state;&#x20;
* It is very time-consuming in case of complex programs and if necessary to understand the source code in detail.&#x20;

The basis of this technique is to add custom lines to the existing source code with the aim of:&#x20;

* Monitor program execution, calls, and termination of functions and evaluation of conditions;&#x20;
* Monitor variable values ​​and their changes during program runtime.&#x20;

As you can see, the basic requirement is for the program to run, because without the program running, the listings cannot be viewed. It is therefore absolutely necessary to be able to translate the program without syntax errors, even at the cost of marking a certain, unexamined block as a comment (and thus actually skipping its functionality).&#x20;

The aim of the statement is in particular:

* Output on the console, which the programmer monitors and evaluates after starting the program;&#x20;
* Output to a file, which the programmer can then open and monitor the result of the program.&#x20;

The source of the statements is typically determined by the amount of data being expressed and possibly other requirements. However, if the application monitors a large amount of data and it is necessary to create a large number of continuous statements (for example, monitoring the value of a variable in a cycle that runs 500 times), it is necessary to make these statements to a file, because the console output will be confusing, or not traceable at all.&#x20;

The mentioned advantage of this procedure is that the programmer can only work in a language similar to the one in which he is used to working. He must be able to know and understand the principles of this language (cycles, conditions, declarations, and definitions of variables, methods, objects, etc.), but to perform testing using continuous statements, he needs to know "how to write the text and value of a variable on the console", or " how to write the text and value of a variable to a file".

In the Java language environment, we can suffice with the command definition:

```java
System.out.println("Výpis"); 
```

To write to a file, you just need to find a page on the Internet that describes writing to a file as simply as possible, or add a function somewhere that we will call and that will solve the whole problem for us:

```java
private static void logToFile(String text) {
  Path filePath = Path.of("logfile.log");
  try {
    Files.writeString(
      filePath, 
      line + System.lineSeparator(), 
      StandardOpenOption.CREATE, 
      StandardOpenOption.APPEND);
  } catch (IOException ex) {
    throw new RuntimeException("Failed to write LOG to file.", ex);
  }
} 
```

We will take this (or any other similar) code from the website and then just test whether it works and can be used:

```java
logToFile("výpis"); 
```

In conclusion, it is important to repeat a very important assumption - **the tested code must not contain syntax errors**. The entire procedure is based on the principle that the application is repeatedly launched and the programmer monitors the changes made. If the application contains syntax errors, it cannot be run and the listings cannot be observed in action. It is often better to comment out a certain part of the code and ignore it so that the application can run.

## Basic use of continuous statement techniques

As mentioned, runtime statements can be used to monitor the flow of a program and to monitor the values ​​of variables.

### Monitor the flow of a program

In the first case, i.e. when tracing the flow of code, the tracing statements are mainly placed at the:

* Start of function – to have feedback that the function has been called;&#x20;
* End of the function – to have feedback that the function has run until the end of the block or the `return` command;
* To the beginning of blocks (after the opening brace {)
  * Cycles – for recording how many times the cycle has taken place&#x20;
  * Conditional blocks of if, else, or switch – to find out whether the condition was met and the block was executed or not.&#x20;

A simple example follows. We will check whether all required indexes within a certain range are processed during the program run. For source code:

```java
int max = 9;
    
int half = max / 2;

for (int i = 0; i <= half; i++) {
 // do something
}
   
for (int i = half; i < max; i++) {
  // do something
} 
```

… we want to monitor whether all values ​​0-9 are processed after running through both cycles. Therefore, a sequence listing the current state of the value of the `current` variable can be simply added to each cycle - see lines 6, 11.

{% code lineNumbers="true" %}
```java
int max = 9;
    
int half = max / 2;

for (int i = 0; i <= half; i++) {
 System.out.println("\t### first);   
 // do something
}
    
for (int i = half; i < max; i++) {      
  System.out.println("\t### second");      
  // do something
} 
```
{% endcode %}

After running the program, you can view the output:

```
    ### first
    ### first
    ### first
    ### first
    ### first
    ### second
    ### second
    ### second
    ### second
    ### second
```

From the output it can be noticed that the run will not run correctly as a total of 10 lines will be output.

### Monitor the value of variables

In the second case, i.e. when monitoring the values ​​of variables, the monitoring statements are placed arbitrarily, but again in particular: • After assignment to a variable – to find out the new value of the variable; • To the end of the corresponding block – to find out whether the value of the variable has not changed until the end of the block; • Anywhere in the code – typically when looking for an unexpected variable change. Consider a simple class representing a person.

```
class Person {
  ...
  public Person(String name, int birthYear) { . . . }
  
  public String getName() { . . .  }

  public int getBirthYear() { . . .  }
  
  public int getAgeInYear(int currentYear){ . . . }
  
} 

```

Since we are concerned with a simple overview of the class content, we ignore all private declarations (i.e. private/protected variables and methods, and in addition, we only notice the headers of the methods, we do not look at the method code yet. We see that the class representing the person considers the first name, last name and a method that, by name, returns the person's age in years from the given value. However, the class does not work as it should, there is a chaotic behavior of the year of birth of the person when working with the code.

```java
Person p = new Person("Michal", 1980);

System.out.println("Name: " + p.getName());
System.out.println("Birth Year: " + p.getBirthYear());

int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
System.out.println("Age this year: " + p.getAgeInYear(currentYear));

System.out.println("Name: " + p.getName());
System.out.println("Birth Year: " + p.getBirthYear());
```

This block returns the results when executed: Name: Michal Year of birth: 1980 Age this year: 33 Name: Michal Year of birth: 396 So the year of birth changed somewhere during the calculation and it is not clear why, because the source code is not explicitly supposed to do that. Since a change can generally occur anywhere, it is necessary to extend the source code with continuous statements in order to find the reason for the change. Statements are added from the point where we are still sure that the value is returned correctly.

```java
Person p = new Person("Michal", 1980);

System.out.println("Name: " + p.getName());
System.out.println("Birth Year: " + p.getBirthYear());
System.out.println("\t### 1 - year of birth: " + p.getBirthYear());

int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
System.out.println("\t### 2 - year of birth: " + p.getBirthYear());

System.out.println("Age this year: " + p.getAgeInYear(currentYear));
System.out.println("\t### 3 - year of birth: " + p.getBirthYear());

System.out.println("Name: " + p.getName());
System.out.println("\t### 4 - year of birth: " + p.getBirthYear());

System.out.println("Birth Year: " + p.getBirthYear());
System.out.println("\t### 5 - year of birth: " + p.getBirthYear());
```

Output obtained:

```
Name: Michal
Year of birth: 1980
  ### 1 - year of birth: 1980
  ### 2 - year of birth: 1980
Age this year: 33
  ### 3 - year of birth: 396
Name: Michal
  ### 4 - year of birth: 396
Year of birth: 396
  ### 5 - year of birth: 396
```

This output shows that the change will take place between statement 2 and 3, i.e. in the statement:

```java
System.out.println("Age this year: " + p.getAgeInYear(currentYear));
```

The listing may look different, for example like this:

```
Name: Michal
Year of birth: 1980
  ### 1 - year of birth: 396
  ### 2 - year of birth: 396
Age this year: 33
  ### 3 - year of birth: 396
Name: Michal
  ### 4 - year of birth: 396
Year of birth: 396
  ### 5 - year of birth: 396
```

Such a state would indicate that the error occurred already after obtaining the value, i.e. already in the command:

```java
System.out.println("Birth Year: " + p.getBirthYear());
```

..., when the value was correct when written, but subsequently changed. Through testing, we found out where more closely, i.e. inside which methods we should look for the error. Such changes typically take place through incorrect programming of methods, when the methods perform an unwanted so-called side effect, that is, they change the states of objects, even if they do not have them. An example of bad programming and the occurrence of the first error is when the programmer incorrectly stored the result in a variable representing the state of the object.

```java
class Person {
  private String name;
  private int birthYear;

  // ... lines omitted for simplicity
  
  public int getAgeInYear(int currentYear){
    birthYear = (currentYear - birthYear) * 12;
    return birthYear / 12;
  }  
} 
```

## Example I

TODO change names

Programmer Tomáš assigned programmer Zdenek a task where he needs to create classes implementing the following behavior:

* There is a class representing the student. A student has a first name, last name, and a set (ie array, collection) of grades.
* There is a class representing a set (ie array, collection) of students. It must be fulfilled, that there are simple methods for inserting another student into this set, as well as getting a student by index and removing a student. The rule applies to adding a student (in both cases, the addition is simply skipped):
  * It is not possible to add a student who has an empty or null name and surname at the same time.
  * Cannot add a student who has the same first and last name as a student in the set already added.&#x20;

After some time, the programmer Zdeněk hands Tomáš the files with the classes that everything is created and working. Tomáš decides to check the submitted implementation, but he does not want to check everything after Zdenek (i.e. go through and learn his source code completely), he just wants to get a quick overview and test whether the specified behavior conditions work.&#x20;

The submitted solution contains 4 files:

* Mark.java&#x20;
* MarkCollection.java&#x20;
* Student.java&#x20;
* StudentCollection.java

Compiling with an empty `main()` method, Tomáš finds that the source code contains no syntax errors, which is important because, as mentioned, you cannot use continuous statement testing with syntax errors.&#x20;

For now, Tomáš will focus only on the area of ​​students. In class, the student will focus on important areas. The important areas for him are the **public** members of the class because those are the ones he will be able to work with from his source code. He will not deal with private members, because he cannot and will not use them.

```java
public class Student {

  // some private code

  public Student(String name, String surname) { . . . }
  public String getName() { . . . }
  public String getSurname() { . . . }
  public MarkCollection getMarks(){ . . . }
} 
```

Zdenek found a public constructor and getter methods to get the first name, last name and list of grades.&#x20;

{% hint style="info" %}
_Getter_ metody jsou metody, které vrací hodnotu nějaké instanční proměnné. Například, pro proměnnou `name` je její getter metoda `getName()`. Pro více info viz například [https://www.w3schools.com/java/java\_encapsulation.asp](https://www.w3schools.com/java/java\_encapsulation.asp).
{% endhint %}

So we can test the basic behavior (we repeat again that we will skip the grading area for now).

```java
Student s = new Student("Michal", "Nový");
System.out.println("Name: " + s.getName());
System.out.println("Surname: " + s.getSurname()); 
```

... produces output:

```
Name: Michal
Surname: Nový
```

The obtained output corresponds to the input, so this part seems to work correctly.&#x20;

{% hint style="warning" %}
For a closer look, it would be useful to check the behavior when entering non-standard values, such as the empty string ("") and the null value. Only a simple test of the functionality is never sufficient.
{% endhint %}

Another interesting class is the StudentCollection class representing a set of students.

```java
public class StudentCollection {  
  // some private code
  public boolean add(Student student){ ... }  
  public Student get(int index){ ... }
  public void remove (Student student){ ... }
  public int size(){ ... }
  // ...
} 
```

Tomáš found the methods and deduces their behavior:

* Add  – to add a new element/student
* Get – to retrieve a student from a recordset by numeric index
* Remove – to remove an element/student from the recordset
* Size – returns the number of elements/students in the list

Tomáš can again perform a simple verification of behavior with respect to the specified requirements. First, it tries to insert the student. It first creates a variable for the set of students, then inserts an instance into it. Then it inserts a student into this set using the `add()` method, and finally it needs to find out if the student is in the collection and if it is not in it, for example, multiple times - so it checks the contents of the collection using a `for` loop.

```java
Student s = new Student("Michal", "Nový");

// create variable to hold a collection of students
StudentCollection students = new StudentCollection();
    
// add first student
students.add(s);

// print all students in the collection
for (int i = 0; i < students.size(); i++) {
  Student pom = students.get(i);
  System.out.println(pom.getName() + " " + pom.getSurname());
} 
```

The obtained output is:

```
Michal Nový
```

So far, everything seems to be working properly. So it tries to insert a second student:

```
    . . .
    s = new Student("Jan", "Rychlý");
    students.add(s);
    
    // výpis všech studentů v kolekci
    for (int i = 0; i < students.size(); i++) {
      Student pom = students.get(i);
      System.out.println(pom.getName() + " " + pom.getSurname());
    } 

```

And the obtained output is:

```
Michal Nový
Jan Rychlý
```

Everything looks fine here too. So they will try to insert a student who will only have a surname without a first name (he should be inserted) and also a student who will have neither a first name nor a surname (he should not be inserted).

```java
. . . 
s = new Student(null, "Zdobný");
students.add(s);
s = new Student(null, "");
students.add(s);
    
// print all students in the collection
for (int i = 0; i < students.size(); i++) {
  Student pom = students.get(i);
  System.out.println(pom.getName() + " " + pom.getSurname());
} 
```

```
Michal Nový
Jan Rychlý
null Zdobný
```

Everything is fine this time too. The following is an attempt to insert a student with the same name (before that, we remove from the source code the statement adding the null student "Stylish" so that the listings shown here are not too long). To be sure, a full listing follows again.

```java
Student s = new Student("Michal", "Nový");

// create a new empty collection of students
StudentCollection students = new StudentCollection();

students.add(s);

s = new Student("Jan", "Rychlý");
students.add(s);

// add a new student with the same name
s = new Student("Jan", "Rychlý");
students.add(s);

// print all students in the collection
for (int i = 0; i < students.size(); i++) {
  Student pom = students.get(i);
  System.out.println(pom.getName() + " " + pom.getSurname());
} 
```

```
Michal Nový
Jan Rychlý
Jan Rychlý
```

Here the application did not run correctly. Only one student "Jan Rychlý" should have entered. So there is a mistake somewhere. In general, the error can occur in two places:

1. In the case of inserting a student, i.e. in the students.add(s) command. This is probably the first idea that the implementation was not done correctly and Zdeněk is not validating the addition of a student with the same name.
2. In the case of a student listing, i.e. in the for cycle – theoretically, there can also be the case that the student was not added to the collection, but the collection for some reason lists the last student more than once. This case is very unlikely at first glance, but it needs to be verified, otherwise we could unnecessarily (and especially for a long time) look for an error in a place where there is no error.

Determining the type of error is very simple - just check how many elements there are in the collection after inserting the third student. If there are two elements, the error is in case 2), i.e. the list of elements. However, if there are 3 elements, there is an error of 1), that is, the third student entered the collection incorrectly.

```java
. . .
s = new Student("Jan", "Rychlý");
students.add(s);

System.out.println("\t### number of elements: " + students.size());
   
// print all the student sin the collection
for (int i = 0; i < students.size(); i++) {
  Student pom = students.get(i);
  System.out.println(pom.getName() + " " + pom.getSurname());
} 
```

```
### number of elements: 3
Michal Nový
Jan Rychlý
Jan Rychlý
```

The number of elements is equal to 3, so the student entered the collection incorrectly and you need to look for an error in the statement `students.add(s);`.  You can simply go to the method definition by placing the mouse cursor over `add()` and using it as a hyperlink, i.e. _Ctrl+click_.

The `add()` method is defined in the `StudentCollection` class and now Tomáš has to pay attention to it in order to be able to find the error.

```java
public class StudentCollection {
  
  private java.util.ArrayList<Student> data = new ArrayList<>();
  
  public boolean add(Student student){
    boolean isValid = true;
    
    if (isValidString(student.getName()) == false && isValidString(student.getSurname()) == false)
      isValid = false;
    
    if (containsStudent (student))
      isValid = true;
    
    if (isValid)
      data.add(student);
    
    return isValid;
  }
  
  . . .
} 
```

It will focus at the `add` method. In it he finds some `isValid` variable - but the main goal is to find something that evokes the insertion of an element into some set of records - and Tomáš finds the command `data.add(student)`. This command inserts records into a variable `data` that Tomáš finds at the top of the class variable declarations - the `data` variable belongs to a class `ArrayList`, which (by its name) seems to represent a set of records.

Another important thing is that `data.add(student)` is called conditionally `if (isValid)` is `true`.

Here, Tomáš inserts another auxiliary statement to find out how it is with the insertion of elements into variable `data`.

```java
. . .
if (isValid) {
  System.out.println("\t### - SC.add(" + student.getSurname() + 
    ") - isValid: " + isValid);
  data.add(student);
} 
. . .
```

On subsequent execution, it finds that:

```
	### - SC.add(Nový) - isValid: true
	### - SC.add(Rychlý) - isValid: true
	### - SC.add(Rychlý) - isValid: true
```

So, the second student "Rychlý" is also inserted into the collection because the `isValid` variable is set to `true`, which is not correct. A quick analysis looks at the previous `if` blocks and finds that:

1. The first if block deals with `isValidString()` ... `getName` ... `getSurname` operations, so it can be assumed that this condition solves the area where it is checked whether the student has the correct name and surname string.
2. The second if block deals with the `containsStudent(student)` operation, which already by its name evokes a case where it is checked whether a student already exists somewhere.

So it will focus on that block, but before it starts studying the contents of the `containsStudent()` method, it will test how the code behaves in that area - and extend it with the following statements.

```java
public boolean add(Student student) {
  boolean isValid = true;

  if (isValidString(student.getName()) == false && isValidString(student.getSurname()) == false) {
    isValid = false;
  }

  System.out.println("\t### - SC.add 1 ("
          + student.getSurname() + ") - isValid: " + isValid);

  if (containsStudent(student)) {
    System.out.println("\t### - SC.add 2 ("
            + student.getSurname() + ") - isValid: " + isValid);

    isValid = true;

    System.out.println("\t### - SC.add 3 ("
            + student.getSurname() + ") - isValid: " + isValid);
  }

  System.out.println("\t### - SC.add 4 ("
          + student.getSurname() + ") - isValid: " + isValid);

  if (isValid) {
    System.out.println("\t### - SC.add 5 ("
            + student.getSurname() + ") - isValid: " + isValid);
    data.add(student);
  }

  return isValid;
} 
```

It is important to note that now Tomáš already numbers the continuous statements in addition - SC.add 5, etc. So, he can distinguish the individual statements from each other. It can then see where the code went for each function call:

1. The first listing should always be made for each added object;
2. The second statement is executed only if the function `containsStudent()` returns `true` and the program jumps to the conditional if block. It is important that this statement be the first one immediately after the opening parenthesis of the block so that no further change to the `isValid` variable can occur before that.
3. The third statement is performed at the end of the conditional block. Again, it is important that this statement be the last one immediately before the closing parenthesis of the block so that the value of the `isValid` variable cannot be changed further within the block.
4. The fourth statement should always be performed and is only a check for the value of the `isValid` variable.
5. The fifth listing is performed only if the element is physically added to the collection, because it is placed inside the `if (isValid) ...` condition block.

After the subsequent launch, Tomáš gets the results:

<pre><code>	### - SC.add 1 (Nový) - isValid: true
<strong>	### - SC.add 4 (Nový) - isValid: true
</strong>	### - SC.add 5 (Nový) - isValid: true
	### - SC.add 1 (Rychlý) - isValid: true
	### - SC.add 4 (Rychlý) - isValid: true
	### - SC.add 5 (Rychlý) - isValid: true
	### - SC.add 1 (Rychlý) - isValid: true
	### - SC.add 2 (Rychlý) - isValid: true
	### - SC.add 3 (Rychlý) - isValid: true
	### - SC.add 4 (Rychlý) - isValid: true
	### - SC.add 5 (Rychlý) - isValid: true
	### number of elements: 3

</code></pre>

So what did Tomas discover?&#x20;

Statements 1, 4 and 5 are called for the student "Nový", so it is correctly evaluated that the student is not yet in the collection and the block with the condition and statements 2 and 3 are skipped. Listing 5 says that the student is physically added to the collection.

For the "Rychlý" student, statements 1, 4 and 5 are called for the first time, and thus again it is correctly evaluated that the student is not yet in the collection and the block with condition 2 and 3 is skipped. Listing 5 says that the student is physically added to the collection.

For the "Rychlý" student, statement 1 is called a second time, but then statements 2 and 3 are also called, so the `containsStudent(student)` function **worked correctly** and correctly detected that a student with the same name is already in the collection.&#x20;

{% hint style="warning" %}
This is very important to note - again, Tomáš has found that the function works correctly without having to study the complex code inside the function.
{% endhint %}

However, Listing 3 also reveals that the value of the `isValid` variable is not changing, which is apparently not a bad thing, since Listings 4 and 5 are executed next - and Listing 5 indicates that the object has been physically added to the collection - which is not correct.

Next, Tomáš must focus on what is happening inside the block between statements 2 and 3 and why the `isValid` variable with the value `false` does not come from here. If there were another sequence of statements in that part of the code, it would have to analogically parse them in the same way. Fortunately, Tomáš finds only one statement here that is clearly wrong, and Tomáš corrects it to:

```java
if (containsStudent(student)) {
  System.out.println("\t### - SC.add 2 ("
          + student.getSurname() + ") - isValid: " + isValid);

  isValid = false;

  System.out.println("\t### - SC.add 3 ("
          + student.getSurname() + ") - isValid: " + isValid);
} 
```

Tomáš runs the corrected version again and gets the output (note the lines 7 - 10):

{% code lineNumbers="true" %}
```
	### - SC.add 1 (Nový) - isValid: true
	### - SC.add 4 (Nový) - isValid: true
	### - SC.add 5 (Nový) - isValid: true
	### - SC.add 1 (Rychlý) - isValid: true
	### - SC.add 4 (Rychlý) - isValid: true
	### - SC.add 5 (Rychlý) - isValid: true
	### - SC.add 1 (Rychlý) - isValid: true
	### - SC.add 2 (Rychlý) - isValid: true
	### - SC.add 3 (Rychlý) - isValid: false
	### - SC.add 4 (Rychlý) - isValid: false
	### počet prvků: 2
Michal Nový
Jan Rychlý
```
{% endcode %}

Tomáš can now check that blocks 1, 2, 3 and 4 are executed for the second student being added "Fast", but block 5 is not executed, the element is not physically added, and the application (respectively this checked part) now works correctly.&#x20;

Now, Tomáš would subsequently test the behavior of `remove()` and other public methods in the same way.

## Example II

The second part of the task given by Tomáš Zdenek was for the student to have a list of grades:

* Marks can be entered 1\*, 1, 1-, 2, 2-, 3, 3-, 4, 4-, 5
* Students can add, edit and remove grades.&#x20;

Based on this list of marks, the average is to be calculated as follows:

* Twenty percent of the worst and best values ​​are trimmed (rounded up). If there are no marks left to evaluate, this step is not performed.
* The arithmetic mean of the obtained values ​​is calculated.

{% hint style="info" %}
For this example, we suppose czech grading system, in a range 1\*, 1, 1-, 2, 2-, 3, 3-, 4, 4-, 5, where 1\* has meaning "excelent", the minus sign represents worsening.
{% endhint %}

Tomáš examines Zdenek's solution and finds two matching classes.

The first class is `Mark` in the `Mark.java` file. Tomáš will again focus on public members.

```java
/**
 * Represents mark of the student.
 * @author Marek Vajgl
 */
public enum Mark {

  _1h(0.75),
  _1(1),
  _1m(1.5),
  _2(2),
  _2m(2.5),
  _3(3),
  _3m(3),
  _4(4),
  _4m(4.5),
  _5(5);
  
. . .

  /**
   * Returns numerical value of mark for further processing
   * @return Numerical value of the mark (e.g. 0.75, 2, 3.5, ...)
   */
  public double getValue() { . . . }
  /**
   * Returns string representation of the mark, how is the mark displayed
   * @return String representation of the mark (e.g. 1*, 1-, 3, ...)
   */
  public String getDisplayValue() { . . . }
  
  @Override
  public String toString(){ . . . }
} 

```

In the implementation, Tomáš finds an enumeration type (enum) with representations for individual grades, where the "h" postfix means an asterisk and the "m" postfix means a minus, i.e. \_1h is the grade "1\*", \_2m is the grade "2-". He also notices that Zdeněk has (finally) added some, at least basic comments, so he quickly discovers that there are two other methods in the type - `getValue()` representing the mark as a number of type double (so that the values ​​can be added/divided and the arithmetic mean can be calculated ) and the `getDisplayValue()` function, which is used to convert the mark into the format that people see and perceive (1\*, 2-, etc.).

{% hint style="info" %}
_An enumeration type_ is a type whose values ​​must can be only taken from a preselected set - an enumeration. Grades are a typical example, as the programmer-user is only wanted to use the grades 1\*, 1, 1-, … 4, 4-, 5, but no others (for example, 2+ is not allowed). The enum type is used exactly in these cases when we want to limit the choice options.
{% endhint %}

Next, it finds a second class representing the `MarkCollection` group of marks in the `MarkCollection.java` file:

```java
public class MarkCollection extends java.util.ArrayList<Mark> {
  
  public double getTrimmedMean (){ . . . }
  }

  . . .
  
} 
```

This class contains only one method – `getTrimmedMean()`, which, according to the name, is used to calculate the trimmed mean according to the input. However, it does not contain any other methods! Looking at the class declaration, Tomáš discovers that the class inherits from the `java.util.ArrayList` class, i.e. inherits from a class that itself can be and behave as a dynamic array for grades.

{% hint style="info" %}
The `ArrayList` class belongs to the Java Collection Framework. If you are not familiar, we will briefly explain that it is an implementation of a dynamic field with methods such as `add()`, `remove()`, `get()` and others, which can add, remove and retrieve an element according to the index. Inheritance of course says that the descendant (in our case the `MarkCollection` class) inherits all these methods from its ancestor `ArrayList`.
{% endhint %}

It's time to verify the implementation.

First, Tomáš tries to assign a few grades to the student and write them down.

```java
Student s = new Student("Michal", "Nový");

// add two grades
s.getMarks().add(Mark._1h);
s.getMarks().add(Mark._4);
    
for (int i = 0; i < s.getMarks().size(); i++) {
  // get value by index into a variable
  Mark m = s.getMarks().get(i);   
      
  // print a visual value of the grade
  System.out.print(m.getDisplayValue() + ", ");
}
System.out.println(); 
```

Tomas receives a result:

```
1*, 4,
```

So everything is fine here.&#x20;

Let's try to add a summary of the average. If a mark of 1\* represents a value of 0.75 and marks with a minus represent values ​​on the border (i.e., 3- is taken as 3.5), the average for {1\*; 4} will be:

$$
\bar{x}=\frac{0.75+4}{2}=2.375
$$

```java
Student s = new Student("Michal", "Nový");

// add marks
s.getMarks().add(Mark._1h);
s.getMarks().add(Mark._4);
    
for (int i = 0; i < s.getMarks().size(); i++) {
  // get mark by index
  Mark m = s.getMarks().get(i);   
      
  // print mark display representation
  System.out.print(m.getDisplayValue() + ", ");
}
System.out.println();
double mean = s.getMarks().getTrimmedMean();
System.out.println("Mean = " + mean); 
```

However, after running, Tomáš gets the output:

```
1*, 4, 
Exception in thread "main" java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [D
	at isworking.MarkCollection.getTrimmedMean(MarkCollection.java:29)
	at isworking.IsWorking.main(IsWorking.java:32)
Java Result: 1
```

So the program caused a runtime error. From the statement above, it can be seen that the error occurred in the function `Isworking.MarkCollection.getTrimmedMean()`. For further processing, Tomáš must therefore focus on this method, which looks long and complex, so he will not go through it in detail:

```java
public double getTrimmedMean (){
  double ret;
  
  List<Double> vals = getMarksAsValues();
  int outStep = getOutStepForCount (vals.size(), 0.2);
  int fromIndex = outStep;
  int toIndex = vals.size() - outStep;
    
  List<Double> trimmedVals = new java.util.ArrayList<>();
  for (int i = fromIndex; i < toIndex; i++) {
    trimmedVals.add(vals.get(i));
  }
    
  Object o = trimmedVals.toArray();
  double [] data = (double []) o;
  ret = calculateMean (data);
    
  return ret;
} 
```

However, only the line with the error (the middle line in the listing) is important to him:

```java
. . .    
Object o = trimmedVals.toArray();
double [] data = (double []) o;
ret = calculateMean (data);
. . .
```

Fortunately, this passage is quite readable - from a set of `trimmedVals` values, the `toArray()` function is used to obtain the o object, which is then hard-coded into a double array with the next command `double [] data = (double[]) o;`. However, this cast will fail because, as the exception says, the object cannot be cast to type `double[]`. Tomáš looks further and finds that `trimmedVals` is of type `java.util.List`, while data is of type `double[]`.

The solution to this problem can go in several ways - Tomas decides that instead of complicatedly tracking what the `toArray()` function does and how to modify it to work together with `double[]`, he simply writes his own function that does the conversion:

```java
private double [] convertListToArray (List<Double> data){
  double [] ret = new double[data.size()];
  for (int i = 0; i < data.size(); i++) {
    ret[i] = data.get(i);
  }
  return ret;
} 
```

And modifies the call:

```java
public double getTrimmedMean (){
  double ret;
   
  List<Double> vals = getMarksAsValues();
  int outStep = getOutStepForCount (vals.size(), 0.2);
  int fromIndex = outStep;
  int toIndex = vals.size() - outStep;
    
  List<Double> trimmedVals = new java.util.ArrayList<>();
  for (int i = fromIndex; i < toIndex; i++) {
    trimmedVals.add(vals.get(i));
  }
    
  /* nefunguje !!!
  Object o = trimmedVals.toArray();
  double [] data = (double []) o; */
    
  /* updated version follows */
  double [] data = convertListToArray(trimmedVals);
  ret = calculateMean (data);
  
  return ret;
} 
```

It took him about 5 minutes. Then, he will run the code.

```
1*, 4, 
Mean = 2.375
```

The calculated average corresponds to the correct values.

So he tries a more complex variant, in which he incorporates all the marks several times, i.e. the sequence {1\*; 1; 1-; 2; 2-; 3; 3-; 4; 4-; 5; 5; 4-; 4; 3-; 3; 2-; 2; 1-; 1; 1\*}.

```java
Student s = new Student("Michal", "Nový");

// add multiple grades
s.getMarks().add(Mark._1h);
s.getMarks().add(Mark._1);
s.getMarks().add(Mark._1m);
s.getMarks().add(Mark._2);
s.getMarks().add(Mark._2m);
s.getMarks().add(Mark._3);
s.getMarks().add(Mark._3m);
s.getMarks().add(Mark._4);
s.getMarks().add(Mark._4m);
s.getMarks().add(Mark._5);
s.getMarks().add(Mark._5);
s.getMarks().add(Mark._4m);
s.getMarks().add(Mark._4);
s.getMarks().add(Mark._3m);
s.getMarks().add(Mark._3);
s.getMarks().add(Mark._2m);
s.getMarks().add(Mark._2);
s.getMarks().add(Mark._1m);
s.getMarks().add(Mark._1);
s.getMarks().add(Mark._1h);

for (int i = 0; i < s.getMarks().size(); i++) {
  Mark m = s.getMarks().get(i);
  System.out.print(m.getDisplayValue() + ", ");
}

System.out.println();
double mean = s.getMarks().getTrimmedMean();
System.out.println("Mean = " + mean); 
```

The output:

```
1*, 1, 1-, 2, 2-, 3, 3, 4, 4-, 5, 5, 4-, 4, 3, 3, 2-, 2, 1, 1*, 
Mean = 2.789473684210526
```

In it, however, he finds that the mark "3-" does not appear even once, instead the mark "3" appears twice. Therefore, it will focus once again on the mark "3-" and insert only "3-" into the sequence.

```java
s.getMarks().add(Mark._3m);
s.getMarks().add(Mark._3m);
```

The grade "3-" really behaves like a 3:

```
3, 3, 
Mean = 3.0
```

So it focuses on the mark "\_3m" in the definition in the Mark class and finds:

```java
  _2m(2.5),
  _3(3),
  _3m(3),
  _4(4),
  _4m(4.5), 
```

There is clearly an error here (when comparing with `_2m` and  `_4m`), the value of `_3m` should surely be 3.5 in the parenthesis (see listing above).&#x20;

So he fixes the value to `_3m(3.5),` and run the code again:

```
3-, 3-, 
Mean = 3.5
```

Now Tomáš can return to the original example. But first he has to find out what result to expect for the original set {1\*; 1; 1-; 2; 2-; 3; 3-; 4; 4-; 5; 5; 4-; 4; 3-; 3; 2-; 2; 1-; 1; 1\*}.&#x20;

It has 20 marks in entry. 20% of 20 marks is 4, so he will remove 4 worst and 4 best marks. First, they sort the array into the sequence {1\*; 1\*; 1; 1; 1-; 1-; 2; 2; 2-; 2-; 3; 3; 3-; 3-; 4; 4; 4-; 4-; 5; 5}. After removing the 4 best and 4 worst marks, he gets {1-; 1-; 2; 2; 2-; 2-; 3; 3; 3-; 3-; 4; 4}. The average of these remaining 12 marks is:

$$
\bar{x}=\frac{2*0.75+2*2+2*2.5+2*3+2*3.5+2*4}{12}=2.75
$$

The output is:

```
1*, 1, 1-, 2, 2-, 3, 3-, 4, 4-, 5, 5, 4-, 4, 3-, 3, 2-, 2, 1-, 1, 1*, 
Mean = 2.775
```

So this is not good either. And Tomáš has to return to the `getTrimmedMean()` function again. It knows that the average is calculated from `trimmedVals` values ​​at the bottom of the function, so it tries to print this set.

```java
public double getTrimmedMean (){
  double ret;
  
  List<Double> vals = getMarksAsValues();
  int outStep = getOutStepForCount (vals.size(), 0.2);
  int fromIndex = outStep;
  int toIndex = vals.size() - outStep;
  
  List<Double> trimmedVals = new java.util.ArrayList<>();
  for (int i = fromIndex; i < toIndex; i++) {
    trimmedVals.add(vals.get(i));
  }
  
  // added listing
  for (int i = 0; i < trimmedVals.size(); i++) {
    System.out.println("\t### " + trimmedVals.get(i));
  }
  
  double [] data = convertListToArray(trimmedVals);
  ret = calculateMean (data);
  
  return ret;
} 
```

Output:

```
1*, 1, 1-, 2, 2-, 3, 3-, 4, 4-, 5, 5, 4-, 4, 3-, 3, 2-, 2, 1-, 1, 1*, 
	### 0.75
	### 1.0
	### 1.5
	### 2.0
	### 2.5
	### 3.0
	### 3.5
	### 4.0
	### 4.5
	### 5.0
	### 5.0
	### 4.5
	### 4.0
	### 3.5
	### 3.0
	### 2.5
	### 2.0
	### 1.5
	### 1.0
	### 0.75
Mean = 2.775
```

He sees that even the trimmed values ​​used for calculation contain all marks instead of just selected ones. A further look reveals that trimming is done based on some `fromIndex` and `toIndex` variables few lines above:

```java
List<Double> trimmedVals = new java.util.ArrayList<>();
for (int i = fromIndex; i < toIndex; i++) {
  trimmedVals.add(vals.get(i));
} 
```

He prints both values:

```java
System.out.println(
      "\t### from: " + fromIndex + " // to: " + toIndex); 
```

```
### from: 0 // to: 20
```

So the values ​​are not calculated correctly. Looking at the code again, he finds where they are obtained (i.e. where they are assigned to) and finds:

```java
int outStep = getOutStepForCount (vals.size(), 0.2);
int fromIndex = outStep;
int toIndex = vals.size() - outStep; 
```

So everything is related to some value of `outStep`, for which it may not even need to verify that it is equal to zero. However, it is interested in the `getOutStepForCount()` function, which enters the size and 0.2 will be the percentage of the trimmed values.

```java
private int getOutStepForCount(int length, double trim) {
  int ret;
  int out = (int) Math.ceil(length / trim);
  int twoOut = out * 2;
  if (length < (twoOut + 1))
    ret = 0;
  else
    ret = out;
 return ret;
} 
```

In the function, a trimmed value is "somehow" obtained in the `out`variable by dividing the number of elements by the % of selected elements. Here Tomáš checks how this mathematical operation behaves, because it is apparently critical in the calculation of the number of trimmed elements:

```java
private int getOutStepForCount(int length, double trim) {
  int ret;
  int out = (int) Math.ceil(length / trim);
  System.out.println("\t### len: " + length + 
    " // trim: " + trim + " // out: " + out);
  int twoOut = out * 2;
  if (length < (twoOut + 1))
    ret = 0;
  else
    ret = out;
  return ret;
} 
```

The listing is not correct, the expected 4 elements should come out for clipping:

```
### len: 20 // trim: 0.2 // out: 100
```

Now, Tomáš has to look what the function actually does, and he discovers that the required operation should be multiplication (and not division) between the length and trim elements. Will try quick replace:

```java
int out = (int) Math.ceil(length * trim); 
```

... and obtains

```
	### len: 20 // trim: 0.2 // out: 4
```

..., i.e. the correct result. However, the sequence of counted marks is still wrong, even though the count is correct (12 marks):

```
	### 2.5
	### 3.0
	### 3.5
	### 4.0
	### 4.5
	### 5.0
	### 5.0
	### 4.5
	### 4.0
	### 3.5
	### 3.0
	### 2.5
```

He realizes that the function trims the first 4 and the last 4 from the source data - however, for correct trimming, Tomáš had to sort the array and the array is certainly not sorted in the program (can be verified again by quickly listing the items of the `vals` variable. So Tomáš can find on the Internet how to sorts the `List` array and applies the found solution before trimming the values:

```java
public double getTrimmedMean (){
  double ret;
    
  List<Double> vals = getMarksAsValues();
  int outStep = getOutStepForCount (vals.size(), 0.2);
  int fromIndex = outStep;
  int toIndex = vals.size() - outStep;
   
  System.out.println("\t### from: " + fromIndex + " // to: " + toIndex);
    
  // sort items, mandatory:
  java.util.Collections.sort(vals);
  List<Double> trimmedVals = new java.util.ArrayList<>();
  for (int i = fromIndex; i < toIndex; i++) {
    trimmedVals.add(vals.get(i));
  }
    
  for (int i = 0; i < trimmedVals.size(); i++) {
    System.out.println("\t### " + trimmedVals.get(i));
  }
    
  double [] data = convertListToArray(trimmedVals);
  ret = calculateMean (data);
    
  return ret;
} 
```

The program runs and receives the correct dump:

```
1*, 1, 1-, 2, 2-, 3, 3-, 4, 4-, 5, 5, 4-, 4, 3-, 3, 2-, 2, 1-, 1, 1*, 
	### len: 20 // trim: 0.2 // out: 4
	### from: 4 // to: 16
	### 1.5
	### 1.5
	### 2.0
	### 2.0
	### 2.5
	### 2.5
	### 3.0
	### 3.0
	### 3.5
	### 3.5
	### 4.0
	### 4.0
Průměr = 2.75
```

It is still advisable to check the behavior for a small number of stamps (if clipping is not performed). So Tomáš adds only 2 grades to the program and asks for their average:

```java
s.getMarks().add(Mark._2m);
s.getMarks().add(Mark._3m);
```

The result:

```
2-, 3-, 
	### len: 2 // trim: 0.2 // out: 1
	### from: 0 // to: 2
	### 2.5
	### 3.5
Mean = 3.0
```

From the listing, he can see that it is correctly calculated from two marks, 1 value should be trimmed (out:1), but since there are few values, nothing is trimmed and the program works with all elements (from 0 to 2).

{% hint style="info" %}
The value 2 (the upper bound) is taken exclusively, so only indices from 0 inclusive to 2 inclusive are taken, i.e. `for (i = 0; i < 2; i++)` that is 0, 1.
{% endhint %}
