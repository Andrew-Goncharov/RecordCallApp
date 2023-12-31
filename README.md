# Проект: RecordCallApp
### Описание проекта

Мобильное приложение для устройств под операционной системой Android позволяет звонить по набранному
номеру телефона и записывать аудио файлы.

> Компания Google ввела ограничения для записи звонков сторонними приложениями
> на Android.

### Иллюстрация интерфейса

Главный экран приложения:

<img src="https://github.com/Andrew-Goncharov/RecordCallApplication/blob/master/Screenshots/main_screen.png" width="200" alt="Скриншот главного экнрана приложения">

Список доступных контактов:

<img src="https://github.com/Andrew-Goncharov/RecordCallApplication/blob/master/Screenshots/contact_screen.png" width="200" alt="Скриншот экрана со списком контактов">

### Структура проекта

Основные файлы и папки проекта расположены в директории `RecordCallApp/app/src/main/`:

- Файл AndroidManifest.xml содержит информацию для инструментов сборки Android,
  операционной системы Android и Google Play.
- В директории `RecordCallApp/app/src/main/java/com/example` расположены исходные файлы
  на языке программирования Java.
- В директории `res/layout/` находятся файлы макета пользовательского интерфейса.

### Описание функций

- Номер телефона для вызова можно ввести в соответствующее текстовое поле
  или выбрать доступный контакт.
- Для начала записи нужно нажать кнопку `START RACORDING`, для остановки - `STOP RECORDING`.
- После завершения записи адио файл сохраниться в памяти смартфона под названием
  `audioRecording.mp3`.
- Чтобы воспроизвести записанный звук нужно нажать кнопку `START PLAYING`.
  Для завершения прослушивания - `STOP PLAYING`.

