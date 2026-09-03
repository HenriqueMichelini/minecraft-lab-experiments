package dataset;

import io.github.henriquemichelini.dataset.infra.FixedDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class FixedDatasetTest {

    @Test
    void shouldContainSingleSection() {
        var dataset = new FixedDataset();

        assertEquals("fixed", dataset.name());
        assertEquals(1, dataset.sections().length);
    }
}
