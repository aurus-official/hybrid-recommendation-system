package com.aurus.server.batch.derive.weather;

import jakarta.annotation.Nullable;

import com.aurus.server.engine.EngineEventPublisher;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class DerivedWeatherDataWriter implements ItemWriter<DerivedWeatherDataModel>, StepExecutionListener {

    private final DerivedWeatherDataRepository derivedWeatherDataRepository;
    private final EngineEventPublisher engineEventPublisher;
    private DerivedWeatherDataModel derivedWeatherDataModel;

    public DerivedWeatherDataWriter(DerivedWeatherDataRepository derivedWeatherDataRepository,
            EngineEventPublisher engineEventPublisher) {
        this.derivedWeatherDataRepository = derivedWeatherDataRepository;
        this.engineEventPublisher = engineEventPublisher;
    }

    @Override
    public void write(Chunk<? extends DerivedWeatherDataModel> chunk) throws Exception {
        this.derivedWeatherDataModel = derivedWeatherDataRepository.save(chunk.getItems().get(0));
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        BatchStatus batchStatus = stepExecution.getStatus();
        if (batchStatus == BatchStatus.COMPLETED) {
            engineEventPublisher.publishDerivedWeatherDataReadyEvent(this.derivedWeatherDataModel);
        }
        return StepExecutionListener.super.afterStep(stepExecution);
    }

}
