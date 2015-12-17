package com.adhamenaya.calendar;

import java.util.ArrayList;

import com.adhamenaya.cal.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class CalenderAdapter extends ArrayAdapter<CalendarItem> {
	private Context context;
	private ArrayList<CalendarItem> data = new ArrayList<CalendarItem>();

	public CalenderAdapter(Context context, ArrayList<CalendarItem> data,
			int ResId) {
		super(context, ResId);
		this.context = context;
		this.data = data;

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.calendar_cell, parent, false);

			CalendarItem item = data.get(position);

			TextView ivTitle = (TextView) rowView.findViewById(R.id.tvTitle);

			ivTitle.setText(item.title);

			if (item.isHeader && !item.isEmpty) {
				ivTitle.setTypeface(null, Typeface.BOLD);
			} else if (item.isEmpty) {
				ivTitle.setTextColor(context.getResources().getColor(R.color.light_color));

			} else {

				rowView.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View arg0) {
						chooseDate(position);
						return true;
					}
				});

			}

			if (item.isCurrent) {
				ivTitle.setTextColor(Color.BLUE);
				ivTitle.setTypeface(null, Typeface.BOLD);
			}
		}
		return rowView;
	}

	private void chooseDate(final int pos) {
		final CharSequence[] items = { "Edit", "Delete", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {

				} else if (item == 1) {

				} else if (item == 2) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	static class ViewHolder {
		TextView ivTitle;
	}
}