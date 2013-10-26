package ru.tulupov.nsuconnect.protocol.commands;

import android.content.Intent;

import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;
import ru.tulupov.nsuconnect.request.StartTypingRequest;
import ru.tulupov.nsuconnect.request.StopTypingRequest;

public class SendStopTypingCommand implements Command {
    private static final String TAG = SendStopTypingCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        StopTypingRequest request = new StopTypingRequest(context.getRequestSession(), context.getErrorListener());
        context.getRequestQueue().add(request);
    }
}
