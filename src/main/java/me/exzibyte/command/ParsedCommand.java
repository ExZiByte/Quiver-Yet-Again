package me.exzibyte.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ParsedCommand {

    private final Command command;
    private String requiredPermission;

    public ParsedCommand(Command command) {
        this.command = command;
        parse();
    }

    @NotNull
    public Command command() {
        return command;
    }

    @Nullable
    public String requiredPermission() {
        return requiredPermission;
    }

    private void parse() {
        var annotation = command.getClass().getAnnotation(Permission.class);

        if (annotation != null) {
            this.requiredPermission = annotation.value();
        }
    }
}
