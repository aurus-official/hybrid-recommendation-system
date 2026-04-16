package com.aurus.server.llm;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LLMRecommendationRepository extends CrudRepository<LLMRecommendationModel, Long> {

}
