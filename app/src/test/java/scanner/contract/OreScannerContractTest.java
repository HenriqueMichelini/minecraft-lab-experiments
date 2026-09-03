package scanner.contract;

import io.github.henriquemichelini.scanner.domain.BlockType;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.OreScanner;
import io.github.henriquemichelini.scanner.domain.OreOccurrence;
import io.github.henriquemichelini.scanner.domain.BlockPosition;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class OreScannerContractTest {

    protected abstract OreScanner scanner();

    @Test
    void shouldReturnNoOccurrencesWhenSectionContainsNoOres() {
        var blocks = filledWith(BlockType.STONE);

        var result = scanner().scan(new ChunkSection(blocks));

        assertEquals(0, result.oreCount());
        assertTrue(result.occurrences().isEmpty());
    }

    @Test
    void shouldFindSingleOre() {
        var blocks = filledWith(BlockType.STONE);

        blocks[0] = BlockType.DIAMOND_ORE;

        var result = scanner().scan(new ChunkSection(blocks));

        assertEquals(1, result.oreCount());

        assertEquals(
                Set.of(
                        new OreOccurrence(
                                new BlockPosition(0, 0, 0),
                                BlockType.DIAMOND_ORE
                        )
                ),
                Set.copyOf(result.occurrences())
        );
    }

    @Test
    void shouldFindMultipleOresAtCorrectPositions() {
        var blocks = filledWith(BlockType.STONE);

        blocks[0] = BlockType.DIAMOND_ORE;
        blocks[100] = BlockType.IRON_ORE;
        blocks[4095] = BlockType.COAL_ORE;

        var result = scanner().scan(new ChunkSection(blocks));

        var expected = Set.of(
                new OreOccurrence(
                        new BlockPosition(0, 0, 0),
                        BlockType.DIAMOND_ORE
                ),
                new OreOccurrence(
                        new BlockPosition(4, 0, 6),
                        BlockType.IRON_ORE
                ),
                new OreOccurrence(
                        new BlockPosition(15, 15, 15),
                        BlockType.COAL_ORE
                )
        );

        assertEquals(3, result.oreCount());
        assertEquals(expected, Set.copyOf(result.occurrences()));
    }

    @Test
    void shouldFindAllBlocksWhenSectionContainsOnlyOre() {
        var blocks = filledWith(BlockType.DIAMOND_ORE);

        var result = scanner().scan(new ChunkSection(blocks));

        assertEquals(
                ChunkSection.BLOCK_COUNT,
                result.oreCount()
        );
    }

    private static BlockType[] filledWith(BlockType type) {
        var blocks = new BlockType[ChunkSection.BLOCK_COUNT];
        Arrays.fill(blocks, type);
        return blocks;
    }
}
