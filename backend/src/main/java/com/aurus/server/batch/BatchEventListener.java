package com.aurus.server.batch;

import com.aurus.server.batch.aggregate.AggregatingEvent;
import com.aurus.server.batch.process.ProcessingEvent;

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
    public void listenProcessingEvent(ProcessingEvent processingEvent) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job processingJob = jobRegistry.getJob("processing");
        JobParameters jobParameters = new JobParametersBuilder().addLong("id", processingEvent.id()).toJobParameters();
        jobOperator.start(processingJob, jobParameters);
    }

    @EventListener
    @Async
    public void listenAggregatingEvent(AggregatingEvent aggregatingEvent) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job aggregatingJob = jobRegistry.getJob("aggregating");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("startingWindow", aggregatingEvent.startingWindow())
                .addLocalDateTime("endingWindow", aggregatingEvent.endingWindow()).toJobParameters();
        jobOperator.start(aggregatingJob, jobParameters);
        System.out.println("IS IT ASYNC OR NAH?");

    }
}
