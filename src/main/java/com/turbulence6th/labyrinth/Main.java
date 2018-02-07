package com.turbulence6th.labyrinth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {

		URL url = Main.class.getClassLoader().getResource("labyrinth.txt");

		Character[][] map = Files.lines(Paths.get(url.toURI()))
				.map(e -> e.chars().mapToObj(f -> (char) f).toArray(Character[]::new)).toArray(Character[][]::new);

		Arrays.stream(map).forEach(e -> {
			Arrays.stream(e).forEach(System.out::print);
			System.out.println();
		});
		
		System.out.println();
		
		int iStart = 1, jStart = 2;
		int iEnd = 0, jEnd = 22;

		int[][] distances = new int[map.length][map[0].length];
		Arrays.stream(distances).forEach(e -> Arrays.fill(e, Integer.MAX_VALUE));
		fillMap(map, distances, iStart, jStart, 0);
		
		Arrays.stream(distances).forEach(e -> {
			Arrays.stream(e).forEach(f -> System.out.print((f == Integer.MAX_VALUE ? "MAX" : String.valueOf(f)) + " "));
			System.out.println();
		});
		
		System.out.println();
		
		List<Character> directions = getDirections(distances, iEnd, jEnd);
		
		directions.stream().map(e -> e + " ").forEach(System.out::print);
		
	}
	
	private static List<Character> getDirections(int[][] distances, int i, int j) {
		int iCurrent = i, jCurrent = j;
		List<Character> directions = new ArrayList<>();
		while(distances[iCurrent][jCurrent] != 0) {
			char direction = '*';
			int iMin = -1, jMin = -1, min = Integer.MAX_VALUE;
			if(iCurrent - 1 >= 0 && distances[iCurrent - 1][jCurrent] < min) {
				direction = 'D';
				iMin = iCurrent - 1;
				jMin = jCurrent;
				min = distances[iCurrent - 1][jCurrent];
			}
			
			if(jCurrent - 1 >= 0 && distances[iCurrent][jCurrent - 1] < min) {
				direction = 'R';
				iMin = iCurrent;
				jMin = jCurrent - 1;
				min = distances[iCurrent][jCurrent - 1];
			}
			
			if(iCurrent + 1 < distances.length && distances[iCurrent + 1][jCurrent] < min) {
				direction = 'U';
				iMin = iCurrent + 1;
				jMin = jCurrent;
				min = distances[iCurrent + 1][jCurrent];
			}
			
			if(jCurrent + 1 < distances[0].length && distances[iCurrent][jCurrent + 1] < min) {
				direction = 'L';
				iMin = iCurrent;
				jMin = jCurrent + 1;
				min = distances[iCurrent][jCurrent + 1];
			}
			
			iCurrent = iMin;
			jCurrent = jMin;
			directions.add(0, direction);
		}
		
		return directions;
	}

	private static void fillMap(Character[][] map, int[][] distances, int i, int j, int d) {
		
		if(i < 0 || j < 0 || i >= map.length || j >= map[0].length || map[i][j] == 'X') {
			return;
		}
		
		if(d < distances[i][j]) {
			distances[i][j] = d;
		}
		
		else {
			return;
		}

		fillMap(map, distances, i - 1, j, d + 1);
		fillMap(map, distances, i, j - 1, d + 1);
		fillMap(map, distances, i + 1, j, d + 1);
		fillMap(map, distances, i, j + 1, d + 1);

	}

}
