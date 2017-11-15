package com.codechef.practice.easy.year2015;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

class EnormousInputTest
{
	private static int n, k, t, count;

	public static void main(String[] args)
	{
		start();
	}

	public static void start()
	{
		InputReader inputReader = new InputReader(System.in);

			n = inputReader.readInt();
			k = inputReader.readInt();

			for (int i = 0; i < n; i++)
			{
				t = inputReader.readInt();

				if (t % k == 0)
					count++;
			}
			
			inputReader.close();
		
		System.out.println(count);
	}
	
	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[102400]; //a byte buffer to store the characters in 
		private int curChar; //to hold the count of current character
		private int numChars; //number of characters

		public InputReader(InputStream stream)
		{ //pass the inputstream class
			this.stream = stream;
		}

		public final int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();
			
			if (curChar >= numChars)
			{
				curChar = 0;
			
				try
				{
					numChars = stream.read(buf); //this is the implicit function present in the inputStream which reads a Character into a temporary buffer
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
		public final int readInt()
		{ //a function to read nextInteger skipping the newlines and empty spaces
			int c = read();
			
			while (isSpaceChar(c)) //
				c = read();
			
			int sgn = 1;
			
			if (c == '-')
			{ //if the number is negative
				sgn = -1;
				c = read();
			}

			int res = 0; //integer variable to hold the number
			
			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public final long readLong()
		{ // similar as Integer
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-')
			{
				sgn = -1;
				c = read();
			}
			
			long res = 0;
			
			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public final String readString()
		{
			int c = read(); //read function returns one character at a time and the unicode value of the character is returned
			
			while (isSpaceChar(c))
				c = read();
			
			StringBuilder res = new StringBuilder(); //using a String Builder to build the String
			
			do
			{
				res.appendCodePoint(c); // appendCodePoint function to append the character from its unicode value
				c = read();
			} while (!isSpaceChar(c));
			
			return res.toString();
		}
		
		public final static boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public final String next()
		{
			return readString();
		}
		
		public final void close()
		{
			try
			{
				this.stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}

}

