package com.aurus.server.batch.aggregate;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class AggregatedSensorDataWriter implements ItemWriter<AggregatedSensorDataModel> {

    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;

    public AggregatedSensorDataWriter(AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
    }

    @Override
    public void write(Chunk<? extends AggregatedSensorDataModel> chunk) throws Exception {
        aggregatedSensorDataRepository.saveAll(chunk.getItems());

    }

}
