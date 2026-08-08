package com.aurus.server.ingestion;

import com.aurus.server.batch.BatchEventPublisher;
import com.aurus.server.ingestion.hardware_status.HardwareStatusDTO;
import com.aurus.server.ingestion.hardware_status.HardwareStatusModel;
import com.aurus.server.ingestion.hardware_status.HardwareStatusRepository;
import com.aurus.server.ingestion.sensor.RawSensorDataDTO;
import com.aurus.server.ingestion.sensor.RawSensorDataModel;
import com.aurus.server.ingestion.sensor.RawSensorDataRepository;
import com.aurus.server.notification.NotificationEventPublisher;
import com.aurus.server.notification.hardware_status.NotificationHighPriorityHardwareStatusDTO;

import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.stereotype.Service;

@Service
public class IngestionService {

    private final RawSensorDataRepository rawSensorDataRepository;
    private final HardwareStatusRepository hardwareStatusRepository;
    private final BatchEventPublisher batchEventPublisher;
    private final NotificationEventPublisher notificationEventPublisher;
    private final float INVALID_VALUE = -1f;

    IngestionService(RawSensorDataRepository rawSensorDataRepository,
            HardwareStatusRepository hardwareStatusRepository, BatchEventPublisher batchEventPublisher,
            NotificationEventPublisher notificationEventPublisher) {
        this.rawSensorDataRepository = rawSensorDataRepository;
        this.hardwareStatusRepository = hardwareStatusRepository;
        this.batchEventPublisher = batchEventPublisher;
        this.notificationEventPublisher = notificationEventPublisher;
    }

    void ingestRawSensorDataToDatabase(RawSensorDataDTO rawSensorDataDTO) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        if (rawSensorDataDTO == null) {
            return;
        }

        RawSensorDataModel rawSensorDataModel = new RawSensorDataModel(rawSensorDataDTO.soilTemp(),
                rawSensorDataDTO.airTemp(), rawSensorDataDTO.humidity(), rawSensorDataDTO.pressure(),
                rawSensorDataDTO.lux(),
                rawSensorDataDTO.uvVolts(), rawSensorDataDTO.tdsVolts(), rawSensorDataDTO.prongMoisture(),
                rawSensorDataDTO.capacitiveMoisture());

        if (!validateSensorData(rawSensorDataModel.getSoilTemp(), "SOIL_TEMP")) {
            rawSensorDataModel.setSoilTemp(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getAirTemp(), "AIR_TEMP")) {
            rawSensorDataModel.setAirTemp(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getHumidity(), "HUMIDITY")) {
            rawSensorDataModel.setHumidity(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getPressure(), "PRESSURE")) {
            rawSensorDataModel.setPressure(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getLux(), "LUX")) {
            rawSensorDataModel.setLux(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getUvVolts(), "UV_VOLTS")) {
            rawSensorDataModel.setUvVolts(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getTdsVolts(), "TDS_VOLTS")) {
            rawSensorDataModel.setTdsVolts(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getProngMoisture(), "PRONG_MOISTURE")) {
            rawSensorDataModel.setProngMoisture(INVALID_VALUE);
        }

        if (!validateSensorData(rawSensorDataModel.getCapacitiveMoisture(), "CAPACITIVE_MOISTURE")) {
            rawSensorDataModel.setCapacitiveMoisture(INVALID_VALUE);
        }

        RawSensorDataModel savedRawSensorDataModel = rawSensorDataRepository.save(rawSensorDataModel);

        batchEventPublisher.publishProcessingSensorDataEvent(savedRawSensorDataModel.getId());
    }

    void ingestHardwareStatusToDatabase(HardwareStatusDTO hardwareStatusDTO) {
        if (hardwareStatusDTO == null) {
            return;
        }

        HardwareStatusModel hardwareStatusModel = new HardwareStatusModel(hardwareStatusDTO.ads1(),
                hardwareStatusDTO.ads2(), hardwareStatusDTO.bme280(), hardwareStatusDTO.guvas12sd(),
                hardwareStatusDTO.ds18b20());

        HardwareStatusModel returnedHardwareStatusModel = hardwareStatusRepository.save(hardwareStatusModel);

        if (!hardwareStatusDTO.ads1() ||
                !hardwareStatusDTO.ads2() ||
                !hardwareStatusDTO.bme280() ||
                !hardwareStatusDTO.guvas12sd() ||
                !hardwareStatusDTO.ds18b20()) {

            NotificationHighPriorityHardwareStatusDTO notificationHighPriorityHardwareStatusDTO = new NotificationHighPriorityHardwareStatusDTO(
                    returnedHardwareStatusModel.getCreatedAt(), returnedHardwareStatusModel.getId());
            notificationEventPublisher
                    .publishNotificationHighPriorityHardwareStatusEvent(notificationHighPriorityHardwareStatusDTO);
            return;
        }

    }

    private boolean validateSensorData(float value, String sensorName) {
        float AIR_TEMP_MIN = 0f;
        float AIR_TEMP_MAX = 60f;

        float SOIL_TEMP_MIN = 0f;
        float SOIL_TEMP_MAX = 50f;

        float HUMIDITY_MIN = 0f;
        float HUMIDITY_MAX = 100f;

        float PRESSURE_MIN = 850f;
        float PRESSURE_MAX = 1100f;

        float LUX_MIN = -0.0001f;
        float LUX_MAX = 120000f;

        float UV_VOLTS_MIN = -0.0001f;
        float UV_VOLTS_MAX = 3.3f;

        float TDS_VOLTS_MIN = -0.0001f;
        float TDS_VOLTS_MAX = 3.3f;

        float PRONG_MOISTURE_MIN = -0.0001f;
        float PRONG_MOISTURE_MAX = 5f;

        float CAPACITIVE_MOISTURE_MIN = -0.0001f;
        float CAPACITIVE_MOISTURE_MAX = 5f;

        switch (sensorName) {
            case "AIR_TEMP":
                return value < AIR_TEMP_MAX && value > AIR_TEMP_MIN;

            case "SOIL_TEMP":
                return value < SOIL_TEMP_MAX && value > SOIL_TEMP_MIN;

            case "HUMIDITY":
                return value < HUMIDITY_MAX && value > HUMIDITY_MIN;

            case "PRESSURE":
                return value < PRESSURE_MAX && value > PRESSURE_MIN;

            case "LUX":
                return value < LUX_MAX && value > LUX_MIN;

            case "UV_VOLTS":
                return value < UV_VOLTS_MAX && value > UV_VOLTS_MIN;

            case "TDS_VOLTS":
                return value < TDS_VOLTS_MAX && value > TDS_VOLTS_MIN;

            case "PRONG_MOISTURE":
                return value < PRONG_MOISTURE_MAX && value > PRONG_MOISTURE_MIN;

            case "CAPACITIVE_MOISTURE":
                return value < CAPACITIVE_MOISTURE_MAX && value > CAPACITIVE_MOISTURE_MIN;
            default:
                return false;
        }
    }
}
