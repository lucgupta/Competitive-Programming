package com.codeforces.competitions.educational.year2017.round19;

import java.io.*;
import java.util.*;

public final class TaskA
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
        int n, k;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			k = in.nextInt();

			List<Integer> div = new ArrayList<>();

			for (int i = 2; i <= n / 2; i++)
			{
				while (n % i == 0)
				{
					div.add(i);
					n /= i;
				}

				if (n == 1)
					break;
			}

			if (n > 1)
				div.add(n);

			if (div.size() < k)
				out.println(-1);
			else
			{
				for (int i = 0; i < k - 1; i++)
					out.print(div.get(i) + " ");

				int prod = 1;

				for (int i = k - 1; i < div.size(); i++)
					prod *= div.get(i);

				out.println(prod);
			}
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
