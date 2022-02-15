[![Build and release](https://github.com/Propromp/NeoCommander/actions/workflows/build_and_release.yml/badge.svg)](https://github.com/Propromp/NeoCommander/actions/workflows/build_and_release.yml)
# NeoCommander
![image](https://user-images.githubusercontent.com/62823412/153975680-21d1f20b-1989-49f1-b480-3bf5b2501e06.png)
![image](https://user-images.githubusercontent.com/62823412/153975791-7362809d-b0a3-4f60-8f5a-5e5b938eb1cd.png)
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
