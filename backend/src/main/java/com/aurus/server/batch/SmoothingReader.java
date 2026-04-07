package com.aurus.server.batch;

import java.util.Optional;

import com.aurus.server.ingestion.RawSensorDataModel;
import com.aurus.server.ingestion.RawSensorDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemReader;

public class SmoothingReader implements ItemReader<RawSensorDataModel>, StepExecutionListener {

    private final RawSensorDataRepository rawSensorDataRepository;
    private Long id;
    private Long lastSeenId;

    public SmoothingReader(RawSensorDataRepository rawSensorDataRepository) {
        this.rawSensorDataRepository = rawSensorDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        id = stepExecution.getJobParameters().getLong("id");
    }

    @Override
    public @Nullable RawSensorDataModel read() throws Exception {
        Optional<RawSensorDataModel> rawSensorDataModel = rawSensorDataRepository.findById(id);
        if (id.equals(lastSeenId))
            return null;
        lastSeenId = rawSensorDataModel.get().getId();
        return rawSensorDataModel.orElse(null);
    }

}
