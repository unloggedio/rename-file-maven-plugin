## Rename file maven plugin

Rename multiple files in maven lifecycle using Regex pattern match

Example usage

```xml

<plugin>
    <groupId>video.bug</groupId>
    <artifactId>rename-file-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <phase>compile</phase>
            <goals>
                <goal>rename-file</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <source>(.+).class</source>
        <target>$1.scl.unlogged</target>
        <workingDirectory>${build.directory}/classes/io/unlogged/processor</workingDirectory>
    </configuration>
</plugin>

```

## Dev

### Build

```mvn clean install ```

### Deploy

```mvn deploy```

### Debug

```MAVEN_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005" mvn clean package```