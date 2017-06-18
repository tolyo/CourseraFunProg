## Map-reduce

Мы теперь умеем создавать потоки и передавать информацию между ними.

Самое время замахнуться на Вильяма нашего Шекспира. То есть нет, не на
Шекспира, а на известный шаблон [MapReduce](https://ru.wikipedia.org/wiki/MapReduce).

Имеются несколько текстовых файлов: **data1.txt**, **data2.txt** etc. Нужно
выбрать все слова, которые содержатся в этих файлах, и посчитать
количество вхождений каждого слова.

Например, слово **"вслух"** встречается там 2 раза, а слово **"бензопила"** -- 1 раз.

Вам нужно реализовать функцию **map_reduce:start/1**, которая получает
на старте список файлов, а на выходе отдает карту, где ключи -- все
слова, встречающиеся в этих файлах, а значения -- количество вхождений
для каждого слова. Смотрите **map_reduce_test**.

Для каждого файла нужно стартовать отдельный Map-поток, который
прочитает данные из файла и посчитает слова в нем. Тут нужно также
обработать ситуацию, если заданный файл не удалось прочитать.  Далее,
нужен поток Reduce, который соберет данные из всех Map-потоков,
суммирует их, и вернет результат.