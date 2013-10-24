package ru.tulupov.nsuconnect.slidingmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ru.tulupov.nsuconnect.R;

public class SlidingMenyActivity extends BaseSlidingFragmentActivity {

	private Fragment mContent;

	public SlidingMenyActivity() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.content_frame);
        setBehindContentView(R.layout.menu_frame);
		// set the Above View
//		if (savedInstanceState != null)
//			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
//		if (mContent == null)
//			mContent = new ColorFragment(R.color.red);
//
//		// set the Above View
//		setContentView(R.layout.content_frame);
//		getSupportFragmentManager()
//		.beginTransaction()
//		.replace(R.id.content_frame, mContent)
//		.commit();
//
//		// set the Behind View
//		setBehindContentView(R.layout.menu_frame);
//		getSupportFragmentManager()
//		.beginTransaction()
//		.replace(R.id.menu_frame, new SlidingMenuFragment())
//		.commit();

		// customize the SlidingMenu

		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}

}
