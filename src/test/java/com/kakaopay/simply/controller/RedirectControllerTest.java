package com.kakaopay.simply.controller;

import com.kakaopay.simply.dto.Link;
import com.kakaopay.simply.service.SimplyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

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
public class RedirectControllerTest {

    @InjectMocks
    private RedirectController sut;

    @Mock
    private SimplyService simplyService;

    private String shortCode;

    @Before
    public void setUp() throws Exception {
        shortCode = "TESTCODE";
    }

    @Test
    public void redirect_URL_없음() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl("")
                .shortCode(shortCode)
                .build();

        given(simplyService.getLinkByShortCode(anyString())).willReturn(expected);

        // WHEN
        String actual = sut.redirect(shortCode);

        // THEN
        Assert.assertTrue(StringUtils.isEmpty(actual.replace("redirect:", "")));
        Assert.assertEquals("redirect:", actual);

        verify(simplyService, atLeastOnce()).getLinkByShortCode(anyString());
    }

    @Test
    public void redirect_URL_있음() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl("http://test.com")
                .shortCode(shortCode)
                .build();

        given(simplyService.getLinkByShortCode(anyString())).willReturn(expected);

        // WHEN
        String actual = sut.redirect(shortCode);

        // THEN
        Assert.assertFalse(StringUtils.isEmpty(actual));
        Assert.assertEquals("redirect:" + expected.getOriginUrl(), actual);

        verify(simplyService, atLeastOnce()).getLinkByShortCode(anyString());
    }
}