# Firefly Simulator
Master thesis project.


## How to run the app
To run the application you should have installed *maven* and *java*. 

Run this command to start the application:

```powershell
simulation-bpmn>  mvn clean compile install exec:java -D exec.mainClass="enso.App" "-Dbpmn=parallelTasksWithOneResource.bpmn" "-DprocessId=parallelTasksWithOneResource" "-DinstancesNumber=2" "-DdelayBetweenInstances
=0"
```

The bpmn files

*bpmn* is the file name of the bpmn process. All the bpmn files have to be placed in the following directory: `\resources\bpmprocesses`

*processId* the process id of the bpmn

*instancesNumber* represents the number of process instances run by the app.

*delayBetweenInstances* represents the time between the start of the process instances.

# Results

The simulation's results will be placed in the following directory: `\results\`
