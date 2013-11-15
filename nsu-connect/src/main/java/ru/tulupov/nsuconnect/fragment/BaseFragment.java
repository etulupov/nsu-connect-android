package ru.tulupov.nsuconnect.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ru.tulupov.nsuconnect.activity.BaseActivityInterface;


public abstract class BaseFragment extends Fragment implements BaseActivityInterface {

    @Override
    public void onResume() {
        super.onResume();

        EasyTracker.getInstance(getActivity()).send(MapBuilder.createAppView().set(Fields.SCREEN_NAME, ((Object) this).getClass().getName()).build());
    }

    public int getTitleId() {
        return 0;
    }

    public int getMenuItemId() {
        return 0;
    }

    public boolean onBackPressed() {
        return false;
    }

    private BaseActivityInterface getBaseActivity() {
        return (BaseActivityInterface) getActivity();
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        if (getBaseActivity() != null) {
            getBaseActivity().addFragment(fragment);
        }
    }

    @Override
    public void showFragment(BaseFragment fragment) {
        if (getBaseActivity() != null) {
            getBaseActivity().showFragment(fragment);
        }
    }

    @Override
    public SlidingMenu getSlidingMenu() {
        if (getBaseActivity() != null) {
            return getBaseActivity().getSlidingMenu();
        }
        return null;
    }

    @Override
    public void closeFragment() {
        if (getBaseActivity() != null) {
            getBaseActivity().closeFragment();
        }
    }

    protected View findViewById(int id) {
        if (getView() != null) {
            return getView().findViewById(id);
        }

        return null;
    }

    @Override
    public void showDialog(DialogFragment fragment) {
        if (getBaseActivity() != null) {
            getBaseActivity().showDialog(fragment);
        }
    }

    public void showToast(int id) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();
        }
    }
}
