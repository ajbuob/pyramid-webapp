package com.abuob.pyramid.service;

import com.abuob.pyramid.dto.PyramidDto;
import com.abuob.pyramid.exception.InvalidWordException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PyramidServiceImpl implements PyramidService {

    private static final Logger logger = LoggerFactory.getLogger(PyramidServiceImpl.class);

    @Override
    public PyramidDto determineWordPyramid(String word, boolean isDisplayResultMap) throws InvalidWordException {

        logger.debug("Checking word:{}", word);

        if (!StringUtils.isAlpha(word)) {
            logger.error("Invalid word input:{}", word);
            throw new InvalidWordException("Invalid word input:" + word);
        }

        final PyramidDto pyramidDto = new PyramidDto(word);

        final Map<Character, Integer> characterCountMap = new HashMap<>();
        Integer distinctChars = 0;
        Integer maxCharCount = 1;

        //Parse input word to determine count of each character
        for (Character c : word.toCharArray()) {
            if (characterCountMap.containsKey(c)) {
                //Character already present, increment count by 1
                characterCountMap.put(c, characterCountMap.get(c) + 1);
            } else {
                //Character NOT present, create entry pair
                distinctChars++;
                characterCountMap.put(c, 1);
            }
            //Keep track of maximum occurrences
            if (characterCountMap.get(c) > maxCharCount) {
                maxCharCount = characterCountMap.get(c);
            }
        }
        logger.debug("POST PROCESSING - distinctChars:{} maxCharCount: {}", distinctChars, maxCharCount);

        //Pyramid words will have a integer count sequence of 1,2,..,maxCharCount
        //with no gaps or duplicates in the sequence.
        //maxCharCount > distinctChars (gap - one count is missing in the sequence)
        //maxCharCount < distinctChars (duplicates - more than 1 character exists with the same count)
        final boolean isPyramidWord = maxCharCount.equals(distinctChars);
        logger.debug("RESULT - word:{} isPyramidWord: {}", word, isPyramidWord);

        pyramidDto.setPyramidWord(isPyramidWord);

        //Populate reverse map for valid solution (avoid duplicate keys)
        //and if the method caller requests it
        if (isPyramidWord && isDisplayResultMap) {
            //TreeMap guarantees ascending integer sequence order of keys
            Map<Integer, Character> reverseMap = new TreeMap<>();
            for (Map.Entry<Character, Integer> entry : characterCountMap.entrySet()) {
                reverseMap.put(entry.getValue(), entry.getKey());
            }
            pyramidDto.setIntegerToCharacterMap(reverseMap);
        }
        return pyramidDto;
    }

    @Override
    public PyramidDto determineWordPyramid(String word) throws InvalidWordException {
        return determineWordPyramid(word, false);
    }
}
