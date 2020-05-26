package com.abuob.pyramid.controller;

import com.abuob.pyramid.dto.PyramidDto;
import com.abuob.pyramid.exception.InvalidWordException;
import com.abuob.pyramid.service.PyramidService;
import com.abuob.pyramid.web.PyramidQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pyramid")
public class PyramidController {

    private static final Logger logger = LoggerFactory.getLogger(PyramidController.class);

    private PyramidService pyramidService;

    @Autowired
    public PyramidController(PyramidService pyramidService) {
        this.pyramidService = pyramidService;
    }


    @GetMapping(value = "/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PyramidQueryResponse> determineWordPyramid(
            @PathVariable("word") String word,
            @RequestParam(value = "displayResultMap", required = false) boolean isDisplayResultMap) {

        PyramidQueryResponse pyramidQueryResponse = new PyramidQueryResponse();
        pyramidQueryResponse.setWord(word);

        final PyramidDto pyramidDto;
        final boolean isPyramidWord;
        final Map<Integer, Character> integerToCharacterMap;

        try {
            logger.debug("Checking word: {} ");
            pyramidDto = pyramidService.determineWordPyramid(word, isDisplayResultMap);

            isPyramidWord = pyramidDto.isPyramidWord();
            pyramidQueryResponse.setPyramidWord(isPyramidWord);

            //Populate response Map for pyramid word if user requests it
            if (isPyramidWord && isDisplayResultMap) {
                integerToCharacterMap = pyramidDto.getIntegerToCharacterMap();
                pyramidQueryResponse.setIntegerToCharacterMap(integerToCharacterMap);
            }
            logger.debug("RESPONSE - word: {} isPyramidWord: {}", word, isPyramidWord);
            return new ResponseEntity<>(pyramidQueryResponse, HttpStatus.OK);

        } catch (InvalidWordException iwe) {
            logger.error("Invalid word request: {}", word);
            return new ResponseEntity<>(pyramidQueryResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred - message", e.getMessage());
            return new ResponseEntity<>(pyramidQueryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
