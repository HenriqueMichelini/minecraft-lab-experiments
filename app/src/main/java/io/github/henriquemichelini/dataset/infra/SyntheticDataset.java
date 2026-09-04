package io.github.henriquemichelini.dataset.infra;

import io.github.henriquemichelini.dataset.domain.Dataset;
import io.github.henriquemichelini.scanner.domain.ChunkSection;

public final class SyntheticDataset implements Dataset {
    private final String name;
    private final ChunkSection[] sections;


    public SyntheticDataset(String name, ChunkSection[] sections) {
        this.name = name;
        this.sections = sections;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ChunkSection[] sections() {
        return sections;
    }
}
