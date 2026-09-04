package io.github.henriquemichelini.dataset.infra;

import io.github.henriquemichelini.dataset.domain.Dataset;
import io.github.henriquemichelini.dataset.domain.DatasetScenario;
import io.github.henriquemichelini.dataset.domain.DatasetSpec;
import io.github.henriquemichelini.scanner.domain.BlockType;
import io.github.henriquemichelini.scanner.domain.ChunkSection;

import java.util.Arrays;
import java.util.SplittableRandom;

public class SyntheticDatasetGenerator {
    public Dataset generate(DatasetSpec spec) {
        var random = new SplittableRandom(spec.seed());

        var sections = new ChunkSection[spec.size().sectionCount()];

        for (int i = 0; i < sections.length; i++) {
            sections[i] = generateSection(
                    spec.scenario(),
                    random.split()
            );
        }

        var name = "%s-%s-%d".formatted(
                spec.scenario(),
                spec.size(),
                spec.seed()
        );

        return new SyntheticDataset(
                name, sections
        );
    }

    private ChunkSection generateSection(
            DatasetScenario scenario,
            SplittableRandom random
    ) {
        var blocks = new BlockType[ChunkSection.BLOCK_COUNT];

        Arrays.fill(blocks, BlockType.STONE);

        int oreCount = (int) Math.round(
                ChunkSection.BLOCK_COUNT * scenario.oreRatio()
        );

        if (oreCount == ChunkSection.BLOCK_COUNT) {
            Arrays.fill(blocks, BlockType.DIAMOND_ORE);
            return new ChunkSection(blocks);
        }

        distributeOres(
                blocks,
                oreCount,
                random
        );

        return new ChunkSection(blocks);
    }

    private void distributeOres(
            BlockType[] blocks,
            int oreCount,
            SplittableRandom random
    ) {
        int[] indexes = new int[ChunkSection.BLOCK_COUNT];

        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        for (int i = 0; i < oreCount; i++) {
            int selected = random.nextInt(i, indexes.length);

            int temp = indexes[i];
            indexes[i] = indexes[selected];
            indexes[selected] = temp;

            blocks[indexes[i]] = BlockType.DIAMOND_ORE;
        }
    }
}
