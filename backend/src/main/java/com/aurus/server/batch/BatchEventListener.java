package com.aurus.server.batch;

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
import org.springframework.stereotype.Component;

@Component
public class BatchEventListener {

    private final JobOperator jobOperator;
    private final JobRegistry jobRegistry;

    public BatchEventListener(JobOperator jobOperator, JobRegistry jobRegistry) {
        this.jobOperator = jobOperator;
        this.jobRegistry = jobRegistry;
    }

    @EventListener
    @Async
    public void listenSmoothingEvent(SmoothingEvent smoothingEvent) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, InvalidJobParametersException, JobRestartException {
        Job smoothingJob = jobRegistry.getJob("smoothing");

        JobParameters jobParameters = new JobParametersBuilder().addLong("id", smoothingEvent.id()).toJobParameters();

        jobOperator.start(smoothingJob, jobParameters);
        System.out.println("WORKING SHEESH");
    }
}
