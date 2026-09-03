package io.github.henriquemichelini.scanner.domain;

import java.util.Objects;

public class ChunkSection {
    private static final int SIZE = 16;
    public static final int BLOCK_COUNT = SIZE * SIZE * SIZE;
    private final BlockType[] blocks;

    public ChunkSection(BlockType[] blocks) {
        Objects.requireNonNull(blocks);

        if (blocks.length != BLOCK_COUNT) {
            throw new IllegalArgumentException(
                    "Expected %d blocks, got %d"
                            .formatted(BLOCK_COUNT, blocks.length)
            );
        }

        this.blocks = blocks;
    }

    public BlockType blockAt(int index)
    {
        return blockAt(index);
    }

    public int size()  {
        return blocks.length;
    }
}
