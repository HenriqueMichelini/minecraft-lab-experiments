package io.github.henriquemichelini.dataset.infra;

import io.github.henriquemichelini.dataset.domain.DatasetScenario;
import io.github.henriquemichelini.dataset.domain.DatasetSize;
import io.github.henriquemichelini.dataset.domain.DatasetSpec;
import io.github.henriquemichelini.scanner.domain.BlockType;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SyntheticDatasetGeneratorTest {

    private final SyntheticDatasetGenerator generator =
            new SyntheticDatasetGenerator();

    @Test
    void shouldGenerateSameDatasetForSameSeed() {
        var spec = new DatasetSpec(
                DatasetScenario.RANDOM_50,
                DatasetSize.SMALL,
                42L
        );

        var first = generator.generate(spec);
        var second = generator.generate(spec);

        assertEquals(
                first.sections().length,
                second.sections().length
        );

        for (int sectionIndex = 0;
             sectionIndex < first.sections().length;
             sectionIndex++) {

            var firstSection =
                    first.sections()[sectionIndex];

            var secondSection =
                    second.sections()[sectionIndex];

            assertSectionsEqual(
                    firstSection,
                    secondSection
            );
        }
    }

    @Test
    void shouldGenerateDifferentDatasetForDifferentSeeds() {
        var first = generator.generate(
                new DatasetSpec(
                        DatasetScenario.RANDOM_50,
                        DatasetSize.SMALL,
                        42L
                )
        );

        var second = generator.generate(
                new DatasetSpec(
                        DatasetScenario.RANDOM_50,
                        DatasetSize.SMALL,
                        43L
                )
        );

        assertTrue(
                containsDifference(
                        first.sections(),
                        second.sections()
                ),
                "Different seeds should produce different block distributions"
        );
    }

    @ParameterizedTest
    @MethodSource("scenarioOreCounts")
    void shouldGenerateExactOreCountPerSection(
            DatasetScenario scenario,
            int expectedOreCount
    ) {
        var dataset = generator.generate(
                new DatasetSpec(
                        scenario,
                        DatasetSize.SMALL,
                        42L
                )
        );

        for (var section : dataset.sections()) {
            assertEquals(
                    expectedOreCount,
                    countOres(section),
                    () -> "Unexpected ore count for scenario " + scenario
            );
        }
    }

    @ParameterizedTest
    @MethodSource("datasetSizes")
    void shouldExposeConfiguredNumberOfSections(
            DatasetSize size,
            int expectedSectionCount
    ) {
        assertEquals(
                expectedSectionCount,
                size.sectionCount()
        );
    }

    @Test
    void shouldGenerateOnlyStoneAndDiamondOre() {
        var dataset = generator.generate(
                new DatasetSpec(
                        DatasetScenario.RANDOM_50,
                        DatasetSize.SMALL,
                        42L
                )
        );

        for (var section : dataset.sections()) {
            for (int index = 0;
                 index < section.size();
                 index++) {

                var type = section.blockAt(index);

                assertTrue(
                        type == BlockType.STONE
                                || type == BlockType.DIAMOND_ORE,
                        () -> "Unexpected block type: " + type
                );
            }
        }
    }

    private static Stream<Arguments> scenarioOreCounts() {
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

    private static Stream<Arguments> datasetSizes() {
        return Stream.of(
                Arguments.of(
                        DatasetSize.TINY,
                        1
                ),
                Arguments.of(
                        DatasetSize.SMALL,
                        64
                ),
                Arguments.of(
                        DatasetSize.MEDIUM,
                        1_024
                ),
                Arguments.of(
                        DatasetSize.LARGE,
                        4_096
                )
        );
    }

    private static int countOres(
            ChunkSection section
    ) {
        int count = 0;

        for (int index = 0;
             index < section.size();
             index++) {

            if (section.blockAt(index).isOre()) {
                count++;
            }
        }

        return count;
    }

    private static void assertSectionsEqual(
            ChunkSection expected,
            ChunkSection actual
    ) {
        assertEquals(
                expected.size(),
                actual.size()
        );

        for (int index = 0;
             index < expected.size();
             index++) {

            assertSame(
                    expected.blockAt(index),
                    actual.blockAt(index),
                    "Block differs at index " + index
            );
        }
    }

    private static boolean containsDifference(
            ChunkSection[] first,
            ChunkSection[] second
    ) {
        if (first.length != second.length) {
            return true;
        }

        for (int sectionIndex = 0;
             sectionIndex < first.length;
             sectionIndex++) {

            var firstSection =
                    first[sectionIndex];

            var secondSection =
                    second[sectionIndex];

            for (int blockIndex = 0;
                 blockIndex < firstSection.size();
                 blockIndex++) {

                if (firstSection.blockAt(blockIndex)
                        != secondSection.blockAt(blockIndex)) {

                    return true;
                }
            }
        }

        return false;
    }
}