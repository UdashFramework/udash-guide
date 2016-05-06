import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt._

object UdashGuideBuild extends Build {
  val GuideStaticFilesDir = "UdashStatic/guide"

  def copyIndex(file: File, to: File) = {
    val newFile = Path(to.toPath.toString + "/index.html")
    IO.copyFile(file, newFile.asFile)
  }

  val compileStaticsGuide = taskKey[Seq[File]]("Guide static files manager.")
  val copyStaticsGuide = taskKey[Unit]("Copy guide static files into backend target.")

  // Compile proper version of JS depending on build version.
  val compileStaticsGuideForRelease = Def.taskDyn {
    val outDir = crossTarget.value / GuideStaticFilesDir / "WebContent"
    if (!isSnapshot.value) {
      Def.task {
        val indexFile = sourceDirectory.value / s"main/assets/index.prod.html"
        copyIndex(indexFile, outDir)
        (fullOptJS in Compile).value
        (packageMinifiedJSDependencies in Compile).value
        (packageScalaJSLauncher in Compile).value
      }
    } else {
      Def.task {
        val indexFile = sourceDirectory.value / s"main/assets/index.dev.html"
        copyIndex(indexFile, outDir)
        (fastOptJS in Compile).value
        (packageJSDependencies in Compile).value
        (packageScalaJSLauncher in Compile).value
      }
    }
  }
}
