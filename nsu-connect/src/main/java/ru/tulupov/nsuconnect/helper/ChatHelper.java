package ru.tulupov.nsuconnect.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.SQLException;
import java.util.Date;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.activity.BaseActivityInterface;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.fragment.BaseFragment;
import ru.tulupov.nsuconnect.fragment.ChatFragment;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.service.DataService;

public class ChatHelper {
    private static final String TAG = ChatHelper.class.getSimpleName();

    public static Chat createChat(Context context) {

        try {
            Chat chat = new Chat();

            chat.setDate(new Date());
            chat.setActive(true);
            int count = 0;
            Chat lastChat = HelperFactory.getHelper().getChatDao().getLast();
            if (lastChat != null) {
                count = lastChat.getId();
            }

            chat.setName(context.getString(R.string.chat_default_title_formatter, count + 1));
            HelperFactory.getHelper().getChatDao().create(chat);

            return chat;
        } catch (SQLException e) {
            Log.e(TAG, "chat creation error", e);
        }

        return null;
    }

    public static void openChatFragment(BaseActivityInterface context) {
        Chat chat = ChatHelper.createChat(context.getActivity());


        context.getActivity().startService(new Intent(context.getActivity(), DataService.class).setAction(DataService.ACTION_CREATE_SESSION).putExtra(DataService.EXTRA_ID, chat.getId()));

        context.addFragment(ChatFragment.newInstance(context.getActivity(), chat.getId()));
    }
}
