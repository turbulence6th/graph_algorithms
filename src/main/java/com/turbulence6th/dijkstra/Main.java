package com.turbulence6th.dijkstra;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {

		URL url = Main.class.getClassLoader().getResource("dijkstra.txt");

		Graph graph = new Graph();

		Files.lines(Paths.get(url.toURI())).forEach(e -> {
			String[] nodeDatas = e.split(" ");
			graph.addEdge(nodeDatas[0], nodeDatas[1], Integer.parseInt(nodeDatas[2]));
		});
		
		System.out.println(graph);
		
		graph.dijkstra("a", "e");

	}

}
