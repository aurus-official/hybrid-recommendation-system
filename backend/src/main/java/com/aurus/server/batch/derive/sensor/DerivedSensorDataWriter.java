package com.aurus.server.batch.derive.sensor;

import jakarta.annotation.Nullable;

import com.aurus.server.engine.EngineEventPublisher;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class DerivedSensorDataWriter implements ItemWriter<DerivedSensorDataModel>, StepExecutionListener {

    private final DerivedSensorDataRepository derivedSensorDataRepository;
    private final EngineEventPublisher engineEventPublisher;
    private DerivedSensorDataModel derivedSensorDataModel;

    public DerivedSensorDataWriter(DerivedSensorDataRepository derivedSensorDataRepository,
            EngineEventPublisher engineEventPublisher) {
        this.derivedSensorDataRepository = derivedSensorDataRepository;
        this.engineEventPublisher = engineEventPublisher;
    }

    @Override
    public void write(Chunk<? extends DerivedSensorDataModel> chunk) throws Exception {
        this.derivedSensorDataModel = derivedSensorDataRepository.save(chunk.getItems().get(0));
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        BatchStatus batchStatus = stepExecution.getStatus();
        if (batchStatus == BatchStatus.COMPLETED) {
            engineEventPublisher.publishDerivedSensorDataReadyEvent(this.derivedSensorDataModel);
        }
        return StepExecutionListener.super.afterStep(stepExecution);
    }

}
