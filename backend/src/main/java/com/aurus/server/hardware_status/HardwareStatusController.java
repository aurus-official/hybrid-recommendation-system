package com.aurus.server.hardware_status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hardware-status")
public class HardwareStatusController {

    private final HardwareStatusService hardwareStatusService;

    public HardwareStatusController(HardwareStatusService hardwareStatusService) {
        this.hardwareStatusService = hardwareStatusService;
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<HardwareStatusPageDTO> getRecommendationTimeIdPage(
            @PathVariable() int pageNumber) {
        HardwareStatusPageDTO hardwareStatusPageDTO = hardwareStatusService.getHardwareStatusPageDTO(pageNumber);
        return ResponseEntity.ok(hardwareStatusPageDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<HardwareStatusOutputDTO> getAllDataDTO(@PathVariable long id) {
        HardwareStatusOutputDTO hardwareStatusOutputDTO = hardwareStatusService.getHardwareStatusOutputDTO(id);

        return ResponseEntity.ok(hardwareStatusOutputDTO);
    }
}
