**This is the template README. Please update this with project specific content.**

# stc-submissions-api-test

API journey tests suite for the [securities-transfer-charge-frontend](https://github.com/hmrc/securities-transfer-charge-frontend) using SeleniumWebdriver with ScalaTest.

## Pre-requisites

### Services

Start Mongo Docker container as follows:

```bash
docker run --rm -d -p 27017:27017 --name mongo percona/percona-server-mongodb:6.0
```

To start the required services via [service manager](https://github.com/hmrc/sm2), run:

```bash
sm2 --start STAMP_TAXES_ON_SHARES_ALL
```

## Tests

Run tests as follows:

* Argument `<environment>` must be `local`, `dev`, `qa` or `staging`.

```bash
./run-tests.sh <environment>
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
