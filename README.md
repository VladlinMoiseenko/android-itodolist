




android как хранить токен
По заявлению Android хранить в Shared Preferences с private mode безопасно. 

http://developer.alexanderklimov.ru/android/preference.php
http://forum.alexanderklimov.ru/viewtopic.php?id=27



td
===
Пока на апи http://apitdlist.dev.vladlin.ru/v1/task 


запрос отправляется 
строка в БД создается AuthorizationCode
не могу получить результат
попробовать поменять модель на AuthorizeModel
получилось




глянуть здесь https://futurestud.io/tutorials/retrofit-send-objects-in-request-body


Сделать авторизацию в активити login
с известными данными demo 123456
POST http://apitdlist.dev.vladlin.ru/v1/authorize
в боди
  "username":"demo",
  "password":"123456"


Проверисть с данными:
{
  "username":"demo12",
  "password":"demo12"
}

Response
{
    "status": 1,
    "data": {
        "authorization_code": "d02766737a9b4135327d79c069767102",
        "expires_at": 1543477551
    }
}


переименовать movieResponse

@GET("user")
Call<User> getUser(@Header("Authorization") String authorization)


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

Перед выкладыванием на гитхаб убрать логи, искать по "TAG"
main и test удалить 
td


d
---
сделать красиво лого на телефон и фавикон на сайт itodolist
Создаю модели для authorize
Удаляю ui/login/LoginInteractor
