package com.turbulence6th.dijkstra;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {

	private List<Node> nodes;

	public Graph() {
		this.nodes = new ArrayList<>();
	}

	public void addEdge(String data1, String data2, int distance) {
		Node node1 = this.nodes.stream().filter(e -> e.data.equals(data1)).findFirst().orElseGet(() -> new Node(data1));
		Node node2 = this.nodes.stream().filter(e -> e.data.equals(data2)).findFirst().orElseGet(() -> new Node(data2));
		node1.edges.add(new Edge(node2, distance));
		node2.edges.add(new Edge(node1, distance));
	}

	public void dijkstra(String from, String to) {
		Node fromNode = this.nodes.stream().filter(e -> e.data.equals(from)).findFirst().get();
		fromNode.minDistance = 0;

		Queue<Node> queue = new LinkedList<>();
		queue.add(fromNode);

		while (!queue.isEmpty()) {
			Node node = queue.poll();

			if (node.isVisited) {
				continue;
			}

			node.isVisited = true;
			
			for (Edge edge : node.edges) {
				Node child = edge.node;
				if (edge.distance + node.minDistance < child.minDistance) {
					child.minDistance = edge.distance + node.minDistance;
					child.minEdge = node;
				}

				queue.add(child);

			}
		}
		
		Node visited = this.nodes.stream().filter(e -> e.data.equals(to)).findFirst().get();
		System.out.println("Min distance: " + visited.minDistance);
		do {
			System.out.println(visited.data);
			visited = visited.minEdge;
		}
		
		while(visited != null);
	}

	private void addNode(Node node) {
		this.nodes.add(node);
	}

	@Override
	public String toString() {
		return this.nodes.toString();
	}

	private class Edge {

		Node node;

		int distance;

		public Edge(Node node, int distance) {
			this.node = node;
			this.distance = distance;
		}

	}

	private class Node {

		String data;

		List<Edge> edges;

		Node minEdge;

		int minDistance;

		boolean isVisited;

		public Node(String data) {
			this.data = data;
			this.edges = new ArrayList<>();
			this.minEdge = null;
			this.minDistance = Integer.MAX_VALUE;
			this.isVisited = false;
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
			return this.data + " :" + this.edges.stream().reduce("",
					(a, b) -> a + " " + b.node.data + " ; " + b.distance, (a, b) -> a + " " + b) + "\n";
		}

	}

}
