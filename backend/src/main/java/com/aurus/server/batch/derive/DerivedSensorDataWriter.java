package com.aurus.server.batch.derive;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class DerivedSensorDataWriter implements ItemWriter<DerivedSensorDataModel> {

    private DerivedSensorDataRepository derivedSensorDataRepository;

    public DerivedSensorDataWriter(DerivedSensorDataRepository derivedSensorDataRepository) {
        this.derivedSensorDataRepository = derivedSensorDataRepository;
    }

    @Override
    public void write(Chunk<? extends DerivedSensorDataModel> chunk) throws Exception {
        derivedSensorDataRepository.saveAll(chunk.getItems());

    }

}
