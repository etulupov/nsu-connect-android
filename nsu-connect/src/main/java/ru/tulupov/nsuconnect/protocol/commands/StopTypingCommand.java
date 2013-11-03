package ru.tulupov.nsuconnect.protocol.commands;

import android.content.Intent;

import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.helper.BroadcastHelper;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;
import ru.tulupov.nsuconnect.service.DataService;

public class StopTypingCommand implements Command {
    private static final String TAG = StopTypingCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        context.getApplicationContext().sendBroadcast(BroadcastHelper.getChatTypingIntent(context.getChat().getId(), false));

    }
}
