package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.activity.MainActivity;
import ru.tulupov.nsuconnect.adapter.PagerAdapter;
import ru.tulupov.nsuconnect.helper.IntentActionHelper;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.model.SearchParameters;
import ru.tulupov.nsuconnect.model.Settings;
import ru.tulupov.nsuconnect.widget.CustomViewPager;


public class WelcomeFragment extends BaseFragment {
    public enum Page {
        YOUR_UNIVERSITY,
        YOUR_GENDER,
        TARGET_GENDER,
        YOUR_AGE,
        TARGET_AGE,
        FINISH,
    }


    public static WelcomeFragment newInstance(final Context context) {
        return (WelcomeFragment) Fragment.instantiate(context, WelcomeFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_welcome, container, false);
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
    private List<Page> pagesBackStack;

    private SearchSettingsFragment yourUniversityFragment;
    private SearchSettingsFragment yourGenderFragment;
    private SearchSettingsFragment targetGenderFragment;
    private SearchSettingsFragment yourAgeFragment;
    private SearchSettingsFragment targetAgeFragment;
    private SearchSettingFinishFragment finishFragment;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagesBackStack = new ArrayList<Page>();
        pagesBackStack.add(Page.YOUR_UNIVERSITY);
        pager = (CustomViewPager) view.findViewById(R.id.pager);
        pager.setPagingEnabled(false);
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());

        yourUniversityFragment = YourUniversitySettingsFragment.newInstance(getActivity());
        yourGenderFragment = YourGenderSettingsFragment.newInstance(getActivity());
        targetGenderFragment = TargetGenderSettingsFragment.newInstance(getActivity());
        yourAgeFragment = YourAgeSettingsFragment.newInstance(getActivity());
        targetAgeFragment = TargetAgeSettingsFragment.newInstance(getActivity());
        finishFragment = SearchSettingFinishFragment.newInstance(getActivity());

        adapter.setFragments(Arrays.asList(yourUniversityFragment, yourGenderFragment, targetGenderFragment, yourAgeFragment, targetAgeFragment, finishFragment));

        pager.setAdapter(adapter);

        yourUniversityFragment.setOnSelectListener(new SearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                navigate(Page.YOUR_GENDER);
            }
        });
        yourGenderFragment.setOnSelectListener(new SearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                int position = selected.get(0);
                if (position == 0) {
                    targetGenderFragment.clearSelection();
                    navigate(Page.YOUR_AGE);
                } else {
                    navigate(Page.TARGET_GENDER);
                }
            }
        });
        targetGenderFragment.setOnSelectListener(new SearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                navigate(Page.YOUR_AGE);
            }
        });
        yourAgeFragment.setOnSelectListener(new SearchSettingsFragment.OnSelectListener() {
            @Override
            public void onSelect(List<Integer> selected) {
                int position = selected.get(0);
                if (position == 0) {
                    targetAgeFragment.clearSelection();
                    navigate(Page.FINISH);
                } else {
                    navigate(Page.TARGET_AGE);
                }
            }
        });
        targetAgeFragment.setOnSelectListener(new SearchSettingsFragment.OnSelectListener() {
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
    }

    protected void saveSettings() {
        SearchParameters searchParameters = new SearchParameters();

        String[] universityIds = getResources().getStringArray(R.array.search_your_university_ids);

        List<Integer> university = new ArrayList<Integer>();
        for (Integer position : yourUniversityFragment.getSelectedItems()) {
            university.add(Integer.valueOf(universityIds[position]));
        }

        searchParameters.setYourUniversity(university);
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
        pagesBackStack.add(page);
        pager.setCurrentItem(page.ordinal(), true);
    }

    @Override
    public boolean onBackPressed() {
        if (pagesBackStack.size() == 1) {
            return false;
        }
        pagesBackStack.remove(pagesBackStack.size() - 1);
        int lastPage = pagesBackStack.get(pagesBackStack.size() - 1).ordinal();

        pager.setCurrentItem(lastPage);
        return true;
    }
}
