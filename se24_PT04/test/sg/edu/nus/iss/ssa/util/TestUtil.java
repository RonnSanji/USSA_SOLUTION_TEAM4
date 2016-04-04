package sg.edu.nus.iss.ssa.util;

import java.util.ArrayList;
import java.util.Random;

public class TestUtil
{
	public static String generateRandomString(int length)
	{
		return generateRandomString(length, null, false);
	}

	public static String generateRandomString(int length, ArrayList<String> excludeList, boolean caseSenstive)
	{
		if (length <= 0)
		{
			return null;
		}
		Random ran = new Random();
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str = "";
		for (int i = 0; i < length; i++)
		{
			str += letters.charAt(ran.nextInt(letters.length()));
		}

		if (excludeList == null || excludeList.size() == 0)
		{
			return str;
		}
		if (!caseSenstive)
		{
			for (int i = 0; i < excludeList.size(); i++)
			{
				excludeList.set(i, excludeList.get(i).toUpperCase());
			}
		}

		while (excludeList.contains(str))
		{
			str = "";
			for (int i = 0; i < length; i++)
			{
				str += letters.charAt(ran.nextInt(letters.length()));
			}
		}

		return str;
	}
}
