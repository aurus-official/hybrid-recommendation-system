package com.aurus.server.llm;

import com.aurus.server.shared.AllDataDTO;

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
        System.out.println("PAGEE");
        return ResponseEntity.ok(llmPageRecommendationDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AllDataDTO> getAllDataDTO(@PathVariable long id) {
        System.out.println("IDDEEE");
        AllDataDTO allDataDTO = llmRecommendationService.getAllDataDTO(id);

        return ResponseEntity.ok(allDataDTO);
    }
}
