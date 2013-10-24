package ru.tulupov.nsuconnect.slidingmenu;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;
import ru.tulupov.nsuconnect.util.adapter.FindViewById;

public class SlidingMenuAdapter extends BeanHolderAdapter<SlidingMenuItem, SlidingMenuAdapter.Holder> {
    public static class Holder {
        @FindViewById(R.id.icon)
        public ImageView icon;
        @FindViewById(R.id.text)
        public TextView text;
    }

    public SlidingMenuAdapter() {
        super(R.layout.item_sliding_menu, Holder.class);
    }

    @Override
    protected void updateHolder(Context context, SlidingMenuAdapter.Holder holder, SlidingMenuItem item, int position) {
        holder.icon.setImageResource(item.icon);
        holder.text.setText(item.text);
    }


}