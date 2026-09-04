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
