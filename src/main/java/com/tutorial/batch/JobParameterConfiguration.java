package com.tutorial.batch;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobParameterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("Job")
            .start(step1())
            .next(step2())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();

                System.out.println("=============================");
                System.out.println("Step1 has executed");
                System.out.println("jobParameters = " + jobParameters);
                System.out.println("=============================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
                Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();

                System.out.println("=============================");
                System.out.println("Step2 has executed");
                System.out.println("jobParameters = " + jobParameters);
                System.out.println("=============================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}