package com.aurus.server.ingestion;

import org.springframework.stereotype.Service;

@Service
public class IngestionService {

    private final RawSensorDataRepository rawSensorDataRepository;

    IngestionService(RawSensorDataRepository rawSensorDataRepository) {
        this.rawSensorDataRepository = rawSensorDataRepository;
    }

    boolean ingestRawSensorDataToDatabase(RawSensorDataDTO rawSensorDataDTO) {
        if (rawSensorDataDTO == null) {
            return false;
        }

        RawSensorDataModel rawSensorDataModel = new RawSensorDataModel(rawSensorDataDTO.waterTemp(),
                rawSensorDataDTO.airTemp(), rawSensorDataDTO.humidity(), rawSensorDataDTO.pressure(),
                rawSensorDataDTO.lux(),
                rawSensorDataDTO.uvVolts(), rawSensorDataDTO.tdsVolts(), rawSensorDataDTO.prongMoisture(),
                rawSensorDataDTO.capacitiveMoisture());

        rawSensorDataRepository.save(rawSensorDataModel);
        return true;
    }
}
