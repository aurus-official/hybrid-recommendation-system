package com.aurus.server.batch.derive.weather;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

public class DerivedWeatherDataWriter implements ItemWriter<DerivedWeatherDataModel> {

    private DerivedWeatherDataRepository derivedWeatherDataRepository;

    public DerivedWeatherDataWriter(DerivedWeatherDataRepository derivedWeatherDataRepository) {
        this.derivedWeatherDataRepository = derivedWeatherDataRepository;
    }

    @Override
    public void write(Chunk<? extends DerivedWeatherDataModel> chunk) throws Exception {
        derivedWeatherDataRepository.saveAll(chunk.getItems());

    }

}
