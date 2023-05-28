#   Тестирование API для Trello 

<h2>  Cтек </h2>
<p align="center">
  <img width="6%" title="IDEA" src="materials/pictures/IDEA.png">
  <img width="6%" title="Gradle" src="materials/pictures/Gradle.png">
  <img width="6%" title="Allure Report" src="materials/pictures/Allure Report.png" >
  <img width="6%" title="GitHub" src="materials/pictures/GitHub.png"> 
  <img width="6%" title="Telegram" src="materials/pictures/Telegram.png">
  <img width="6%" title="Jenkins" src="materials/pictures/Jenkins.png"> 
  <img width="6%" title="Java" src="materials/pictures/Java.png">
</p>

<h2> <img width="5%" title="Jenkins" src="materials/pictures/Jenkins.png"> Старт запуска в Jenkins  </h2>
<p align="center">
<img width="70%" title="Jenkins Build" src="materials/screens/запуск в Jenkins.png">
 </p>

 ##  Результат тестирования [Allure Reports](https://jenkins.autotests.cloud/job/TrelloTest/) 


|        <img width="5%" title="Allure Report" src="materials/pictures/Allure Report.png" > Allure <img src="materials/screens/Allure1.png?raw=true">        | <img width="5%" title="Allure Report" src="materials/pictures/Allure Report.png" > Allure<img src="materials/screens/Allure2.png?raw=true"> |
|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------:|

<h2> <img width="5%" title="Telegram" src="materials/pictures/Telegram.png"> Уведомление в Telegram</h2>

<img  src="materials/screens/Telegram.png?raw=true">                                                                         

### <img width="6%" title="Gradle" src="materials/pictures/Gradle.png"> Gradle
```
plugins {
    id 'java-library'
    id "io.freefair.lombok" version "6.0.0-m2"
    id 'io.qameta.allure' version '2.11.2'
}
allure {
    report {
        version.set("2.21.0")
    }
    adapter { // отвечает за появление папки build/allure-results
        aspectjWeaver.set(true) //обработка аннотации @Step
        frameworks {
            junit5 { //название фреймворка
                adapterVersion.set("2.21.0") //версия интеграции фреймворка и Allure
            }
        }
    }
}

compileTestJava {
    options.encoding = 'UTF-8'
}

compileJava.options.encoding = 'UTF-8'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(
            "org.junit.jupiter:junit-jupiter:5.9.1",
            "org.slf4j:slf4j-simple:2.0.6",
            "io.rest-assured:rest-assured:5.3.0",
            "com.fasterxml.jackson.core:jackson-databind:2.14.2",
            "org.assertj:assertj-core:3.24.2",
            "com.github.javafaker:javafaker:1.0.2",
            "io.rest-assured:json-path:5.3.0",
            "io.qameta.allure:allure-rest-assured:2.21.0"
    )
}

tasks.withType(Test) {
    systemProperties(System.getProperties())
    useJUnitPlatform()

    testLogging {
        lifecycle {
            events "started", "skipped", "failed", "standard_error", "standard_out"
            exceptionFormat "short"
        }
    }
}
test {
    useJUnitPlatform()
}
task CardTest(type: Test) {
    useJUnitPlatform() {
        includeTags("Card")
    }
}
task ListTest(type: Test) {
    useJUnitPlatform() {
        includeTags("List")
    }
}
task BoardTest(type: Test) {
    useJUnitPlatform() {
        includeTags("Board")
    }
}
task DeleteCardTest(type: Test) {
    useJUnitPlatform() {
        includeTags("deleteCard")
    }
}
```