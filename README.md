# Text Games
A CLI application for text based games. For the current version, only an anagrams game is included.

## Anagrams
The anagrams game supports two user features which are seamlessly integrated:
- Two subsequent phrases can be entered to check if they are anagrams.
- All phrases previously entered will be returned if they are anagrams of the entered phrase.

To close the application, simply press enter with no inputs.

![App Screenshot](app-screenshot.png?raw=true "App Screenshot")

### Build
```bash
$ ./gradlew build
```

### Run
```bash
# Run using gradle
$ ./gradlew run --console=plain --quiet
# Jar file (after build)
$ java -jar build/libs/text-games-1.0.jar
```