package com.abuob.pyramid.controller;

import com.abuob.pyramid.dto.PyramidDto;
import com.abuob.pyramid.exception.InvalidWordException;
import com.abuob.pyramid.service.PyramidService;
import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
class PyramidControllerTest {

    @MockBean
    private PyramidService pyramidService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_determineWordPyramid_badRequest() throws Exception {

        when(pyramidService.determineWordPyramid(anyString(), anyBoolean())).thenThrow(InvalidWordException.class);
        final String testWord = "sad342";

        mockMvc.perform(get("/pyramid/" + testWord)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.word", Matchers.equalTo(testWord)))
                .andExpect(jsonPath("$.isPyramidWord", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.integerToCharacterMap", Matchers.nullValue()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_determineWordPyramid_successTrue() throws Exception {

        final String testWord = "banana";

        PyramidDto pyramidDto = new PyramidDto(testWord);
        pyramidDto.setPyramidWord(true);

        when(pyramidService.determineWordPyramid(anyString(), anyBoolean()))
                .thenReturn(pyramidDto);

        mockMvc.perform(get("/pyramid/" + testWord)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.word", Matchers.equalTo(testWord)))
                .andExpect(jsonPath("$.isPyramidWord", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.integerToCharacterMap", Matchers.nullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_determineWordPyramid_successTrueWithMap() throws Exception {

        final String testWord = "banana";

        Map<Integer, Character> integerCharacterMap = ImmutableMap.<Integer, Character>builder()
                .put(1, 'b')
                .put(2, 'a')
                .put(3, 'n')
                .build();

        PyramidDto pyramidDto = new PyramidDto(testWord);
        pyramidDto.setPyramidWord(true);
        pyramidDto.setIntegerToCharacterMap(integerCharacterMap);

        when(pyramidService.determineWordPyramid(anyString(), anyBoolean()))
                .thenReturn(pyramidDto);

        mockMvc.perform(get("/pyramid/" + testWord + "?displayResultMap=true")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.word", Matchers.equalTo(testWord)))
                .andExpect(jsonPath("$.isPyramidWord", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.integerToCharacterMap", Matchers.aMapWithSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_determineWordPyramid_successFalse() throws Exception {

        final String testWord = "bandana";

        PyramidDto pyramidDto = new PyramidDto(testWord);
        pyramidDto.setPyramidWord(false);

        when(pyramidService.determineWordPyramid(anyString(), anyBoolean()))
                .thenReturn(pyramidDto);

        mockMvc.perform(get("/pyramid/" + testWord)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.word", Matchers.equalTo(testWord)))
                .andExpect(jsonPath("$.isPyramidWord", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.integerToCharacterMap", Matchers.nullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_determineWordPyramid_successFalseWithMap() throws Exception {

        final String testWord = "bandana";

        PyramidDto pyramidDto = new PyramidDto(testWord);
        pyramidDto.setPyramidWord(false);

        when(pyramidService.determineWordPyramid(anyString(), anyBoolean()))
                .thenReturn(pyramidDto);

        mockMvc.perform(get("/pyramid/" + testWord + "?displayResultMap=true")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.word", Matchers.equalTo(testWord)))
                .andExpect(jsonPath("$.isPyramidWord", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.integerToCharacterMap", Matchers.nullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_determineWordPyramid_error() throws Exception {

        when(pyramidService.determineWordPyramid(anyString(), anyBoolean())).thenThrow(RuntimeException.class);
        final String testWord = "banana";

        mockMvc.perform(get("/pyramid/" + testWord)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.word", Matchers.equalTo(testWord)))
                .andExpect(jsonPath("$.isPyramidWord", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.integerToCharacterMap", Matchers.nullValue()))
                .andExpect(status().isInternalServerError());
    }
}