package ru.tulupov.nsuconnect.adapter;


import android.content.Context;
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


    }

    private DateFormat dateFormat;

    public MessagesAdapter() {
        super(0, Holder.class);

        dateFormat = new SimpleDateFormat("HH:mm");
    }





    @Override
    protected void updateHolder(Context context, Holder holder, Chat item, int position) {
        holder.text.setText(item.getName());

        holder.date.setText(dateFormat.format(item.getDate()));


    }


}
