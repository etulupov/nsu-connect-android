package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.tulupov.nsuconnect.R;


public   class SearchSettingFinishFragment extends Fragment {
    public static SearchSettingFinishFragment newInstance(final Context context) {
        return (SearchSettingFinishFragment) Fragment.instantiate(context, SearchSettingFinishFragment.class.getName());
    }

    public interface OnSelectListener {
        void onSelect(List<Integer> selected);
    }

    private OnSelectListener onSelectListener;

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_search_settings_finish, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//
//        TextView title = (TextView) view.findViewById(R.id.title);
//        title.setText(getTitleTextId());
//
//        final ListView list = (ListView) view.findViewById(R.id.list);
//
//        String[] sports = getResources().getStringArray(getItemsArrayId());
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, sports);
//        list.setChoiceMode(getListChoiceMode());
//        list.setAdapter(adapter);
//
//        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                SparseBooleanArray checked = list.getCheckedItemPositions();
//                List<Integer> selected = new ArrayList<Integer>();
//                for (int i = 0; i < checked.size(); i++) {
//                    int position = checked.keyAt(i);
//                    if (checked.valueAt(i)) {
//                        selected.add(position);
//                    }
//                }
//                if (selected.isEmpty()) {
//                    Toast.makeText(getActivity(), getErrorTextId(), Toast.LENGTH_LONG).show();
//                } else if (onSelectListener != null) {
//                    onSelectListener.onSelect(selected);
//                }
//            }
//        });
    }


}
