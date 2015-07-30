# intership-2015-cards
Cards Project
<p> С помощью нашего продукта вы можете делиться скидками с друзьями.
<p> [Ссылка на активную версию сайта , staging]( http://discounts.7bits.it ) <br>
(логин: admin пароль:redline) <br>
<p> Для запуска проекта понадобятся: <br>
<ol>
<li>Java Development Kit 7</li>
 <li>Tomcat 7</li>
 <li>Maven 3</li>
<li>Git версии 2.*.*.</li>
 <li>Intellij IDEA Community Editionv
 <li>PosgreSQL</li>
 <li>Сгенерируйте SSH ключи для удобного доступа к репозиторию.</li>
 <li>Склонируйте репозиторий к себе в папку.</li>
 <li>Запуск проекта через IDEA.</li>
</ol>
<ol>
  <li>Установка JDK7.<br>  
  `sudo add-apt-repository ppa:webupd8team/java`<br>
  `sudo apt-get update`<br>
  `sudo apt-get install oracle-java7-installer`
  </li>
  <li>Установка Tomcat 7.<br>
  `sudo apt-get install tomcat7`
  </li>
  <li> Установка Maven 3.<br>
  `sudo apt-get install maven`
  </li>
  <li>Установка Git.<br>
   `sudo add-apt-repository ppa:git-core/ppa -y` <br>
   `sudo apt-get update`<br>
   `sudo apt-get install git`
  </li>
  <li>Установка IDEA.<br>
  [Ссылка на скачивание IDE.](https://www.jetbrains.com/idea/download/) <br>
  Разархивируйте скачанный архив. В разархивированной папке находится папка bin, в которой находится файл idea.sh. Запустите его, с его помощью установится IDEA.
  </li>
  <li>Настройка PostgreSQL.<br>
  `sudo apt-get install postgresql` <br>
  Зайдите из под рута и создайте нового пользователя admin c паролем redline, базу данных cardsdb, затем дайте пользователю admin все права на базу cardsdb.<br>
  `sudo -u postgres psql`<br>
  `create user admin with password 'redline';`<br>
  `create database cardsdb;`<br>
  `grant all privileges on database "cardsdb" to admin;`<br>
  `\q`
  <p> При запуске проекта и последующих пулах с гитхаба может понадобиться зайти на cardsdb через терминал, это можно сделать следующим образом:<br>
  `psql -U admin -h localhost -d cardsdb -W`<br>
  После ввода соответствующего пароля вы окажетесь в cardsdb.<br>
  Для обзора списка таблиц:<br>
  `\dt`<br>
  Для удаления таблиц после очередного пула с гитхаба(если проект не собирается, и летят ошибки):<br>
  `drop table <список всех таблиц через запятую, полученных с помощью команды \dt>`
  </li>
  <li>Генерация SSH ключей.<br>
  Пройдите процедуру генерации, следуя всем пунктам в нижеуказанной ссылке.<br>
  [https://help.github.com/articles/generating-ssh-keys/](https://help.github.com/articles/generating-ssh-keys/)
  </li>
  <li> Клонирование репозитория через терминал.<br>
  `cd`       
  `git clone -b site git@github.com:7bits/intership-2015-cards.git`  <br>
  (На данный момент самая актуальная версия находится на ветке site)
  </li>
<li>Запуск проекта.<br>
Запустите Intellij IDEA, и с помощью опции Open откройте папку intership-2015-cards.<br>
Потребуется некоторое время подождать, пока подгрузятся все зависимости и плагины.<br>
Создайте конфигурацию для запуска: Run -> Edit Configurations ->Кнопка + слева вверху -> Maven.<br>
В поле Command Line напишите spring-boot:run, назовите конфигурацию как хотите и нажмите OK.<br>
Теперь Проект можно запустить по нажатию характерной зеленой стрелки вверху справа от названия вашей конфигурации.<br>
</li>