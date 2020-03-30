package mj.testing.quarts

import mj.testing.quarts.ScalaJobData._
import org.quartz.JobBuilder.newJob
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory
import org.quartz._

import scala.language.implicitConversions

object Daemon {

  def main(args: Array[String]): Unit = {
    try {
      val scheduler = StdSchedulerFactory.getDefaultScheduler
      scheduler.start()

      val gitlabCreds = GitlabCreds(None, None)
      val creds       = Creds(None, None)

      val jdm = new JobDataMap().withGitlabCreds(gitlabCreds).withJenkinsCreds(creds).withJiraCreds(creds)

      val maintenanceMrsJob: JobDetail = newJob(classOf[DumbJob]).withIdentity("job1", "group1").setJobData(jdm).build

      val trigger = newTrigger
        .withIdentity("trigger1", "group1")
        .startNow
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever)
        .build

      scheduler.scheduleJob(maintenanceMrsJob, trigger)

      sys.addShutdownHook(scheduler.shutdown()) //: @silent
    } catch {
      case se: SchedulerException => se.printStackTrace()
    }
  }
}

class DumbJob() extends Job {

  override def execute(context: JobExecutionContext): Unit = {
    println("1")
  }
}
