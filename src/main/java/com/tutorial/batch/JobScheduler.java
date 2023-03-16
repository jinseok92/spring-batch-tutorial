package com.tutorial.batch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final JobConfiguration jobConfiguration;

//    @Scheduled(cron = "0 * * * * *")
    @Scheduled(initialDelay = 3000, fixedDelay = 5000)
    public void jobScheduled()
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date date = new Date();
        sdf.format(date);
        System.out.println("date = " + date);

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("date", new JobParameter(date));
        JobParameters parameters = new JobParameters(jobParametersMap);

        jobLauncher.run(jobConfiguration.job(), parameters);

    }
}
