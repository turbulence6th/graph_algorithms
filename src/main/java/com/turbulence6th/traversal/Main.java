package com.turbulence6th.traversal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {

		URL url = Main.class.getClassLoader().getResource("bfs_dfs.txt");
		
		Graph graph = new Graph();

		Files.lines(Paths.get(url.toURI())).forEach(e -> {
			String[] nodeDatas = e.split(" ");
			graph.addEdge(nodeDatas[0], nodeDatas[1]);
		});
		
		System.out.println("Graph is :");
		System.out.println(graph);
		
		System.out.println();
		System.out.println("*******************");
		System.out.println();
		
		System.out.println("DFS is :");
		graph.dfs();
		
		System.out.println();
		System.out.println("*******************");
		System.out.println();
		
		System.out.println("BFS is :");
		graph.bfs();
	}

}
