package com.fuzzymeme.graphbasedpancakeflipper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.richard.graphs.Arc;
import com.richard.graphs.Graph;
import com.richard.graphs.Node;

public class GraphBasedPancakeFlipper {
	
	private TimeRecorder recorder = new TimeRecorder();

	public Graph<List<Integer>, Integer> flip(Integer ... input) {
		
		Graph<List<Integer>, Integer> graph = new Graph<>();		
		
		List<Integer> inputList = Arrays.asList(input);
		Map<List<Integer>, String> alreadySeen = new HashMap<>(4000000);
		BigInteger target = GraphBasedPancakeFlipper.getFactorial(inputList.size());
		
		List<List<Integer>> agenda = new LinkedList<>(); // Remember how much faster this is with a linkedlist rather than an arraylist 
		agenda.add(inputList);
		
		String inputListNodeId = graph.addNode(inputList);
		alreadySeen.put(inputList, inputListNodeId);
				
		long before = System.currentTimeMillis();
		long lastPause = System.currentTimeMillis();
		int jumps = 1;
		while(agenda.size() > 0) {
			long loopStartTime = System.currentTimeMillis();
			inputList = agenda.get(0);
			Node comingFromNode = graph.getNode(alreadySeen.get(inputList));
			if(comingFromNode.fromArcs().iterator().hasNext()){
				Arc inputArc = comingFromNode.fromArcs().iterator().next();
				jumps = graph.getArcPayload(inputArc.getId());
			} else {
				jumps = 0;
			}
			
			// Generate all the flip permutations 
			for(int i = 0; i < inputList.size() - 1; i++) {
//				lastTime = System.currentTimeMillis();
				List<Integer> flipped = flip(i, inputList);
//				reportTime("flip");

				// If it's not already there
//				lastTime = System.currentTimeMillis();
				if(!alreadySeen.containsKey(flipped)) {
//					reportTime("containsKey");
					String newNodeId = graph.addNode(flipped);
					graph.addArc(comingFromNode.getId(), jumps + 1, newNodeId);
					agenda.add(flipped);
					alreadySeen.put(flipped, newNodeId);
				}
				
				long now = System.currentTimeMillis();
				if(now - lastPause > 10) {
					try {
						Thread.sleep(7);
					} catch (InterruptedException ie) {
					}
					lastPause = System.currentTimeMillis();
				}
			}
			agenda.remove(inputList);  // Check this hasn't broken it
			
			if(alreadySeen.size() % 10000 == 0) {				
				reportTimes();
				System.out.print("Count: " + alreadySeen.size() + " ");
				double percentage = (((double)alreadySeen.size()) / (double) target.intValue()) * 100.0;
				System.out.print("Percentage: " + percentage + " ");
				
				long now = System.currentTimeMillis();
				double elapsed = ((double) now - before);
				double totalEstimatedTime = ((elapsed / percentage) * 100.0);
				double timeLeft = totalEstimatedTime - elapsed;
				System.out.println("Remaining: " + (timeLeft / 1000.0) + " ");	
			}
			
			long timeNow = System.currentTimeMillis();
			recorder.addDatum("loop", timeNow - loopStartTime);

		}

		long after = System.currentTimeMillis();
		double elapsed = ((double) after - before) / 1000.0;
		
		int totalFlips = getSumOfFlips(graph);		
		
//		System.out.println("Graph:\n" + getNodePayloads(graph));
		System.out.println("Node count: " + graph.getNodeCount());
//		System.out.println("Arcs:\n" + getArcPayloads(graph));
		
		System.out.println("FlipTotal: " + totalFlips);
		
		double average = (double)  totalFlips / (double) graph.getNodeCount();
		System.out.println("Average: " + average);
		System.out.println("Elapsed: " + elapsed);
		
		return graph;
	}
	
	private void reportTimes() {
		System.out.println("Times: " + recorder);
	}
	
	private int getSumOfFlips(Graph<List<Integer>, Integer> graph) {
		
		int flipsTotal = 0;
		for (Arc arc : graph.getArcs().values()) {
			flipsTotal += graph.getArcPayload(arc.getId());
		}
		
		return flipsTotal;
	}
	
	private String getNodePayloads(Graph<List<Integer>, Integer> graph) {
		StringBuffer buffer = new StringBuffer();

		for (Node node : graph.getNodes().values()) {
			buffer.append(node.getId()).append(":").append(graph.getNodePayload(node.getId())).append("\n");
		}
		
		return buffer.toString();
	}
	
	private String getArcPayloads(Graph<List<Integer>, Integer> graph) {
		StringBuffer buffer = new StringBuffer();

		for (Arc arc : graph.getArcs().values()) {
			buffer.append(arc.getId()).append("->").append(arc).append("_").append(graph.getArcPayload(arc.getId())).append("\n");
		}
		
		return buffer.toString();
	}
	
	private List<Integer> flip(int flipPoint, final List<Integer> stack) {

		List<Integer> flipped = new ArrayList<Integer>();
		flipped.addAll(reverse(stack.subList(0, stack.size() - flipPoint)));
		flipped.addAll(stack.subList(stack.size() - flipPoint, stack.size()));
		return flipped;
	}
	
	private List<Integer> reverse(List<Integer> list){
		List<Integer> copy = new ArrayList<>(list);
		Collections.reverse(copy);
		return copy;
	}
	
	public static BigInteger getFactorial(int size) {
		
		BigInteger fact = BigInteger.valueOf(1);

	    for (int i = 1; i <= size; i++)
	        fact = fact.multiply(BigInteger.valueOf(i));
		
		return fact;		
	}
	
	public static void main(String[] args) {
		GraphBasedPancakeFlipper flipper = new GraphBasedPancakeFlipper();
		flipper.flip(1, 2, 3, 4);

	}

}
