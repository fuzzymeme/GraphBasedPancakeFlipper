package com.fuzzymeme.graphbasedpancakeflipper.utils;

public class CounterMapUtils {
	
	public static String toPrettyPrintString(CounterMap<Integer> map) {
		
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < map.keySet().size(); i++) {
			if(map.keySet().contains(i)) {
				buffer.append(i).append(":").append(map.get(i)).append("\n");
			}
		}

		return buffer.toString().trim();
	}

}
