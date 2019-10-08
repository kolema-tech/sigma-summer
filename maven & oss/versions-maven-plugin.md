
依赖：

```xml
 <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>versions-maven-plugin</artifactId>
                <version>2.3</version>
                <configuration>
                    <generateBackupPoms>false</generateBackupPoms>
                </configuration>
            </plugin>
        </plugins>
 </build>
```

* 设置版本：
```bash
mvn versions:set -DnewVersion=1.0.7-SNAPSHOT
```

* 回滚版本
```bash
mvn versions:revert
```

* 更新子模块

```bash
mvn versions:update-child-modules
```
