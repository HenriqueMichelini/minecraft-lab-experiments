package io.github.henriquemichelini.scanner.domain;

import java.util.Objects;

public final class ShortOreIndexScanResult {

    private final short[] indexes;
    private final int count;

    public ShortOreIndexScanResult(
            short[] indexes,
            int count
    ) {
        this.indexes = Objects.requireNonNull(indexes);

        if (count < 0 || count > indexes.length) {
            throw new IllegalArgumentException(
                    "Count must be between 0 and indexes.length"
            );
        }

        this.count = count;
    }

    public int count() {
        return count;
    }

    public int indexAt(int resultIndex) {
        if (resultIndex < 0 || resultIndex >= count) {
            throw new IndexOutOfBoundsException(resultIndex);
        }

        return indexes[resultIndex];
    }

    public int capacity() {
        return indexes.length;
    }
}