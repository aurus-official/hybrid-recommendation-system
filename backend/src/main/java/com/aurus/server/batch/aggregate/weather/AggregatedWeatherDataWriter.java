package com.aurus.server.batch.aggregate.weather;

import com.aurus.server.batch.BatchEventPublisher;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class AggregatedWeatherDataWriter implements ItemWriter<AggregatedWeatherDataModel>, StepExecutionListener {

    private final AggregatedWeatherDataRepository aggregatedWeatherDataRepository;
    private final BatchEventPublisher batchEventPublisher;
    private long id;

    public AggregatedWeatherDataWriter(AggregatedWeatherDataRepository aggregatedWeatherDataRepository,
            BatchEventPublisher batchEventPublisher) {
        this.aggregatedWeatherDataRepository = aggregatedWeatherDataRepository;
        this.batchEventPublisher = batchEventPublisher;
    }

    @Override
    public void write(Chunk<? extends AggregatedWeatherDataModel> chunk) throws Exception {
        AggregatedWeatherDataModel returnedAggregatedWeatherDataModel = aggregatedWeatherDataRepository
                .save(chunk.getItems().get(0));
        this.id = returnedAggregatedWeatherDataModel.getId();
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        BatchStatus batchStatus = stepExecution.getStatus();
        if (batchStatus == BatchStatus.COMPLETED) {
            batchEventPublisher.publishDerivingWeatherDataEvent(id);
        }
        return StepExecutionListener.super.afterStep(stepExecution);
    }

}
