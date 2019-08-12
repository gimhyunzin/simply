package com.kakaopay.simply.controller;

import com.kakaopay.simply.dto.Link;
import com.kakaopay.simply.dto.LinkParams;
import com.kakaopay.simply.service.SimplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hjk
 */
@Slf4j
@RestController
public class SimplyController {

    @Autowired
    private SimplyService simplyService;

    /**
     * 링크 목록
     *
     * @return
     */
    @GetMapping("/simply/links")
    public ResponseEntity<List<Link>> simplyLinks() throws Exception {
        return new ResponseEntity<>(
                simplyService.getAllLinks(), HttpStatus.OK);
    }

    /**
     * 링크 변환
     *
     * @param params
     * @return
     */
    @PostMapping("/simply/convert")
    public ResponseEntity<Link> simplyConvert(@RequestBody LinkParams params) throws Exception {
        return new ResponseEntity<>(
                simplyService.convertLink(params.getUrl()), HttpStatus.OK);
    }
}
