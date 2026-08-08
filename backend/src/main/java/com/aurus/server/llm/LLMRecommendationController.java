package com.aurus.server.llm;

import com.aurus.server.shared.AllLLMRelatedDataDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendations")
public class LLMRecommendationController {

    private final LLMRecommendationService llmRecommendationService;

    public LLMRecommendationController(LLMRecommendationService llmRecommendationService) {
        this.llmRecommendationService = llmRecommendationService;
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<LLMPageRecommendationDTO> getRecommendationTimeIdPage(
            @PathVariable() int pageNumber) {
        LLMPageRecommendationDTO llmPageRecommendationDTO = llmRecommendationService.getRecommendationPage(pageNumber);
        return ResponseEntity.ok(llmPageRecommendationDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AllLLMRelatedDataDTO> getAllDataDTO(@PathVariable long id) {
        AllLLMRelatedDataDTO allLLMRelatedDataDTO = llmRecommendationService.getAllLLMRelatedDataDTO(id);

        return ResponseEntity.ok(allLLMRelatedDataDTO);
    }
}
