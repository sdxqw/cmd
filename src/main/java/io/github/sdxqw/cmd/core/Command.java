package io.github.sdxqw.cmd.core;

import java.util.List;

public interface Command {
    boolean matches(List<String> command);
    void execute(List<String> text, List<String> command);
    List<String> getAliases();
}
