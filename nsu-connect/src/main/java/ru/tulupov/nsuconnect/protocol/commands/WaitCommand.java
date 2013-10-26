package ru.tulupov.nsuconnect.protocol.commands;

import ru.tulupov.nsuconnect.helper.SoundHelper;
import ru.tulupov.nsuconnect.protocol.Command;
import ru.tulupov.nsuconnect.protocol.CommandContext;

public class WaitCommand extends SystemCommand{
    @Override
    public void execute(CommandContext context) {
        SoundHelper.beep();
        writeSystem(context, "Ожидание подключения");
    }
}
