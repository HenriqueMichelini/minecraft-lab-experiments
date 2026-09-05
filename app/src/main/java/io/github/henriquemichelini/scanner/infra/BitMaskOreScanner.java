package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.OreMaskScanner;
import io.github.henriquemichelini.scanner.domain.OreMaskScanResult;

public final class BitMaskOreScanner
        implements OreMaskScanner {

    @Override
    public OreMaskScanResult scan(
            ChunkSection section
    ) {
        var words =
                new long[OreMaskScanResult.WORD_COUNT];

        for (int index = 0;
             index < section.size();
             index++) {

            if (!section.blockAt(index).isOre()) {
                continue;
            }

            int wordIndex = index >>> 6;
            int bitIndex = index & 63;

            words[wordIndex] |= 1L << bitIndex;
        }

        return new OreMaskScanResult(words);
    }
}