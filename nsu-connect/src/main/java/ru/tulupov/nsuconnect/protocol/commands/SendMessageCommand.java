package ru.tulupov.nsuconnect.protocol.commands;

import ru.tulupov.nsuconnect.util.Log;

import java.sql.SQLException;
import java.util.Date;

import ru.tulupov.nsuconnect.database.ContentUriHelper;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;
import ru.tulupov.nsuconnect.request.StopTypingRequest;

public class SendMessageCommand implements Command {
    private static final String TAG = SendMessageCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        Message message = new Message();
        message.setMessage(context.getStatus().getMsg());
        message.setDate(new Date());
        message.setUrl(context.getStatus().getUrl());
        message.setChat(context.getChat());
        message.setUser(context.getCurrentUser());
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
