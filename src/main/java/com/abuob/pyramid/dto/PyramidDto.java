package com.abuob.pyramid.dto;

import java.util.Map;

public class PyramidDto {

    private String word;

    private boolean isPyramidWord;

    private Map<Integer, Character> integerToCharacterMap;

    public PyramidDto(final String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public boolean isPyramidWord() {
        return isPyramidWord;
    }

    public void setPyramidWord(boolean pyramidWord) {
        this.isPyramidWord = pyramidWord;
    }

    public Map<Integer, Character> getIntegerToCharacterMap() {
        return integerToCharacterMap;
    }

    public void setIntegerToCharacterMap(Map<Integer, Character> integerToCharacterMap) {
        this.integerToCharacterMap = integerToCharacterMap;
    }
}
