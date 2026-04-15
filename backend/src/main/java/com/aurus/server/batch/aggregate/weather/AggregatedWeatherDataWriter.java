package com.aurus.server.batch.aggregate.weather;

import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class AggregatedWeatherDataWriter implements ItemWriter<AggregatedWeatherDataModel>, StepExecutionListener {

    private final AggregatedWeatherDataRepository aggregatedWeatherDataRepository;
    private StepExecution stepExecution;

    public AggregatedWeatherDataWriter(AggregatedWeatherDataRepository aggregatedWeatherDataRepository) {
        this.aggregatedWeatherDataRepository = aggregatedWeatherDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(Chunk<? extends AggregatedWeatherDataModel> chunk) throws Exception {
        AggregatedWeatherDataModel returnedAggregatedWeatherDataModel = aggregatedWeatherDataRepository
                .save(chunk.getItems().get(0));
        long aggregatedWeatherId = returnedAggregatedWeatherDataModel.getId();
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        executionContext.put("aggregatedWeatherId", aggregatedWeatherId);
    }

}
