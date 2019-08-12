package com.kakaopay.simply.controller;

import com.kakaopay.simply.dto.Link;
import com.kakaopay.simply.dto.LinkParams;
import com.kakaopay.simply.service.SimplyService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by hjk
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SimplyControllerTest {

    @InjectMocks
    private SimplyController sut;

    @Mock
    private SimplyService simplyService;

    private LinkParams params;

    @Before
    public void setUp() throws Exception {
        params = new LinkParams();
        params.setUrl("http://test.com");
    }

    @Test
    public void simplyConvert() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl(params.getUrl())
                .shortCode("TESTCODE")
                .build();

        given(simplyService.convertLink(anyString())).willReturn(expected);

        // WHEN
        ResponseEntity<Link> actual = sut.simplyConvert(params);

        // THEN
        Assert.assertNotNull(actual.getBody());
        Assert.assertEquals(expected.getShortCode(), actual.getBody().getShortCode());
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());

        verify(simplyService, atLeastOnce()).convertLink(anyString());
    }

    @Test
    public void simplyLinks() throws Exception {

        // GIVEN
        int expectedSize = 3;
        List<Link> expected = Lists.newArrayList();
        IntStream.range(0, expectedSize).forEach(i -> expected.add(new Link()));

        given(simplyService.getAllLinks()).willReturn(expected);

        // WHEN
        ResponseEntity<List<Link>> actual = sut.simplyLinks();

        // THEN
        Assert.assertNotNull(actual.getBody());
        Assert.assertEquals(expectedSize, actual.getBody().size());
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());

        verify(simplyService, atLeastOnce()).getAllLinks();
    }
}