package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
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


public abstract class SearchSettingsFragment extends BaseFragment {

    private static final String PREFS_STATE_FORMATTER = "state_%s";

    private static final String PREFERENCES_NAME = "search_settings_2";


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_search_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getTitleTextId());
        list = (ListView) view.findViewById(R.id.list);
        list.setChoiceMode(getListChoiceMode());

        initAdapter();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveState();
            }
        });
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> selected = getSelectedItems();

                if (selected.isEmpty()) {
                    Toast.makeText(getActivity(), getErrorTextId(), Toast.LENGTH_LONG).show();
                } else {
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(selected);
                    }
                }
            }
        });


    }

    protected void initAdapter() {
        String[] sports = getItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, sports);
        list.setAdapter(adapter);
        if (getView() != null) {
            getView().findViewById(R.id.container).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
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
                if (checked.valueAt(i)) {
                    selected.add(position);
                }
            }
        }
        return selected;
    }

    public void clearSelection() {
        if (list != null) {
            list.clearChoices();
            for (int i = 0; i < list.getChildCount(); i++) {
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
            String className = ((Object) this).getClass().getSimpleName();
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
                    if (position < list.getCount()) {
                        list.setItemChecked(position, true);
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
