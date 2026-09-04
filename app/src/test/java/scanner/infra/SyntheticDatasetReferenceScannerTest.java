package scanner.infra;

import io.github.henriquemichelini.dataset.domain.DatasetScenario;
import io.github.henriquemichelini.dataset.domain.DatasetSize;
import io.github.henriquemichelini.dataset.domain.DatasetSpec;
import io.github.henriquemichelini.dataset.infra.SyntheticDatasetGenerator;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.infra.NaiveOreScanner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SyntheticDatasetReferenceScannerTest {

    private final SyntheticDatasetGenerator generator =
            new SyntheticDatasetGenerator();

    private final NaiveOreScanner scanner =
            new NaiveOreScanner();

    @ParameterizedTest
    @EnumSource(DatasetScenario.class)
    void referenceScannerShouldFindExpectedNumberOfOres(
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

        var result = scanner.scan(section);

        int expectedOreCount = (int) Math.round(
                ChunkSection.BLOCK_COUNT
                        * scenario.oreRatio()
        );

        assertEquals(
                expectedOreCount,
                result.oreCount(),
                () -> """
                        Unexpected result for scenario %s.
                        Expected %d ores but scanner found %d.
                        """.formatted(
                        scenario,
                        expectedOreCount,
                        result.oreCount()
                )
        );
    }
}