package com.hackerearth.competitions.indiahacks.year2017.qualification;

import java.io.*;
import java.util.*;

public class HackersWithBits
{
    public static void main(String[] args)
    {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
        in.close();
        out.flush();
        out.close();
    }

    static class Solver
    {
        int n, arr[];
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);

			int max = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = i; j < n; j++)
				{
					int temp = arr[i];

					arr[i] = arr[j];
					arr[j] = temp;
					max = Math.max(max, getMax());
					arr[j] = arr[i];
					arr[i] = temp;
				}
			}

			out.println(max);
		}

		int getMax()
		{
			int max = 0;
			int cnt = 0;

			for (int x : arr)
			{
				if (x == 0)
					cnt = 0;
				else
				{
					cnt++;
					max = Math.max(max, cnt);
				}
			}

			return max;
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

        public boolean isSpaceChar(int c)
        {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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

}

/*



*/
