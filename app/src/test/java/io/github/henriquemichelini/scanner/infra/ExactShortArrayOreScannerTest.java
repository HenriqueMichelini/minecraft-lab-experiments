package io.github.henriquemichelini.scanner.infra;

import io.github.henriquemichelini.dataset.domain.DatasetScenario;
import io.github.henriquemichelini.dataset.domain.DatasetSize;
import io.github.henriquemichelini.dataset.domain.DatasetSpec;
import io.github.henriquemichelini.dataset.infra.SyntheticDatasetGenerator;
import io.github.henriquemichelini.scanner.domain.BlockPosition;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExactShortArrayOreScannerTest {

    private final SyntheticDatasetGenerator generator =
            new SyntheticDatasetGenerator();

    private final NaiveOreScanner referenceScanner =
            new NaiveOreScanner();

    private final ExactShortArrayOreScanner scanner =
            new ExactShortArrayOreScanner();

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

            assertEquals(
                    toIndex(occurrence.position()),
                    actual.indexAt(resultIndex),
                    "Different ore index at result "
                            + resultIndex
            );
        }
    }

    @ParameterizedTest
    @MethodSource("expectedCapacities")
    void shouldAllocateExactResultCapacity(
            DatasetScenario scenario,
            int expectedCapacity
    ) {
        var dataset = generator.generate(
                new DatasetSpec(
                        scenario,
                        DatasetSize.TINY,
                        42L
                )
        );

        var result =
                scanner.scan(dataset.sections()[0]);

        assertEquals(
                expectedCapacity,
                result.capacity()
        );

        assertEquals(
                expectedCapacity,
                result.count()
        );
    }

    @Test
    void shouldStoreEveryIndexForFullDataset() {
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

        for (int index = 0;
             index < ChunkSection.BLOCK_COUNT;
             index++) {

            assertEquals(
                    index,
                    result.indexAt(index)
            );
        }
    }

    private static Stream<Arguments> expectedCapacities() {
        return Stream.of(
                Arguments.of(
                        DatasetScenario.EMPTY,
                        0
                ),
                Arguments.of(
                        DatasetScenario.SPARSE,
                        41
                ),
                Arguments.of(
                        DatasetScenario.NORMAL,
                        205
                ),
                Arguments.of(
                        DatasetScenario.DENSE,
                        1_024
                ),
                Arguments.of(
                        DatasetScenario.RANDOM_50,
                        2_048
                ),
                Arguments.of(
                        DatasetScenario.FULL,
                        4_096
                )
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