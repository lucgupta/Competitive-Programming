package com.codechef.practice.easy.year2016;

import java.io.*;
import java.util.*;

class Polygon
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
		int n;
		Point[] points;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			points = new Point[n];

			for (int i = 0; i < n; i++)
				points[i] = new Point(in.nextInt(), in.nextInt());

			Stack<Point> hull = getConvexHull();
			double peri = 0.0;
			Point last, prev;

			last = prev = hull.pop();

			while (hull.size() > 0)
			{
				Point top = hull.pop();

				peri += dist(top, prev);
				prev = top;

				if (hull.size() == 0)
					peri += dist(top, last);
			}

			out.printf("%.1f", peri);
		}

		Stack<Point> getConvexHull()
		{
			Stack<Point> hull = new Stack<>();

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.y == o2.y)
						return Integer.compare(o1.x, o2.x);

					return Integer.compare(o1.y, o2.y);
				}
			});

			hull.push(points[0]);

			for (int i = 1; i < n; i++)
				points[i].polarAngle = points[0].findPolarAngle(points[i]);

			Arrays.sort(points, 1, n, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.polarAngle == o2.polarAngle)
						return Double.compare(dist(points[0], o1), dist(points[0], o2));

					return Double.compare(o1.polarAngle, o2.polarAngle);
				}
			});

			int ctr = 1;

			while (ctr < n && points[ctr].equals(points[0]))
				ctr++;

			if (ctr == n)
				return hull;

			int ctr2 = ctr + 1;

			while (ctr2 < n && counterClockWise(points[0], points[ctr], points[ctr2]) == 0)
				ctr2++;

			hull.push(points[ctr2 - 1]);

			for (int i = ctr2; i < n; i++)
			{
				Point top = hull.pop();

				while (counterClockWise(hull.peek(), top, points[i]) <= 0)
					top = hull.pop();

				hull.push(top);
				hull.push(points[i]);
			}

			return hull;
		}

		double dist(Point a, Point b)
		{
			return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
		}

		int counterClockWise(Point a, Point b, Point c)
		{
			return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		}

		class Point
		{
			int x, y;
			double polarAngle;

			double findPolarAngle(Point pt)
			{
				return Math.atan2(pt.y - y, pt.x - x) * 180.0 / Math.PI;
			}

			public Point(int x, int y)
			{
				this.x = x;
				this.y = y;
			}

			@Override public boolean equals(Object obj)
			{
				Point pt = (Point) obj;

				return x == pt.x && y == pt.y;
			}

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

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
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
