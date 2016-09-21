package com.codeforces.competitions.year2016.round369div2;

import java.io.*;
import java.util.InputMismatchException;

public class TaskB
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
		long[][] mat;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			mat = new long[n][n];

			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					mat[i][j] = in.nextInt();

			if (n == 1)
			{
				out.println(1);

				return;
			}

			long[] rows, cols;
			int emp = -1;

			rows = new long[n];
			cols = new long[n];

			for (int i = 0; i < n; i++)
			{
				long sum = 0;

				for (int j = 0; j < n; j++)
				{
					if (mat[i][j] == 0)
						emp = i;
					else
						sum += mat[i][j];
				}

				rows[i] = sum;
			}

			boolean poss = true;
			long tot = (emp == 0 ? rows[1] : rows[0]);

			for (int i = 0; i < n; i++)
			{
				if (i == emp)
					continue;

				if (rows[i] != tot)
				{
					poss = false;

					break;
				}
			}

			if (!poss)
				out.println(-1);
			else
			{
				int empCol = -1;

				for (int i = 0; i < n; i++)
				{
					long sum = 0;

					for (int j = 0; j < n; j++)
					{
						if (mat[j][i] == 0)
							empCol = i;
						else
							sum += mat[j][i];
					}

					cols[i] = sum;
				}

				for (int i = 0; i < n; i++)
				{
					if (i == empCol)
						continue;

					if (cols[i] != tot)
					{
						poss = false;

						break;
					}
				}

				if (!poss)
					out.println(-1);
				else
				{
					long left, right;

					left = right = 0;

					for (int i = 0, j = 0; i < n; i++, j++)
						left += mat[i][j];

					for (int i = n - 1, j = 0; i >= 0; i--, j++)
						right += mat[i][j];

					long toPut = tot - rows[emp];

					mat[emp][empCol] = toPut;

					rows = new long[n];
					cols = new long[n];

					for (int i = 0; i < n; i++)
					{
						long sum = 0;

						for (int j = 0; j < n; j++)
							sum += mat[i][j];

						rows[i] = sum;
					}

					for (int i = 0; i < n; i++)
					{
						long sum = 0;

						for (int j = 0; j < n; j++)
							sum += mat[j][i];

						cols[i] = sum;
					}

					left = right = 0;

					for (int i = 0, j = 0; i < n; i++, j++)
						left += mat[i][j];

					for (int i = n - 1, j = 0; i >= 0; i--, j++)
						right += mat[i][j];

					boolean possible = true;

					if (left != tot || right != tot)
						possible = false;

					for (int i = 0; i < n; i++)
					{
						if (rows[i] != tot)
						{
							possible = false;

							break;
						}

						if (cols[i] != tot)
						{
							possible = false;

							break;
						}
					}

					if (!possible)
						out.println(-1);
					else if (toPut <= 0)
						out.println(-1);
					else
						out.println(toPut);
				}
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

		public float nextFloat() // problematic
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

		public double nextDouble() // not completely accurate
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