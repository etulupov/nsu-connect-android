package ru.tulupov.nsuconnect.fragment;

import android.support.v4.app.Fragment;

import ru.tulupov.nsuconnect.activity.BaseActivityInterface;


public abstract class BaseFragment extends Fragment implements BaseActivityInterface {

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
}
