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

        // BatchStatus batchStatus = s
        // if (batchStatus == BatchStatus.COMPLETED) {
        // batchEventPublisher.publishDerivingSensorDataEvent(id);
        // }

        // AggregatedWeatherDataModel returnedAggregatedWeatherDataModel =
        // aggregatedWeatherDataRepository
        // .save(chunk.getItems().get(0));
        // this.id = returnedAggregatedWeatherDataModel.getId();
        derivedWeatherDataRepository.saveAll(chunk.getItems());
    }

}
