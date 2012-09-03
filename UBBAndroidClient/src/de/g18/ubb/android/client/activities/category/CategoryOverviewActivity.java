package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;
import java.util.List;

import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CategoryOverviewActivity extends Activity {

	private ArrayList<BudgetBookModel> transferredData;
	private List<Category> catList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_overview);
		
		Button b = (Button) findViewById(R.id.b_category_create);
		b.setOnClickListener(new OverviewListener());
		
		Button c = (Button) findViewById(R.id.category_change);
		c.setOnClickListener(new OverviewListener());
		
		Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");
		Long i  = transferredData.get(0).getId();
		BudgetBook bb = ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i);
		
		catList = ServiceRepository.getCategoryService().getAll(bb);
		
		Spinner sp = (Spinner) findViewById(R.id.sp_categorie);
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
				android.R.layout.simple_list_item_1,
				catList);
		
		sp.setAdapter(adapter);
		
		
	}

	
	public class OverviewListener implements OnClickListener{

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.b_category_create:
				Intent intent = new Intent(getApplicationContext(),
						CategorieCreateActivity.class);
				intent.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
				startActivity(intent);
				break;
			case R.id.category_change:
				Intent intent1 = new Intent(getApplicationContext(),
						CategoryChangeActivity.class);
				intent1.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
				startActivity(intent1);
				break;
			default:
				break;
			}
		}
		
	}

}
