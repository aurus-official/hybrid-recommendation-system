package com.aurus.server.batch.process.weather;

import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class ProcessedWeatherDataWriter implements ItemWriter<ProcessedWeatherDataModel>, StepExecutionListener {

    private final ProcessedWeatherDataRepository processedWeatherDataRepository;
    private StepExecution stepExecution;

    public ProcessedWeatherDataWriter(ProcessedWeatherDataRepository processedWeatherDataRepository) {
        this.processedWeatherDataRepository = processedWeatherDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(Chunk<? extends ProcessedWeatherDataModel> chunk) throws Exception {
        ProcessedWeatherDataModel returnedProcessedWeatherDataModel = processedWeatherDataRepository
                .save(chunk.getItems().get(0));
        long processedWeatherId = returnedProcessedWeatherDataModel.getId();
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        executionContext.put("processedWeatherId", processedWeatherId);
    }

}
