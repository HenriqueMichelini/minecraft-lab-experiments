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

FINDING-005

A fixed short[4096] result buffer outperformed the equivalent
int[4096] buffer in every tested distribution.

Allocation dropped from ~16.4 KB/op to ~8.2 KB/op (-49.9%),
while throughput improved between ~6% and ~32%.

This suggests that primitive width matters even for this small
working set, likely due to reduced allocation/zeroing cost,
memory traffic and cache footprint.

FINDING-006

Compared with the object-based reference implementation,
the fixed short[] representation reached ~18.8x higher
throughput on FULL sections while reducing allocation from
~247 KB/op to ~8.2 KB/op.

FINDING-007

Geometric dynamic growth greatly reduces wasted memory
for sparse outputs, but multiple allocations and copies
substantially reduce throughput.

Only EMPTY outperformed the fixed short[] implementation.
For every non-empty workload, fixed short[] was faster.

FINDING-008

Allocation volume alone is not a sufficient proxy for
allocation cost.

SPARSE dynamic output allocated only ~296 B/op versus
~8.2 KB/op for fixed short[], yet ran ~52% slower.