package com.hackerearth.competitions.codemonk.numbertheory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class Fifth
{
	static int t, primeCount, primes[], cum[];
	static long l, r, k;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		Fifth fifth = new Fifth();
		
		in = fifth.new InputReader(System.in);
		out = fifth.new OutputWriter(System.out);
		
		solve();
		
		out.flush();
		
		in.close();
		out.close();
	}
	
	static void solve()
	{
		pre();
		
		t = in.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			l = in.nextLong();
			r = in.nextLong();
			k = in.nextLong();
			
			long num, den;
			int count = 0;
			int fac;
			
			num = den = 1;
			
			for (long j = l; j <= r; j++)
			{
				long sqrt = (long) Math.sqrt(j) + 1;
				long half = j / 2;
				fac = 1;
				
/*				for (int k = 0; primes[k] <= sqrt; k++)
				{
					if (j % primes[k] == 0)
					{
						fac++;
						num *= (j - 1);
						den *= j;
					}
				}*/
				
				for (int k = 0; primes[k] < j; k++)
				{
					if (j % primes[k] == 0)
					{
						fac++;
						num *= (j - 1);
						den *= j;
					}
				}
				
/*				if ((long) Math.sqrt(j) * (long) Math.sqrt(j) == j)
					fac--;*/
				
				if (fac % k == 0)
					count++;
				
				System.out.println("j : " + j + ", fac : " + fac + ", count : " + count);
			}
			
			System.out.printf("%.7f\n", (double) (count / (r - l + 1)));
		}
	}
	
	static void pre()
	{
		primes = new int[78500];
		cum = new int[78500];
		
		primes[0] = cum[0] = 2;
		primes[1] = 3;
		primes[2] = 5;
		
		cum[1] = cum[0] + primes[1];
		cum[2] = cum[1] + primes[2];
		
		primeCount = 3;
		
		for (int i = 7; i <= 1000000; i+= 2)
		{
			if (i % 10 == 5)
				continue;
			
			if (isPrime(i))
			{
				primes[primeCount] = i;
				cum[primeCount] = cum[primeCount - 1] + primes[primeCount];
				
				primeCount++;
			}
		}
		
		System.out.println("primecount : " + primeCount);
	}
	
	static boolean isPrime(int n)
	{
		int sqrt = (int) Math.sqrt(n);
		
		for (int i = 0; primes[i] <= sqrt && i < primeCount; i++)
			if (n % primes[i] == 0)
				return false;
		
		return true;
	}
	
	class InputReader
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
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	class OutputWriter
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
