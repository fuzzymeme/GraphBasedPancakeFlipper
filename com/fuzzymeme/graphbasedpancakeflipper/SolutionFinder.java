package com.fuzzymeme.graphbasedpancakeflipper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.richard.graphs.Arc;
import com.richard.graphs.Graph;
import com.richard.graphs.Node;

public class SolutionFinder {

	
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
	
	public Graph<List<Integer>, Integer> generateGraph() {
		GraphBasedPancakeFlipper flipper = new GraphBasedPancakeFlipper();
		Graph<List<Integer>, Integer> graph = flipper.flip(0, 1, 2, 3);
		return graph;
	}
	
	private void findSolution(Integer ... inputArray) {
		
		Graph<List<Integer>, Integer> graph = generateGraph();
		findSolutionFor(graph, inputArray);
	}
	
	private void findAllSolutions() {
		
		Graph<List<Integer>, Integer> graph = generateGraph();
		
		PermutationGenerator permGenerator = new PermutationGenerator();
		List<List<Integer>> perms = permGenerator.getAllPermutations(4);
		
		int totalFlipCount = 0;
		int index = 0;
		for(List<Integer> perm: perms) {
			int flipCount = findSolutionFor(graph, perm.toArray(new Integer[perm.size()]));
			totalFlipCount += flipCount;
			System.out.println(index + ": " + perm + " \t" + flipCount + "   " + totalFlipCount);
			index++;
		}
	}
	
	public static void main(String[] args) {
		
		SolutionFinder solutionFinder = new SolutionFinder();	
		solutionFinder.findAllSolutions();		
		solutionFinder.findSolution(1, 0, 3, 2);
	}
}
