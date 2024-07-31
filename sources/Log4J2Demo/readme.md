# Log4J2 Demo

This is a simple project demonstrating the possibilities of 
logging in Java using Log4J2 library.

Important content:
* src/main/java/cz.osu.prf.kip
  * Main - contains the main methods starting the application
  * Workers - contains the worker classes
    * Worker - the abstract basic class for other workers
    * Producer - the worker producing the data
    * Consumer - the worker consuming the data
* src/main/resources
  * log4j2.xml - Log4j2 configuration file
* pom.xml - maven configuration file. 

The application creates `Producers`, which are producing numbers and 
putting them into the store, and `Consumers`, which are consuming
the data from the store. Both classes are logging data using their own loggers.

`Main` has also its own logger.

The content of `log4j2.xml` file says that:
* anything in `cz.osu.prf.kip.workers` uses two appenders, one on the console and another into the file. The console logs only INFO and higher messages, file logs everything.
* the rest (`Main` in this case) logs to the console only, but everything (even the TRACE level).

The file `pom.xml` shows all the required dependencies to run the project.

## Before run
You need to change the path of the target file in the `log4j2.xml` file - currently set to `r:/log.txt`
