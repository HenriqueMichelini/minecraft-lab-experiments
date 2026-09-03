package scanner.domain;

import io.github.henriquemichelini.scanner.domain.BlockType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class BlockTypeTest {

    @Test
    void shouldRecognizeOres() {
        assertTrue(BlockType.COAL_ORE.isOre());
        assertTrue(BlockType.IRON_ORE.isOre());
        assertTrue(BlockType.GOLD_ORE.isOre());
        assertTrue(BlockType.REDSTONE_ORE.isOre());
        assertTrue(BlockType.LAPIS_ORE.isOre());
        assertTrue(BlockType.DIAMOND_ORE.isOre());
        assertTrue(BlockType.EMERALD_ORE.isOre());
    }

    @Test
    void shouldRejectNonOres() {
        assertFalse(BlockType.AIR.isOre());
        assertFalse(BlockType.STONE.isOre());
        assertFalse(BlockType.DEEPSLATE.isOre());
    }
}
