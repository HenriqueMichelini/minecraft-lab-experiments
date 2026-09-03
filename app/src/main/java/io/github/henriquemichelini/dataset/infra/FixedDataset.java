package io.github.henriquemichelini.dataset.infra;

import io.github.henriquemichelini.dataset.domain.Dataset;
import io.github.henriquemichelini.scanner.domain.BlockType;
import io.github.henriquemichelini.scanner.domain.ChunkSection;

import java.util.Arrays;

public class FixedDataset implements Dataset {
    private final ChunkSection[] sections;

    public FixedDataset() {
        var blocks = new BlockType[ChunkSection.BLOCK_COUNT];

        Arrays.fill(blocks, BlockType.STONE);

        blocks[0] = BlockType.DIAMOND_ORE;
        blocks[100] = BlockType.IRON_ORE;
        blocks[4095] = BlockType.COAL_ORE;

        this.sections = new ChunkSection[] {
                new ChunkSection(blocks)
        };
    }

    @Override
    public String name() {
        return "fixed";
    }

    @Override
    public ChunkSection[] sections() {
        return sections;
    }
}
