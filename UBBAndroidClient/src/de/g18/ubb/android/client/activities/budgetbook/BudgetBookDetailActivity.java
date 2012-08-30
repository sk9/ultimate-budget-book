package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;

import org.hibernate.cfg.NotYetImplementedException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookDetailActivity extends
		AbstractActivity<BudgetBookOverviewModel> {

	private Button delete;
	private Button buttonPrevious;
	private Button buttonNext;

	private ArrayList<BudgetBookModel> transferredData;
	private TextView budgetBookDetails;

	// maps to: 0 = day, 1 = month and 2 = year // default = 0
	private int dynamicViewLayoutID = 0;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private boolean viewMonthSet = false;
	private boolean viewYearSet = false;
	private boolean viewDaySet = false;

	private GestureDetector gestureDetector;

	@Override
	protected BudgetBookOverviewModel createModel() {
		return new BudgetBookOverviewModel();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_budget_book_details, menu);
		return true;
	}

	protected int getdynamicLinearLayoutID() {
		return dynamicViewLayoutID;
	}

	protected void setdynamicLinearLayoutID(int aNewValue) {
		dynamicViewLayoutID = aNewValue;
	}

	protected int getLinearLayoutID() {
		switch (dynamicViewLayoutID) {
		case 0:
			return R.BudgetBook.daylinearLayout;
		case 1:
			return R.BudgetBook.monthlinearLayout;
		case 2:
			return R.BudgetBook.yearlinearLayout;
		default:
			return R.BudgetBook.daylinearLayout;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_budget_book_detail;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initComponents();
		initGestureComponent();
		loadExtraContent("BudgetBookModel");
		showDayDetailsOnView();
		initEventHandling();
	}

	private void initGestureComponent() {
		gestureDetector = new GestureDetector(this,
				new BudgetBookDetailGestureDetector());
		ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBook.details);
		vf.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		});
	}

	private void loadExtraContent(String key) {
		Bundle b = getIntent().getExtras();
		transferredData = b.getParcelableArrayList(key);
	}

	private void showDayDetailsOnView() {
		if (!viewDaySet) {
			LinearLayout lView = (LinearLayout) findViewById(getLinearLayoutID());
			budgetBookDetails = new TextView(this);
			budgetBookDetails.setText(transferredData.get(0).getName());
			lView.addView(budgetBookDetails);
			viewDaySet = true;
		}
	}

	private void showMonthDetailsOnView() {
		if (!viewMonthSet) {
			LinearLayout lView = (LinearLayout) findViewById(getLinearLayoutID());
			budgetBookDetails = new TextView(this);
			budgetBookDetails.setText(transferredData.get(0).getName());
			lView.addView(budgetBookDetails);
			viewMonthSet = true;
		}
	}

	private void showYearDetailsOnView() {
		if (!viewYearSet) {
			LinearLayout lView = (LinearLayout) findViewById(getLinearLayoutID());
			budgetBookDetails = new TextView(this);
			budgetBookDetails.setText(transferredData.get(0).getName());
			lView.addView(budgetBookDetails);
			viewMonthSet = true;
		}
	}

	private void showDetailsOnView() {
		// über getDynamicLinearLayoutID() wissen wir in welcher view wir uns
		// befinden
		switch (dynamicViewLayoutID) {
		case 0:
			showDayDetailsOnView(); // per default immer initialisiert
			break;
		case 1:
			showMonthDetailsOnView(); // wird dynamisch initialisiert
			break;
		case 2:
			showYearDetailsOnView(); // wird dynamisch initialisiert
			break;
		default:
			showDayDetailsOnView();
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.menu_addBooking:
			Toast.makeText(this, "Menü - Neuer Eintrag wurde ausgewählt",
					Toast.LENGTH_SHORT).show();
			break;

		case R.menu.menu_categoryBooking:
			switchToCategoryOverview();
			break;

		default:
			break;
		}
		return true;
	}

	private void switchToCategoryOverview() {
		Intent i = new Intent(getApplicationContext(),
				CategoryOverviewActivity.class);
		i.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
		startActivity(i);
	}

	private void initComponents() {
		delete = (Button) findViewById(R.BudgetBookDetails.deleteEntry);
	}

	private void initEventHandling() {
		delete.setOnClickListener(new DeleteBudgetBookButtonListener());
	}

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class DeleteBudgetBookButtonListener implements
			OnClickListener {

		public void onClick(View aView) {
			throw new NotYetImplementedException(
					"Todo: nach drücken auf delete sollen checkboxen dem layout hinzugefügt werden");
		}
	}

	class BudgetBookDetailGestureDetector extends SimpleOnGestureListener {
		private static final String TAG = "BudgetBookDetailGestureDetector";

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBook.details);
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
				return false;
			}

			// rechts nach links
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				vf.setInAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_right));
				vf.setOutAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_left));
				vf.showNext();
				setdynamicLinearLayoutID(vf.getDisplayedChild());
				showDetailsOnView();
				// Log.d(TAG, Integer.toString(getdynamicLinearLayoutID()));
				// links nach rechts
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				vf.setInAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_left));
				vf.setOutAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_right));
				vf.showPrevious();
				setdynamicLinearLayoutID(vf.getDisplayedChild());
				showDetailsOnView();
				// Log.d(TAG, Integer.toString(getdynamicLinearLayoutID()));
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

}
