package ru.tulupov.nsuconnect.adapter;


import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.User;
import ru.tulupov.nsuconnect.util.DateUtils;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;
import ru.tulupov.nsuconnect.util.adapter.FindViewById;

public class MessagesAdapter extends BeanHolderAdapter<Chat, MessagesAdapter.Holder> {
    private static final String TAG = MessagesAdapter.class.getSimpleName();

    public static class Holder {
        @FindViewById(R.id.text)
        public TextView text;

        @FindViewById(R.id.date)
        public TextView date;

        @FindViewById(R.id.description)
        public TextView description;

        @FindViewById(R.id.stop)
        public ImageView stop;
    }

    public interface OnClickListener {
        void onStop(Chat chat);
    }

    private OnClickListener onClickListener;


    public MessagesAdapter() {
        super(R.layout.item_message, Holder.class);


    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    protected void updateHolder(Context context, Holder holder, final Chat item, int position) {


        if (item.getLastMessageId() != null) {
            try {

                item.setLastMessage(HelperFactory.getHelper().getMessageDao().get(item.getLastMessageId()));
            } catch (SQLException e) {
                Log.e(TAG, "error load last message", e);
            }
        }

        Message lastMessage = item.getLastMessage();
        if (lastMessage != null) {
            holder.date.setText(lastMessage.getMessage());

            if (lastMessage.getMessage() != null) {
                holder.description.setText(Html.fromHtml(lastMessage.getMessage()));

            } else {
                holder.description.setText(context.getString(R.string.messages_image_type));
            }

            holder.description.setBackgroundResource(lastMessage.isSentFlag() && lastMessage.isReadFlag() ? android.R.color.transparent : R.color.light_gray);
        } else {
            holder.description.setBackgroundResource(android.R.color.transparent);
        }

        holder.text.setText(item.getName());

        holder.date.setText(DateUtils.formatChatDate(context, item.getDate()));

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
