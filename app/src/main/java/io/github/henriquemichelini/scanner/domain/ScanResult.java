package io.github.henriquemichelini.scanner.domain;

import java.util.List;

public record ScanResult(
        List<OreOccurrence> occurrences
) {
    public int oreCount() {
        return occurrences.size();
    }
}
