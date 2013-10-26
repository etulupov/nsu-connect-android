package ru.tulupov.nsuconnect.protocol;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.protocol.commands.ConnectCommand;
import ru.tulupov.nsuconnect.protocol.commands.DisconnectCommand;
import ru.tulupov.nsuconnect.protocol.commands.ImageMessageCommand;
import ru.tulupov.nsuconnect.protocol.commands.MessageCommand;
import ru.tulupov.nsuconnect.protocol.commands.SendMessageCommand;
import ru.tulupov.nsuconnect.protocol.commands.SendStartTypingCommand;
import ru.tulupov.nsuconnect.protocol.commands.SendStopTypingCommand;
import ru.tulupov.nsuconnect.protocol.commands.StartTypingCommand;
import ru.tulupov.nsuconnect.protocol.commands.StopTypingCommand;
import ru.tulupov.nsuconnect.protocol.commands.WaitCommand;
import ru.tulupov.nsuconnect.request.Constants;
import ru.tulupov.nsuconnect.request.SendMessageRequest;
import ru.tulupov.nsuconnect.service.DataService;

public class CommandFactory {
    private static final Map<String, Class<? extends Command>> COMMANDS;
    private static final String TAG = CommandFactory.class.getSimpleName();

    static {
        COMMANDS = new HashMap<String, Class<? extends Command>>() {

            {
                // Server commands
                put(Constants.STATUS_WAITING, WaitCommand.class);
                put(Constants.STATUS_CONNECTED, ConnectCommand.class);
                put(Constants.STATUS_DISCONNECTED, DisconnectCommand.class);
                put(Constants.STATUS_MESSAGE, MessageCommand.class);
                put(Constants.STATUS_IAMGE_MESSAGE, ImageMessageCommand.class);
                put(Constants.STATUS_START_TYPING, StartTypingCommand.class);
                put(Constants.STATUS_STOP_TYPING, StopTypingCommand.class);

                // DataService commands
                put(DataService.ACTION_START_TYPING, SendStartTypingCommand.class);
                put(DataService.ACTION_STOP_TYPING, SendStopTypingCommand.class);
                put(DataService.ACTION_SEND_MESSAGE,SendMessageCommand.class);
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
