import functions.TabulatedFunction;
import functions.FunctionPoint;

public class Main {
    public static void main(String[] args) {
        // 1. Создаём табулированную функцию f(x) = x^2 от 0 до 4 с 5 точками
        TabulatedFunction f = new TabulatedFunction(0, 4, 5);
        for (int i = 0; i < f.getPointsCount(); i++) {
            double x = f.getPointX(i);
            f.setPointY(i, x * x); // y = x^2
        }

        // 2. Выводим точки и значения функции
        System.out.println("Табулированная функция f(x) = x^2:");
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("Точка " + i + ": x=" + f.getPointX(i) + ", y=" + f.getPointY(i));
        }

        // 3. Проверяем метод getFunctionValue (интерполяция и точки вне области)
        System.out.println("\nЗначения функции в произвольных точках:");
        double[] testX = {-1, 0, 0.5, 1, 1.5, 2, 3.5, 4, 5};
        for (double x : testX) {
            double y = f.getFunctionValue(x);
            if (Double.isNaN(y)) {
                System.out.println("f(" + x + ") = не определено");
            } else {
                System.out.println("f(" + x + ") = " + y);
            }
        }

        // 4. Изменяем значение точки
        System.out.println("\nМеняем y второй точки на 100:");
        f.setPointY(1, 100);
        System.out.println("f(" + f.getPointX(1) + ") = " + f.getPointY(1));

        // 5. Добавляем новую точку
        System.out.println("\nДобавляем точку (2.5, 50):");
        f.addPoint(new FunctionPoint(2.5, 50));
        System.out.println("Количество точек после добавления: " + f.getPointsCount());

        // 6. Удаляем точку с индексом 0
        System.out.println("\nУдаляем точку с индексом 0:");
        f.deletePoint(0);
        System.out.println("Количество точек после удаления: " + f.getPointsCount());

        // 7. Выводим все точки после изменений
        System.out.println("\nВсе точки после изменений:");
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("Точка " + i + ": x=" + f.getPointX(i) + ", y=" + f.getPointY(i));
        }
    }
}