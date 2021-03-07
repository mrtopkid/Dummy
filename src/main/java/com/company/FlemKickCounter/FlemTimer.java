package com.company.FlemKickCounter;

import org.quartz.*;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class FlemTimer {

    public void startTrigger() throws SchedulerException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

        Scheduler sched = schedFact.getScheduler();

        sched.start();

        JobDetail job = newJob(FlemTallyService.class)
                .withIdentity("myJob", "group1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(22, 0))
                .build();

        sched.scheduleJob(job, trigger);
    }




}


