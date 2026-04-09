package com.aurus.server.batch.process;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class ProcessedSensorDataWriter implements ItemWriter<ProcessedSensorDataModel> {

    private final ProcessedSensorDataRepository processedSensorDataRepository;

    public ProcessedSensorDataWriter(ProcessedSensorDataRepository processedSensorDataRepository) {
        this.processedSensorDataRepository = processedSensorDataRepository;
    }

    @Override
    public void write(Chunk<? extends ProcessedSensorDataModel> chunk) throws Exception {
        processedSensorDataRepository.saveAll(chunk.getItems());
    }

}
