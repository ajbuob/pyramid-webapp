package com.abuob.pyramid.web;

import com.abuob.pyramid.dto.PyramidDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PyramidQueryResponse {

    private String word;

    private boolean isPyramidWord;

    private Map<Integer, Character> integerToCharacterMap;

    public PyramidQueryResponse() {
    }

    public PyramidQueryResponse(PyramidDto pyramidDto) {
        setWord(pyramidDto.getWord());
        setPyramidWord(pyramidDto.isPyramidWord());
        setIntegerToCharacterMap(pyramidDto.getIntegerToCharacterMap());
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @JsonProperty(value = "isPyramidWord")
    public boolean isPyramidWord() {
        return isPyramidWord;
    }

    public void setPyramidWord(boolean pyramidWord) {
        isPyramidWord = pyramidWord;
    }

    public Map<Integer, Character> getIntegerToCharacterMap() {
        return integerToCharacterMap;
    }

    public void setIntegerToCharacterMap(Map<Integer, Character> integerToCharacterMap) {
        this.integerToCharacterMap = integerToCharacterMap;
    }
}
