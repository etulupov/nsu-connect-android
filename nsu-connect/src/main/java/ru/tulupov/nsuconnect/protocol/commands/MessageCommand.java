package ru.tulupov.nsuconnect.protocol.commands;

import android.content.Intent;
import android.util.Log;

import java.sql.SQLException;
import java.util.Date;

import ru.tulupov.nsuconnect.database.ContentUriHelper;
import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.helper.NotificationHelper;
import ru.tulupov.nsuconnect.helper.SoundHelper;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;

public class MessageCommand implements Command {
    private static final String TAG = MessageCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        context.getApplicationContext(). sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_TYPING_STATUS).putExtra(DatabaseConstants.EXTRA_IS_TYPING, false));


        Message message = new Message();
        message.setMessage(context.getStatus().getMsg());
        
        message.setDate(new Date());
        message.setChat(context.getChat());
        message.setUser(context.getAnonymousUser());
        message.setSentFlag(true);
        try {
            HelperFactory.getHelper().getMessageDao().create(message);
            ContentUriHelper.notifyChange(context.getApplicationContext(), ContentUriHelper.getConversationUri(context.getChat().getId()));

            HelperFactory.getHelper().getChatDao().updateLastMessage(context.getChat(), message);
            ContentUriHelper.notifyChange(context.getApplicationContext(), ContentUriHelper.getChatUri());
        } catch (SQLException e) {
            Log.e(TAG, "cannot create message entity", e);
        }


        SoundHelper.beep();

        NotificationHelper.notify(context.getApplicationContext(), message, context.getChat());
    }
}
