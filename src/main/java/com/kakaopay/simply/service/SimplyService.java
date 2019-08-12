package com.kakaopay.simply.service;

import com.kakaopay.simply.dto.Link;

import java.util.List;

/**
 * Created by hjk
 */
public interface SimplyService {

    /**
     * 링크 목록
     *
     * @return
     */
    List<Link> getAllLinks();

    /**
     * 링크 조회 By short-code
     *
     * @param shortCode
     * @return
     */
    Link getLinkByShortCode(String shortCode);

    /**
     * 링크 조회 By origin-url
     *
     * @param originUrl
     * @return
     */
    Link getLinkByOriginUrl(String originUrl);

    /**
     * 링크 변환
     *
     * @param originUrl
     * @return
     */
    Link convertLink(String originUrl) throws Exception;
}
