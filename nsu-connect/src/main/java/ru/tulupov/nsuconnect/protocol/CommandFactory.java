package ru.tulupov.nsuconnect.protocol;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.protocol.commands.ConnectCommand;
import ru.tulupov.nsuconnect.protocol.commands.DisconnectCommand;
import ru.tulupov.nsuconnect.protocol.commands.ImageMessageCommand;
import ru.tulupov.nsuconnect.protocol.commands.MessageCommand;
import ru.tulupov.nsuconnect.protocol.commands.StartTypingCommand;
import ru.tulupov.nsuconnect.protocol.commands.StopTypingCommand;
import ru.tulupov.nsuconnect.protocol.commands.WaitCommand;
import ru.tulupov.nsuconnect.request.Constants;

public class CommandFactory {
    private static final Map<String, Class<? extends Command>> COMMANDS;
    private static final String TAG = CommandFactory.class.getSimpleName();

    static {
        COMMANDS = new HashMap<String, Class<? extends Command>>() {

            {
                put(Constants.STATUS_WAITING, WaitCommand.class);
                put(Constants.STATUS_CONNECTED, ConnectCommand.class);
                put(Constants.STATUS_DISCONNECTED, DisconnectCommand.class);
                put(Constants.STATUS_MESSAGE, MessageCommand.class);
                put(Constants.STATUS_IAMGE_MESSAGE, ImageMessageCommand.class);
                put(Constants.STATUS_START_TYPING, StartTypingCommand.class);
                put(Constants.STATUS_STOP_TYPING, StopTypingCommand.class);
            }
        };
    }

    public static Command get(String command) {
        Class<? extends Command> clazz = COMMANDS.get(command);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                Log.e(TAG, "Error", e);
            }
        }
        return null;
    }


}
