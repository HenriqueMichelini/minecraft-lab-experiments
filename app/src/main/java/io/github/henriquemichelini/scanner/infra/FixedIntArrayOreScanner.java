package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.OreIndexScanResult;
import io.github.henriquemichelini.scanner.domain.OreIndexScanner;

public final class FixedIntArrayOreScanner implements OreIndexScanner {

    @Override
    public OreIndexScanResult scan(ChunkSection section) {
        var indexes = new int[ChunkSection.BLOCK_COUNT];

        int count = 0;

        for (int index = 0; index < section.size(); index++) {
            if (!section.blockAt(index).isOre()) continue;

            indexes[count++] = index;
        }

        return new OreIndexScanResult(indexes, count);
    }

}
