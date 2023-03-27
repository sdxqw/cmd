package io.github.sdxqw.cmd.core.commands;

import io.github.sdxqw.cmd.core.Command;

import java.util.List;

public class Echo implements Command {

    List<String> aliases = List.of("say", "echo");

    @Override
    public boolean matches(List<String> command) {
        return aliases.contains(command.get(0));
    }

    @Override
    public void execute(List<String> text, List<String> command) {
        if (command.size() > 1) {
            String message = String.join(" ", command.subList(1, command.size()));
            text.add(message);
        }
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }


}
