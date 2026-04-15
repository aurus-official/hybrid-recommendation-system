package com.aurus.server.batch.aggregate.sensor;

import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class AggregatedSensorDataWriter implements ItemWriter<AggregatedSensorDataModel>, StepExecutionListener {

    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;
    private StepExecution stepExecution;

    public AggregatedSensorDataWriter(AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(Chunk<? extends AggregatedSensorDataModel> chunk) throws Exception {
        AggregatedSensorDataModel returnedAggregatedSensorDataModel = aggregatedSensorDataRepository
                .save(chunk.getItems().get(0));
        long aggregatedSensorId = returnedAggregatedSensorDataModel.getId();
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        executionContext.put("aggregatedSensorId", aggregatedSensorId);
    }
}
