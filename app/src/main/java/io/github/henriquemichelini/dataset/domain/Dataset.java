package io.github.henriquemichelini.dataset.domain;

import io.github.henriquemichelini.scanner.domain.ChunkSection;

public interface Dataset {
    String name();
    ChunkSection[] sections();
}
