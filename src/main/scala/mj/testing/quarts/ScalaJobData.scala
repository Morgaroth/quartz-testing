package mj.testing.quarts

import org.quartz.JobDataMap


case class Creds(login: Option[String], pass: Option[String])

case class GitlabCreds(token: Option[String], server: Option[String])

object ScalaJobData {

  implicit class ScalaJobData(underlying: JobDataMap) {
    def set(key: String, value: AnyRef): JobDataMap = {
      underlying.put(key, value)
      underlying
    }

    def get[T](key: String): T = underlying.get(key).asInstanceOf[T]

    def withGitlabCreds(value: GitlabCreds): JobDataMap = set("gitlab.creds", value)

    def withJiraCreds(value: Creds): JobDataMap = set("jira.creds", value)

    def withJenkinsCreds(value: Creds): JobDataMap = set("jenkins.creds", value)

    def readGitlabCreds: GitlabCreds = get[GitlabCreds]("gitlab.creds")

    def readJiraCreds: Creds = get[Creds]("jira.creds")

    def readJenkinsCreds: Creds = get[Creds]("jenkins.creds")
  }

}
