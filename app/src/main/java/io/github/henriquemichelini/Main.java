package io.github.henriquemichelini;

import io.github.henriquemichelini.dataset.infra.FixedDataset;
import io.github.henriquemichelini.scanner.infra.NaiveOreScanner;

public final class Main {
    static void main(String[] args) {
        var dataset = new FixedDataset();
        var scanner = new NaiveOreScanner();

        for (var section : dataset.sections()) {
            var result = scanner.scan(section);

            System.out.printf(
                    "%s: %d ores%n",
                    dataset.name(),
                    result.oreCount()
            );
        }
    }
}
