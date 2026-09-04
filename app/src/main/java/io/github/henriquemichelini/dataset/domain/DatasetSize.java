package io.github.henriquemichelini.dataset.domain;

public enum DatasetSize {

    TINY(1),
    SMALL(64),
    MEDIUM(1_024),
    LARGE(4_096);

    private final int sectionCount;

    DatasetSize(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    public int sectionCount() {
        return sectionCount;
    }
}
