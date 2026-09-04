package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.ShortOreIndexScanner;
import io.github.henriquemichelini.scanner.domain.ShortOreIndexScanResult;

public final class FixedShortArrayOreScanner
        implements ShortOreIndexScanner {

    @Override
    public ShortOreIndexScanResult scan(
            ChunkSection section
    ) {
        var indexes =
                new short[ChunkSection.BLOCK_COUNT];

        int count = 0;

        for (int index = 0;
             index < section.size();
             index++) {

            if (!section.blockAt(index).isOre()) {
                continue;
            }

            indexes[count++] = (short) index;
        }

        return new ShortOreIndexScanResult(
                indexes,
                count
        );
    }
}