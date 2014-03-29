# Bad

Have you ever needed more reasons to get management to approve the payment of technical debt? Have you ever wanted to
be super passive aggressive to your co-workers and tell them their code sucks? Need to remind yourself to refactor your
code? Well this library is for you!

Simply add this library to your maven or gradle project, and then place the `@Bad` annotations where needed.

Every time you build you will now see warnings about how your code should be refactored! Great for reminding yourself
to get better, to refactor, to show others there code sucks!

## Download

Maven:

```xml
<dependency>
    <groupId>com.andrewreitz.bad</groupId>
    <artifactId>bad</artifactId>
    <version>{Latest Version}</version>
</dependency>
```

Gradle:

```groovy
compile com.andrewreitz.bad:bad:{Latest Version}
```

## Future Updates
`@Prototype` Show that the code was prototype and `@Slow` slow your compilation down so you have more to screw around!

## License

    Copyright 2014 Andrew Reitz
    This work is free. You can redistribute it and/or modify it under the
    terms of the Do What The Fuck You Want To Public License, Version 2,
    as published by Sam Hocevar. See the COPYING file for more details.
