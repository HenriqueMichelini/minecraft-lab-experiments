package io.github.henriquemichelini.scanner.domain;

import java.util.Objects;

public final class OreMaskScanResult {

    public static final int WORD_BITS = Long.SIZE;
    public static final int WORD_COUNT =
            ChunkSection.BLOCK_COUNT / WORD_BITS;

    private final long[] words;

    public OreMaskScanResult(long[] words) {
        this.words = Objects.requireNonNull(words);

        if (words.length != WORD_COUNT) {
            throw new IllegalArgumentException(
                    "Expected %d words, got %d"
                            .formatted(
                                    WORD_COUNT,
                                    words.length
                            )
            );
        }
    }

    public boolean contains(int blockIndex) {
        checkBlockIndex(blockIndex);

        int wordIndex = blockIndex >>> 6;
        int bitIndex = blockIndex & 63;

        return (words[wordIndex] & (1L << bitIndex)) != 0;
    }

    public int count() {
        int count = 0;

        for (long word : words) {
            count += Long.bitCount(word);
        }

        return count;
    }

    public int wordCount() {
        return words.length;
    }

    public long wordAt(int wordIndex) {
        return words[wordIndex];
    }

    private static void checkBlockIndex(int blockIndex) {
        if (blockIndex < 0
                || blockIndex >= ChunkSection.BLOCK_COUNT) {

            throw new IndexOutOfBoundsException(blockIndex);
        }
    }
}