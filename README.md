## Luxgatej

Desktop UI app for Luxgate platform.


Run app in development mode

```
$ ./gradlew ui:launch
```

Make `.deb`

```
$ ./gradlew ui:deb
$ sudo dpkg -i ui/build/jfx/native/luxgatej-1.0.deb
```

Ensure you have installed Oracle JDK8 on your dev machine.

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
```

Project should work using OpenJDK distribution too.