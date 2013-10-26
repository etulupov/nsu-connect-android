package ru.tulupov.nsuconnect.protocol.commands;

import android.content.Intent;

import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;

public class StopTypingCommand implements Command {
    private static final String TAG = StopTypingCommand.class.getSimpleName();

    @Override
    public void execute(CommandContext context) {
        context.getApplicationContext().sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_TYPING_STATUS).putExtra(DatabaseConstants.EXTRA_IS_TYPING, false));

    }
}
