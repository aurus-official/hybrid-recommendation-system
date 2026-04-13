package com.aurus.server.batch;

import com.aurus.server.batch.aggregate.sensor.AggregatingSensorDataEvent;
import com.aurus.server.batch.aggregate.weather.AggregatingWeatherDataEvent;
import com.aurus.server.batch.derive.sensor.DerivingSensorDataEvent;
import com.aurus.server.batch.derive.weather.DerivingWeatherDataEvent;
import com.aurus.server.batch.process.sensor.ProcessingSensorDataEvent;
import com.aurus.server.batch.process.weather.ProcessingWeatherDataEvent;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class BatchEventListener {

    private final JobOperator jobOperator;
    private final JobRegistry jobRegistry;

    public BatchEventListener(JobOperator jobOperator, JobRegistry jobRegistry) {
        this.jobOperator = jobOperator;
        this.jobRegistry = jobRegistry;
    }

    @EventListener
    @Async
    public void listenProcessingSensorDataEvent(ProcessingSensorDataEvent processingSensorDataEvent)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job processingSensorJob = jobRegistry.getJob("processingSensorData");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("rawSensorId", processingSensorDataEvent.rawSensorId(), false)
                .toJobParameters();
        jobOperator.start(processingSensorJob, jobParameters);
    }

    @EventListener
    @Async
    public void listenProcessingWeatherDataEvent(ProcessingWeatherDataEvent processingWeatherDataEvent)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job processingWeatherJob = jobRegistry.getJob("processingWeatherData");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("rawWeatherId", processingWeatherDataEvent.rawWeatherId(), false)
                .toJobParameters();
        jobOperator.start(processingWeatherJob, jobParameters);
    }

    @EventListener
    @Async
    public void listenAggregatingSensorDataEvent(AggregatingSensorDataEvent aggregatingSensorDataEvent)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job aggregatingSensorJob = jobRegistry.getJob("aggregatingSensorData");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("startingWindow", aggregatingSensorDataEvent.startingWindow(), false)
                .addLocalDateTime("endingWindow", aggregatingSensorDataEvent.endingWindow(), false).toJobParameters();
        jobOperator.start(aggregatingSensorJob, jobParameters);
    }

    @EventListener
    @Async
    public void listenAggregatingWeatherDataEvent(AggregatingWeatherDataEvent aggregatingWeatherDataEvent)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job aggregatingWeatherJob = jobRegistry.getJob("aggregatingWeatherData");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("processedWeatherId", aggregatingWeatherDataEvent.processedWeatherId(), false)
                .toJobParameters();
        jobOperator.start(aggregatingWeatherJob, jobParameters);
    }

    @EventListener
    @Async
    public void listenDerivingSensorDataEvent(DerivingSensorDataEvent derivingSensorDataEvent)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job derivingSensorJob = jobRegistry.getJob("derivingSensorData");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("aggregatedSensorId", derivingSensorDataEvent.aggregatedSensorId(), false)
                .toJobParameters();
        jobOperator.start(derivingSensorJob, jobParameters);
    }

    @EventListener
    @Async
    public void listenDerivingWeatherDataEvent(DerivingWeatherDataEvent derivingWeatherDataEvent)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job derivingWeatherJob = jobRegistry.getJob("derivingWeatherData");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("aggregatedWeatherId", derivingWeatherDataEvent.aggregatedWeatherId(), false)
                .toJobParameters();
        jobOperator.start(derivingWeatherJob, jobParameters);
    }
}
