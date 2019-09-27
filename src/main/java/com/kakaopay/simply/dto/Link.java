package com.kakaopay.simply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by hjk
 */
@Entity(name = "link")
@NoArgsConstructor
@Getter
public class Link {

    public static final int SHORT_CODE_SIZE = 8;

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String originUrl;

    @Column
    private String shortCode;

    @Builder
    public Link(String originUrl, String shortCode) {
        this.originUrl = originUrl;
        this.shortCode = shortCode;
    }

    // TODO: 현재 스킴+호스트+포트 로 변경 필요
    public String getSimplyUrl() {
        return "http://localhost:8080/" + this.shortCode;
    }
}
