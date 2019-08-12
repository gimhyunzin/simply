package com.kakaopay.simply.service;

import com.kakaopay.simply.dto.Link;
import com.kakaopay.simply.repository.SimplyRepository;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by hjk
 */
@RunWith(SpringRunner.class)
public class SimplyServiceImplTest {

    @InjectMocks
    private SimplyService sut = new SimplyServiceImpl();

    @Mock
    private SimplyRepository simplyRepository;

    private String shortCode;
    private String originUrl;

    @Before
    public void setUp() throws Exception {
        shortCode = "TESTCODE";
        originUrl = "http://test.com";
    }

    @Test
    public void getAllLinks() throws Exception {

        // GIVEN
        int expectedSize = 5;
        List<Link> expected = Lists.newArrayList();
        IntStream.range(0, expectedSize).forEach(i -> expected.add(new Link()));

        given(simplyRepository.findAll()).willReturn(expected);

        // WHEN
        List<Link> actual = sut.getAllLinks();

        // THEN
        Assert.assertNotNull(actual);
        Assert.assertEquals(expectedSize, actual.size());

        verify(simplyRepository, atLeastOnce()).findAll();
    }

    @Test
    public void getLinkByShortCode() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl(originUrl)
                .shortCode(shortCode)
                .build();

        given(simplyRepository.findByShortCode(shortCode)).willReturn(expected);

        // WHEN
        Link actual = sut.getLinkByShortCode(shortCode);

        // THEN
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getShortCode(), actual.getShortCode());
        Assert.assertEquals(expected.getOriginUrl(), actual.getOriginUrl());

        verify(simplyRepository, atLeastOnce()).findByShortCode(anyString());
    }

    @Test
    public void getLinkByOriginUrl() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl(originUrl)
                .shortCode(shortCode)
                .build();

        given(simplyRepository.findByOriginUrl(originUrl)).willReturn(expected);

        // WHEN
        Link actual = sut.getLinkByOriginUrl(originUrl);

        // THEN
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getOriginUrl(), actual.getOriginUrl());
        Assert.assertEquals(expected.getShortCode(), actual.getShortCode());

        verify(simplyRepository, atLeastOnce()).findByOriginUrl(anyString());
    }

    @Test
    public void convertLink_코드없음() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl(originUrl)
                .shortCode("")
                .build();

        given(simplyRepository.findByOriginUrl(anyString())).willReturn(expected);

        // WHEN
        Link actual = sut.convertLink(originUrl);

        // THEN
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getOriginUrl(), actual.getOriginUrl());
        Assert.assertNotEquals(expected.getShortCode(), actual.getShortCode());

        verify(simplyRepository, atLeastOnce()).findByOriginUrl(anyString());
        verify(simplyRepository, atLeastOnce()).existsByShortCode(anyString());
        verify(simplyRepository, atLeastOnce()).save(any(Link.class));
    }

    @Test
    public void convertLink_코드있음() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl(originUrl)
                .shortCode(shortCode)
                .build();

        given(simplyRepository.findByOriginUrl(anyString())).willReturn(expected);

        // WHEN
        Link actual = sut.convertLink(originUrl);

        // THEN
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getOriginUrl(), actual.getOriginUrl());
        Assert.assertEquals(expected.getShortCode(), actual.getShortCode());

        verify(simplyRepository, atLeastOnce()).findByOriginUrl(anyString());
        verify(simplyRepository, never()).existsByShortCode(anyString());
        verify(simplyRepository, never()).save(any(Link.class));
    }

    @Test
    public void convertLink_코드있음_동일한코드에_마지막슬래시() throws Exception {

        // GIVEN
        Link expected = Link.builder()
                .originUrl(originUrl + "/")
                .shortCode(shortCode)
                .build();

        given(simplyRepository.findByOriginUrl(anyString())).willReturn(expected);

        // WHEN
        Link actual = sut.convertLink(originUrl);

        // THEN
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getOriginUrl(), actual.getOriginUrl());
        Assert.assertEquals(expected.getShortCode(), actual.getShortCode());

        verify(simplyRepository, atLeastOnce()).findByOriginUrl(anyString());
        verify(simplyRepository, never()).existsByShortCode(anyString());
        verify(simplyRepository, never()).save(any(Link.class));
    }

    @Test(expected = Exception.class)
    public void convertLink_올바르지않은_URL() throws Exception {

        // WHEN
        sut.convertLink("THIS_IS_INVALID_URL");

        // THEN
        verify(simplyRepository, never()).findByOriginUrl(anyString());
        verify(simplyRepository, never()).existsByShortCode(anyString());
        verify(simplyRepository, never()).save(any(Link.class));
    }
}