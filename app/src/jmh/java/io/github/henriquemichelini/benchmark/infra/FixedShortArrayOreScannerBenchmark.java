//package io.github.henriquemichelini.benchmark.infra;
//
//import io.github.henriquemichelini.dataset.domain.DatasetScenario;
//import io.github.henriquemichelini.dataset.domain.DatasetSize;
//import io.github.henriquemichelini.dataset.domain.DatasetSpec;
//import io.github.henriquemichelini.dataset.infra.SyntheticDatasetGenerator;
//import io.github.henriquemichelini.scanner.domain.ChunkSection;
//import io.github.henriquemichelini.scanner.domain.ShortOreIndexScanResult;
//import io.github.henriquemichelini.scanner.infra.FixedShortArrayOreScanner;
//import org.openjdk.jmh.annotations.*;
//
//import java.util.concurrent.TimeUnit;
//
//@BenchmarkMode(Mode.Throughput)
//@OutputTimeUnit(TimeUnit.SECONDS)
//@Warmup(
//        iterations = 5,
//        time = 1,
//        timeUnit = TimeUnit.SECONDS
//)
//@Measurement(
//        iterations = 5,
//        time = 1,
//        timeUnit = TimeUnit.SECONDS
//)
//@Fork(2)
//@State(Scope.Thread)
//public class FixedShortArrayOreScannerBenchmark {
//
//    @Param({
//            "EMPTY",
//            "SPARSE",
//            "NORMAL",
//            "DENSE",
//            "RANDOM_50",
//            "FULL"
//    })
//    public String scenario;
//
//    private FixedShortArrayOreScanner scanner;
//    private ChunkSection section;
//
//    @Setup(Level.Trial)
//    public void setup() {
//        scanner =
//                new FixedShortArrayOreScanner();
//
//        var dataset =
//                new SyntheticDatasetGenerator()
//                        .generate(
//                                new DatasetSpec(
//                                        DatasetScenario.valueOf(
//                                                scenario
//                                        ),
//                                        DatasetSize.TINY,
//                                        42L
//                                )
//                        );
//
//        section =
//                dataset.sections()[0];
//    }
//
//    @Benchmark
//    public ShortOreIndexScanResult scan() {
//        return scanner.scan(section);
//    }
//}