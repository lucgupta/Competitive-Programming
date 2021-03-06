package com.checker;

import com.codeforces.competitions.year2017.round452div2.TaskF;

import java.io.*;
import java.util.Scanner;

public class Checker
{
	public static void main(String[] args) throws IOException
	{
		int tests = 1;
		long start = System.currentTimeMillis();

		while (tests-- > 0)
		{
//			TestsGenerator.main(null);
//			System.out.println("test generated");
//			System.out.println("generation in : " + (System.currentTimeMillis() - start));
			start = System.currentTimeMillis();

//			BruteSolution.main(null);
//			System.out.println("brute done in : " + (System.currentTimeMillis() - start));

			InputStream in = new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/checker/input.txt"));
			OutputStream out = new FileOutputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/checker/output.txt"));

			start = System.currentTimeMillis();
			new TaskF(in, out);
			System.out.println("sol done in : " + (System.currentTimeMillis() - start));
//			System.out.println("tests : " + tests);

/*			if (!check())
			{
				System.out.println("Caught!!");

				break;
			}*/

			if (tests % 1000 == 0)
				System.out.println("tests : " + tests);

//			System.out.println("*************");
		}

		System.out.println("time taken for all tests : " + (System.currentTimeMillis() - start));
	}

	static boolean check()
	{
		Scanner one, two;

		try
		{
			one = new Scanner(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/bruteOutput.txt"));
			two = new Scanner(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/output.txt"));

			while (one.hasNext() || two.hasNext())
			{
				if (!one.hasNext() || !two.hasNext())
					return false;

				String a, b;

				a = one.nextLine();
				b = two.nextLine();

				if (!a.equals(b))
					return false;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return true;
	}

}
