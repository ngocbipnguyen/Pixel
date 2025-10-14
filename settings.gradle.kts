pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Pixel"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:data")
include(":core:database")
include(":core:designsystem")
include(":core:model")
include(":core:network")
include(":feature:foryou")
include(":feature:settings")
include(":feature:topic")
include(":feature:bookmarks")
include(":feature:interests")
include(":sync:work")
include(":feature:collection")
include(":feature:home")
