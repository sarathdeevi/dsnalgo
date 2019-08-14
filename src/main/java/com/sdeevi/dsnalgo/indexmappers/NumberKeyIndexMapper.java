package com.sdeevi.dsnalgo.indexmappers;

import java.util.HashMap;
import java.util.Map;

public class NumberKeyIndexMapper implements CharIndexMapper {

    private Map<Character, Character> numberCharMap = new HashMap<>();

    public NumberKeyIndexMapper() {
        numberCharMap.put('A', '2');
        numberCharMap.put('B', '2');
        numberCharMap.put('C', '2');
        numberCharMap.put('D', '3');
        numberCharMap.put('E', '3');
        numberCharMap.put('F', '3');
        numberCharMap.put('G', '4');
        numberCharMap.put('H', '4');
        numberCharMap.put('I', '4');
        numberCharMap.put('J', '5');
        numberCharMap.put('K', '5');
        numberCharMap.put('L', '5');
        numberCharMap.put('M', '6');
        numberCharMap.put('N', '6');
        numberCharMap.put('O', '6');
        numberCharMap.put('P', '7');
        numberCharMap.put('Q', '7');
        numberCharMap.put('R', '7');
        numberCharMap.put('S', '7');
        numberCharMap.put('T', '8');
        numberCharMap.put('U', '8');
        numberCharMap.put('V', '8');
        numberCharMap.put('W', '9');
        numberCharMap.put('X', '9');
        numberCharMap.put('Y', '9');
        numberCharMap.put('Z', '9');
        numberCharMap.put('1', '1');
        numberCharMap.put('2', '2');
        numberCharMap.put('3', '3');
        numberCharMap.put('4', '4');
        numberCharMap.put('5', '5');
        numberCharMap.put('6', '6');
        numberCharMap.put('7', '7');
        numberCharMap.put('8', '8');
        numberCharMap.put('9', '9');
    }

    @Override
    public Character map(Character c) {
        return numberCharMap.get(Character.toUpperCase(c));
    }
}
