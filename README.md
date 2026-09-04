FINDING-001
Ore detection itself is cheap.
The baseline can scan one hot 4096-block section in ~1.5–2.4 µs.

FINDING-002
Data distribution materially affects branch-heavy scanning.
RANDOM_50 is substantially slower than EMPTY/FULL in count-only scans.

FINDING-003
Object materialization dominates dense-result workloads.
FULL grows from ~3.75 µs with coordinate calculation to ~41.8 µs
when BlockPosition/OreOccurrence/List results are materialized.

FINDING-004

Replacing object-per-occurrence output with a contiguous
primitive array improved FULL throughput by ~15.2x and
reduced allocation by ~93.3%.

Even SPARSE improved ~2x despite allocating ~6.3x more
bytes, indicating that allocation volume alone does not
describe allocation cost; object count and memory layout
matter substantially.
