package com.aurus.server.batch;

import java.util.List;

import javax.sql.DataSource;

import com.aurus.server.batch.aggregate.AggregatedSensorDataModel;
import com.aurus.server.batch.aggregate.AggregatedSensorDataProcessor;
import com.aurus.server.batch.aggregate.AggregatedSensorDataReader;
import com.aurus.server.batch.aggregate.AggregatedSensorDataRepository;
import com.aurus.server.batch.aggregate.AggregatedSensorDataWriter;
import com.aurus.server.batch.process.ProcessedSensorDataModel;
import com.aurus.server.batch.process.ProcessedSensorDataProcessor;
import com.aurus.server.batch.process.ProcessedSensorDataReader;
import com.aurus.server.batch.process.ProcessedSensorDataRepository;
import com.aurus.server.batch.process.ProcessedSensorDataWriter;
import com.aurus.server.ingestion.RawSensorDataModel;
import com.aurus.server.ingestion.RawSensorDataRepository;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JdbcDefaultBatchConfiguration;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.boot.batch.jdbc.autoconfigure.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.batch.jdbc.autoconfigure.BatchJdbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableConfigurationProperties(BatchJdbcProperties.class)
public class BatchConfig extends JdbcDefaultBatchConfiguration {

    @Bean
    Job processingJob(JobRepository jobRepository, Step processingStep) {
        return new JobBuilder("processing", jobRepository)
                .start(processingStep)
                .build();
    }

    @Bean
    Step processingStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, ItemReader<RawSensorDataModel> processingReader,
            ItemWriter<ProcessedSensorDataModel> processingWriter,
            ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> processingProcessor) {
        return new StepBuilder(jobRepository)
                .<RawSensorDataModel, ProcessedSensorDataModel>chunk(1).transactionManager(transactionManager)
                .reader(processingReader)
                .processor(processingProcessor)
                .writer(processingWriter)
                .build();
    }

    @Bean
    ItemReader<RawSensorDataModel> processingReader(RawSensorDataRepository rawSensorDataRepository) {
        return new ProcessedSensorDataReader(rawSensorDataRepository);
    }

    @Bean
    ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> processingProcessor(
            RawSensorDataRepository rawSensorDataRepository) {
        return new ProcessedSensorDataProcessor(rawSensorDataRepository);
    }

    @Bean
    ItemWriter<ProcessedSensorDataModel> processingWriter(ProcessedSensorDataRepository processedSensorDataRepository) {
        return new ProcessedSensorDataWriter(processedSensorDataRepository);
    }

    @Bean
    Job aggregatingJob(JobRepository jobRepository, Step aggregatingStep) {
        return new JobBuilder("aggregating", jobRepository)
                .start(aggregatingStep)
                .build();
    }

    @Bean
    Step aggregatingStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, ItemReader<RawSensorDataModel> aggregatingReader,
            ItemWriter<ProcessedSensorDataModel> aggregatingWriter,
            ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> aggregatingProcessor) {
        return new StepBuilder(jobRepository)
                .<RawSensorDataModel, ProcessedSensorDataModel>chunk(1).transactionManager(transactionManager)
                .reader(aggregatingReader)
                .processor(aggregatingProcessor)
                .writer(aggregatingWriter)
                .build();
    }

    @Bean
    ItemReader<List<ProcessedSensorDataModel>> aggregatingReader(
            ProcessedSensorDataRepository processedSensorDataRepository) {
        return new AggregatedSensorDataReader(processedSensorDataRepository);
    }

    @Bean
    ItemProcessor<List<ProcessedSensorDataModel>, AggregatedSensorDataModel> aggregatingProcessor() {
        return new AggregatedSensorDataProcessor();
    }

    @Bean
    ItemWriter<AggregatedSensorDataModel> aggregatingWriter(
            AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        return new AggregatedSensorDataWriter(aggregatedSensorDataRepository);
    }

    @Override
    @Bean
    protected TaskExecutor getTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setVirtualThreads(true);
        return threadPoolTaskExecutor;
    }

    @Bean
    JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }

    @Bean
    BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(
            DataSource dataSource,
            BatchJdbcProperties properties) {
        properties.setTablePrefix("BATCH_");
        return new BatchDataSourceScriptDatabaseInitializer(dataSource, properties);
    }
}
