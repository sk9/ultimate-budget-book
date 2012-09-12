package de.g18.ubb.android.client.activities.booking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.g18.ubb.android.client.utils.UBBConstants;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class BookingDatePickerDialog extends DatePickerDialog {
	
	private final int minYear;
	private final int minMonth;
	private final int minDay;

	private int maxYear;
	private int maxMonth;
	private int maxDay;

	private final Calendar mCalendar;
	private final SimpleDateFormat formatter;

	public BookingDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);

		mCalendar = Calendar.getInstance();

		// mindest Datum setzen
		minYear = UBBConstants.minYear;
		minMonth = UBBConstants.minMonth;
		minDay = UBBConstants.minDay;

		mCalendar.setTime(new Date());
		// maximales Datum setzen
		maxYear = mCalendar.get(Calendar.YEAR);
		maxMonth = mCalendar.get(Calendar.MONTH);
		maxDay = mCalendar.get(Calendar.DATE);

		// initiales Datum setzen
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, monthOfYear);
		mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		// set up date display format
		formatter = new SimpleDateFormat(UBBConstants.DATE_FORMAT);
		setTitle(formatter.format(mCalendar.getTime()));
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		boolean beforeMinDate = false;
		boolean afterMaxDate = false;

		if (year < minYear) {
			beforeMinDate = true;
		} else if (year == minYear) {
			if (month < minMonth) {
				beforeMinDate = true;
			} else if (month == minMonth) {
				if (day < minDay) {
					beforeMinDate = true;
				}
			}
		}

		if (!beforeMinDate) {
			if (year > maxYear) {
				afterMaxDate = true;
			} else if (year == maxYear) {
				if (month > maxMonth) {
					afterMaxDate = true;
				} else if (month == maxMonth) {
					if (day > maxDay) {
						afterMaxDate = true;
					}
				}
			}
		}

		// ungültige Datumseingaben werden auf min oder max datum gesetzt
		if (beforeMinDate || afterMaxDate) {
			if (beforeMinDate) {
				year = minYear;
				month = minMonth;
				day = minDay;
			} else {
				year = maxYear;
				month = maxMonth;
				day = maxDay;
			}
			view.updateDate(year, month, day);
		}

		// zeige im Dialog das Datum als Titel an
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, month);
		mCalendar.set(Calendar.DAY_OF_MONTH, day);
		setTitle(formatter.format(mCalendar.getTime()));
	}
}