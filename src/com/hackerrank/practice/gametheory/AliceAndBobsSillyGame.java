package com.hackerrank.practice.gametheory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class AliceAndBobsSillyGame
{
	public static void main(String[] args)
	{
		new AliceAndBobsSillyGame(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int g, n;
		int[] cumPrimesCnt;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			pre();
			g = in.nextInt();

			while (g-- > 0)
			{
				n = in.nextInt();

				if ((cumPrimesCnt[n] & 1) == 1)
					out.println("Alice");
				else
					out.println("Bob");
			}
		}

		void pre()
		{
			int lim = (int) 1e5 + 5;
			boolean[] isComposite = new boolean[lim];
			int curr = 2;

			while ((curr << 1) < lim)
			{
				isComposite[curr << 1] = true;
				curr++;
			}

			for (int i = 3; i < lim; i += 2)
			{
				if (isComposite[i])
					continue;

				curr = i + i;

				while (curr < lim)
				{
					isComposite[curr] = true;
					curr += i;
				}
			}

			cumPrimesCnt = new int[lim];
			cumPrimesCnt[2] = 1;

			for (int i = 3; i < lim; i++)
				cumPrimesCnt[i] = cumPrimesCnt[i - 1] + (isComposite[i] ? 0 : 1);
		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		int read()
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

		int nextInt()
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

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
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

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	private AliceAndBobsSillyGame(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		in.close();
		out.flush();
		out.close();
	}

}
