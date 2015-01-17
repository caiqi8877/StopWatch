package edu.nyu.pqs.stopwatch.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

public class StopWatchImpl implements IStopwatch{
	  private long startTime = 0;
	  private long endTime = 0;
	  private long lastLapTime = 0;
	  private String Id;
	  private boolean running = false;
	  private List<Long> lapList =Collections.synchronizedList(new ArrayList<Long>());
	  
	  
	  /**
	   * construtor create a new stopWatch with special ID
	   * public object outside the package can instantiate it
	   * @param Id the special id for the object
	   */
	  public StopWatchImpl(String Id){
		  this.Id = Id;
	  }
	  /**
	   * Returns the Id of this stopwatch
	   * @return the Id of this stopwatch.  Will never be empty or null.
	   */
	  public String getId(){
		  return this.Id;
	  };

	  /**
	   * Starts the stopwatch.
	   * @throws IllegalStateException if called when the stopwatch is already running
	   */
	  public void start(){
	  	synchronized(this){
			  if(running){
			    throw new IllegalStateException("it is already running");
			  }else{
			    startTime = System.currentTimeMillis();
			    lastLapTime = startTime;
			    running = true;
			  }
		  }
	  };

	  /**
	   * Stores the time elapsed since the last time lap() was called
	   * or since start() was called if this is the first lap.
	   * @throws IllegalStateException if called when the stopwatch isn't running
	   */
	  public void lap(){
	  	synchronized(this){
			  if(!running){
			  	throw new IllegalStateException("it is not running");
			  }else{
			  	long CurrentTime = System.currentTimeMillis();
			  	long lapTime = CurrentTime - lastLapTime;
			  	lapList.add(lapTime);
			  	lastLapTime = CurrentTime;
			  }
		  }
	  };

		/**
		 * Stops the stopwatch (and records one final lap).
		 * @throws IllegalStateException if called when the stopwatch isn't running
		 */
	  public synchronized void stop(){
	  	synchronized(this){
			  if(!running){
			  	throw new IllegalStateException("it is not running");
			  }else{
					endTime = System.currentTimeMillis();
					long lapTime = endTime - lastLapTime;
					lapList.add(lapTime);
					running = false;
			  }
		  }
	  };

		/**
		 * Resets the stopwatch.  If the stopwatch is running, this method stops the
		 * watch and resets it.  This also clears all recorded laps.
		 */
		public synchronized void reset(){
			synchronized(this){
				startTime = 0;
				endTime = 0;
				lastLapTime = 0;
				lapList.clear();
				running = false;
			}
		};

		/**
		 * Returns a list of lap times (in milliseconds).  This method can be called at
		 * any time and will not throw an exception.
		 * @return a list of recorded lap times or an empty list if no times are recorded.
		 */
		public synchronized List<Long> getLapTimes(){
		  return lapList;
		};
		
		/**
		 * Two stopwatches are equal only when they are the same stopwatch or they have the same id
		 * @param obj the object to test whether it equal to this stopWatch
		 * @return true if the two stopwatches are equal, and false otherwise
		 */
		@Override
		public boolean equals(Object obj) {
		  if (obj == this){
			  return true;
		  }else if( !(obj instanceof StopWatchImpl)){
			  return false;  
		  }else{
		    StopWatchImpl s = (StopWatchImpl) obj;
		    synchronized (this) {
		      return Id.equals(s.Id);
		    }
		  }
		}
		/**
		 * Two stopwatches are equal only when they are the same stopwatch or they have the same id
		 * @param obj1,obj2 are the two stopwatches to test whether one equals to the other 
		 * @return true if the two stopwatches are equal, and false otherwise
		 */
		
		public boolean equals(Object obj1, Object obj2) {
	           return obj1 == null ? obj2 == null : obj1.equals(obj2);
	       }
		
		/**
		 * Returns the hash code value for the stopwatch
		 * the hash code comes from the stopwatch's id, startTime, lapList, endTime and running flag
		 * @return the hash code value of the stopwatch
		 */
		@Override 
		public int hashCode() {
			   synchronized (this) {
	           int hash = 1;
	           hash = hash * 39 + Id.hashCode();
	           hash = hash * 31 + (int)startTime;
	           hash = hash * 43 + lapList.hashCode();
	           hash = hash * 19 + (int)endTime;
	           return running ? hash + 1 : hash ;
			   }
		}
		
		/**
		 * Returns the string represent a unique stopwatch
		 * the string is consist of the time the stop watch shows
		 * @return the time consist of the hour:min:sec:millisec
		 */
		@Override
		public String toString(){
			synchronized (this) {
				
				String end = toTime(endTime - startTime);
				String lap = " ";
				for(int i = 0; i<lapList.size(); i++){
					lap += toTime(lapList.get(i)) + "   ";
				}
				return "endTime: " + end + "\n" + "laptime:" + lap;
			}
		}
		
		/**
		 * 
		 * @param timespan: translate the time format
		 * @return the nice format of time
		 */
		private String toTime(long timespan){
			long milli =  ( timespan / 10 ) % 100;
			long sec = ( timespan / 1000) % 60;
			long min = ( timespan / 60000 ) % 60;
			return min + " min " + sec + "." + milli + " s";
		}

		
}
