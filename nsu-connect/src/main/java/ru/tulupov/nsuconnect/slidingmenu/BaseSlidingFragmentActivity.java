package ru.tulupov.nsuconnect.slidingmenu;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuItem;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import ru.tulupov.nsuconnect.R;

public class BaseSlidingFragmentActivity extends SlidingFragmentActivity {


	protected ListFragment mFrag;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
//		if (savedInstanceState == null) {
//			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
//			mFrag = new SampleListFragment();
//			t.replace(R.id.menu_frame, mFrag);
//			t.commit();
//		} else {
//			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
//		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
