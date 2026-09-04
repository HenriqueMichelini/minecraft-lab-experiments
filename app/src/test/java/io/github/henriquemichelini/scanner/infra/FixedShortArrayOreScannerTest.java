package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.dataset.domain.DatasetScenario;
import io.github.henriquemichelini.dataset.domain.DatasetSize;
import io.github.henriquemichelini.dataset.domain.DatasetSpec;
import io.github.henriquemichelini.dataset.infra.SyntheticDatasetGenerator;
import io.github.henriquemichelini.scanner.domain.BlockPosition;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedShortArrayOreScannerTest {

    private final SyntheticDatasetGenerator generator =
            new SyntheticDatasetGenerator();

    private final NaiveOreScanner referenceScanner =
            new NaiveOreScanner();

    private final FixedShortArrayOreScanner scanner =
            new FixedShortArrayOreScanner();

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

        for (int resultIndex = 0;
             resultIndex < actual.count();
             resultIndex++) {

            var occurrence =
                    expected.occurrences()
                            .get(resultIndex);

            int expectedIndex =
                    toIndex(occurrence.position());

            assertEquals(
                    expectedIndex,
                    actual.indexAt(resultIndex),
                    "Different ore index at result "
                            + resultIndex
            );
        }
    }

    @Test
    void shouldStoreAllIndexesForFullDataset() {
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

        assertEquals(
                ChunkSection.BLOCK_COUNT,
                result.capacity()
        );

        for (int index = 0;
             index < ChunkSection.BLOCK_COUNT;
             index++) {

            assertEquals(
                    index,
                    result.indexAt(index)
            );
        }
    }

    @Test
    void shouldReturnNoIndexesForEmptyDataset() {
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

        assertEquals(
                ChunkSection.BLOCK_COUNT,
                result.capacity()
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