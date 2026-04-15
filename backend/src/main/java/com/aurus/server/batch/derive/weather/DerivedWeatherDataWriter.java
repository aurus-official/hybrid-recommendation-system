package com.aurus.server.batch.derive.weather;

import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class DerivedWeatherDataWriter implements ItemWriter<DerivedWeatherDataModel>, StepExecutionListener {

    private final DerivedWeatherDataRepository derivedWeatherDataRepository;
    private StepExecution stepExecution;

    public DerivedWeatherDataWriter(DerivedWeatherDataRepository derivedWeatherDataRepository) {
        this.derivedWeatherDataRepository = derivedWeatherDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(Chunk<? extends DerivedWeatherDataModel> chunk) throws Exception {
        DerivedWeatherDataModel derivedWeatherDataModel = derivedWeatherDataRepository.save(chunk.getItems().get(0));
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        executionContext.put("derivedWeatherModel", derivedWeatherDataModel);
    }
}
