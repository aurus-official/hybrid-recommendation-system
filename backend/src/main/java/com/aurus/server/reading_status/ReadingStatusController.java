package com.aurus.server.reading_status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reading-status")
public class ReadingStatusController {

    private final ReadingStatusService readingStatusService;

    public ReadingStatusController(ReadingStatusService readingStatusService) {
        this.readingStatusService = readingStatusService;
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<ReadingStatusPageDTO> getRecommendationTimeIdPage(
            @PathVariable() int pageNumber) {
        ReadingStatusPageDTO readingStatusPageDTO = readingStatusService.getReadingStatusPageDTO(pageNumber);
        return ResponseEntity.ok(readingStatusPageDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReadingStatusDTO> getAllDataDTO(@PathVariable long id) {
        ReadingStatusDTO readingStatusDTO = readingStatusService.getReadingStatusDTO(id);

        return ResponseEntity.ok(readingStatusDTO);
    }
}
