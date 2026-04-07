package com.aurus.server.ingestion;

import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class IngestionController {

    private final IngestionService ingestionService;

    IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping(path = "/ingest")
    void ingestRawData(@RequestBody RawDataDTO rawDataDTO) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        ingestionService.ingestRawSensorDataToDatabase(rawDataDTO.rawSensorData());
        ingestionService.ingestRawHealthCheckDataToDatabase(rawDataDTO.rawHealthCheckData());
    }
}
