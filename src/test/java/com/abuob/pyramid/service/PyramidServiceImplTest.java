package com.abuob.pyramid.service;

import com.abuob.pyramid.dto.PyramidDto;
import com.abuob.pyramid.exception.InvalidWordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PyramidServiceImplTest {

    private PyramidService pyramidService;

    @BeforeEach
    public void setup() {
        pyramidService = new PyramidServiceImpl();
    }


    @Test
    public void test_determineWordPyramid_null() {
        assertThatThrownBy(() ->
        {
            pyramidService.determineWordPyramid(null, false);
        })
                .isInstanceOf(InvalidWordException.class)
                .hasMessage("Invalid word input:null")
                .hasNoCause();
    }

    @Test
    public void test_determineWordPyramid_empty() {
        assertThatThrownBy(() ->
        {
            pyramidService.determineWordPyramid("", false);
        })
                .isInstanceOf(InvalidWordException.class)
                .hasMessage("Invalid word input:")
                .hasNoCause();
    }

    @Test
    public void test_determineWordPyramid_singleChar() throws InvalidWordException {
        //[1:w]
        final String input = "w";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isTrue();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_singleCharRepeat() throws InvalidWordException {
        //[3:w]
        final String input = "www";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }


    @Test
    public void test_determineWordPyramid_success() throws InvalidWordException {
        //[1:b,2:n,3:a]
        final String input = "banana";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isTrue();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_successWithMap() throws InvalidWordException {
        //[1:b,2:n,3:a]
        final String input = "banana";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, true);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isTrue();
        assertThat(resultDto.getIntegerToCharacterMap()).isNotNull();
        assertThat(resultDto.getIntegerToCharacterMap()).isNotEmpty();
        assertThat(resultDto.getIntegerToCharacterMap()).hasSize(3);
    }

    @Test
    public void test_determineWordPyramid_duplicateCountBegin() throws InvalidWordException {
        //[1:b,1:d,2:n,3:a]
        final String input = "bandana";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_duplicateCountBeginWithMap() throws InvalidWordException {
        //[1:b,1:d,2:n,3:a]
        final String input = "bandana";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, true);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_duplicateCountMiddle() throws InvalidWordException {
        //[1:a,2:b,2:c,3:d]
        final String input = "abbccddd";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_duplicateCountMiddleWithMap() throws InvalidWordException {
        //[1:a,2:b,2:c,3:d]
        final String input = "abbccddd";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, true);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_duplicateCountEnd() throws InvalidWordException {
        //[1:b,2:b,2:c]
        final String input = "abbcc";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_duplicateCountEndWithMap() throws InvalidWordException {
        //[1:b,2:b,2:c]
        final String input = "abbcc";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, true);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_gapBeginning() throws InvalidWordException {
        //[2:b,3:c]
        final String input = "bbccc";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_gapBeginningWithMap() throws InvalidWordException {
        //[2:b,3:c]
        final String input = "bbccc";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, true);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_gapMiddle() throws InvalidWordException {
        //[1:a,3:c]
        final String input = "accc";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, false);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }

    @Test
    public void test_determineWordPyramid_gapMiddleWithMap() throws InvalidWordException {
        //[1:a,3:c]
        final String input = "accc";
        PyramidDto resultDto = pyramidService.determineWordPyramid(input, true);
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(input);
        assertThat(resultDto.isPyramidWord()).isFalse();
        assertThat(resultDto.getIntegerToCharacterMap()).isNull();
    }
}