package ru.tulupov.nsuconnect.protocol.commands;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.Date;

import ru.tulupov.nsuconnect.database.ContentUriHelper;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;

public abstract class SystemCommand implements Command {
    private static final String TAG = SystemCommand.class.getSimpleName();

    protected void writeSystem(CommandContext context, String text) {
        Message message = new Message();
        message.setMessage(text);
        message.setDate(new Date());
        message.setChat(context.getChat());
        message.setUser(context.getSystemUser());
        message.setSentFlag(true);
        message.setReadFlag(true);
        try {
            HelperFactory.getHelper().getMessageDao().create(message);
            ContentUriHelper.notifyChange(context.getApplicationContext(), ContentUriHelper.getConversationUri(context.getChat().getId()));

            HelperFactory.getHelper().getChatDao().updateLastMessage(context.getChat(), message);
            ContentUriHelper.notifyChange(context.getApplicationContext(), ContentUriHelper.getChatUri());
        } catch (SQLException e) {
            Log.e(TAG, "cannot create message entity", e);
        }
    }
}
