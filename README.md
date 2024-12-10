# Text Games
A CLI application for text based games.

## Anagrams
![App Screenshot](app-screenshot.png?raw=true "App Screenshot")

The anagrams game has two inputs, the subject and anagram to test against the subject.

To close the application, simply press enter with no inputs.

### Tests
> ./gradlew test

### Code Checks
> ./gradlew detekt

### Build
> ./gradlew build

### Running
Run using gradle
> ./gradlew run --console=plain --quiet

Run jar file (after build)
> java -jar build/libs/text-games-1.0.jar