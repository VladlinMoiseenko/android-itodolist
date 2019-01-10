







Получить токен



td
===


Проверисть с данными:
{
  "username":"demo12",
  "password":"demo12"
}



Пока на апи http://apitdlist.dev.vladlin.ru/v1/task 
Переключить на основной апи

Иcпользовать токен  getTask(@Header("Authorization") String authorization)


При реализации Редактирования Удаления
см. в androidmvp / MainPresenter / void onItemClicked

Не делать сильно сложно
написать что это минимально жизнеспособный продукт 
итак довольно много библиотек и паттерн MVP
Написать что не сделано:
-сохранение состояния при изменении ориентации
-кэширование данных в локальное хранилище/бд

Как делать на самом гугле? апи? или локальной БД?
-через апи, так как я в начале подписался что клиент будет на андроиде
-удалить регистрацию

Перед выкладыванием на гитхаб убрать логи, искать по "TAG"


d
---
сделать красиво лого на телефон и фавикон на сайт itodolist
Создаю модели для authorize
Retrofit — Send Objects in Request Body https://futurestud.io/tutorials/retrofit-send-objects-in-request-body
Удаляю ui/login/LoginInteractor
commit 20190109_1843
Сохраняем настройки в Shared Preferences с private mode http://developer.alexanderklimov.ru/android/preference.php
Сделать модели для AccesstokenModel

