package com.proway.app.items.enums;

import lombok.Getter;

@Getter
public enum Rarity {
    COMMON(200),
    RARE(500),
    SUPER_RARE(1000),
    EPIC(2000);

    private final int goldValue;

    Rarity(int goldValue) {
        this.goldValue = goldValue;
    }

}

