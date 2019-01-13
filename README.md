





td
===

Исправить ошибки:
-accesstoken нужно получать проще из любой точки программы, через View как в TaskActivity
-все сообщения собрать в string
-убрать knife или везде испорльзовать
-Переписать по возможности на лямбда
-плюс переделать, см. http://developer.alexanderklimov.ru/android/android-support-design.php
-убрать логи, искать по "TAG"

Не делать сильно сложно
написать что это минимально жизнеспособный продукт MVP
итак довольно много библиотек и паттерн MVP
Написать что не сделано:
-сохранение состояния при изменении ориентации
-кэширование данных в локальное хранилище/бд

Как делать на самом гугле? апи? или локальной БД?
-через апи, так как я в начале подписался что клиент будет на андроиде
-удалить регистрацию

Перед выкладыванием на гитхаб убрать логи, искать по "TAG"

Тестовый апи http://apitdlist.dev.vladlin.ru/v1/task  demo / 123456 / demo12
Основной апи http://apitdlist.vladlin.ru/ demo1212 demo1213 demo1111

d
---
реализовать Регистрацию POST /v1/register
cоздаль модели ProfileModel ProfileDataModel
В Credentials добавить email
Изменить layout/activity_registration
Изменить ui.registration.RegistrationActivity и добавить остальные файлы
RegistrationPresenter
RegistrationInteractor
RegistrationView
commit 20190113_1714 промежуточный
Сделать все три поля обязательными
проверить demo1111
commit 
commit
commit
commit
commit
commit
commit