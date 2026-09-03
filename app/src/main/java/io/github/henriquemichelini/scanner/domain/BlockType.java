package io.github.henriquemichelini.scanner.domain;

public enum BlockType {
    AIR,
    STONE,
    DEEPSLATE,

    COAL_ORE,
    IRON_ORE,
    GOLD_ORE,
    REDSTONE_ORE,
    LAPIS_ORE,
    DIAMOND_ORE,
    EMERALD_ORE;

    public boolean isOre() {
        return switch (this) {
            case COAL_ORE,
                 IRON_ORE,
                 GOLD_ORE,
                 REDSTONE_ORE,
                 LAPIS_ORE,
                 DIAMOND_ORE,
                 EMERALD_ORE -> true;

            default -> false;
        };
    }
}
