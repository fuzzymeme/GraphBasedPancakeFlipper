package com.fuzzymeme.graphbasedpancakeflipper;

import java.util.HashMap;
import java.util.Map;

public class TimeRecorder {

	private class TimeData {
		private long totalTime;
		private int numberOfCalls;
	}

	private Map<String, TimeData> timeToDoStuffMap = new HashMap<>();

	public void addDatum(String tag, long extraTime) {
		
		if(!timeToDoStuffMap.containsKey(tag)) {
			timeToDoStuffMap.put(tag, new TimeData());
		}
		
		TimeData timeData = timeToDoStuffMap.get(tag);
		timeData.totalTime += extraTime;
		timeData.numberOfCalls++;
	}
	
	public double getAverageTime(String tag) {
		TimeData timeData = timeToDoStuffMap.get(tag);
		double averageTime = ((double) timeData.totalTime) / ((double) timeData.numberOfCalls);
		return averageTime;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		for (String key : timeToDoStuffMap.keySet()) {
			buffer.append(key).append(" ").append(getAverageTime(key)).append(", ");
		}

		return "TimeRecorder [" + buffer.toString() + "]";
	}
}

