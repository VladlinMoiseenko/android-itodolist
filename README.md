




td
===

Реализовать кнопку выход с очисткой настройки accessToken и дерганьем апи GET /v1/logout

Реализзовать проверку полей "username" и "password" 


Проверисть с другими данными: "demo12" "demo12"


Тестовый апи http://apitdlist.dev.vladlin.ru/v1/task 
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
commit 20190109_2123
Получить токен после авторизации и сохранить
При запуске программв проверять, есть ли accessToken
