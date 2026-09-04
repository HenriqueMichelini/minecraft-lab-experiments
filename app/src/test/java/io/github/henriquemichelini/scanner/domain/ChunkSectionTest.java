package io.github.henriquemichelini.scanner.domain;

import io.github.henriquemichelini.scanner.domain.BlockType;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class ChunkSectionTest {

    @Test
    void shouldAcceptExactly4096Blocks() {
        var blocks = new BlockType[ChunkSection.BLOCK_COUNT];

        assertDoesNotThrow(
                () -> new ChunkSection(blocks)
        );
    }

    @Test
    void shouldRejectTooFewBlocks() {
        var blocks =
                new BlockType[ChunkSection.BLOCK_COUNT - 1];

        assertThrows(
                IllegalArgumentException.class,
                () -> new ChunkSection(blocks)
        );
    }

    @Test
    void shouldRejectTooManyBlocks() {
        var blocks =
                new BlockType[ChunkSection.BLOCK_COUNT + 1];

        assertThrows(
                IllegalArgumentException.class,
                () -> new ChunkSection(blocks)
        );
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(
                NullPointerException.class,
                () -> new ChunkSection(null)
        );
    }
}
