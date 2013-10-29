package ru.tulupov.nsuconnect.fragment;


import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public class DialogAttachPhoto extends DialogFragment {

    public interface OnSelectListener {
        void onCamera();

        void onGallery();
    }

    private OnSelectListener onSelectListener;

    public static DialogAttachPhoto newInstance(final Context context) {
        return (DialogAttachPhoto) Fragment.instantiate(context, DialogAttachPhoto.class.getName());
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }


    @Override
    public Dialog getDialog() {
        return super.getDialog();
    }
}
