package com.spoj.practice.classic;

import java.io.*;
import java.util.*;

/**
 * Java code gets TLE yet again -_- .
 * <br />Only if I had a dollar for every time this happened on SPOJ.
 * C++ code AC : KthNumber.cpp in the same folder.
 */
class KthNumber
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, q;
		int[] arr;
		List<Integer>[] tree;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			q = in.nextInt();
			arr = new int[n];
			tree = new List[n << 2];

			List<Integer> list = new ArrayList<>(n);

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				list.add(i, arr[i]);
			}

			computeIndices(list);
			build(1, 0, n - 1);

			while (q-- > 0)
			{
				int low, high;
				int left, right, req, k, ans;

				low = 0;
				high = n - 1;
				left = in.nextInt() - 1;
				right = in.nextInt() - 1;
				k = in.nextInt();
				req = right - left + 1 - k;
				ans = 0;

				while (low <= high)
				{
					int mid = low + high >> 1;
					int cnt = query(1, 0, n - 1, left, right, mid);

					if (cnt <= req)
					{
						ans = mid;
						high = mid - 1;
					}
					else
						low = mid + 1;
				}

				out.println(list.get(ans));
			}
		}

		void computeIndices(List<Integer> list)
		{
			Collections.sort(list);
			Map<Integer, Integer> map = new HashMap<>(n << 1);
			int counter = 0;

			for (int i = 0; i < n; i++)
				if (!map.containsKey(list.get(i)))
					map.put(list.get(i), counter++);

			for (int i = 0; i < n; i++)
				arr[i] = map.get(arr[i]);
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = new ArrayList<>();
				tree[node].add(arr[treeStart]);

				return;
			}

			int mid = treeStart + treeEnd >> 1;

			build(node << 1, treeStart, mid);
			build((node << 1) + 1, mid + 1, treeEnd);

			List<Integer> left, right;
			int l, r, x, y;

			left = tree[node << 1];
			right = tree[(node << 1) + 1];
			l = left.size();
			r = right.size();
			x = y = 0;
			tree[node] = new ArrayList<>();

			while (x < l || y < r)
			{
				if (left.get(x) < right.get(y))
				{
					tree[node].add(left.get(x++));

					if (x == l)
					{
						while (y < r)
							tree[node].add(right.get(y++));
					}
				}
				else
				{
					tree[node].add(right.get(y++));

					if (y == r)
					{
						while (x < l)
							tree[node].add(left.get(x++));
					}
				}
			}
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, long val)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return findBigger(node, val);

			int mid = treeStart + treeEnd >> 1;
			int left, right;

			left = query(node << 1, treeStart, mid, rangeStart, rangeEnd, val);
			right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd, val);

			return left + right;
		}

		int findBigger(int node, long val)
		{
			List<Integer> list = tree[node];
			int size, left, right, mid;

			size = list.size();
			left = 0;
			right = size - 1;

			while (left <= right)
			{
				mid = left + right >> 1;

				if (list.get(mid) <= val)
				{
					if (mid == size - 1)
						return 0;

					if (list.get(mid + 1) > val)
						return size - mid - 1;

					left = mid + 1;
				}
				else
				{
					if (mid == 0)
						return size;

					if (list.get(mid - 1) <= val)
						return size - mid;

					right = mid - 1;
				}
			}

			return 0;
		}

		public Solver(InputReader in, OutputWriter out)
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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
				} catch (IOException e)
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
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

}

/*

8 2
5 3 2 4 6 8 5 2
3 6 1
3 6 2

*/
