```bash
# 直接打包SNAPSHOT
mvn clean deploy -DskipTests

# 部署release斑斑
mvn clean deploy -P release -DskipTests
```


