package com.adhamenaya.calendar;

public class Calendar {

	/**********************************************************************
	 * Given the month (M), day (D), and year (Y), return which day of the week
	 * it falls on according to the Gregorian calendar. For M use 1 for January,
	 * 2 for February, and so forth. Outputs 0 for Sunday, 1 for Monday, and so
	 * forth.
	 **********************************************************************/
	// months[i] = name of month i
	public static String[] months = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };

	// days[i] = number of days in month i
	static int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static int day(int M, int D, int Y) {

		M = M + 1;

		int y = Y - (14 - M) / 12;
		int x = y + y / 4 - y / 100 + y / 400;
		int m = M + 12 * ((14 - M) / 12) - 2;
		int d = (D + x + (31 * m) / 12) % 7;
		return d;
	}

	// return true if the given year is a leap year
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 != 0))
			return true;
		if (year % 400 == 0)
			return true;
		return false;
	}

	public static void main(String[] args) {
		int M = Integer.parseInt("5"); // month (Jan = 1, Dec = 12)
		int Y = Integer.parseInt("1987"); // year

		// check for leap year
		if (M == 2 && isLeapYear(Y))
			days[M] = 29;

		// print calendar header
		System.out.println("   " + months[M] + " " + Y);
		System.out.println(" S  M Tu  W Th  F  S");

		// starting day
		int d = day(M, 1, Y);

		// print the calendar
		for (int i = 0; i < d; i++)
			System.out.print("   ");
		for (int i = 1; i <= days[M]; i++) {
			System.out.printf("%2d ", i);
			if (((i + d) % 7 == 0) || (i == days[M]))
				System.out.println();
		}

	}
}
