package com.aurus.server.llm;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LLMRecommendationRepository extends JpaRepository<LLMRecommendationModel, Long> {
    Optional<LLMRecommendationModel> findFirstByOrderByCreatedAtDesc();
}
