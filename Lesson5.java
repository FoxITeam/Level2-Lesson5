package ru.foxit.grayfox;

public class Lesson5 {

//    1. Необходимо написать два метода, которые делают следующее:
//    1) Создают одномерный длинный массив, например:
//
//    static final int size = 10000000;
//    static final int h = size / 2;
//    float[] arr = new float[size];
//
//    2) Заполняют этот массив единицами;
//    3) Засекают время выполнения: long a = System.currentTimeMillis();
//    4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
//    arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//    5) Проверяется время окончания метода System.currentTimeMillis();
//    6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
//
//    Отличие первого метода от второго:
//    Первый просто бежит по массиву и вычисляет значения.
//    Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
//
//    Пример деления одного массива на два:
//
//    System.arraycopy(arr, 0, a1, 0, h);
//    System.arraycopy(arr, h, a2, 0, h);
//
//    Пример обратной склейки:
//
//    System.arraycopy(a1, 0, arr, 0, h);
//    System.arraycopy(a2, 0, arr, h, h);
//
//    Примечание:
//    System.arraycopy() – копирует данные из одного массива в другой:
//    System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
//    По замерам времени:
//    Для первого метода надо считать время только на цикл расчета:
//
//      for (int i = 0; i < size; i++) {
//      arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//    }
//
//    Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.

    // Создаем переменная размер и выдаем значение 10000000
    private static final int SIZE = 10000000;
    // Создаем переменную половина размера - HALF_SIZE, где мы делим на 2.
    private static final int H_SIZE = SIZE / 2;

   //ALT + INSERT - getter and setter - но тут я их не использовал.


    // Запуск программы маин, где мы запускаем 2 метода
    public static void main(String[] s) {
        Lesson5 rune = new Lesson5();
        rune.oneRunThread();
        rune.twoRunThread();
    }


    // Метод считалка reader
    public float[] reader(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            // Проходят по всему массиву и для каждой ячейки считают новое значение по формуле
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        //Возвращает массив
        return arr;
    }


    // Первый метод
    public void oneRunThread () {
        // Создаем длинный одномерный массив
        float[] arr = new float[SIZE];
        // Условие
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        // Проверяется время окончания метода
        long a = System.currentTimeMillis();
        // Запускаем считалку
        reader(arr);
        // Отображаем в консоль
        System.out.println("Первый метод потока завершился: " + (System.currentTimeMillis() - a));
    }


    // Второй метод
    public void twoRunThread() {

        float[] arr = new float[SIZE];
        float[] arr1 = new float[H_SIZE];
        float[] arr2 = new float[H_SIZE];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        // Проверяется время окончания метода
        long a = System.currentTimeMillis();
        //Деления одного массива на два
        System.arraycopy(arr, 0, arr1, 0, H_SIZE);
        System.arraycopy(arr, H_SIZE, arr2, 0, H_SIZE);


        // Первый поток
        new Thread() {
            public void run() {
                float[] a1 = reader(arr1);
                System.arraycopy(a1, 0, arr1, 0, a1.length);
            }
        }.start();

        // Второй поток
        new Thread() {
            public void run() {
                float[] a2 = reader(arr2);
                System.arraycopy(a2, 0, arr2, 0, a2.length);
            }
        }.start();
        // Обратная склейка
        System.arraycopy(arr1, 0, arr, 0, H_SIZE);
        System.arraycopy(arr2, 0, arr, H_SIZE, H_SIZE);
        // Выводиться время работы.
        System.out.println("Второй метод потока завершился: " + (System.currentTimeMillis() - a));
    }


}
