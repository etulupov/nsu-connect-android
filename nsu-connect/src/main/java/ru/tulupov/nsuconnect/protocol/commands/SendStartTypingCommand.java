package ru.tulupov.nsuconnect.protocol.commands;

import android.content.Intent;

import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;
import ru.tulupov.nsuconnect.request.StartTypingRequest;

public class SendStartTypingCommand implements Command {
    private static final String TAG = SendStartTypingCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        StartTypingRequest request = new StartTypingRequest(context.getRequestSession(), context.getErrorListener());
        context.getRequestQueue().add(request);
    }
}
