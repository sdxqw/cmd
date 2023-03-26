package io.github.sdxqw.cmd.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Utils {

    /**
     * Reads the contents of a file at the specified path into a direct ByteBuffer.
     *
     * @param path the path of the file to read
     * @return a direct ByteBuffer containing the contents of the file, or null if an error occurred
     */
    public static ByteBuffer readFile(Path path) {
        try (FileChannel fc = FileChannel.open(path, StandardOpenOption.READ)) {
            if (fc.size() == 0) throw new RuntimeException("File is empty: " + path);
            ByteBuffer buffer = ByteBuffer.allocateDirect(Math.toIntExact(fc.size()));
            if (fc.read(buffer) == -1) throw new IOException("Failed to read from file: " + path);
            buffer.flip();
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
