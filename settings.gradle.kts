pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
include(":exinputdata")
include(":mycalendar")
include(":ch12_ex1")
include(":exlogin01")
include(":exsqlite")
include(":exaqlite04")
include(":json")
include(":json_volley")
include(":colorpicker")
