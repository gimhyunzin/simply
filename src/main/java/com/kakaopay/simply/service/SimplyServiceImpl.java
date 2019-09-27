package com.kakaopay.simply.service;

import com.kakaopay.simply.dto.Ascii;
import com.kakaopay.simply.dto.Link;
import com.kakaopay.simply.repository.SimplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by hjk
 */
@Slf4j
@Service
public class SimplyServiceImpl implements SimplyService {

    @Autowired
    private SimplyRepository simplyRepository;

    /**
     * 링크 목록
     *
     * @return
     */
    @Override
    public List<Link> getAllLinks() {
        return Optional.ofNullable(simplyRepository.findAll())
                .orElse(Collections.emptyList());
    }

    /**
     * 링크 조회 By short-code
     *
     * @param shortCode
     * @return
     */
    @Override
    public Link getLinkByShortCode(String shortCode) {
        return Optional.ofNullable(simplyRepository.findByShortCode(shortCode))
                .orElse(Link.builder()
                        .originUrl("")
                        .shortCode(shortCode)
                        .build()
                );
    }

    /**
     * 링크 조회 By origin-url
     *
     * @param originUrl
     * @return
     */
    @Override
    public Link getLinkByOriginUrl(String originUrl) {
        return Optional.ofNullable(simplyRepository.findByOriginUrl(originUrl))
                .orElse(Link.builder()
                        .originUrl(originUrl)
                        .shortCode("")
                        .build()
                );
    }

    /**
     * 링크 변환
     *
     * @param originUrl
     * @return
     */
    @Override
    public Link convertLink(String originUrl) throws Exception {

        originUrl = this.cleanup(originUrl);

        Link link = this.getLinkByOriginUrl(originUrl);

        // 코드 없으면 생성
        if (StringUtils.isEmpty(link.getShortCode())) {
            link = Link.builder()
                    .originUrl(originUrl)
                    .shortCode(this.generateCode())
                    .build();

            simplyRepository.save(link);
        }

        log.info("convert link : {}", link.toString());

        return link;
    }

    private String cleanup(String url) throws Exception {

        // 1) URL 체크
        UrlValidator validator = new UrlValidator(new String[] {"http", "https"});
        if (! validator.isValid(url)) {
            log.error("invalid url : {}", url);
            throw new IllegalArgumentException("올바르지 않은 URL 입니다.");
        }

        // 2) 마지막 / 제거
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < Link.SHORT_CODE_SIZE; i++) {
            sb.append(this.createRandomChar());
        }

        // TODO: 성능 고려 필요
        // 이미 존재하면 다시 호출
        if (simplyRepository.existsByShortCode(sb.toString())) {
            log.info("already exist short-code : {}", sb.toString());
            return generateCode();
        }

        log.info("generate short-code [COMPLETE] : {}", sb.toString());
        return sb.toString();
    }

    // TODO: 알파벳 범위 합쳐보기
    private char createRandomChar() {
        Random random = new Random();
        Ascii ascii = Ascii.values()[random.nextInt(Ascii.values().length)];
        return (char) (random.nextInt(ascii.getRange()) + ascii.getMin());
    }
}
