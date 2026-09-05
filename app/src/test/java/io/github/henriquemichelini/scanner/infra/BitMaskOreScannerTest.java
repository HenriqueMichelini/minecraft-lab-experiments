package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.dataset.domain.DatasetScenario;
import io.github.henriquemichelini.dataset.domain.DatasetSize;
import io.github.henriquemichelini.dataset.domain.DatasetSpec;
import io.github.henriquemichelini.dataset.infra.SyntheticDatasetGenerator;
import io.github.henriquemichelini.scanner.domain.BlockPosition;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.OreMaskScanResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class BitMaskOreScannerTest {

    private final SyntheticDatasetGenerator generator =
            new SyntheticDatasetGenerator();

    private final NaiveOreScanner referenceScanner =
            new NaiveOreScanner();

    private final BitMaskOreScanner scanner =
            new BitMaskOreScanner();

    @ParameterizedTest
    @EnumSource(DatasetScenario.class)
    void shouldProduceSameResultsAsReferenceScanner(
            DatasetScenario scenario
    ) {
        var dataset = generator.generate(
                new DatasetSpec(
                        scenario,
                        DatasetSize.TINY,
                        42L
                )
        );

        var section = dataset.sections()[0];

        var expected =
                referenceScanner.scan(section);

        var actual =
                scanner.scan(section);

        assertEquals(
                expected.oreCount(),
                actual.count()
        );

        for (var occurrence : expected.occurrences()) {
            int index =
                    toIndex(occurrence.position());

            assertTrue(
                    actual.contains(index),
                    "Expected ore at index " + index
            );
        }
    }

    @Test
    void emptyDatasetShouldHaveNoBitsSet() {
        var dataset = generator.generate(
                new DatasetSpec(
                        DatasetScenario.EMPTY,
                        DatasetSize.TINY,
                        42L
                )
        );

        var result =
                scanner.scan(dataset.sections()[0]);

        assertEquals(0, result.count());

        for (int word = 0;
             word < result.wordCount();
             word++) {

            assertEquals(
                    0L,
                    result.wordAt(word)
            );
        }
    }

    @Test
    void fullDatasetShouldHaveAllBitsSet() {
        var dataset = generator.generate(
                new DatasetSpec(
                        DatasetScenario.FULL,
                        DatasetSize.TINY,
                        42L
                )
        );

        var result =
                scanner.scan(dataset.sections()[0]);

        assertEquals(
                ChunkSection.BLOCK_COUNT,
                result.count()
        );

        for (int word = 0;
             word < result.wordCount();
             word++) {

            assertEquals(
                    -1L,
                    result.wordAt(word)
            );
        }
    }

    @Test
    void shouldAddressBoundaryIndexesCorrectly() {
        var dataset = generator.generate(
                new DatasetSpec(
                        DatasetScenario.FULL,
                        DatasetSize.TINY,
                        42L
                )
        );

        var result =
                scanner.scan(dataset.sections()[0]);

        assertTrue(result.contains(0));
        assertTrue(result.contains(63));
        assertTrue(result.contains(64));
        assertTrue(result.contains(127));
        assertTrue(result.contains(4095));
    }

    @Test
    void shouldRejectInvalidIndexes() {
        var dataset = generator.generate(
                new DatasetSpec(
                        DatasetScenario.EMPTY,
                        DatasetSize.TINY,
                        42L
                )
        );

        var result =
                scanner.scan(dataset.sections()[0]);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> result.contains(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> result.contains(4096)
        );
    }

    private static int toIndex(
            BlockPosition position
    ) {
        return (position.y() << 8)
                | (position.z() << 4)
                | position.x();
    }
}