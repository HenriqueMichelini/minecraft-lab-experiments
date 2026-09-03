package io.github.henriquemichelini.scanner.domain;

import java.util.List;

public record ScanResult(
        List<OreOcurrency> occurrencies
) {
    public int oreCount() {
        return occurrencies.size();
    }
}
