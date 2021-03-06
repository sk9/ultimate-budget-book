package de.g18.ubb.android.client.shared.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.BookingsAdapter.BookingTag;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.util.StringUtil;

/**
 * bereitet einzelne Buchungen so auf, das der Namen und der Betrag dargestellt werden können.
 * 
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public final class BookingsAdapter extends AbstractAdapter<Booking, BookingTag> {

	public BookingsAdapter(Context aContext) {
        this(aContext, new ArrayList<Booking>());
    }

    public BookingsAdapter(Context aContext, List<Booking> aBooking) {
        super(aContext, R.layout.budgetbookbooking_row, aBooking);
    }

    @Override
    protected BookingTag createTag(View aConvertView) {
        BookingTag tag = new BookingTag();
        tag.name = (TextView) aConvertView.findViewById(R.BudgetBookBookingRowLayout.name);
        tag.amount = (TextView) aConvertView.findViewById(R.BudgetBookBookingRowLayout.amount);
        return tag;
    }

    @Override
    protected void updateTag(BookingTag aTag, Booking aEntry) {
        String ammountPrefix = StringUtil.EMPTY;
    	if (aEntry.getType() == BookingType.SPENDING) {
    	    ammountPrefix = "-";
			aTag.amount.setTextColor(Color.RED);
		} else {
			aTag.amount.setTextColor(Color.BLACK);
		}

        aTag.name.setText(aEntry.getBookingName());
        aTag.amount.setText(ammountPrefix + Float.toString(aEntry.getAmount()) + " " + UBBConstants.CURRENCY_EURO_SIGN);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    static final class BookingTag {
        TextView name;
        TextView amount;
    }
}
