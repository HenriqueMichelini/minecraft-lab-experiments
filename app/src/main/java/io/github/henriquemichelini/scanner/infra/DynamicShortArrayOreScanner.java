package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.ShortOreIndexScanner;
import io.github.henriquemichelini.scanner.domain.ShortOreIndexScanResult;

import java.util.Arrays;

public final class DynamicShortArrayOreScanner
        implements ShortOreIndexScanner {

    private static final int INITIAL_CAPACITY = 16;

    @Override
    public ShortOreIndexScanResult scan(
            ChunkSection section
    ) {
        short[] indexes = new short[INITIAL_CAPACITY];
        int count = 0;

        for (int index = 0;
             index < section.size();
             index++) {

            if (!section.blockAt(index).isOre()) {
                continue;
            }

            if (count == indexes.length) {
                indexes = grow(indexes);
            }

            indexes[count++] = (short) index;
        }

        return new ShortOreIndexScanResult(
                indexes,
                count
        );
    }

    private static short[] grow(short[] indexes) {
        return Arrays.copyOf(
                indexes,
                indexes.length << 1
        );
    }
}