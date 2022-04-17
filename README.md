# java-jcstress

A repository to review the tool jcstress

## How to run

```
mvn clean install
java -jar ./target/java-jcstress.jar -v -t info.jab.concurrent.ConcurrentAccountTest
```

## Others commands

```
mvn prettier:write
mvn versions:display-dependency-updates
```

## References

- https://github.com/openjdk/jcstress
- https://github.com/inponomarev/jcstress-demo
