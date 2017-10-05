## Dvare Framework
A Lightweight Java business rule expression language.


## Example

```java
public class ArithmeticOperationTest {
    
    public void testApp() throws ExpressionParseException, InterpretException {

        RuleConfiguration factory = new RuleConfiguration();
        String expr = "Variable5->substring(2,2)->toInteger() between [80,90] and" +
         " Variable5->substring(3,2)->toInteger() in [45,46]";
        Expression expression = factory.getParser().fromString(expr, ArithmeticOperation.class);
        RuleBinding rule = new RuleBinding(expression);
        ArithmeticOperation arithmeticOperation = new ArithmeticOperation();
        arithmeticOperation.setVariable5("D845");

        RuleEvaluator evaluator = factory.getEvaluator();
        boolean result = (Boolean) evaluator.evaluate(rule, arithmeticOperation);
        assertTrue(result);
    }
 }
```

## Current version

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4c684251c3984e33a88bbfd0cd4e4df8)](https://www.codacy.com/app/hammadirshad/dvare-framework?utm_source=github.com&utm_medium=referral&utm_content=dvare/dvare-framework&utm_campaign=badger)
* The current stable version is `2.2` : [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.dvare/dvare-framework/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.dvare|dvare-framework|2.2|) [![Javadoc](https://javadoc-emblem.rhcloud.com/doc/org.dvare/dvare-framework/badge.svg)](http://www.javadoc.io/doc/org.dvare/dvare-framework)
* The current snapshot version is `2.3-SNAPSHOT` : [![Build Status](https://travis-ci.org/dvare/dvare-framework.svg?branch=master)](https://travis-ci.org/dvare/dvare-framework) [![Coverage Status](https://coveralls.io/repos/github/dvare/dvare-framework/badge.svg?branch=master)](https://coveralls.io/github/dvare/dvare-framework?branch=master)[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4c684251c3984e33a88bbfd0cd4e4df8)](https://www.codacy.com/app/hammadirshad/dvare-framework?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dvare/dvare-framework&amp;utm_campaign=Badge_Grade)

In order to use snapshot versions, you need to add the following maven repository in your `pom.xml`:

```xml
<repository>
    <id>ossrh</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```


 Maven dependency:
```xml
<dependencies>
        <dependency>
            <groupId>org.dvare</groupId>
            <artifactId>dvare-framework</artifactId>
            <version>2.2</version>
        </dependency>         
</dependencies>
```

## License
Dvare Framework  is released under the [![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT).

```
The MIT License (MIT)

Copyright (c) 2016-2017 DVARE (Data Validation and Aggregation Rule Engine)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Sogiftware.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

