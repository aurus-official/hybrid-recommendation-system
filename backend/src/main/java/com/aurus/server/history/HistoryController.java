package com.aurus.server.history;

import java.util.List;

import com.aurus.server.llm.LLMRecommendationDTO;
import com.aurus.server.shared.AllDataDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<List<LLMRecommendationDTO>> getRecommendationTimeIdPage(
            @PathVariable() int pageNumber) {
        List<LLMRecommendationDTO> llmRecommendationDTOs = historyService.getRecommendationPage(pageNumber);
        System.out.println("PAGEE");
        return ResponseEntity.ok(llmRecommendationDTOs);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AllDataDTO> getAllDataDTO(@PathVariable long id) {
        System.out.println("IDDEEE");
        AllDataDTO allDataDTO = historyService.getAllDataDTO(id);

        return ResponseEntity.ok(allDataDTO);
    }
}
