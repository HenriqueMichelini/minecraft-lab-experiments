package io.github.henriquemichelini.dataset.domain;

public enum DatasetScenario {
    EMPTY(0.00),
    SPARSE(0.01),
    NORMAL(0.05),
    DENSE(0.25),
    RANDOM_50(0.50),
    FULL(1.00);

    private final double oreRatio;

    DatasetScenario(double oreRatio) {
        this.oreRatio = oreRatio;
    }

    public double oreRatio() {
        return oreRatio;
    }
}
