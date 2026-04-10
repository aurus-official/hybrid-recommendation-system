package com.aurus.server.batch.aggregate;

import com.aurus.server.batch.BatchEventPublisher;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class AggregatedSensorDataWriter implements ItemWriter<AggregatedSensorDataModel>, StepExecutionListener {

    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;
    private final BatchEventPublisher batchEventPublisher;
    private long id;

    public AggregatedSensorDataWriter(AggregatedSensorDataRepository aggregatedSensorDataRepository,
            BatchEventPublisher batchEventPublisher) {
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
        this.batchEventPublisher = batchEventPublisher;
    }

    @Override
    public void write(Chunk<? extends AggregatedSensorDataModel> chunk) throws Exception {
        this.id = chunk.getItems().get(0).getId();
        aggregatedSensorDataRepository.saveAll(chunk.getItems());
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {

        batchEventPublisher.publishDerivingEvent(id);
        return StepExecutionListener.super.afterStep(stepExecution);
    }

}
