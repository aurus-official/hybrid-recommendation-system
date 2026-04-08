package com.aurus.server.batch;

import com.aurus.server.ingestion.RawSensorDataModel;
import com.aurus.server.ingestion.RawSensorDataRepository;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJdbcJobRepository(tablePrefix = "BATCH_", maxVarCharLength = 1000)
public class BatchConfig {

    @Bean
    Job smoothingJob(JobRepository jobRepository, Step smoothingStep) {
        return new JobBuilder("smoothing", jobRepository)
                .start(smoothingStep)
                .build();
    }

    @Bean
    Step smoothingStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, ItemReader<RawSensorDataModel> smoothingReader,
            ItemWriter<ProcessedSensorDataModel> smoothingWriter,
            ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> smoothingProcessor) {
        return new StepBuilder(jobRepository)
                .<RawSensorDataModel, ProcessedSensorDataModel>chunk(1).transactionManager(transactionManager)
                .reader(smoothingReader)
                .processor(smoothingProcessor)
                .writer(smoothingWriter)
                .build();
    }

    @Bean
    ItemReader<RawSensorDataModel> smoothingReader(RawSensorDataRepository rawSensorDataRepository) {
        return new SmoothingReader(rawSensorDataRepository);
    }

    @Bean
    ItemWriter<ProcessedSensorDataModel> smoothingWriter(ProcessedSensorDataRepository processedSensorDataRepository) {
        return new SmoothingWriter(processedSensorDataRepository);
    }

    @Bean
    ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> smoothingProcessor(
            RawSensorDataRepository rawSensorDataRepository) {
        return new SmoothingProcessor(rawSensorDataRepository);
    }

    @Bean
    @Primary
    TaskExecutor batchTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setVirtualThreads(true);
        return executor;
    }

    @Bean
    JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }
}
