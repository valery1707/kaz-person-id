### Code coverage

`mvn clean test jacoco:report`

### Deploy to Maven Central

1. Commit and deploy artifact to Sonatype OSSRH
  1. `mvn versions:set -DnewVersion=1.2.3`
  1. `git commit -m 'Release version 1.2.3' pom.xml`
  1. `mvn clean deploy -P release -Dgpg.passphrase=real-gpg-passphrase`
  1. `mvn versions:set -DnewVersion=1.2.3-SNAPSHOT`
  1. `git commit -m 'Prepare next development' pom.xml`
1. Promote artifacts to Maven Central
  1. [Sonatype OSSRH](https://oss.sonatype.org/)
  1. Find staging repository with name "namevalery1707-"
  1. Close it
  1. Wait some time, Refresh
  1. Release
