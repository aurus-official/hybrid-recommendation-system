package com.aurus.server.batch.process.weather;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class ProcessedWeatherDataWriter implements ItemWriter<ProcessedWeatherDataModel> {

    private final ProcessedWeatherDataRepository processedWeatherDataRepository;

    public ProcessedWeatherDataWriter(ProcessedWeatherDataRepository processedWeatherDataRepository) {
        this.processedWeatherDataRepository = processedWeatherDataRepository;
    }

    @Override
    public void write(Chunk<? extends ProcessedWeatherDataModel> chunk) throws Exception {
        processedWeatherDataRepository.saveAll(chunk.getItems());
    }

}
