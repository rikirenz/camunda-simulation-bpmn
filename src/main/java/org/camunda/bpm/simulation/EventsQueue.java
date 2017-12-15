package org.camunda.bpm.simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.camunda.bpm.interfaces.Observer;
import org.camunda.bpm.interfaces.Subject;

public class EventsQueue implements Subject {

	private static Queue<SimulationEvent> eventsQueue = new LinkedList<SimulationEvent>();
	private static List<Observer> observers = new ArrayList<Observer>();
	private final Object MUTEXOBS = new Object();
	private final Object MUTEXQUE = new Object();


	public EventsQueue() { }
	
	public void add(SimulationEvent currentEvent) {		
		synchronized (MUTEXQUE) {
			eventsQueue.add(currentEvent);
		}
		notifyObservers("An element has been added to the queue");
	}

	public SimulationEvent remove() {		
		synchronized (MUTEXQUE) {
			return eventsQueue.remove();
		}
	}
	
	public void register(Observer obj) {
		if(obj == null) throw new NullPointerException("Null Observer");
		synchronized (MUTEXOBS) {
			// define observers
			if(!observers.contains(obj)) observers.add(obj);
		}
	}

	public void unregister(Observer obj) {
		synchronized (MUTEXOBS) {
			observers.remove(obj);
		}
	}

	public void notifyObservers(String updateReason) {
		List<Observer> observersLocal = null;
		//synchronization is used to make sure any observer registered after message is received is not notified
		synchronized (MUTEXOBS) {
			observersLocal = new ArrayList<Observer>(this.observers);
		}
		for (Observer obj : observersLocal) {
			obj.update(updateReason);
		}

	}
	
}
