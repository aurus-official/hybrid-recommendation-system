package com.aurus.server.batch;

import java.util.List;

import javax.sql.DataSource;

import com.aurus.server.batch.aggregate.AggregatedSensorDataModel;
import com.aurus.server.batch.aggregate.AggregatedSensorDataProcessor;
import com.aurus.server.batch.aggregate.AggregatedSensorDataReader;
import com.aurus.server.batch.aggregate.AggregatedSensorDataRepository;
import com.aurus.server.batch.aggregate.AggregatedSensorDataWriter;
import com.aurus.server.batch.derive.DerivedSensorDataModel;
import com.aurus.server.batch.derive.DerivedSensorDataProcessor;
import com.aurus.server.batch.derive.DerivedSensorDataReader;
import com.aurus.server.batch.derive.DerivedSensorDataRepository;
import com.aurus.server.batch.derive.DerivedSensorDataWriter;
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
            PlatformTransactionManager transactionManager, ItemReader<List<ProcessedSensorDataModel>> aggregatingReader,
            ItemWriter<AggregatedSensorDataModel> aggregatingWriter,
            ItemProcessor<List<ProcessedSensorDataModel>, AggregatedSensorDataModel> aggregatingProcessor) {
        return new StepBuilder(jobRepository)
                .<List<ProcessedSensorDataModel>, AggregatedSensorDataModel>chunk(1)
                .transactionManager(transactionManager)
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
            AggregatedSensorDataRepository aggregatedSensorDataRepository, BatchEventPublisher batchEventPublisher) {
        return new AggregatedSensorDataWriter(aggregatedSensorDataRepository, batchEventPublisher);
    }

    @Bean
    Job derivingJob(JobRepository jobRepository, Step derivingStep) {
        return new JobBuilder("deriving", jobRepository)
                .start(derivingStep)
                .build();
    }

    @Bean
    Step derivingStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, ItemReader<AggregatedSensorDataModel> derivingReader,
            ItemWriter<DerivedSensorDataModel> derivingWriter,
            ItemProcessor<AggregatedSensorDataModel, DerivedSensorDataModel> derivingProcessor) {
        return new StepBuilder(jobRepository)
                .<AggregatedSensorDataModel, DerivedSensorDataModel>chunk(1).transactionManager(transactionManager)
                .reader(derivingReader)
                .processor(derivingProcessor)
                .writer(derivingWriter)
                .build();
    }

    @Bean
    ItemReader<AggregatedSensorDataModel> derivingReader(
            AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        return new DerivedSensorDataReader(aggregatedSensorDataRepository);
    }

    @Bean
    ItemProcessor<AggregatedSensorDataModel, DerivedSensorDataModel> derivingProcessor() {
        return new DerivedSensorDataProcessor();
    }

    @Bean
    ItemWriter<DerivedSensorDataModel> derivingWriter(
            DerivedSensorDataRepository derivedSensorDataRepository) {
        return new DerivedSensorDataWriter(derivedSensorDataRepository);
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
