package ru.tulupov.nsuconnect.protocol.commands;

import android.content.Intent;
import android.util.Log;

import java.sql.SQLException;
import java.util.Date;

import ru.tulupov.nsuconnect.database.ContentUriHelper;
import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.Status;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;

public class ImageMessageCommand implements Command {
    private static final String TAG = ImageMessageCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        context.getApplicationContext(). sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_TYPING_STATUS).putExtra(DatabaseConstants.EXTRA_IS_TYPING, false));

   
        Message message = new Message();
        message.setMessage(context.getStatus().getMsg());
        message.setUrl(context.getStatus().getUrl());
        message.setDate(new Date());
        message.setChat(context.getChat());
        message.setUser(context.getAnonymousUser());
        message.setSentFlag(true);
        try {
            HelperFactory.getHelper().getMessageDao().create(message);
            ContentUriHelper.notifyChange(context.getApplicationContext(), ContentUriHelper.getConversationUri(context.getChat().getId()));
        } catch (SQLException e) {
            Log.e(TAG, "cannot create message entity", e);
        }
    }
}
