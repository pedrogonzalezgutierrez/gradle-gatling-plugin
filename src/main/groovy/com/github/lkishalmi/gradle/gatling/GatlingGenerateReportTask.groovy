package com.github.lkishalmi.gradle.gatling

import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.JavaExec

class GatlingGenerateReportTask extends JavaExec {

    GatlingGenerateReportTask() {
        main = GatlingPlugin.GATLING_MAIN_CLASS
        classpath = project.configurations.gatlingRuntime
    }

    @InputDirectory
    File getSimulationLogFolder() {
        project.extensions.getByType(GatlingPluginExtension).simulationLogFolder
    }

    @Override
    void exec() {
        project.javaexec {
            main = this.getMain()
            classpath = this.getClasspath()

            if( getSimulationLogFolder() == null ) {
                throw new IllegalArgumentException("`simulationLogFolder` needs to be defined in the Closure")
            } else if( !getSimulationLogFolder().exists() ) {
                throw new IllegalArgumentException("The folder '"+simulationLogFolder+"' does not exist")
            }
            // Generate reports
            args "-ro", simulationLogFolder
        }
    }
}