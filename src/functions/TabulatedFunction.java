package functions;

public class TabulatedFunction {

    private FunctionPoint[] points;
    private int size;

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (pointsCount < 2) throw new IllegalArgumentException("pointsCount must be >= 2");

        this.size = pointsCount;
        this.points = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            this.points[i] = new FunctionPoint(x, 0);
        }
    }

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        if (values.length < 2) throw new IllegalArgumentException("values length must be >= 2");

        this.size = values.length;
        this.points = new FunctionPoint[size];
        double step = (rightX - leftX) / (size - 1);

        for (int i = 0; i < size; i++) {
            double x = leftX + i * step;
            this.points[i] = new FunctionPoint(x, values[i]);
        }
    }

    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[size - 1].getX();
    }

    public double getFunctionValue(double x) {
        // Восстановлено: Проверка, находится ли x ЗА пределами границ (ИЛИ)
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;

        for (int i = 0; i < size - 1; i++) {
            if (x >= points[i].getX() && x <= points[i + 1].getX()) {
                double x0 = points[i].getX();
                double y0 = points[i].getY();
                double x1 = points[i + 1].getX();
                double y1 = points[i + 1].getY();

                // Формула линейной интерполяции
                return y0 + (y1 - y0) * (x - x0) / (x1 - x0);
            }
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return size;
    }

    public FunctionPoint getPoint(int index) {
        return new FunctionPoint(points[index]); // копия точки
    }

    public void setPoint(int index, FunctionPoint point) {
        // Восстановлено: Проверка невалидного индекса (ИЛИ)
        if (index < 0 || index >= size) return;

        double newX = point.getX();
        if (index > 0 && newX <= points[index - 1].getX()) return;
        if (index < size - 1 && newX >= points[index + 1].getX()) return;

        points[index] = new FunctionPoint(point);
    }

    public double getPointX(int index) {
        return points[index].getX();
    }

    public void setPointX(int index, double x) {
        // Восстановлено: Проверка невалидного индекса (ИЛИ)
        if (index < 0 || index >= size) return;
        if (index > 0 && x <= points[index - 1].getX()) return;
        if (index < size - 1 && x >= points[index + 1].getX()) return;
        points[index].setX(x);
    }

    public double getPointY(int index) {
        return points[index].getY();
    }

    public void setPointY(int index, double y) {
        points[index].setY(y);
    }

    public void deletePoint(int index) {
        // Восстановлено: Проверка размера массива ИЛИ невалидного индекса (ИЛИ)
        if (size <= 2 || index < 0 || index >= size) return;

        for (int i = index; i < size - 1; i++) {
            points[i] = points[i + 1];
        }
        points[size - 1] = null;
        size--;
    }

    public void addPoint(FunctionPoint point) {
        int pos = 0;
        while (pos < size && points[pos].getX() < point.getX()) pos++;

        if (size == points.length) {
            FunctionPoint[] newPoints = new FunctionPoint[size + 1];
            System.arraycopy(points, 0, newPoints, 0, pos);
            newPoints[pos] = new FunctionPoint(point);
            System.arraycopy(points, pos, newPoints, pos + 1, size - pos);
            points = newPoints;
        } else {
            for (int i = size; i > pos; i--) {
                points[i] = points[i - 1];
            }
            points[pos] = new FunctionPoint(point);
        }
        size++;
    }
}