package com.aurus.server.batch.process.weather;

import java.util.Optional;

import com.aurus.server.ingestion.weather.RawWeatherDataModel;
import com.aurus.server.ingestion.weather.RawWeatherDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemReader;

public class ProcessedWeatherDataReader implements ItemReader<RawWeatherDataModel>, StepExecutionListener {

    private RawWeatherDataRepository rawWeatherDataRepository;
    private long id;
    private long lastSeenId;

    public ProcessedWeatherDataReader(RawWeatherDataRepository rawWeatherDataRepository) {
        this.rawWeatherDataRepository = rawWeatherDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        id = stepExecution.getJobParameters().getLong("rawWeatherId");
    }

    @Override
    public @Nullable RawWeatherDataModel read() throws Exception {
        Optional<RawWeatherDataModel> rawWeatherDataModel = rawWeatherDataRepository.findById(id);
        if (id == lastSeenId)
            return null;
        lastSeenId = rawWeatherDataModel.get().getId();
        return rawWeatherDataModel.orElse(null);
    }

}
