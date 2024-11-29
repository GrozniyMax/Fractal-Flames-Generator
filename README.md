# Приложение для создания фрактального пламени

## Основные возможности:
1) Конфигурация итогового изображения происходит на основе json файла
2) Часто редактируемые параметры вынесены в аргументы командной строки
3) Возможность работы в многопоточном и одно поточном режимах ([сравнения производительности](#сравнение-производительности))

## Возможности для генерации изображений
1) Выбор применяемых трансформаций
2) Настройка применяемых трансформаций
3) Настройка пост-трансформаций
4) Настройка симметрии
5) Настройка коррекции сгенерированного изображения

## Структура json-конфигурации
### Шаблон конфигурационного файла
```json
{
    "active-variations": [], // Множество активных функций
    "functions": [], // Список доступных трансформаций
    "pipeline": {
        "mode": "...", // Режим работы(SINGLE_TREAD, MULTI_TREAD)
        "plot": {}, // Объект графика или график по умолчанию ("default")
        "image-mode": "...",
        "post-transformations": [], // Список пост-трансформаций
        "symmetry": {}, // объект симметрии
        "correction": [] // Список коррекций изображения
    }
}
```
Для более подробного описания:
* [active-variations](#вариации)
* [functions](#функции)
* [mode](#режим-работы)
* [plot](#графикplot)
* [image-mode](#тип-изображения)
* [post-transformations](#пост-эффекты)
* [symmetry](#симметрия)
* [correction](#коррекция)

### Вариации
> Принимает в себя список названий вариаций, которые будут использоваться для генерации изображения
> 
**Список всех доступных вариаций:**
1) identity 
2) sinusoidal 
3) spherical 
4) swirl 
5) horseshoe 
6) handkerchief 
7) ex 
8) cross 
9) tangent 
10) cosine


### Функции
> Список используемых
> 
**Есть 4 типа функции(значение аттрибута type)**
1) affine - линейное преобразование. Принимает в себя 6 коэффициентов
2) simple - простая трансформация. Принимает с себя аффинную трансформацию
3) weighted - суммирует влияние всех вариаций. Принимает аффинную трансформацию и массив весов
4) full - предыдущая с дополнительной аффинной пост-трансформацией. Принимает все тоже, что и предыдущая  
__**Кроме того все функции включают в себя цвет**__

### Режим работы
> Принимает ограниченный набор значений(варианты: MULTI_THREAD, SINGLE_TREAD)

### График(plot)
> Поле с нецелыми координатами, которое транслируется в изображение
>
Можно ввести ``default`` и тогда будет использовано дефолтное
**Пример**
```json
{
    "x": 1.0,
    "y": 5.0,
    "width": 2.7,
    "height": 6.5
}
```
### Тип изображения
> Определяет тип используемого цвета(ARGB/RGB)

### Пост-эффекты
> Дополнительная функция, вызываемая в конце каждого из преобразований  

Поддерживает всё из [функции](#функции), а также:
1) Умножение на число (число передается через поле coefficent)
2) Прибавление точки (точка передается через аттрибуты)

### Симметрия
> Описывает применяемую к изображению симметрию

Вариации:
1) Симметрия по оси x(``"type": "x"``)
2) Симметрия по оси y(``"type": "y"``)
3) Радиальная(``"type": "radial"``). Необходимо указать количество секторов

### Коррекция
> Описывает применяемые для улучшения изображения средства

Вариации:
1) "Глупая"(``"type":"stupid"``). Все пиксели в которых количество попаданий меньше заданного(поле hits-count) красятся в черный
2) Логарифмическая-гамма коррекция(необходимо указывать аттрибут gamma). Меняет пиксель относительно количества попаданий в него
   * Цветовая гамма коррекция(меняет цвет пикселя в зависимости от количества попаданий в пиксель)
   * "Альфа" гамма коррекция(переводит количество попаданий в прозрачность пикселя)

# Сравнение производительности
##  Сравнения производительности
| Количество итераций | Один поток | Многопоточный режим (5,5,5) | Yet Another Многопоточный режим (5, 5, 5) |
|---------------------|------------|-----------------------------|-------------------------------------------|
| 3000                | 257.2      | 274.2                       | 258                                       |
| 30 000              | 282.4      | 282                         | 296                                       |
| 300 000             | 424        | 453                         | 334.4                                     |
| 3 000 000           | 1227.3     | 2110                        | 569.2                                     |
| 30 000 000          | 8088.6     | 20753                       | 2186.2                                    |
| 100 000 000         | 27380      | проверяющий не дождался 😪  | 6671.6                                    |
__**Справка:**__
 * Многопоточный режим - точки генерируются в одном потоке, переводятся на изображение несколькими потоками
 * Точки и генерируются и переводятся на изображение разными потоками
__**Выводы:**__
 * Многопоточная реализация(любая из) не дает/дает раскрывается лишь при большом количестве вариаций
 * Многопоточный режим(default) хоть и ближе к оригинальному способу генерации, не имеет смысла, а только замедляет

## Сравнение от изменения числа потоков (Yet Another реализация)

| Количество потоков | 3 000 000 | 20 000 000 | 40 000 000 | 60 000 000 |
|--------------------|-----------|------------|------------|------------|
| (5, 5, 5)          | 569.2     | 1636       | 2842       | 4115       |
| (8, 5, 5)          | 556       | 1415       | 2379       | 3234       |
| (5, 8, 5)          | 586       | 1591       | 2893       | 4225       |
| (5, 5, 8)          | 557       | 1669       | 2934       | 3912       |
| Optimal(10, 0, 3)  | 465       | 1078.6     | 1961       | 2772       |
> Описание потоков представлено в следующем формате (потоки генерации, потоки пайп лайна, потоки записи)  

> Optimal - версия, в которой я попытался совместить все плюсы от многопоточки и однопоточки (сохраняя общее число потоков)
__**Выводы:**__
* Увеличение количества потоков на генерацию дает наиболее ощутимый прирост производительности
* Остальные части программы дают маленький прирост/не дают вовсе прироста. Имеет смысл возможно сделать их в целом одно поточными
* На основе данной таблицы, я добавлю режим optimal, в котором попытаюсь совместить все преимущества 

## Вывод
Благодаря переводу программы в многопоточную работу, в результате на 100 000 000 итераций удалось свести время работы с 27380 до 4853 миллисекунд. Прирост производительности составил 5,56 раз.  
Я уверен, что данный прирост не максимальный и можно уменьшить время работы еще больше
