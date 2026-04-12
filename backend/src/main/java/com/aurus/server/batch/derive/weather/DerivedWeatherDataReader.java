package com.aurus.server.batch.derive.weather;

import java.util.Optional;

import com.aurus.server.batch.aggregate.AggregatedSensorDataModel;
import com.aurus.server.batch.aggregate.AggregatedSensorDataRepository;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemReader;

public class DerivedWeatherDataReader implements ItemReader<ProcessedWeatherDataModel>, StepExecutionListener {

    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;
    private long id;
    private long lastSeenId;

    public DerivedWeatherDataReader(AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        id = stepExecution.getJobParameters().getLong("aggregatedSensorId");
    }

    @Override
    public @Nullable ProcessedWeatherDataModel read() throws Exception {
        Optional<AggregatedSensorDataModel> aggregatedSensorDataModel = aggregatedSensorDataRepository.findById(id);
        if (id == lastSeenId)
            return null;
        lastSeenId = aggregatedSensorDataModel.get().getId();
        return aggregatedSensorDataModel.orElse(null);
    }
}
