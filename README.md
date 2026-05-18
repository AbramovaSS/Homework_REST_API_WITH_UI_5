# 📚 Проект по автоматизации тестирования API с REST Assured на примере демо-стенда [book-club](https://book-club.qa.guru/)

Данный проект содержит набор автоматизированных UI- и API-тестов для сайта книжного клуба [https://book-club.qa.guru/](https://book-club.qa.guru/).
 
Тесты разработаны на основе Swagger-спецификации проекта и покрывают полный цикл CRUD-операций: регистрация, авторизация, управление клубами и отзывами.


Запуск тестов происходит через джобу в [Jenkins](https://jenkins.autotests.cloud/view/java_students/job/AbramovaS_Ready_project_for_Alfa_Bank/),
которая также отвечает за генерацию Allure-отчетов и отправку уведомлений о
результатах в Telegram. Для комплексного анализа результатов настроена интеграция с
системами [Allure TestOps](https://allure.autotests.cloud/jobrun/51167) и [Jira](https://jira.autotests.cloud/browse/HOMEWORK-1578).

---

## 🛠 Cтек

<p align="center">
   <a href="https://www.java.com" target="_blank" rel="noopener"><img src="media/icons/java.svg" alt="Java" width="50" height="50"></a>
   <a href="https://gradle.org" target="_blank" rel="noopener"><img src="media/icons/gradle_logo_icon_248152.svg" alt="Gradle" width="50" height="50"></a>
<a href="https://rest-assured.io/" target="_blank" rel="noopener"><img src="media/icons/rest_assured.png" alt="Telegram" width="50" height="50"></a>   
<a href="https://selenide.org" target="_blank" rel="noopener"><img src="media/icons/selenide.png" alt="Selenide" width="50" height="50"></a>
   <a href="https://junit.org/junit5/" target="_blank" rel="noopener"><img src="media/icons/JUnit5.svg" alt="JUnit 5" width="50" height="50"></a>
   <a href="https://www.jenkins.io" target="_blank" rel="noopener"><img src="media/icons/jenkins.svg" alt="Jenkins" width="50" height="50"></a>
   <a href="https://aerokube.com/selenoid/" target="_blank" rel="noopener"><img src="media/icons/selenoid.png" alt="Selenoid" width="50" height="50"></a>
   <a href="https://allure.qatools.ru" target="_blank" rel="noopener"><img src="media/icons/allure.Default" alt="Allure" width="50" height="50"></a>
   <a href="https://allure.qatools.ru/testops" target="_blank" rel="noopener"><img src="media/icons/Allure2.svg" alt="Allure TestOps" width="50" height="50"></a>
   <a href="https://www.atlassian.com/software/jira" target="_blank" rel="noopener"><img src="media/icons/atlassian_jira.svg" alt="Jira" width="50" height="50"></a>
   <a href="https://telegram.org" target="_blank" rel="noopener"><img src="media/icons/telegram.png" alt="Telegram" width="50" height="50"></a>

---

Содержание
---
- [Сборка в Jenkins](#сборка-в-jenkins)
- [Пример Allure-отчета](#пример-allure-отчета)
- [Интеграция с Allure TestOps](#интеграция-с-Allure-TestOps)
- [Интеграция с  Jira](#интеграция-с-Jira)
- [Уведомление в Telegram](#уведомление-в-telegram)
- [Пример видео из Selenoid](#пример-видео-из-selenoid)
---


## <a id="сборка-в-jenkins">Сборка в Jenkins</a>
Jenkins автоматизирует запуск автотестов при изменении кода или по расписанию. 
Для выбора параметров (например, окружения, браузера, версии браузера и т.д.) и запуска сборки в Jenkins необходимо нажать <kbd>Build with Parameters</kbd>.
После прогона формируется отчет: результаты тестов, включая скриншоты, логи и видео, сохраняются в формате Allure и доступны по ссылке.

![Основная информация](media/screenshots/image_jenkins.png)
## <a id="пример-allure-отчета">Пример Allure-отчета</a>
Увидеть результаты автотестов можно в интерактивном Allure-отчёте — с детальными скриншотами, логами, видео и историей запусков. 
Ссылка на отчёт доступна после успешного запуска сборки в Jenkins.

![Основная информация](media/screenshots/image_allure.png)
## <a id="интеграция-с-Allure-TestOps">Интеграция с Allure TestOps</a>
Интеграция с Jenkins позволяет автоматически передавать результаты тестов из 
сборки в TestOps, где можно отслеживать историю запусков, анализировать прогоны, управлять тест-кейсами, дефектами и требованиями в одном месте. Через общие дашборды
можно делиться отчётами с командой и заказчиками.<br>
Jenkins-сборки можно запускать напрямую из Allure TestOps, выбрав нужную джобу и указав параметры.

### Дашборд
![Основная информация](media/screenshots/img_testops_1.png)
### Тест-кейсы
![Основная информация](media/screenshots/img_testops.png)
## <a id="интеграция-с-Jira">Интеграция с  Jira</a>
В проекте настроена автоматическая отправка данных о сборке из Jenkins в систему управления задачами и проектами - Jira. В результате в задачах Jira появляются:
- Ссылка на сборку в Jenkins с деталями (номер, статус, логи)
- Список изменений (коммиты, авторы)
- Статус тестов (прошли/упали — на основе Allure-отчёта)
- Привязка к задачам — каждая сборка автоматически связывается с соответствующими задачами (Epics, Stories, Bugs)

### Тест-кейсы из TestOps, привязанные к задаче
![Основная информация](media/screenshots/img_jira.png)

### Запуски автотестов с возможностью перехода в TestOps
![Основная информация](media/screenshots/img_jira_1.png)
## <a id="уведомление-в-telegram">Уведомление в Telegram</a>
Результат прогона отправляется в чат мессенджера Telegram

![Основная информация](media/screenshots/img_telega.png)










