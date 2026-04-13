package com.aurus.server.batch.aggregate.sensor;

import java.time.LocalDateTime;
import java.util.List;

import com.aurus.server.batch.process.sensor.ProcessedSensorDataModel;
import com.aurus.server.batch.process.sensor.ProcessedSensorDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemReader;

public class AggregatedSensorDataReader implements ItemReader<List<ProcessedSensorDataModel>>, StepExecutionListener {

    private final ProcessedSensorDataRepository processedSensorDataRepository;
    private LocalDateTime startingWindow;
    private LocalDateTime endingWindow;

    public AggregatedSensorDataReader(ProcessedSensorDataRepository processedSensorDataRepository) {
        this.processedSensorDataRepository = processedSensorDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.startingWindow = stepExecution.getJobParameters().getLocalDateTime("startingWindow");
        this.endingWindow = stepExecution.getJobParameters().getLocalDateTime("endingWindow");
    }

    @Override
    public @Nullable List<ProcessedSensorDataModel> read() throws Exception {
        List<ProcessedSensorDataModel> processedSensorDataModels = processedSensorDataRepository
                .findAllProcessedSensorDataModelInAWindow(startingWindow, endingWindow);

        return processedSensorDataModels.isEmpty() ? null : processedSensorDataModels;
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getReadCount() == 0) {
            return ExitStatus.FAILED;
        }
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
