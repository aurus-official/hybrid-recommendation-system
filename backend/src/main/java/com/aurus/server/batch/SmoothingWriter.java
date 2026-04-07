package com.aurus.server.batch;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class SmoothingWriter implements ItemWriter<ProcessedSensorDataModel> {

    private final ProcessedSensorDataRepository processedSensorDataRepository;

    public SmoothingWriter(ProcessedSensorDataRepository processedSensorDataRepository) {
        this.processedSensorDataRepository = processedSensorDataRepository;
    }

    @Override
    public void write(Chunk<? extends ProcessedSensorDataModel> chunk) throws Exception {
        processedSensorDataRepository.saveAll(chunk.getItems());
        // System.out.println("THIS WILL WORK I THINK!");
    }

}
