package io.github.sdxqw.cmd.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CmdTypes {

    @Getter
    public List<Command> cmd = new ArrayList<>();

    public CmdTypes(Command... cmd) {
        this.cmd.addAll(List.of(cmd));
    }
}
