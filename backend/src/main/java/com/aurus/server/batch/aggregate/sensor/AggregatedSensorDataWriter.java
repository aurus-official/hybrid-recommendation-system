package com.aurus.server.batch.aggregate.sensor;

import com.aurus.server.notification.reading_status.NotificationHighPriorityReadingStatusDTO;
import com.aurus.server.reading_status.ReadingStatusModel;
import com.aurus.server.reading_status.ReadingStatusService;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class AggregatedSensorDataWriter implements ItemWriter<AggregatedSensorDataModel>, StepExecutionListener {

    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;
    private final ReadingStatusService readingStatusService;
    private StepExecution stepExecution;

    public AggregatedSensorDataWriter(AggregatedSensorDataRepository aggregatedSensorDataRepository,
            ReadingStatusService readingStatusService) {
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
        this.readingStatusService = readingStatusService;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(Chunk<? extends AggregatedSensorDataModel> chunk) throws Exception {
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        Object value = executionContext.get("isThereInvalidValue");
        Boolean isThereInvalidValue = (value != null) ? (Boolean) value : false;

        if (isThereInvalidValue) {
            ReadingStatusModel readingStatusModel = readingStatusService
                    .addReadingStatusModel(chunk.getItems().get(0));
            NotificationHighPriorityReadingStatusDTO notificationHighPriorityReadingStatusDTO = new NotificationHighPriorityReadingStatusDTO(
                    readingStatusModel.getCreatedAt(),
                    readingStatusModel.getId());
            executionContext.put("reading-status-dto", notificationHighPriorityReadingStatusDTO);
            return;
        }

        AggregatedSensorDataModel returnedAggregatedSensorDataModel = aggregatedSensorDataRepository
                .save(chunk.getItems().get(0));
        long aggregatedSensorId = returnedAggregatedSensorDataModel.getId();
        executionContext.put("aggregatedSensorId", aggregatedSensorId);
    }

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        Object value = this.stepExecution.getJobExecution().getExecutionContext().get("isThereInvalidValue");
        Boolean isThereInvalidValue = (value != null) ? (Boolean) value : false;

        if (isThereInvalidValue) {
            stepExecution.setStatus(BatchStatus.FAILED);
            return ExitStatus.FAILED;
        }

        return ExitStatus.COMPLETED;
    }

}
