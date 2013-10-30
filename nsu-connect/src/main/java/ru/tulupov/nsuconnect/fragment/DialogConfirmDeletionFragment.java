package ru.tulupov.nsuconnect.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import eu.inmite.android.lib.dialogs.SimpleDialogFragment;
import ru.tulupov.nsuconnect.R;

public class DialogConfirmDeletionFragment extends SimpleDialogFragment {
    public static DialogConfirmDeletionFragment newInstance(final Context context) {

        return (DialogConfirmDeletionFragment) Fragment.instantiate(context, DialogConfirmDeletionFragment.class.getName());
    }

    public interface OnDeleteListener {
        void onDelete();
    }

    private OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    public Builder build(Builder builder) {
        Context context = getActivity().getApplicationContext();

        builder.setTitle(context.getString(R.string.dialog_delete_title));
        builder.setMessage(context.getString(R.string.dialog_delete_confirm));
        builder.setPositiveButton(context.getString(R.string.dialog_yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null) {
                    onDeleteListener.onDelete();
                }
                dismiss();
            }
        });


        builder.setNegativeButton(context.getString(R.string.dialog_no), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        return builder;
    }
}
