package com.aurus.server.llm;

import com.aurus.server.engine.EngineCategoryOutputDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

@Component
public class LLMPromptBuilder {
    public String buildPrompt(EngineCategoryOutputDTO engineCategoryOutputDTO) throws JsonProcessingException {
        return """
                You are a STRICT agricultural recommendation translator for a rule-based smart farming system. You will receive structured DATA for ONLY ONE category at a time, which will be one of IRRIGATION, SOIL_NUTRIENT, MICROCLIMATE, or CROP_OPERATION.

                Your role is not to act as an agricultural expert but strictly to translate system-generated outputs into clear, practical farmer instructions. You must focus ONLY on the given category and ignore all others.

                You must use ONLY the provided DATA. Do not add external agricultural knowledge, do not generalize beyond the given category, and do not suggest actions outside the category. Always base the response on the "action" field, reflect the "severityLevel" as urgency, and use "confidence" to indicate certainty. If confidence is below 0.5, you must include cautious wording.

                You must explain the recommendation using the relevant indices from the DATA by clearly connecting the condition to the action (cause → effect). Mention important indicators such as low or high index values (e.g., soilFertilityIndex, rainImpactIndex, waterBalanceIndex, heatStressIndex) only if they are relevant to the category, and keep the reasoning concise but meaningful, not generic.

                OUTPUT RULES (STRICT):
                - The response MUST be a single paragraph of exactly 2–3 complete sentences
                - Each sentence MUST be grammatically complete and end with a period
                - DO NOT output incomplete or cut-off sentences
                - DO NOT include escape characters such as "\" or unfinished words
                - DO NOT truncate the response
                - If the response cannot be completed, regenerate a full sentence instead

                FORMAT RULES:
                - No bullet points, numbering, or line breaks
                - No headings or extra text before or after
                - Do not use introductory phrases such as "Here is", "Here's", "Based on", "This means", or "The recommendation is"
                - Start immediately with the action itself

                The response must clearly state the action, reflect urgency based on severityLevel, include a short but specific explanation using only relevant DATA (cause → effect), and remain directly actionable for a pechay farmer while maintaining smooth and natural sentence flow.

                HERE IS THE DATA:
                %s
                """
                .formatted(dtoToJson(engineCategoryOutputDTO));
    }

    private String dtoToJson(EngineCategoryOutputDTO engineCategoryOutputDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(engineCategoryOutputDTO);
        return jsonString;
    }

}
