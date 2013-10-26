package ru.tulupov.nsuconnect.protocol.commands;

import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;
import ru.tulupov.nsuconnect.request.DisconnectRequest;
import ru.tulupov.nsuconnect.request.StartTypingRequest;

public class SendDisconnectCommand implements Command {
    private static final String TAG = SendDisconnectCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        DisconnectRequest request = new DisconnectRequest(context.getRequestSession(), context.getErrorListener());
        context.getRequestQueue().add(request);
    }
}
