package ru.tulupov.nsuconnect.adapter;


import android.content.Context;
import android.widget.TextView;


import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;
import ru.tulupov.nsuconnect.util.adapter.FindViewById;
import ru.tulupov.nsuconnect.util.adapter.HolderAdapter;

public class MessageAdapter extends BeanHolderAdapter<Message, MessageAdapter.Holder> {
    public static class Holder {
        @FindViewById(R.id.text)
        public TextView text;
    }

    public MessageAdapter() {
        super(R.layout.item_message, Holder.class);
    }

    @Override
    protected void updateHolder(Context context, Holder holder, Message item, int position) {
        holder.text.setText(item.getMessage());
    }


}
