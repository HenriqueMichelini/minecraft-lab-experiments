//package io.github.henriquemichelini.benchmark.infra;
//
//import io.github.henriquemichelini.dataset.domain.DatasetScenario;
//import io.github.henriquemichelini.dataset.domain.DatasetSize;
//import io.github.henriquemichelini.dataset.domain.DatasetSpec;
//import io.github.henriquemichelini.dataset.infra.SyntheticDatasetGenerator;
//import io.github.henriquemichelini.scanner.domain.ChunkSection;
//import io.github.henriquemichelini.scanner.domain.ScanResult;
//import io.github.henriquemichelini.scanner.infra.NaiveOreScanner;
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
//public class NaiveOreScannerBenchmark {
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
//    private NaiveOreScanner scanner;
//    private ChunkSection section;
//
//    @Setup(Level.Trial)
//    public void setup() {
//        scanner = new NaiveOreScanner();
//
//        var spec = new DatasetSpec(
//                DatasetScenario.valueOf(scenario),
//                DatasetSize.TINY,
//                42L
//        );
//
//        var dataset =
//                new SyntheticDatasetGenerator()
//                        .generate(spec);
//
//        section = dataset.sections()[0];
//    }
//
//    @Benchmark
//    public ScanResult scan() {
//        return scanner.scan(section);
//    }
//
//    @Benchmark
//    public int countOres() {
//        int count = 0;
//
//        for (int index = 0; index < section.size(); index++) {
//            if (section.blockAt(index).isOre()) {
//                count++;
//            }
//        }
//
//        return count;
//    }
//
//    @Benchmark
//    public int calculatePositions() {
//        int checksum = 0;
//
//        for (int index = 0; index < section.size(); index++) {
//            if (!section.blockAt(index).isOre()) {
//                continue;
//            }
//
//            int x = index & 15;
//            int z = (index >> 4) & 15;
//            int y = (index >> 8) & 15;
//
//            checksum += x + y + z;
//        }
//
//        return checksum;
//    }
//}