package org.lion;

import java.nio.file.Path;

public interface TransformHandler {
    void handle(Path path);
}
