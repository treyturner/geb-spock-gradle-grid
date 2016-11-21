The last time I checked, Spock supplies 'per-specification' and 'per-feature-iteration' setup and teardown methods, but does not seem to supply 'per-feature' setup and teardown methods, which run once per feature regardless of iteration. To address this, two Spock plugins are included: [BeforeFeature] and [AfterFeature].

[BeforeFeature]: BeforeFeature.groovy
[AfterFeature]: AfterFeature.groovy

To use them, annotate a feature as follows:

```groovy
@BeforeFeature("setupFeature")
@AfterFeature("cleanupFeature")
def "should multiply a number by itself"() {

    expect: "the square of an Integer to equal the number times itself"
    myInt**2 == myInt * myInt
    
    where:
    //Iterates the test, filling myInt with each value of the Array
    myInt << [1, 2, 3, 4, 5]
}
```

Make sure you declare the methods you supplied somewhere that is accessible to the spec class!

```groovy
def setupFeature() {
    //only runs once, even if the feature iterates using a 'where' block
    log.info "reading a config file, connecting to a database, etc..."
}

def cleanupFeature() {
    //only runs once, even if the feature iterates using a 'where' block
    log.info "closing a file or database connection..."
}
```

There are [tests provided] for this functionality.

[tests provided]: ../../../test/groovy/util/spock

