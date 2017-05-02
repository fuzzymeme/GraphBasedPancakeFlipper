package com.fuzzymeme.graphbasedpancakeflipper.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CounterMap<K> {
	
	private Map<K, Integer> map = new HashMap<K, Integer>();
	
	public void remove(K key){
		if(!map.containsKey(key)){
			return;
		}
		map.remove(key);
	}
	
	public void inc(K key){
		
		if(!map.containsKey(key)){
			map.put(key, new Integer(0));
		}
		
		map.put(key, map.get(key) + 1);
	}

	public void set(K key, int count){		
		map.put(key, count);
	}

	public boolean containsKey(K key){
		return map.containsKey(key);
	}
	
	public int get(K key){
		if(map.containsKey(key)){
			return map.get(key);
		} else {
			return 0;
		}
	}
	
	public Set<K> keySet(){
		return map.keySet();
	}
		
	public int size(){
		return map.size();
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}

