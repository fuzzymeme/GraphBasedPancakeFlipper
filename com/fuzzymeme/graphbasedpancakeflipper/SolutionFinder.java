package com.fuzzymeme.graphbasedpancakeflipper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import com.fuzzymeme.graphbasedpancakeflipper.utils.CounterMap;
import com.fuzzymeme.graphbasedpancakeflipper.utils.CounterMapUtils;
import com.richard.graphs.Arc;
import com.richard.graphs.Graph;
import com.richard.graphs.Node;

public class SolutionFinder {

	private CounterMap<Integer> flipSetCounts = new CounterMap<>();
	
	private int findSolutionFor(Graph<List<Integer>, Integer> graph, Integer ... inputArray) {
		
		List<Integer> inputList = Arrays.asList(inputArray);
		
		Node previousNode = graph.getFirstNodeByValue(inputList);
		List<Integer> previousValue = graph.getNodePayload(previousNode.getId());
		Collection<Arc> arcs = previousNode.fromArcs();
		
		int flipCount = 0;
		while(arcs.iterator().hasNext()) {
			String nextNodeId = arcs.iterator().next().getFromNodeId();
			Node nextNode = graph.getNode(nextNodeId);
			List<Integer> nextValue = graph.getNodePayload(nextNodeId);
			System.out.println("Flip " + flipCount + " (" + previousNode.getId() + "): " + previousValue + " connected to " + nextValue);
			arcs = nextNode.fromArcs();
			flipCount++;
			
			previousNode = nextNode;
			previousValue = nextValue;
		}
		
		System.out.println("Complete in " + flipCount + " flips");
		
		return flipCount;
	}
	
	public Graph<List<Integer>, Integer> generateGraph(int stackLength) {
		GraphBasedPancakeFlipper flipper = new GraphBasedPancakeFlipper();
		Integer[] array = IntStream.range(0, stackLength).boxed().toArray(Integer[]::new); 
		
		Graph<List<Integer>, Integer> graph = flipper.flip(array);
		return graph;
	}
	
	private void findSolution(Integer ... inputArray) {
		
		Graph<List<Integer>, Integer> graph = generateGraph(inputArray.length);
		findSolutionFor(graph, inputArray);
	}
	
	private void findAllSolutions(int length) {
		
		Graph<List<Integer>, Integer> graph = generateGraph(length);
		
		PermutationGenerator permGenerator = new PermutationGenerator();
		List<List<Integer>> perms = permGenerator.getAllPermutations(length);
		
		int totalFlipCount = 0;
		int index = 0;
		for(List<Integer> perm: perms) {
			int flipCount = findSolutionFor(graph, perm.toArray(new Integer[perm.size()]));
			flipSetCounts.inc(flipCount);
			totalFlipCount += flipCount;
			System.out.println(index + ": " + perm + " \t" + flipCount + "   " + totalFlipCount);
			index++;
		}
		
		System.out.println("CounterMap:\n" + CounterMapUtils.toPrettyPrintString(flipSetCounts));
	}
	
	public static void main(String[] args) {
		
		SolutionFinder solutionFinder = new SolutionFinder();	
//		solutionFinder.findAllSolutions(5);		
		solutionFinder.findSolution(2, 4, 0, 3, 1);
	}
}
