package io.github.henriquemichelini.dataset.domain;

public record DatasetSpec(
        DatasetScenario scenario,
        DatasetSize size,
        long seed
) {
}
