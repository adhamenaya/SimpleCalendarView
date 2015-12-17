package com.adhamenaya.calendar;

import java.util.ArrayList;
import java.util.List;

import com.adhamenaya.cal.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class MainActivity extends FragmentActivity {

	private GridView gv;
	private Spinner spMonth;
	private Spinner spYear;
	private int minYears = 1980;
	private int maxYears = 2080;
	private int currentDay;
	private int currentMonth;
	private int selectedMonth;
	private int selectedYear;
	private int currentYear;
	private Button btnPrev;
	private Button btnNext;
	private ProgressBar loader;
	private OnItemSelectedListener selectListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initUI();

		btnPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				prevCal();
			}
		});
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				nextCal();
			}
		});

		selectListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		};
		spMonth.setOnItemSelectedListener(selectListener);
		spYear.setOnItemSelectedListener(selectListener);

	}

	private void initUI() {
		// init calendar
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		currentYear = calendar.get(java.util.Calendar.YEAR);
		currentMonth = calendar.get(java.util.Calendar.MONTH);
		currentDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);

		selectedMonth = currentMonth;
		selectedYear = currentYear;

		setContentView(R.layout.activity_main);
		gv = (GridView) findViewById(R.id.gvCalendar);
		btnPrev = (Button) findViewById(R.id.btnPrev);
		btnNext = (Button) findViewById(R.id.btnNext);
		loader = (ProgressBar) findViewById(R.id.loader);

		// Month spinner
		spMonth = (Spinner) findViewById(R.id.spMonth);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.month_arrays,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spMonth.setAdapter(adapter);

		// Year spinner
		spYear = (Spinner) findViewById(R.id.spYear);

		List<String> list = new ArrayList<String>();
		for (int i = maxYears; i >= minYears; i--) {
			list.add(String.valueOf(i));

		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spYear.setAdapter(dataAdapter);
		spMonth.setSelection(currentMonth);
		spYear.setSelection(maxYears - currentYear);

		showCalendar(currentMonth, currentYear);

	}

	private void nextCal() {

		Log.d("month before", selectedMonth + "");

		if (selectedMonth < 11) {
			selectedMonth = selectedMonth + 1;
		} else {
			selectedMonth = 0;
			if (selectedYear < maxYears)
				selectedYear++;
		}

		showCalendar(selectedMonth, selectedYear);
		spMonth.setSelection(selectedMonth);
		spYear.setSelection(maxYears - selectedYear);
		Log.d("month after", selectedMonth + "");

	}

	private void prevCal() {

		Log.d("month before", selectedMonth + "");

		if (selectedMonth > 0) {
			selectedMonth--;
		} else {
			selectedMonth = 11;
			if (selectedYear > minYears)
				selectedYear--;
		}

		showCalendar(selectedMonth, selectedYear);
		spMonth.setSelection(selectedMonth);
		spYear.setSelection(maxYears - selectedYear);
		Log.d("month after", selectedMonth + "");

	}

	private void showCalendar(int M, int Y) {
		
		int dayAfterThisMonth = 1;

		if (loader != null)
			loader.setVisibility(View.VISIBLE);

		ArrayList<CalendarItem> data = new ArrayList<CalendarItem>();
		// check for leap year
		if (M == 1 && Calendar.isLeapYear(Y))
			Calendar.days[M] = 29;

		// starting day
		int d = Calendar.day(M, 1, Y);

		int size = 7 + d + Calendar.days[M];

		double row = Math.ceil((double) size / (double) 7);

		double fullSize = row * 7;
		fullSize = 49;
		
		for (int i = 0; i < fullSize; i++) {

			if (i < 7) {
				data.add(createItem(1, getApplicationContext().getResources()
						.getStringArray(R.array.days).clone()[i], true, false,
						false));
			} else if (i >= 7 && i < 7 + d) {
				// Get the lest days from the previous month
				int prevMonth = M - 1;

				if (prevMonth < 0)
					prevMonth = 11;

				int prevMonthDays = Calendar.days[prevMonth];
				data.add(createItem(1,
						String.valueOf(prevMonthDays - d + i - 7 + 1), false,
						true, false));

			} else if (i >= 7 + d && i < 7 + d + Calendar.days[M]) {
				// print the calendar

				if (M == currentMonth
						&& Y == currentYear
						&& Integer.parseInt(String.valueOf(i - 7 - d + 1)) == currentDay) {
					data.add(createItem(1, String.valueOf(i - 7 - d + 1),
							false, false, true));
				} else {
					data.add(createItem(1, String.valueOf(i - 7 - d + 1),
							false, false, false));

				}

			} else {
				
				data.add(createItem(1, String.valueOf(dayAfterThisMonth), false, true, false));
				dayAfterThisMonth++;
			}

		}

		CalenderAdapter ad = new CalenderAdapter(this, data,
				R.layout.calendar_cell);

		gv.setAdapter(ad);

		if (loader != null)
			loader.setVisibility(View.GONE);

	}
	private CalendarItem createItem(int id, String txt, boolean isHeader,
			boolean isEmpty, boolean isCurrent) {

		CalendarItem ci = new CalendarItem();
		ci.id = id;
		ci.title = txt;
		ci.isHeader = isHeader;
		ci.isEmpty = isEmpty;
		ci.isCurrent = isCurrent;
		return ci;
	}

}
