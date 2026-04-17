package com.aurus.server.batch.process.sensor;

import java.util.Optional;

import com.aurus.server.ingestion.sensor.RawSensorDataModel;
import com.aurus.server.ingestion.sensor.RawSensorDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemReader;

public class ProcessedSensorDataReader implements ItemReader<RawSensorDataModel>, StepExecutionListener {

    private final RawSensorDataRepository rawSensorDataRepository;
    private long id;
    private long lastSeenId = -1l;

    public ProcessedSensorDataReader(RawSensorDataRepository rawSensorDataRepository) {
        this.rawSensorDataRepository = rawSensorDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        id = stepExecution.getJobParameters().getLong("rawSensorId");
    }

    @Override
    public @Nullable RawSensorDataModel read() throws Exception {
        Optional<RawSensorDataModel> rawSensorDataModel = rawSensorDataRepository.findById(id);
        if (id == lastSeenId)
            return null;

        lastSeenId = rawSensorDataModel.get().getId();
        return rawSensorDataModel.orElse(null);
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        this.lastSeenId = -1l;
        if (stepExecution.getReadCount() == 0) {
            return ExitStatus.FAILED;
        }
        return StepExecutionListener.super.afterStep(stepExecution);
    }

}
