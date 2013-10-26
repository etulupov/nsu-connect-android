package ru.tulupov.nsuconnect.protocol;


public interface Command {
    void execute(CommandContext context);
}
