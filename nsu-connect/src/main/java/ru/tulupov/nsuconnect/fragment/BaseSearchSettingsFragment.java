package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.tulupov.nsuconnect.R;


public abstract class BaseSearchSettingsFragment extends BaseFragment {

    private static final String PREFS_STATE_FORMATTER = "state_%s";

    private static final String PREFERENCES_NAME = "search_settings_5";


    public interface OnSelectListener {
        void onSelect(List<Integer> selected);
    }

    private OnSelectListener onSelectListener;
    private ListView list;

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fgt_base_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_next) {
            onNextClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onNextClick() {
        List<Integer> selected = getSelectedItems();

        if (selected.isEmpty()) {
            Toast.makeText(getActivity(), getErrorTextId(), Toast.LENGTH_LONG).show();
        } else {
            if (onSelectListener != null) {
                onSelectListener.onSelect(selected);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_search_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = (ListView) view.findViewById(R.id.list);
        list.setChoiceMode(getListChoiceMode());

        View header = View.inflate(getActivity(), R.layout.header_search_settings, null);
        TextView title = (TextView) header.findViewById(R.id.title);
        title.setText(getTitleTextId());

        list.addHeaderView(header);

        View footer = View.inflate(getActivity(), R.layout.footer_search_settings, null);
        list.addFooterView(footer);

        initAdapter();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveState();
            }
        });


    }

    protected void initAdapter() {
        String[] sports = getItems();
        // R.layout.item_search_param
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, sports);
        list.setAdapter(adapter);

        restoreState();
    }

    public String[] getItems() {
        return getResources().getStringArray(getItemsArrayId());
    }

    public List<Integer> getSelectedItems() {
        List<Integer> selected = new ArrayList<Integer>();

        if (list != null) {
            SparseBooleanArray checked = list.getCheckedItemPositions();

            for (int i = 0; i < checked.size(); i++) {
                int position = checked.keyAt(i);
                if (position < list.getHeaderViewsCount()) {
                    continue;
                }
                if (position >= list.getCount() - list.getFooterViewsCount()) {
                    continue;
                }
                if (checked.valueAt(i)) {
                    selected.add(position - list.getHeaderViewsCount());
                }
            }
        }
        return selected;
    }

    public void clearSelection() {
        if (list != null) {
            list.clearChoices();
            for (int i = 0; i < list.getCount(); i++) {
                list.setItemChecked(i, false);
            }
        }

        saveState();
    }


    private String getClassName() {
        return ((Object) this).getClass().getSimpleName();
    }

    private void saveState() {
        if (getActivity() != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();

            preferences.edit().putString(String.format(PREFS_STATE_FORMATTER, getClassName()), gson.toJson(getSelectedItems())).commit();
        }
    }

    private void restoreState() {
        if (getActivity() != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            List<Integer> selected = gson.fromJson(preferences.getString(String.format(PREFS_STATE_FORMATTER, getClassName()), ""), listType);
            if (selected != null) {
                for (Integer position : selected) {
                    int i = position + list.getHeaderViewsCount();
                    if (i < list.getCount()) {
                        list.setItemChecked(i, true);
                    }
                }
            }
        }
    }

    protected int getErrorTextId() {
        return R.string.search_nothing_selected;
    }

    protected abstract int getTitleTextId();

    protected abstract int getItemsArrayId();

    protected int getListChoiceMode() {
        return ListView.CHOICE_MODE_MULTIPLE;
    }

}
