package ru.tulupov.nsuconnect.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import eu.inmite.android.lib.dialogs.BaseDialogFragment;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;
import ru.tulupov.nsuconnect.R;

public class DialogItemListFragment extends SimpleDialogFragment {

    private static final String ARGS_ITEMS = "items";

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public static DialogItemListFragment newInstance(final Context context, int itemId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_ITEMS, itemId);
        return (DialogItemListFragment) Fragment.instantiate(context, DialogItemListFragment.class.getName(), args);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    protected Builder build(Builder builder) {
        String[] strings = getActivity().getResources().getStringArray(getArguments().getInt(ARGS_ITEMS));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_dialog_list, strings);


        builder.setItems(adapter, 0, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(position);
                }
            }
        });

        return builder;
    }

}
