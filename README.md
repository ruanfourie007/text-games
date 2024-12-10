# Text Games
A CLI application for text based games. For the current version, only an anagrams game is included.

## Anagrams
The anagrams game requires two inputs, the subject and anagram to test against the subject. All phrases are checked against all previously entered phrases for possible anagrams.

To close the application, simply press enter with no inputs.

![App Screenshot](app-screenshot.png?raw=true "App Screenshot")

### Local Build
> ./gradlew build

### Running
Run using gradle
> ./gradlew run --console=plain --quiet

Run jar file (after build)
> java -jar build/libs/text-games-1.0.jar