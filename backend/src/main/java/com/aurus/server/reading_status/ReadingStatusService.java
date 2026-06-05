package com.aurus.server.reading_status;

import java.util.List;

import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReadingStatusService {

    private final ReadingStatusRepository readingStatusRepository;
    private final int PAGE_SIZE = 8;
    private final float INVALID_VALUE = -1;

    public ReadingStatusService(ReadingStatusRepository readingStatusRepository) {
        this.readingStatusRepository = readingStatusRepository;
    }

    public ReadingStatusModel addReadingStatusModel(AggregatedSensorDataModel aggregatedSensorDataModel) {

        ReadingStatusModel readingStatusModel = new ReadingStatusModel(
                aggregatedSensorDataModel.getSoilTemp().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getAirTemp().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getHumidity().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getPressure().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getLux().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getUv().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getTds().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getProngMoisture().value() != INVALID_VALUE,
                aggregatedSensorDataModel.getCapacitiveMoisture().value() != INVALID_VALUE);

        return readingStatusRepository.saveAndFlush(readingStatusModel);
    }

    public ReadingStatusPageDTO getReadingStatusPageDTO(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by(Sort.Order.desc("id")));
        Page<ReadingStatusModel> readingStatusModelsPage = readingStatusRepository.findAll(pageable);

        List<ReadingStatusSummaryDTO> readingStatusDTOs = readingStatusModelsPage.toList().stream().map(model -> {
            return new ReadingStatusSummaryDTO(model.getId(), model.getCreatedAt());
        }).toList();

        return new ReadingStatusPageDTO(readingStatusDTOs, readingStatusModelsPage.getTotalPages());
    }

    public ReadingStatusDTO getReadingStatusDTO(long id) {
        ReadingStatusModel readingStatusModel = readingStatusRepository
                .findById(id)
                .orElseGet(() -> new ReadingStatusModel());

        return new ReadingStatusDTO(
                id,
                readingStatusModel.isValidSoilTemp(),
                readingStatusModel.isValidAirTemp(),
                readingStatusModel.isValidHumidity(),
                readingStatusModel.isValidPressure(),
                readingStatusModel.isValidLux(),
                readingStatusModel.isValidUvVolts(),
                readingStatusModel.isValidTdsVolts(),
                readingStatusModel.isValidProngMoisture(),
                readingStatusModel.isValidCapacitiveMoisture(),
                readingStatusModel.getCreatedAt());
    }
}
