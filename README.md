# Petrol Watcher

Petrol Watcher is an Android app that helps drivers find better
and cheaper fuel and also plan the total cost of the fuel for
their trips.

## Languages

There are 3 language variants for the app: default (British English),
American English and Portuguese (Brazilian).

| Variant | App name |
| ------- | -------- |
| Default (English - UK) | Petrol Watcher |
| English - US | Gas Watcher |
| Portuguese - BR | Melhor Combustível |

## Build types

If you're not the project owner you can only build the project
in debug mode, but even in debug mode you will still need to
have a file called **release.properties** in the app directory.

Here are some instructions regarding the release.properties file:

### release.properties file

As mentioned previously, the release.properties file should be
placed in the app directory.

Here is an example of how the file should look like. You can copy
and paste the following into your file and the debug mode will
work.

<pre>
    <code>
  key.password=test
  store.password=test
  key.alias=test
  store.file=test
    </code>
</pre>

## Firebase
Firebase is used in this app for authentication, real time
database, cloud storage, analytics and ads.

## Tests

Each instrumented test class has its accompanying robot.
These robots make it easier to test activities and fragments
and also make test cases more readable with methods like
`launchActivity()` or `clickOnSignInButton()` for example.

Note: If you run all instrumented tests at once and the
test suite terminates before running all tests, clean
the project and run them again.

## Project owner

Alan Camargo

[e-mail](mailto:alcam.ukdev@gmail.com)

[LinkedIn](https://www.linkedin.com/in/alancamargo92/)
