package com.aurus.server.batch.derive.sensor;

import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class DerivedSensorDataWriter implements ItemWriter<DerivedSensorDataModel>, StepExecutionListener {

    private final DerivedSensorDataRepository derivedSensorDataRepository;
    private StepExecution stepExecution;

    public DerivedSensorDataWriter(DerivedSensorDataRepository derivedSensorDataRepository) {
        this.derivedSensorDataRepository = derivedSensorDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(Chunk<? extends DerivedSensorDataModel> chunk) throws Exception {
        DerivedSensorDataModel derivedSensorDataModel = derivedSensorDataRepository.save(chunk.getItems().get(0));
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        executionContext.put("derivedSensorModel", derivedSensorDataModel);
    }
}
