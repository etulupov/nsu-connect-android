package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.viewpagerindicator.IconPageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.activity.BaseActivityInterface;
import ru.tulupov.nsuconnect.adapter.IconPagerAdapter;
import ru.tulupov.nsuconnect.helper.ChatHelper;
import ru.tulupov.nsuconnect.helper.IntentActionHelper;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.model.SearchParameters;
import ru.tulupov.nsuconnect.model.Settings;
import ru.tulupov.nsuconnect.widget.CustomViewPager;


public class WelcomeFragment extends BaseFragment {
    public enum Page {
        YOUR_UNIVERSITY,
        TARGET_UNIVERSITY,
        YOUR_GENDER,
        TARGET_GENDER,
        YOUR_AGE,
        TARGET_AGE,
        FINISH,
    }

    protected boolean flagFirstRun;

    private List<Boolean> activePages;

    public WelcomeFragment() {
        this(true);
    }

    public WelcomeFragment(boolean flagFirstRun) {
        this.flagFirstRun = flagFirstRun;
    }

    public static WelcomeFragment newInstance(final Context context) {
        return (WelcomeFragment) Fragment.instantiate(context, WelcomeFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_welcome, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getTitleId() {
        return R.string.fgt_welcome_title;
    }

    @Override
    public int getMenuItemId() {
        return R.id.menu_settings;
    }

    private CustomViewPager pager;
    private IconPageIndicator indicator;

    private IconPagerAdapter adapter;
    private YourUniversitySettingsFragment yourUniversityFragment;
    private TargetUniversitySettingsFragment targerUniversityFragment;
    private BaseSearchSettingsFragment yourGenderFragment;
    private BaseSearchSettingsFragment targetGenderFragment;
    private BaseSearchSettingsFragment yourAgeFragment;
    private BaseSearchSettingsFragment targetAgeFragment;
    private SearchSettingFinishFragment finishFragment;
    private SearchSettingSaveFinishFragment saveFinishFragment;

    private int FIRST_ITEM_POSITION = 0;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pager = (CustomViewPager) view.findViewById(R.id.pager);
        pager.setPagingEnabled(false);


        yourUniversityFragment = YourUniversitySettingsFragment.newInstance(getActivity());
        targerUniversityFragment = TargetUniversitySettingsFragment.newInstance(getActivity());
        yourGenderFragment = YourGenderSettingsFragment.newInstance(getActivity());
        targetGenderFragment = TargetGenderSettingsFragment.newInstance(getActivity());
        yourAgeFragment = YourAgeSettingsFragment.newInstance(getActivity());
        targetAgeFragment = TargetAgeSettingsFragment.newInstance(getActivity());
        finishFragment = SearchSettingFinishFragment.newInstance(getActivity());
        saveFinishFragment = SearchSettingSaveFinishFragment.newInstance(getActivity());
        List<BaseFragment> fragments = new ArrayList<BaseFragment>();

        fragments.addAll(Arrays.asList(yourUniversityFragment, targerUniversityFragment,
                yourGenderFragment, targetGenderFragment, yourAgeFragment,
                targetAgeFragment));

        if (flagFirstRun) {
            fragments.add(finishFragment);
        } else {
            fragments.add(saveFinishFragment);
        }

        List<Integer> icons = new ArrayList<Integer>();
        icons.addAll(Arrays.asList(
                R.drawable.ic_settings_university,
                R.drawable.ic_settings_university,
                R.drawable.ic_settings_gender,
                R.drawable.ic_settings_gender,
                R.drawable.ic_settings_birthday,
                R.drawable.ic_settings_birthday
        ));


        icons.add(R.drawable.ic_settings_close);


        adapter = new IconPagerAdapter(getChildFragmentManager(), fragments, icons);

        activePages = new ArrayList<Boolean>();
        for (int i = 0; i < fragments.size(); i++) {
            activePages.add(true);
        }
        updateActivePages();

        pager.setAdapter(adapter);

        pager.setOnPagingListener(new CustomViewPager.OnPagingListener() {
            @Override
            public boolean allowPaging() {
                return isPageFilled(pager.getCurrentItem());
            }
        });
        yourUniversityFragment.setOnSelectListener(new BaseSearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                int position = selected.get(0);
                if (position == FIRST_ITEM_POSITION) {
                    navigate(Page.YOUR_GENDER);
                } else {
                    navigate(Page.TARGET_UNIVERSITY);
                }
            }
        });
        targerUniversityFragment.setOnSelectListener(new BaseSearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                navigate(Page.YOUR_GENDER);
            }
        });
        yourGenderFragment.setOnSelectListener(new BaseSearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                int position = selected.get(0);
                if (position == FIRST_ITEM_POSITION) {
                    targetGenderFragment.clearSelection();
                    navigate(Page.YOUR_AGE);
                } else {
                    navigate(Page.TARGET_GENDER);
                }
            }
        });
        targetGenderFragment.setOnSelectListener(new BaseSearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                navigate(Page.YOUR_AGE);
            }
        });
        yourAgeFragment.setOnSelectListener(new BaseSearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                int position = selected.get(0);
                if (position == FIRST_ITEM_POSITION) {
                    targetAgeFragment.clearSelection();
                    navigate(Page.FINISH);
                } else {
                    navigate(Page.TARGET_AGE);
                }
            }
        });
        targetAgeFragment.setOnSelectListener(new BaseSearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                navigate(Page.FINISH);
            }
        });

        finishFragment.setOnClickListener(new SearchSettingFinishFragment.OnClickListener() {
            @Override
            public void onClick() {
                saveSettings();
                startActivity(IntentActionHelper.getHomeNewChatIntent(getActivity()));
                SettingsHelper.setFirstRun(getActivity());
                getActivity().finish();
            }
        });

        saveFinishFragment.setOnClickListener(new SearchSettingSaveFinishFragment.OnClickListener() {
            @Override
            public void onSaveClick() {
                saveSettings();
                closeFragment();
            }

            @Override
            public void onStartClick() {
                BaseActivityInterface activity = (BaseActivityInterface) getActivity();
                saveSettings();
                closeFragment();
                ChatHelper.openChatFragment(activity);
            }
        });

        indicator = (IconPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int lastPage = 0;

            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {


                updateActivePages();

                if (!activePages.get(i)) {

                    clearSelection(i);

                    if (lastPage < i) {
                        setCurrentItem(i + 1);
                    } else if (lastPage > i) {
                        setCurrentItem(i - 1);
                    }
                    return;
                }

//                if (lastPage < i) {
//                    pagesBackStack.add(Page.values()[lastPage]);
//                } else if (lastPage > i) {
//                    pagesBackStack.remove(pagesBackStack.size() - 1);
//                }

                ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
                if (i == 0) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(true);
                    actionBar.setTitle(R.string.fgt_welcome_title);
                } else {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(false);
                    actionBar.setTitle(R.string.fgt_welcome_back);
                }

                lastPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void clearSelection(int page) {
        Fragment fragment = adapter.getItem(page);


        if (fragment instanceof BaseSearchSettingsFragment) {
            ((BaseSearchSettingsFragment) fragment).clearSelection();
        }
    }


    private void setCurrentItem(int i) {
        if (i < pager.getAdapter().getCount()) {
            pager.setCurrentItem(i);
            return;
        }


    }

    private int getFirstItem(List<Integer> list) {
        if (list.isEmpty()) {
            return -1;
        }
        return list.get(0);
    }

    private void setActivePage(Page page, boolean state) {
        activePages.set(page.ordinal(), state);
    }


    private boolean isPageFilled(int page) {
        Fragment fragment = adapter.getItem(page);


        if (fragment instanceof BaseSearchSettingsFragment) {

            return !((BaseSearchSettingsFragment) fragment).getSelectedItems().isEmpty();
        }

        if (fragment instanceof SearchSettingFinishFragment
                || fragment instanceof SearchSettingSaveFinishFragment) {
            return true;
        }

        return false;
    }

    private void updateActivePages() {
        setActivePage(Page.TARGET_UNIVERSITY, getFirstItem(yourUniversityFragment.getSelectedItemIds()) != FIRST_ITEM_POSITION);
        setActivePage(Page.TARGET_GENDER, getFirstItem(yourGenderFragment.getSelectedItems()) != FIRST_ITEM_POSITION);
        setActivePage(Page.TARGET_AGE, getFirstItem(yourAgeFragment.getSelectedItems()) != FIRST_ITEM_POSITION);

    }


    protected void saveSettings() {
        SearchParameters searchParameters = new SearchParameters();


        searchParameters.setYourUniversity(yourUniversityFragment.getSelectedItemIds());
        searchParameters.setTargetUniversity(targerUniversityFragment.getSelectedItemIds());
        searchParameters.setYourGender(yourGenderFragment.getSelectedItems());
        searchParameters.setTargetGender(targetGenderFragment.getSelectedItems());
        searchParameters.setYourAge(yourAgeFragment.getSelectedItems());
        searchParameters.setTargetAge(targetAgeFragment.getSelectedItems());

        Settings settings = SettingsHelper.getSettings(getActivity());
        settings.setSearchParameters(searchParameters);
        SettingsHelper.setSettings(getActivity(), settings);


        EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "save settings", searchParameters.toString(), null).build());

    }

    protected void navigate(Page page) {


        pager.setCurrentItem(page.ordinal(), true);
    }

    @Override
    public void onDestroy() {
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        super.onDestroy();

    }

    private int getPrevPage() {
        updateActivePages();
        for (int i = pager.getCurrentItem() - 1; i >= 0; i--) {
            if (activePages.get(i)) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public boolean onBackPressed() {


        if (pager.getCurrentItem() == 0) {


            return false;
        }

        pager.setCurrentItem(getPrevPage());
        return true;
    }
}
