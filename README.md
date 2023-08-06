#   Тестирование API для Trello 

[![Typing SVG](https://readme-typing-svg.herokuapp.com?color=%2336BCF7&lines=Документация+API+Trello)](https://developer.atlassian.com/cloud/trello/rest/api-group-actions/#api-group-actions)

- __*Trello*__ — облачная программа для управления проектами небольших групп

<h2> :hammer_and_wrench: Cтек </h2>
<p align="center">
 <a href="https://www.jetbrains.com/idea/"><img width="6%" title="IDEA" src="materials/pictures/IDEA.png"></a>
  <a href="https://gradle.org/"><img width="6%" title="Gradle" src="materials/pictures/Gradle.png"></a>
  <a href="https://github.com/allure-framework/allure2"><img width="6%" title="Allure Report" src="materials/pictures/Allure Report.png" ></a>
  <a href="https://github.com/"><img width="6%" title="GitHub" src="materials/pictures/GitHub.png"> </a>
  <a href="https://telegram.org"><img width="6%" title="Telegram" src="materials/pictures/Telegram.png"></a>
  <a href="https://www.jenkins.io/"><img width="6%" title="Jenkins" src="materials/pictures/Jenkins.png"> </a>
  <a href="https://www.java.com/"><img width="6%" title="Java" src="materials/pictures/Java.png"></a>
</p>

## :page_with_curl: Проверки
- *Создать список*
- *Переименовать список*
- *Создать карточку*
- *удалить список*
- *Создать новую карточку*

## <img width="5%" title="Jenkins" src="materials/pictures/Jenkins.png"> Сборка в [Jenkins](https://jenkins.autotests.cloud/job/Students/job/TrelloTest/) :gear:
<p align="center">
<img width="70%" title="Jenkins Build" src="materials/screens/запуск в Jenkins.png">
 </p>

__*Сборка:*__
- `TASK` - BoardTest

## :rocket: Запуск тестов

__*Локально, из терминала:*__
```
gradle clean test
```
### Фаил trello.properties
Для запуска теста локально нужно создать фаил в папке resources (src/test/resources) и необходимо указать следующее параметры
```
token = TOKEN
boardID = idBoard
```
## :bar_chart: Отчет о прохождении тестов в Allure и Telegram

|        <img width="5%" title="Allure Report" src="materials/pictures/Allure Report.png" > Allure <img src="materials/screens/Allure1.png?raw=true">        | <img width="5%" title="Allure Report" src="materials/pictures/Allure Report.png" > Allure<img src="materials/screens/Allure2.png?raw=true"> |
|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------:|

<p align="center">
<img width="3%" title="Telegram" src="materials/pictures/Telegram.png"> Telegram
</p>

<p align="center">
<img width="45%" img  src="materials/screens/Telegram.png?raw=true">  
</p>
