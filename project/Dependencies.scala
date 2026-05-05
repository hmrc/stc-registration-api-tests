import sbt._

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% "api-test-runner"         % "0.10.0",
    "io.rest-assured"         % "rest-assured"            % "6.0.0",
    "uk.gov.hmrc"            %% "http-verbs-test-play-30" % "15.7.0",
    "org.scalatestplus.play" %% "scalatestplus-play"      % "7.0.2",
    "org.playframework"      %% "play-pekko-http-server"  % "3.0.10",
    "uk.gov.hmrc"            %% "http-verbs-test-play-30" % "15.1.0"
  ).map(_ % Test)
}
