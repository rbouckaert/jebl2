/*
 * Intervals.java
 *
 * (c) 2002-2005 BEAST Development Core Team
 *
 * This package may be distributed under the
 * Lesser Gnu Public Licence (LGPL)
 */

package jebl.evolution.coalescent;

import beast.core.Param;
import jebl.evolution.trees.RootedTree;
import jebl.evolution.trees.RootedTreeUtils;
import jebl.evolution.graphs.Node;

import java.util.Arrays;

import beast.core.BEASTObject;

/**
 * A concrete class for a set of coalescent intevals.
 *
 * @version $Id: Intervals.java 305 2006-04-26 00:22:30Z rambaut $
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 */
public class Intervals extends BEASTObject implements IntervalList {

	public Intervals(
		@Param(name="tree", description="auto converted jebl2 parameter") RootedTree tree) {
		this(tree.getNodes().size());

		if (!RootedTreeUtils.isBinary(tree)) {
			throw new IllegalArgumentException("Tree must be rooted and binary");
		}
		for (Node node : tree.getExternalNodes()) {
			addSampleEvent(tree.getHeight(node));
		}
		for (Node node : tree.getInternalNodes()) {
			addCoalescentEvent(tree.getHeight(node));
		}
		this.tree = tree;
	}

	public Intervals(
		@Param(name="maxEventCount", description="auto converted jebl2 parameter") Integer maxEventCount) {
		events = new Event[maxEventCount];
		for (int i = 0; i < maxEventCount; i++) {
			events[i] = new Event();
		}
		eventCount = 0;
		sampleCount = 0;

		intervals = new double[maxEventCount - 1];
		intervalTypes = new IntervalType[maxEventCount - 1];
		lineageCounts = new int[maxEventCount - 1];

		intervalsKnown = false;
		
		this.maxEventCount = maxEventCount;
	}

	public void copyIntervals(Intervals source) {
		intervalsKnown = source.intervalsKnown;
		eventCount = source.eventCount;
		sampleCount = source.sampleCount;

		//don't copy the actual events..
		/*
		for (int i = 0; i < events.length; i++) {
			events[i].time = source.events[i].time;
			events[i].type = source.events[i].type;
		}*/

		if (intervalsKnown) {
			System.arraycopy(source.intervals, 0, intervals, 0, intervals.length);
			System.arraycopy(source.intervalTypes, 0, intervalTypes, 0, intervals.length);
			System.arraycopy(source.lineageCounts, 0, lineageCounts, 0, intervals.length);
		}
	}

	public void resetEvents() {
		intervalsKnown = false;
		eventCount = 0;
		sampleCount = 0;
	}

	public void addSampleEvent(double time) {
		events[eventCount].time = time;
		events[eventCount].type = IntervalType.SAMPLE;
		eventCount++;
		sampleCount++;
		intervalsKnown = false;
	}

	public void addCoalescentEvent(double time) {
		events[eventCount].time = time;
		events[eventCount].type = IntervalType.COALESCENT;
		eventCount++;
		intervalsKnown = false;
	}

    public void addMigrationEvent(double time, int destination) {
        events[eventCount].time = time;
        events[eventCount].type = IntervalType.MIGRATION;
        //events[eventCount].info = destination;
        eventCount++;
        intervalsKnown = false;
    }

	public void addNothingEvent(double time) {
		events[eventCount].time = time;
		events[eventCount].type = IntervalType.NOTHING;
		eventCount++;
		intervalsKnown = false;
	}

	@Override
	public int getSampleCount() {
		return sampleCount;
	}

	@Override
	public int getIntervalCount() {
		if (!intervalsKnown) calculateIntervals();
		return intervalCount;
	}

	@Override
	public double getInterval(int i) {
		if (!intervalsKnown) calculateIntervals();
		return intervals[i];
	}

	@Override
	public int getLineageCount(int i) {
		if (!intervalsKnown) calculateIntervals();
		return lineageCounts[i];
	}

	@Override
	public int getCoalescentEvents(int i) {
		if (!intervalsKnown) calculateIntervals();
		if (i < intervalCount-1) {
			return lineageCounts[i]-lineageCounts[i+1];
		} else {
			return lineageCounts[i]-1;
		}
	}

	@Override
	public IntervalType getIntervalType(int i)
	{
		if (!intervalsKnown) calculateIntervals();
		return intervalTypes[i];
	}

	@Override
	public double getTotalDuration() {

		if (!intervalsKnown) calculateIntervals();
		return events[eventCount - 1].time;
	}

	@Override
	public boolean isBinaryCoalescent() { return true; }
	@Override
	public boolean isCoalescentOnly() { return true; }

	private void calculateIntervals() {

		if (eventCount < 2) {
			throw new IllegalArgumentException("Too few events to construct intervals");
		}

		Arrays.sort(events, 0, eventCount - 1);

		if (events[0].type != IntervalType.SAMPLE) {
			throw new IllegalArgumentException("First event is not a sample event");
		}

		intervalCount = eventCount - 1;

		double lastTime = events[0].time;

		int lineages = 1;
		for (int i = 1; i < eventCount; i++) {

			intervals[i - 1] = events[i].time - lastTime;
			intervalTypes[i - 1] = events[i].type;
			lineageCounts[i - 1] = lineages;
			if (events[i].type == IntervalType.SAMPLE) {
				lineages++;
			} else if (events[i].type == IntervalType.COALESCENT) {
				lineages--;
			}
			lastTime = events[i].time;
		}
		intervalsKnown = true;
	}

	private class Event implements Comparable<Event> {

        @Override
		public int compareTo(Event o) {
			double t = o.time;
			if (t < time) {
				return 1;
			} else if (t > time) {
				return -1;
			} else {
				// events are at exact same time so sort by type
				return type.compareTo(o.type);
			}
		}

        /**
         * The type of event
         */
        IntervalType type;

        /**
         * The time of the event
         */
		double time;

        /**
         * Some extra information for the event (e.g., destination of a migration)
         */
        //int info;

    }

	private Event[] events;
	private int eventCount;
	private int sampleCount;

	private boolean intervalsKnown = false;
	private double[] intervals;
	private int[] lineageCounts;
	private IntervalType[] intervalTypes;
	//private int[] destinations;
	private int intervalCount = 0;
	
	@Override
	public void initAndValidate() {
		// nothing to do
	}


	public Integer getMaxEventCount() {
		return maxEventCount;
	}

	public void setMaxEventCount(Integer maxEventCount) {
		this.maxEventCount = maxEventCount;
	}

	public RootedTree getTree() {
		return tree;
	}

	public void setTree(RootedTree tree) {
		this.tree = tree;
	}

	Integer maxEventCount;
	RootedTree tree;
}
