package ru.tulupov.nsuconnect.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;



import eu.inmite.android.lib.dialogs.SimpleDialogFragment;
import ru.tulupov.nsuconnect.R;

public class DialogConfirmExitFragment extends SimpleDialogFragment {
    public static DialogConfirmExitFragment newInstance(final Context context) {

        return (DialogConfirmExitFragment) Fragment.instantiate(context, DialogConfirmExitFragment.class.getName());
    }

    public interface OnExitListener {
        void onExit();
    }

    private OnExitListener onExitListener;

    public void setOnExitListener(OnExitListener onExitListener) {
        this.onExitListener = onExitListener;
    }

    @Override
    public Builder build(Builder builder) {
        Context context = getActivity().getApplicationContext();

        builder.setTitle(context.getString(R.string.dialog_exit_title));
        builder.setMessage(context.getString(R.string.dialog_exit_confirm));
        builder.setPositiveButton(context.getString(R.string.dialog_exit_yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExitListener != null) {
                    onExitListener.onExit();
                }
                dismiss();
            }
        });


        builder.setNegativeButton(context.getString(R.string.dialog_exit_no), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        return builder;
    }
}
