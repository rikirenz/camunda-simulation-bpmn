# Enso
Master thesis project.

To run the application you should have installed *maven* and *java*. 

Run this command to start the application:

```powershell
mvn clean compile install exec:java -D exec.mainClass="enso.App" "-Dbpmn=parallel-amazon.bpmn" "-DinstancesNumber=10" "-DdelayBetweenInstances=10"
```

*instancesNumber* represents the number of process instances run by the app.

*delayBetweenInstances* represents the time between the start of the process instances.
