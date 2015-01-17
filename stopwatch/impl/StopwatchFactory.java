package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
//	public static List<IStopwatch> list = Collections.synchronizedList(new ArrayList<IStopwatch>());
	private static final List<IStopwatch> stopwatchList = Collections.synchronizedList(new ArrayList<IStopwatch>());
	/**
	 * Creates and returns a new IStopwatch object
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	 */
	public static IStopwatch getStopwatch(String id) {
		if(id == null || id.compareTo("") == 0){
			throw new IllegalArgumentException();
		}else{
			for(IStopwatch s:stopwatchList){
				if(s.getId().compareTo(id) == 0){
					throw new IllegalArgumentException();
				}
			}
		}
		StopWatchImpl stopWatch = new StopWatchImpl(id);
		stopwatchList.add(stopWatch);
		return stopWatch;
		
	}

	/**
	 * Returns a list of all created stopwatches
	 * @return a List of al creates IStopwatch objects.  Returns an empty
	 * list if no IStopwatches have been created.
	 */
	public static List<IStopwatch> getStopwatches() {
		return stopwatchList;
	}
}
