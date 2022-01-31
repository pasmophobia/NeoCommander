[![Build and release](https://github.com/Propromp/NeoCommander/actions/workflows/build_and_release.yml/badge.svg)](https://github.com/Propromp/NeoCommander/actions/workflows/build_and_release.yml)
# NeoCommander
## build.gradle.kts
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.propromp:neocommander:%version%")
}
```
## plugin.yml
```yaml
libraries:
  - net.propromp:neocommander:%version%
```