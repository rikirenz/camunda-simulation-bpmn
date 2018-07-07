package simulation;

import java.util.Comparator;

public class SimulationEventsComparator implements Comparator<SimulationEvent>{

	public int compare(SimulationEvent o1, SimulationEvent o2) { 
		if (o1.getEndTime() < o2.getEndTime()) return -1;
		else if (o1.getEndTime() > o2.getEndTime()) return 1;
		return 0;
	}

}
