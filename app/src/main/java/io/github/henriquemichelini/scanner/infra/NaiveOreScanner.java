package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.scanner.domain.*;

import java.util.ArrayList;

public class NaiveOreScanner implements OreScanner {

    @Override
    public ScanResult scan(ChunkSection section) {
        var occurrences = new ArrayList<OreOccurrence>();

        for(int i = 0; i < section.size(); i++) {
            var type = section.blockAt(i);

            if(type.isOre()) {
                occurrences.add(
                        new OreOccurrence(positionOf(i),
                                type
                        )
                );
            }
        }

        return new ScanResult(occurrences);
    }

    private static BlockPosition positionOf(int index) {
        int x = index & 0xF; // index & 15
        int z = (index >> 4) & 0xF;
        int y = (index >> 8) & 0xF;
        return new BlockPosition(x, y, z);
    }
}
