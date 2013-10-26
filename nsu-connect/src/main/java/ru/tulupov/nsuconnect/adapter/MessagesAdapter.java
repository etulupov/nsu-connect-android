package ru.tulupov.nsuconnect.adapter;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.User;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;
import ru.tulupov.nsuconnect.util.adapter.FindViewById;

public class MessagesAdapter extends BeanHolderAdapter<Chat, MessagesAdapter.Holder> {
    public static class Holder {
        @FindViewById(R.id.text)
        public TextView text;

        @FindViewById(R.id.date)
        public TextView date;

        @FindViewById(R.id.stop)
        public ImageView stop;
    }

    public interface OnClickListener {
        void onStop(Chat chat);
    }

    private OnClickListener onClickListener;


    private DateFormat dateFormat;

    public MessagesAdapter() {
        super(R.layout.item_message, Holder.class);

        dateFormat = new SimpleDateFormat("HH:mm");
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    protected void updateHolder(Context context, Holder holder, final Chat item, int position) {
        holder.text.setText(item.getName());

        holder.date.setText(dateFormat.format(item.getDate()));

        if (item.isActive()) {
            holder.stop.setVisibility(View.VISIBLE);
            holder.stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onStop(item);
                    }
                }
            });
        } else {
            holder.stop.setVisibility(View.INVISIBLE);
        }
    }


}
