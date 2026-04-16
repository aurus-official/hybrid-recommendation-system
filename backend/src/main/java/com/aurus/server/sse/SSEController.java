package com.aurus.server.sse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SSEController {
    private final SSEBroadcaster sseBroadcaster;

    public SSEController(SSEBroadcaster sseBroadcaster) {
        this.sseBroadcaster = sseBroadcaster;
    }

    @GetMapping("/latest")
    public SseEmitter stream() {
        System.out.println("SUBSCRIBING");
        return sseBroadcaster.subscribe();
    }

    @GetMapping("/test")
    public String getMethodName() {
        return new String("testing");
    }

}
