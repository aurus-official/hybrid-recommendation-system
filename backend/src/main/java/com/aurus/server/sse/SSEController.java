package com.aurus.server.sse;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SSEController {
    private static final Logger logger = LoggerFactory.getLogger(SseEmitter.class);
    private final SSEBroadcaster sseBroadcaster;

    public SSEController(SSEBroadcaster sseBroadcaster) {
        this.sseBroadcaster = sseBroadcaster;
    }

    @GetMapping("/latest")
    public SseEmitter stream(HttpServletRequest httpServletRequest) {
        logger.info(String.format("\"%s\" was subscribed to SSE.", httpServletRequest.getLocalAddr()));
        return sseBroadcaster.subscribe();
    }

}
