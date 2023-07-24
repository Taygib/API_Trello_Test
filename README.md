#   Тестирование API для Trello 

[![Typing SVG](https://readme-typing-svg.herokuapp.com?color=%2336BCF7&lines=Документация+API+Trello)](https://developer.atlassian.com/cloud/trello/rest/api-group-actions/#api-group-actions)


<h2> :hammer_and_wrench: Cтек </h2>
<p align="center">
  <img width="6%" title="IDEA" src="materials/pictures/IDEA.png">
  <img width="6%" title="Gradle" src="materials/pictures/Gradle.png">
  <img width="6%" title="Allure Report" src="materials/pictures/Allure Report.png" >
  <img width="6%" title="GitHub" src="materials/pictures/GitHub.png"> 
  <img width="6%" title="Telegram" src="materials/pictures/Telegram.png">
  <img width="6%" title="Jenkins" src="materials/pictures/Jenkins.png"> 
  <img width="6%" title="Java" src="materials/pictures/Java.png">
</p>

## :page_with_curl: Проверки
- *Создать список*
- *Переименовать список*
- *Создать карточку*
- *удалить список*
- *Создать новую карточку*

## <img width="5%" title="Jenkins" src="materials/pictures/Jenkins.png"> Сборка в [Jenkins](https://jenkins.autotests.cloud/job/Students/job/TrelloTest/)
<p align="center">
<img width="70%" title="Jenkins Build" src="materials/screens/запуск в Jenkins.png">
 </p>

## :rocket: Запуск тестов

__*Локально, из терминала:*__
```
gradle clean BoardTest
```
__*Удаленно, из Jenkins:*__
```
clean
${TASK}
```

## :bar_chart: Отчет о прохождении тестов в Allure и Telegram

|        <img width="5%" title="Allure Report" src="materials/pictures/Allure Report.png" > Allure <img src="materials/screens/Allure1.png?raw=true">        | <img width="5%" title="Allure Report" src="materials/pictures/Allure Report.png" > Allure<img src="materials/screens/Allure2.png?raw=true"> |
|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------:|

<p align="center">
<img width="5%" title="Telegram" src="materials/pictures/Telegram.png"> Telegram
</p>

<p align="center">
<img width="45%" img  src="materials/screens/Telegram.png?raw=true">  
</p>
