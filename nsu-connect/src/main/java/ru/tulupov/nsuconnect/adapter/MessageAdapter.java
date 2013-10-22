package ru.tulupov.nsuconnect.adapter;


import android.content.Context;
import android.view.View;
import android.widget.TextView;


import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.User;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;
import ru.tulupov.nsuconnect.util.adapter.FindViewById;
import ru.tulupov.nsuconnect.util.adapter.HolderAdapter;

public class MessageAdapter extends BeanHolderAdapter<Message, MessageAdapter.Holder> {
    public static class Holder {
        @FindViewById(R.id.text)
        public TextView text;

        @FindViewById(R.id.author)
        public TextView author;

        @FindViewById(R.id.container)
        public View container;
    }

    public MessageAdapter() {
        super(R.layout.item_message, Holder.class);
    }

    @Override
    protected void updateHolder(Context context, Holder holder, Message item, int position) {
        holder.text.setText(item.getMessage());

        holder.author.setText(item.getUser().getName());

            switch (item.getUser().getType()) {
                case User.TYPE_OTHER:
                    holder.container.setBackgroundColor(0xff0000);
                    break;
                case User.TYPE_YOUR:
                    holder.container.setBackgroundColor(0x00ff00);
                    break;
                case User.TYPE_SYSTEM:
                    holder.container.setBackgroundColor(0x0000ff);
                    break;
            }

    }


}
