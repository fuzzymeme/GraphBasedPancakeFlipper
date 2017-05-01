package com.fuzzymeme.graphbasedpancakeflipper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PermutationGenerator {

	private int[] original;
	private List<List<Integer>> permutations;

	public List<List<Integer>> getAllPermutations(int len) {
		permutations = new ArrayList<List<Integer>>();
		original = getOrg(len);
		permute(0, new LinkedList<Integer>(), new boolean[original.length]);
		return permutations;
	}
	
	private void permute(int level, List<Integer> permedList, boolean used[]) {
		
		int length = original.length;
		
		if (level == length) {
			permutations.add(permedList);
		} else {
			for (int i = 0; i < length; i++) {
				if (!used[i]) {
					used[i] = true;
					LinkedList<Integer> newList = new LinkedList<Integer>();
					newList.addAll(permedList);
					newList.add(original[i]);
					permute(level + 1, newList, used);
					used[i] = false;
				}
			}
		}
	}
	
	private int[] getOrg(int len){

		int[] org = new int[len];
		for(int i = 0; i < len; i++){
			org[i] = i;
		}
		return org;
	}

}
