package io.github.henriquemichelini.benchmark.infra;

import io.github.henriquemichelini.dataset.domain.DatasetScenario;
import io.github.henriquemichelini.dataset.domain.DatasetSize;
import io.github.henriquemichelini.dataset.domain.DatasetSpec;
import io.github.henriquemichelini.dataset.infra.SyntheticDatasetGenerator;
import io.github.henriquemichelini.scanner.domain.ChunkSection;
import io.github.henriquemichelini.scanner.domain.OreMaskScanResult;
import io.github.henriquemichelini.scanner.infra.BitMaskOreScanner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(
        iterations = 5,
        time = 1,
        timeUnit = TimeUnit.SECONDS
)
@Measurement(
        iterations = 5,
        time = 1,
        timeUnit = TimeUnit.SECONDS
)
@Fork(2)
@State(Scope.Thread)
public class BitMaskOreScannerBenchmark2 {

    @Param({
            "EMPTY",
            "SPARSE",
            "NORMAL",
            "DENSE",
            "RANDOM_50",
            "FULL"
    })
    public String scenario;

    private BitMaskOreScanner scanner;
    private ChunkSection section;

    private OreMaskScanResult result;

    @Setup(Level.Trial)
    public void setup() {
        scanner = new BitMaskOreScanner();

        var dataset = new SyntheticDatasetGenerator()
                .generate(
                        new DatasetSpec(
                                DatasetScenario.valueOf(scenario),
                                DatasetSize.TINY,
                                42L
                        )
                );

        section = dataset.sections()[0];

        result = scanner.scan(section);
    }

    @Benchmark
    public int countMask() {
        return result.count();
    }
}

