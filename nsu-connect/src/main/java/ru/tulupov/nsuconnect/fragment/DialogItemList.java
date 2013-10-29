package ru.tulupov.nsuconnect.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import ru.tulupov.nsuconnect.R;

public class DialogItemList extends DialogFragment {

    private static final String ARGS_ITEMS = "items";

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public static DialogItemList newInstance(final Context context, int itemId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_ITEMS, itemId);
        return (DialogItemList) Fragment.instantiate(context, DialogItemList.class.getName(), args);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog__items_title);
        builder.setItems(getArguments().getInt(ARGS_ITEMS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(i);
                }
            }
        });

        return builder.create();
    }

}
