package scanner.contract;

import io.github.henriquemichelini.scanner.domain.OreScanner;
import io.github.henriquemichelini.scanner.infra.NaiveOreScanner;

final class NaiveOreScannerTest extends OreScannerContractTest {

    @Override
    protected OreScanner scanner() {
        return new NaiveOreScanner();
    }
}
