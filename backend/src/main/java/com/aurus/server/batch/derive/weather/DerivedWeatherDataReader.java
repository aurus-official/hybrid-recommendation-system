package com.aurus.server.batch.derive.weather;

import java.util.Optional;

import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataModel;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemReader;

public class DerivedWeatherDataReader implements ItemReader<AggregatedWeatherDataModel>, StepExecutionListener {

    private final AggregatedWeatherDataRepository aggregatedWeatherDataRepository;
    private long id;
    private long lastSeenId;

    public DerivedWeatherDataReader(AggregatedWeatherDataRepository aggregatedWeatherDataRepository) {
        this.aggregatedWeatherDataRepository = aggregatedWeatherDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        id = stepExecution.getJobParameters().getLong("aggregatedWeatherId");
    }

    @Override
    public @Nullable AggregatedWeatherDataModel read() throws Exception {
        Optional<AggregatedWeatherDataModel> aggregatedWeatherDataModel = aggregatedWeatherDataRepository.findById(id);
        if (id == lastSeenId)
            return null;
        lastSeenId = aggregatedWeatherDataModel.get().getId();
        return aggregatedWeatherDataModel.orElse(null);
    }
}
