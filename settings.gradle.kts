pluginManagement {
    repositories {
        maven (url = "https://maven.aliyun.com/nexus/content/groups/public/")
        maven (url = "https://jitpack.io")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven (url = "https://maven.aliyun.com/nexus/content/groups/public/")
        maven (url = "https://jitpack.io")
        google()
        mavenCentral()
    }
}

rootProject.name = "WebRtcSdkDemo"
include(":app")
include(":libwebrtc")
 