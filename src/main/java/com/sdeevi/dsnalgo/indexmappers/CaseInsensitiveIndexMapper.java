package com.sdeevi.dsnalgo.indexmappers;

public class CaseInsensitiveIndexMapper implements CharIndexMapper {
    @Override
    public Character map(Character c) {
        return Character.toLowerCase(c);
    }
}
