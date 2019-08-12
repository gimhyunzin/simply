package com.kakaopay.simply.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hjk
 */
@RequiredArgsConstructor
public enum Ascii {
    ALPHABET_UPPER(65, 90),
    ALPHABET_LOWER(97, 122)
    ;

    @Getter
    private final int min;
    @Getter
    private final int max;

    public int getRange() {
        return this.max - this.min + 1;
    }
}
