package com.aurus.server.batch;

import java.util.List;

import javax.sql.DataSource;

import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataModel;
import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataProcessor;
import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataReader;
import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataRepository;
import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataWriter;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataModel;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataProcessor;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataReader;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataRepository;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataWriter;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataProcessor;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataReader;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataRepository;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataWriter;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataProcessor;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataReader;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataRepository;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataWriter;
import com.aurus.server.batch.process.sensor.ProcessedSensorDataModel;
import com.aurus.server.batch.process.sensor.ProcessedSensorDataProcessor;
import com.aurus.server.batch.process.sensor.ProcessedSensorDataReader;
import com.aurus.server.batch.process.sensor.ProcessedSensorDataRepository;
import com.aurus.server.batch.process.sensor.ProcessedSensorDataWriter;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataProcessor;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataReader;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataRepository;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataWriter;
import com.aurus.server.ingestion.sensor.RawSensorDataModel;
import com.aurus.server.ingestion.sensor.RawSensorDataRepository;
import com.aurus.server.ingestion.weather.RawWeatherDataModel;
import com.aurus.server.ingestion.weather.RawWeatherDataRepository;
import com.aurus.server.shared.TdsWindowNormalization;

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
    Job processingSensorDataJob(JobRepository jobRepository, Step processingSensorDataStep) {
        return new JobBuilder("processingSensorData", jobRepository)
                .start(processingSensorDataStep)
                .build();
    }

    @Bean
    Step processingSensorDataStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, ItemReader<RawSensorDataModel> processingSensorDataReader,
            ItemWriter<ProcessedSensorDataModel> processingSensorDataWriter,
            ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> processingSensorDataProcessor) {
        return new StepBuilder(jobRepository)
                .<RawSensorDataModel, ProcessedSensorDataModel>chunk(1).transactionManager(transactionManager)
                .reader(processingSensorDataReader)
                .processor(processingSensorDataProcessor)
                .writer(processingSensorDataWriter)
                .build();
    }

    @Bean
    ItemReader<RawSensorDataModel> processingSensorDataReader(RawSensorDataRepository rawSensorDataRepository) {
        return new ProcessedSensorDataReader(rawSensorDataRepository);
    }

    @Bean
    ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> processingSensorDataProcessor(
            RawSensorDataRepository rawSensorDataRepository) {
        return new ProcessedSensorDataProcessor(rawSensorDataRepository);
    }

    @Bean
    ItemWriter<ProcessedSensorDataModel> processingSensorDataWriter(
            ProcessedSensorDataRepository processedSensorDataRepository) {
        return new ProcessedSensorDataWriter(processedSensorDataRepository);
    }

    @Bean
    Job processingWeatherDataJob(JobRepository jobRepository, Step processingWeatherDataStep) {
        return new JobBuilder("processingWeatherData", jobRepository)
                .start(processingWeatherDataStep)
                .build();
    }

    @Bean
    Step processingWeatherDataStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, ItemReader<RawWeatherDataModel> processingWeatherDataReader,
            ItemWriter<ProcessedWeatherDataModel> processingWeatherDataWriter,
            ItemProcessor<RawWeatherDataModel, ProcessedWeatherDataModel> processingWeatherDataProcessor) {
        return new StepBuilder(jobRepository)
                .<RawWeatherDataModel, ProcessedWeatherDataModel>chunk(1).transactionManager(transactionManager)
                .reader(processingWeatherDataReader)
                .processor(processingWeatherDataProcessor)
                .writer(processingWeatherDataWriter)
                .build();
    }

    @Bean
    ItemReader<RawWeatherDataModel> processingWeatherDataReader(RawWeatherDataRepository rawWeatherDataRepository) {
        return new ProcessedWeatherDataReader(rawWeatherDataRepository);
    }

    @Bean
    ItemProcessor<RawWeatherDataModel, ProcessedWeatherDataModel> processingWeatherDataProcessor() {
        return new ProcessedWeatherDataProcessor();
    }

    @Bean
    ItemWriter<ProcessedWeatherDataModel> processingWeatherDataWriter(
            ProcessedWeatherDataRepository processedWeatherDataRepository, BatchEventPublisher batchEventPublisher) {
        return new ProcessedWeatherDataWriter(processedWeatherDataRepository, batchEventPublisher);
    }

    @Bean
    Job aggregatingSensorDataJob(JobRepository jobRepository, Step aggregatingSensorDataStep) {
        return new JobBuilder("aggregatingSensorData", jobRepository)
                .start(aggregatingSensorDataStep)
                .build();
    }

    @Bean
    Step aggregatingSensorDataStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<List<ProcessedSensorDataModel>> aggregatingSensorDataReader,
            ItemWriter<AggregatedSensorDataModel> aggregatingSensorDataWriter,
            ItemProcessor<List<ProcessedSensorDataModel>, AggregatedSensorDataModel> aggregatingSensorDataProcessor) {
        return new StepBuilder(jobRepository)
                .<List<ProcessedSensorDataModel>, AggregatedSensorDataModel>chunk(1)
                .transactionManager(transactionManager)
                .reader(aggregatingSensorDataReader)
                .processor(aggregatingSensorDataProcessor)
                .writer(aggregatingSensorDataWriter)
                .build();
    }

    @Bean
    ItemReader<List<ProcessedSensorDataModel>> aggregatingSensorDataReader(
            ProcessedSensorDataRepository processedSensorDataRepository) {
        return new AggregatedSensorDataReader(processedSensorDataRepository);
    }

    @Bean
    ItemProcessor<List<ProcessedSensorDataModel>, AggregatedSensorDataModel> aggregatingSensorDataProcessor() {
        return new AggregatedSensorDataProcessor();
    }

    @Bean
    ItemWriter<AggregatedSensorDataModel> aggregatingSensorDataWriter(
            AggregatedSensorDataRepository aggregatedSensorDataRepository, BatchEventPublisher batchEventPublisher) {
        return new AggregatedSensorDataWriter(aggregatedSensorDataRepository, batchEventPublisher);
    }

    @Bean
    Job aggregatingWeatherDataJob(JobRepository jobRepository, Step aggregatingWeatherDataStep) {
        return new JobBuilder("aggregatingWeatherData", jobRepository)
                .start(aggregatingWeatherDataStep)
                .build();
    }

    @Bean
    Step aggregatingWeatherDataStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<ProcessedWeatherDataModel> aggregatingWeatherDataReader,
            ItemWriter<AggregatedWeatherDataModel> aggregatingWeatherDataWriter,
            ItemProcessor<ProcessedWeatherDataModel, AggregatedWeatherDataModel> aggregatingWeatherDataProcessor) {
        return new StepBuilder(jobRepository)
                .<ProcessedWeatherDataModel, AggregatedWeatherDataModel>chunk(1).transactionManager(transactionManager)
                .reader(aggregatingWeatherDataReader)
                .processor(aggregatingWeatherDataProcessor)
                .writer(aggregatingWeatherDataWriter)
                .build();
    }

    @Bean
    ItemReader<ProcessedWeatherDataModel> aggregatingWeatherDataReader(
            ProcessedWeatherDataRepository processedWeatherDataRepository) {
        return new AggregatedWeatherDataReader(processedWeatherDataRepository);
    }

    @Bean
    ItemProcessor<ProcessedWeatherDataModel, AggregatedWeatherDataModel> aggregatingWeatherDataProcessor() {
        return new AggregatedWeatherDataProcessor();
    }

    @Bean
    ItemWriter<AggregatedWeatherDataModel> aggregatingWeatherDataWriter(
            AggregatedWeatherDataRepository aggregatedWeatherDataRepository, BatchEventPublisher batchEventPublisher) {
        return new AggregatedWeatherDataWriter(aggregatedWeatherDataRepository, batchEventPublisher);
    }

    @Bean
    Job derivingSensorDataJob(JobRepository jobRepository, Step derivingSensorDataStep) {
        return new JobBuilder("derivingSensorData", jobRepository)
                .start(derivingSensorDataStep)
                .build();
    }

    @Bean
    Step derivingSensorDataStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<AggregatedSensorDataModel> derivingSensorDataReader,
            ItemWriter<DerivedSensorDataModel> derivingSensorDataWriter,
            ItemProcessor<AggregatedSensorDataModel, DerivedSensorDataModel> derivingSensorDataProcessor) {
        return new StepBuilder(jobRepository)
                .<AggregatedSensorDataModel, DerivedSensorDataModel>chunk(1).transactionManager(transactionManager)
                .reader(derivingSensorDataReader)
                .processor(derivingSensorDataProcessor)
                .writer(derivingSensorDataWriter)
                .build();
    }

    @Bean
    ItemReader<AggregatedSensorDataModel> derivingSensorDataReader(
            AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        return new DerivedSensorDataReader(aggregatedSensorDataRepository);
    }

    @Bean
    ItemProcessor<AggregatedSensorDataModel, DerivedSensorDataModel> derivingSensorDataProcessor(
            TdsWindowNormalization tdsWindowNormalization) {
        return new DerivedSensorDataProcessor(tdsWindowNormalization);
    }

    @Bean
    ItemWriter<DerivedSensorDataModel> derivingSensorDataWriter(
            DerivedSensorDataRepository derivedSensorDataRepository) {
        return new DerivedSensorDataWriter(derivedSensorDataRepository);
    }

    @Bean
    Job derivingWeatherDataJob(JobRepository jobRepository, Step derivingWeatherDataStep) {
        return new JobBuilder("derivingWeatherData", jobRepository)
                .start(derivingWeatherDataStep)
                .build();
    }

    @Bean
    Step derivingWeatherDataStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<AggregatedWeatherDataModel> derivingWeatherDataReader,
            ItemWriter<DerivedWeatherDataModel> derivingWeatherDataWriter,
            ItemProcessor<AggregatedWeatherDataModel, DerivedWeatherDataModel> derivingWeatherDataProcessor) {
        return new StepBuilder(jobRepository)
                .<AggregatedWeatherDataModel, DerivedWeatherDataModel>chunk(1).transactionManager(transactionManager)
                .reader(derivingWeatherDataReader)
                .processor(derivingWeatherDataProcessor)
                .writer(derivingWeatherDataWriter)
                .build();
    }

    @Bean
    ItemReader<AggregatedWeatherDataModel> derivingWeatherDataReader(
            AggregatedWeatherDataRepository aggregatedWeatherDataRepository) {
        return new DerivedWeatherDataReader(aggregatedWeatherDataRepository);
    }

    @Bean
    ItemProcessor<AggregatedWeatherDataModel, DerivedWeatherDataModel> derivingWeatherDataProcessor() {
        return new DerivedWeatherDataProcessor();
    }

    @Bean
    ItemWriter<DerivedWeatherDataModel> derivingWeatherDataWriter(
            DerivedWeatherDataRepository derivedWeatherDataRepository) {
        return new DerivedWeatherDataWriter(derivedWeatherDataRepository);
    }

    @Override
    @Bean(name = { "taskExecutor" })
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
