package ru.tulupov.nsuconnect.adapter;


import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.images.ImageCacheManager;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.User;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;
import ru.tulupov.nsuconnect.util.adapter.FindViewById;

public class ConversationAdapter extends BeanHolderAdapter<Message, ConversationAdapter.Holder> {
    public static class Holder {
        @FindViewById(R.id.text)
        public TextView text;
        @FindViewById(R.id.image)
        public NetworkImageView image;
        @FindViewById(R.id.date)
        public TextView date;
        @FindViewById(R.id.container)
        public View container;

    }

    private DateFormat dateFormat;

    public ConversationAdapter() {
        super(0, Holder.class);

        dateFormat = new SimpleDateFormat("HH:mm");
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getUser().getType();
    }

    @Override
    protected int getLayoutResourceId(int position) {
        switch (getItemViewType(position)) {
            case User.TYPE_OTHER:
                return R.layout.item_message_in;
            case User.TYPE_YOUR:
                return R.layout.item_message_out;
            case User.TYPE_SYSTEM:
                return R.layout.item_message_system;
        }
        return 0;
    }


    @Override
    protected void updateHolder(Context context, Holder holder, Message item, int position) {
        holder.text.setText(Html.fromHtml(item.getMessage()));

        holder.date.setText(dateFormat.format(item.getDate()));

        holder.container.setBackgroundResource(item.isSentFlag() ? android.R.color.transparent : R.color.light_gray);

        final String url = item.getUrl();
        if (url != null) {
            if (holder.image != null) {
                holder.image.setVisibility(View.VISIBLE);

                if (url.startsWith("http")) {
                    holder.image.setImageUrl(url, ImageCacheManager.getInstance().getImageLoader());
                } else {
                    ImageLoader imageLoader = new ImageLoader(null, null) {
                        @Override
                        public ImageContainer get(String requestUrl, ImageListener listener) {

                            ImageContainer container = new ImageContainer(ImageCacheManager.getInstance().getBitmap(requestUrl), requestUrl, null, null);
                            listener.onResponse(container, true);
                            return container;

                        }
                    };
                    holder.image.setImageUrl(url, imageLoader);
                }
            }
        } else {
            if (holder.image != null) {
                holder.image.setVisibility(View.GONE);
                holder.image.setImageBitmap(null);
            }
        }


    }


}
