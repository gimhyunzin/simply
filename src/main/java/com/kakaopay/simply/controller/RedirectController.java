package com.kakaopay.simply.controller;

import com.kakaopay.simply.service.SimplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by hjk
 */
@Slf4j
@Controller
public class RedirectController {

    @Autowired
    private SimplyService simplyService;

    /**
     * Redirect By short-code
     *
     * @param shortCode
     * @return
     */
    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode) {

        String originUrl = simplyService.getLinkByShortCode(shortCode).getOriginUrl();
        log.info("redirect link : [{}] TO [{}]", shortCode, originUrl);

        return "redirect:" + originUrl;
    }
}
