package com.gps.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.*;
import java.io.IOException;

public class QuartzScheduler extends GenericServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("Scheduling Job ..");

        try {
            JobDetail job = JobBuilder.newJob(RcaEmailReminderJob.class)
                    .withIdentity("rcaEmailReminderJob", "group1").build();


            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("rcaEmailReminderTrigger", "group1")
                    .withSchedule(
                           CronScheduleBuilder.cronSchedule("0 30 0 * * ?"))
                            //CronScheduleBuilder.cronSchedule("0 0/3 * 1/1 * ? *"))

                    .build();

            //schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service(ServletRequest arg0, ServletResponse arg1)
            throws ServletException, IOException {
    }

    public String getServletInfo() {
        return null;
    }
}