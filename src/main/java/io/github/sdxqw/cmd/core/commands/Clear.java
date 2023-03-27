package io.github.sdxqw.cmd.core.commands;

import io.github.sdxqw.cmd.core.Command;

import java.util.List;

public class Clear implements Command {
    List<String> aliases = List.of("clear", "cls");
    @Override
    public boolean matches(List<String> command) {
        return aliases.contains(command.get(0));
    }

    @Override
    public void execute(List<String> text, List<String> command) {
        text.clear();
        text.add("Cleared the console");
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }
}
