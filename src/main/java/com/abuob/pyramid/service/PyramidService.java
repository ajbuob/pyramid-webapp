package com.abuob.pyramid.service;

import com.abuob.pyramid.dto.PyramidDto;
import com.abuob.pyramid.exception.InvalidWordException;

public interface PyramidService {

    /**
     * Determines if the input word is a 'pyramid word'
     * (word in which you can arrange the letters in increasing frequency, starting with 1
     * and continuing without gaps and without duplicates)
     *
     * @param word               the string to be tested
     * @param isDisplayResultMap will populate the integerToCharacterMap in PyramidDto when the word is a pyramid word
     * @return the dto containing the result
     * @throws InvalidWordException if the input word is null or empty
     **/
    PyramidDto determineWordPyramid(final String word, boolean isDisplayResultMap) throws InvalidWordException;

    /**
     * Overloaded method of the above determineWordPyramid(String word, boolean isDisplayResultMap)
     * with isDisplayResultMap=FALSE
     *
     * @param word the string to be tested
     * @return the dto containing the result
     * @throws InvalidWordException if the input word is null or empty
     **/
    PyramidDto determineWordPyramid(final String word) throws InvalidWordException;
}
