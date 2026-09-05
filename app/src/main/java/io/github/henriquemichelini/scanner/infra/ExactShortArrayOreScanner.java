package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.ShortOreIndexScanner;
import io.github.henriquemichelini.scanner.domain.ShortOreIndexScanResult;

public final class ExactShortArrayOreScanner
        implements ShortOreIndexScanner {

    @Override
    public ShortOreIndexScanResult scan(
            ChunkSection section
    ) {
        int oreCount = countOres(section);

        if (oreCount == 0) {
            return new ShortOreIndexScanResult(
                    new short[0],
                    0
            );
        }

        var indexes = new short[oreCount];
        int resultIndex = 0;

        for (int index = 0;
             index < section.size();
             index++) {

            if (!section.blockAt(index).isOre()) {
                continue;
            }

            indexes[resultIndex++] = (short) index;
        }

        return new ShortOreIndexScanResult(
                indexes,
                oreCount
        );
    }

    private static int countOres(
            ChunkSection section
    ) {
        int count = 0;

        for (int index = 0;
             index < section.size();
             index++) {

            if (section.blockAt(index).isOre()) {
                count++;
            }
        }

        return count;
    }
}