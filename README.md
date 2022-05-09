# CatWeather

A weather app with cats.
This is a pet project for learning and developing practice skills on Android. It's not 100% complete, there is some code I want to rewrite/refactor.

It is one-activity app with some fragments on ViewPager. Each fragment has own ViewModel.
Location is taken via Google API.
Weather JSON gets from WeatherAPI: https://www.weatherapi.com
Each type of weather has a pool of pictures with cats, from which a random image is taken.

Language: Kotlin

Used libs/feathures:
Data binding, Fragments, LiveData, Volley, CoroutineWorker, Notifications, EasyPermissions.

TODO list:
- Rewrite getting weather data with Retrofit
- Use Dagger to realise depedency injection pattern

![image](https://user-images.githubusercontent.com/14821756/167438166-dfc9c550-05b8-41f4-8d12-46828ce8c20c.png)
![image](https://user-images.githubusercontent.com/14821756/167438213-08a56056-67ae-452b-99fe-8cf3a8d85097.png)
![image](https://user-images.githubusercontent.com/14821756/167438255-862ca309-e2b9-469e-85e4-99c945aa238b.png)
![image](https://user-images.githubusercontent.com/14821756/167438314-84d39fa9-04e1-4cf7-9322-b35dff8a44f8.png)

