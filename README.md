# Petrol Watcher
Petrol Watcher is an Android app that helps drivers find better
and cheaper fuel, calculate the average fuel consumption and
also plan the total cost of the fuel for their trips.

## Languages
There are 3 language variants for the app: default (British English),
American English and Portuguese (Brazilian).

| Variant | App name |
| ------- | -------- |
| Default (English - UK) | Petrol Watcher |
| English - US | Gas Watcher |
| Portuguese - BR | Petrol Watcher |

## Official releases
Like any other Android apps, official releases can only be
generated with the keystore. If you don't have the keystore
you'll only be able to build and run the app in debug mode.

## Code standards
- Every public function must have a clear KDoc with a
brief explanation.
- Code must be written in Kotlin.

## Firebase
Firebase is used in this app for authentication, real time
database, cloud storage and ads.

## Package structure
Each new feature must be placed in a package inside `feature`.
 i.e.: `feature.vehicles`

## Gitflow
We follow the standard Gitflow guidelines, where the `master`
branch is where production releases are generated from, `develop`
is where feature branches are merged into, and the feature branches
are for features under development (i.e.: `feature/vehicles`).
Enhancements can be made in enhancement branches like
`enhancement/readme` and bug fixes can be made in bug branches
like `bug/main_activity_crash`.

Try to name your branches as objective as possible.

## Quality standards
- Each feature must have be at least 70% covered by either
instrumented or unit tests.
- Each commit and pull request will be checked by Travis CI.

## Contributing
If you want to contribute to the project, fork this repository
and, following the Gitflow, open a pull request to the `develop`
branch.
