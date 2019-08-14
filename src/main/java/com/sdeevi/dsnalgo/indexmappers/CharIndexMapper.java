package com.sdeevi.dsnalgo.indexmappers;

public interface CharIndexMapper {

    Character map(Character c1);

    default String map(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(map(s.charAt(i)));
        }
        return sb.toString();
    }
}
