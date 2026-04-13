package com.aurus.server.batch.aggregate.weather;

import java.util.Optional;

import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemReader;

public class AggregatedWeatherDataReader implements ItemReader<ProcessedWeatherDataModel>, StepExecutionListener {

    private final ProcessedWeatherDataRepository processedWeatherDataRepository;
    private long id;
    private long lastSeenId;

    public AggregatedWeatherDataReader(ProcessedWeatherDataRepository processedWeatherDataRepository) {
        this.processedWeatherDataRepository = processedWeatherDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        id = stepExecution.getJobParameters().getLong("processedWeatherId");
        System.out.println("before step process id : " + id);
    }

    @Override
    public @Nullable ProcessedWeatherDataModel read() throws Exception {
        Optional<ProcessedWeatherDataModel> processedWeatherDataModel = processedWeatherDataRepository.findById(id);
        if (id == lastSeenId)
            return null;
        lastSeenId = processedWeatherDataModel.get().getId();
        return processedWeatherDataModel.orElse(null);
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getReadCount() == 0) {
            return ExitStatus.FAILED;
        }
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
