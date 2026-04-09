package com.aurus.server.batch.derive;

import com.aurus.server.batch.aggregate.AggregatedSensorDataModel;
import com.aurus.server.batch.aggregate.AggregatedSensorDataRepository;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class DerivedSensorDataWriter implements ItemWriter<AggregatedSensorDataModel> {

    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;

    public DerivedSensorDataWriter(AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
    }

    @Override
    public void write(Chunk<? extends AggregatedSensorDataModel> chunk) throws Exception {
        aggregatedSensorDataRepository.saveAll(chunk.getItems());

    }

}
