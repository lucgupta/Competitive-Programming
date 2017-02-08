package com.checker.util;

import com.checker.dto.Edge;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A class to generate a random tree with given number of vertices.
 */
public class GeneratorUtils
{
	/**
	 * Generates a random rooted tree(rooted at 0 or 1, so not completely random) with specified number of vertices
	 * and returns the generated tree as a list of edges.
	 * @param v the parameter for the number of vertices
	 * @param isZeroBased the parameter to specify whether the tree should be in 0-based or 1-based index
	 * @return a randomly generated rooted tree as a list of edges
	 */
	public static List<Point> getRandomTreeAsEdges(int v, boolean isZeroBased)
	{
		List<Point> edges = new ArrayList<>();

		for (int i = 1; i < v; i++)
			edges.add(new Point((int) (Math.random() * i), i));

		if (!isZeroBased)
		{
			for (Point pt : edges)
			{
				pt.x++;
				pt.y++;
			}
		}

		return edges;
	}

	/**
	 * Generates a random rooted tree(rooted at 0 or 1, so not completely random) with specified number of vertices
	 * and returns the generated tree as an array par, where par[i] denotes the parent of vertex i (par[0] = -1).
	 * @param v the parameter for the number of vertices
	 * @param isZeroBased the parameter to specify whether the tree should be in 0-based or 1-based index
	 * @return a randomly generated rooted tree as an array of the parent for each vertex
	 */
	public static int[] getRandomTreeAsParentArray(int v, boolean isZeroBased)
	{
		int[] par = new int[v];

		par[0] = -1;

		for (int i = 1; i < v; i++)
			par[i] = (int) (Math.random() * i);

		if (!isZeroBased)
		{
			for (int i = 1; i < v; i++)
				par[i]++;
		}

		return par;
	}

	/**
	 * Generates a random weighted graph with the specified number of vertices, edges and maxWeight, and returns the
	 * generated graph as a list of edges.
	 * @param v the parameter for the number of vertices
	 * @param e the parameter for the number of edges
	 * @param maxWeight the parameter for the maximum allowed weight for the edges
	 * @param isZeroBased the parameter to specify whether the tree should be in 0-based or 1-based index
	 * @return a randomly generated weighted graph as a list of edges
	 */
	public static List<Edge> generateRandomWeightedGraph(int v, int e, int maxWeight, boolean isZeroBased)
	{
		List<Edge> edges = new ArrayList<>();
		Set<Edge> set = new HashSet<>();

		for (int i = 0; i < e; i++)
		{
			int fr, to, wt;

			wt = (int) (Math.random() * maxWeight) + 1;

			do
			{
				fr = (int) (Math.random() * v);

				do
				{
					to = (int) (Math.random() * v);
				} while (to == fr);

				if (isZeroBased)
					set.add(new Edge(fr, to, wt));
				else
					set.add(new Edge(fr + 1, to + 1, wt));
			} while (set.size() == i);
		}

		edges.addAll(set);

		return edges;
	}

}
