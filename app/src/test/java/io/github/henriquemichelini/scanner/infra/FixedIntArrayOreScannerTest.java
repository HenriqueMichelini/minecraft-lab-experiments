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

class FixedIntArrayOreScannerTest {

    private final SyntheticDatasetGenerator generator =
            new SyntheticDatasetGenerator();

    private final NaiveOreScanner referenceScanner =
            new NaiveOreScanner();

    private final FixedIntArrayOreScanner scanner =
            new FixedIntArrayOreScanner();

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

        for (int i = 0;
             i < actual.count();
             i++) {

            var expectedOccurrence =
                    expected.occurrences().get(i);

            int expectedIndex =
                    toIndex(
                            expectedOccurrence.position()
                    );

            assertEquals(
                    expectedIndex,
                    actual.indexAt(i),
                    "Different ore index at result " + i
            );
        }
    }

    private static int toIndex(
            BlockPosition position
    ) {
        return (position.y() << 8)
                | (position.z() << 4)
                | position.x();
    }

    @Test
    void shouldStoreEveryBlockForFullDataset() {
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
    void shouldReturnZeroResultsForEmptyDataset() {
        var dataset = generator.generate(
                new DatasetSpec(
                        DatasetScenario.EMPTY,
                        DatasetSize.TINY,
                        42L
                )
        );

        var result =
                scanner.scan(dataset.sections()[0]);

        assertEquals(
                0,
                result.count()
        );

        assertEquals(
                ChunkSection.BLOCK_COUNT,
                result.capacity()
        );
    }
}
