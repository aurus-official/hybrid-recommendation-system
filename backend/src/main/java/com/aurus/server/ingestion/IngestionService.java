package com.aurus.server.ingestion;

import com.aurus.server.batch.BatchEventPublisher;

import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.stereotype.Service;

@Service
public class IngestionService {

    private final RawSensorDataRepository rawSensorDataRepository;
    private final RawHealthCheckDataRepository rawHealthCheckDataRepository;
    private final BatchEventPublisher batchEventPublisher;

    IngestionService(RawSensorDataRepository rawSensorDataRepository,
            RawHealthCheckDataRepository rawHealthCheckDataRepository, BatchEventPublisher batchEventPublisher) {
        this.rawSensorDataRepository = rawSensorDataRepository;
        this.rawHealthCheckDataRepository = rawHealthCheckDataRepository;
        this.batchEventPublisher = batchEventPublisher;
    }

    boolean ingestRawSensorDataToDatabase(RawSensorDataDTO rawSensorDataDTO) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        if (rawSensorDataDTO == null) {
            return false;
        }

        RawSensorDataModel rawSensorDataModel = new RawSensorDataModel(rawSensorDataDTO.soilTemp(),
                rawSensorDataDTO.airTemp(), rawSensorDataDTO.humidity(), rawSensorDataDTO.pressure(),
                rawSensorDataDTO.lux(),
                rawSensorDataDTO.uvVolts(), rawSensorDataDTO.tdsVolts(), rawSensorDataDTO.prongMoisture(),
                rawSensorDataDTO.capacitiveMoisture());

        RawSensorDataModel savedRawSensorDataModel = rawSensorDataRepository.save(rawSensorDataModel);

        batchEventPublisher.publishSmoothingEvent(savedRawSensorDataModel.getId());
        return true;
    }

    boolean ingestRawHealthCheckDataToDatabase(RawHealthCheckDataDTO rawHealthCheckDataDTO) {
        if (rawHealthCheckDataDTO == null) {
            return false;
        }

        RawHealthCheckDataModel rawHealthCheckDataModel = new RawHealthCheckDataModel(rawHealthCheckDataDTO.ads1(),
                rawHealthCheckDataDTO.ads2(), rawHealthCheckDataDTO.bme280(), rawHealthCheckDataDTO.guvas12sd(),
                rawHealthCheckDataDTO.ds18b20());

        rawHealthCheckDataRepository.save(rawHealthCheckDataModel);

        return true;
    }
}
