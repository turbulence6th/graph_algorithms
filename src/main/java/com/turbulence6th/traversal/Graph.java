package com.turbulence6th.traversal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {

	private List<Node> nodes;

	public Graph() {
		this.nodes = new ArrayList<>();
	}

	public void addEdge(String data1, String data2) {
		Node node1 = this.nodes.stream().filter(e -> e.data.equals(data1)).findFirst().orElseGet(() -> new Node(data1));
		Node node2 = this.nodes.stream().filter(e -> e.data.equals(data2)).findFirst().orElseGet(() -> new Node(data2));
		node1.adjacencies.add(node2);
		node2.adjacencies.add(node1);
	}

	public void dfs() {
		if (this.nodes.size() == 0) {
			System.out.println("Graph is empty");
			return;
		}

		Node firstNode = this.nodes.get(0);

		Stack<Node> stack = new Stack<>();
		stack.push(firstNode);

		Set<Node> visitedNodes = new HashSet<>();
		visitedNodes.add(firstNode);

		System.out.println(firstNode.data);

		while (!stack.isEmpty()) {
			Node node = stack.peek();
			Optional<Node> optionalChild = node.adjacencies.stream().filter(e -> !visitedNodes.contains(e)).findFirst();
			if (optionalChild.isPresent()) {
				Node child = optionalChild.get();
				visitedNodes.add(child);
				System.out.println(child.data);
				stack.push(child);
			}

			else {
				stack.pop();
			}
		}
	}

	public void bfs() {
		if (this.nodes.size() == 0) {
			System.out.println("Graph is empty");
			return;
		}

		Node firstNode = this.nodes.get(0);

		Queue<Node> queue = new LinkedList<>();
		queue.add(firstNode);

		Set<Node> visitedNodes = new HashSet<>();
		visitedNodes.add(firstNode);

		System.out.println(firstNode.data);

		while (!queue.isEmpty()) {
			Node node = queue.poll();
			Optional<Node> optionalChild = null;
			while ((optionalChild = node.adjacencies.stream().filter(e -> !visitedNodes.contains(e)).findAny())
					.isPresent()) {
				Node child = optionalChild.get();
				visitedNodes.add(child);
				System.out.println(child.data);
				queue.add(child);
			}
		}
	}

	private void addNode(Node node) {
		this.nodes.add(node);
	}

	@Override
	public String toString() {
		return this.nodes.toString();
	}

	private class Node {

		String data;

		List<Node> adjacencies;

		public Node(String data) {
			this.data = data;
			this.adjacencies = new ArrayList<>();
			Graph.this.addNode(this);
		}

		@Override
		public int hashCode() {
			return this.data.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Node)) {
				return false;
			}

			Node node = (Node) o;
			return this.data.equals(node.data);
		}

		@Override
		public String toString() {
			return this.data + " :"
					+ this.adjacencies.stream().reduce("", (a, b) -> a + " " + b.data, (a, b) -> a + " " + b) + "\n";
		}

	}

}
