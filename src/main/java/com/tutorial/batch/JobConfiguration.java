package com.tutorial.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * FlowJob 예제
     * step1 성공 시 : step1 -> step2 -> step3
     * step1 실패 시 : step1 -> step3
     * @return
     */
    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
            // step1 실행
            .start(step1())
                // step1 실행 결과가 FAILED 일 경우
                .on("FAILED")
                // step3 실행
                .to(step3())
                // step3 실행 결과에 상관없이 Flow을 종료한다.
                .on("*")
                .end()
            // step1 으로 부터
            .from(step1())
                // step1 실행 결과가 FAILED 외 모든 경우
                .on("*")
                // step2 실행
                .to(step2())
                // step3 실행
                .next(step3())
                // step3 실행 결과 상관없이 Flow 종료
                .on("*")
                .end()
            // Job 종료
            .end()
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("=============================");
                System.out.println("Step1 has executed");
                System.out.println("=============================");

                // ExitStatus 를 FAILED 로 지정
//                contribution.setExitStatus(ExitStatus.FAILED);

                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("=============================");
                System.out.println("Step2 has executed");
                System.out.println("=============================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("=============================");
                System.out.println("Step3 has executed");
                System.out.println("=============================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}