package com.aurus.server.ingestion;

import jakarta.transaction.Transactional;

import com.aurus.server.batch.BatchEventListener;
import com.aurus.server.batch.SmoothingEvent;

import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.stereotype.Service;

@Service
public class IngestionService {

    private final RawSensorDataRepository rawSensorDataRepository;
    private final RawHealthCheckDataRepository rawHealthCheckDataRepository;
    private final BatchEventListener batchEventListener;

    IngestionService(RawSensorDataRepository rawSensorDataRepository,
            RawHealthCheckDataRepository rawHealthCheckDataRepository, BatchEventListener batchEventListener) {
        this.rawSensorDataRepository = rawSensorDataRepository;
        this.rawHealthCheckDataRepository = rawHealthCheckDataRepository;
        this.batchEventListener = batchEventListener;
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

        batchEventListener.listenSmoothingEvent(new SmoothingEvent(savedRawSensorDataModel.getId()));
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
