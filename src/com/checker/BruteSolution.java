package com.checker;

import java.io.*;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeString.replace;

class BruteSolution
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

		try
		{
			in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/checker/input.txt")));
			out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/checker/bruteOutput.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int v, e;
		boolean[] vis, done;
		boolean[][] poss;
		List<Integer>[] adj;
		InputReader in;
		PrintWriter out;

		int n, q;
		int[] arr;
		static final int mod = (int) 1e9 + 7;

		void solve()
		{
			n = in.nextInt();
			q = in.nextInt();
			arr = in.nextIntArray(n);

			while (q-- > 0)
			{
				int type = in.nextInt();
				int x = in.nextInt() - 1;
				int y = in.nextInt() - 1;

				if (type == 4)
					out.println(getSum(x, y));
				else
				{
					int val = in.nextInt();

					switch (type)
					{
						case 1:
							add(x, y, val);
							break;
						case 2:
							mul(x, y, val);
							break;
						case 3:
							replace(x, y, val);
							break;
					}
				}
			}
		}

		void add(int x, int y, int val)
		{
			for (int i = x; i <= y; i++)
				arr[i] += val;
		}

		void mul(int x, int y, int val)
		{
			for (int i = x; i <= y; i++)
				arr[i] = (int) CMath.mod(arr[i] * (long) val, mod);
		}

		void replace(int x, int y, int val)
		{
			for (int i = x; i <= y; i++)
				arr[i] = val;
		}

		int getSum(int x, int y)
		{
			long sum = 0;

			for (int i = x; i <= y; i++)
				sum = CMath.mod(sum + arr[i], mod);

			return (int) sum;
		}

		void solve2()
		{
			v = in.nextInt();
			e = in.nextInt();
			vis = new boolean[v];
			done = new boolean[v];
			poss = new boolean[v][v];
			adj = new List[v];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();

			for (int i = 0; i < e; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				adj[u].add(v);
			}

			for (int i = 0; i < v; i++)
			{
				poss[i][i] = true;

				for (int j = 0; j < v; j++)
				{
					Arrays.fill(vis, false);

					if (i != j && !poss[i][j])
						check(i, j);
				}
			}

/*			System.out.println("poss :");

			for (int i = 0; i < v; i++, System.out.println())
				for (int j = 0; j < v; j++)
					System.out.print(poss[i][j] ? "t " : "f ");*/

			List<Set<Integer>> list = new ArrayList<>();
			PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
			StringBuilder ans = new StringBuilder("");

			for (int i = 0; i < v; i++)
			{
				Set<Integer> set = new HashSet<>();

				if (!done[i])
				{
					list.add(set);
					findOthers(i, set);
					queue.add(set.size());
				}
			}

			for (int i = 0; i < 5; i++)
			{
				if (queue.size() == 0)
					ans.append("0,");
				else
					ans.append(queue.poll()).append(",");
			}

			out.println(ans.substring(0, ans.length() - 1));

/*			for (Set<Integer> set : list)
				System.out.println("set : " + set + ", size : " + set.size());*/
		}

		boolean check(int from, int to)
		{
			vis[from] = true;

			if (from == to)
			{
				poss[from][to] = true;

				return true;
			}

			for (int x : adj[from])
			{
				if (!vis[x])
				{
					if (check(x, to))
						return poss[from][to] = true;
				}
			}

			return false;
		}

		void findOthers(int node, Set<Integer> set)
		{
			for (int i = 0; i < v; i++)
			{
				if (poss[node][i] && poss[i][node])
				{
					done[i] = true;
					set.add(i);
				}
			}
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		public int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
		}

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c)
		{
			return c == '\n';
		}

		public void close()
		{
			try
			{
				stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

}

/*

8 14
1 2
2 3
2 5
2 6
6 2
5 6
6 7
3 7
7 3
5 4
6 4
4 8
8 4
7 8

*/
