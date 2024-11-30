package backend.academy.model.plot;

/**
 * @param x координата по оси абсцисс
 * @param y координата по оси ординат
 */
public record Point(double x, double y) {

    public Point multiply(double weight) {
        return new Point(x * weight, y * weight);
    }

    public Point add(Point p) {
        return new Point(x + p.x(), y + p.y);
    }

    public static Point empty() {
        return new Point(Double.NaN, Double.NaN);
    }

    public static Point random() {
        return new Point(Math.random() * 2 - 1, Math.random() * 2 - 1);
    }
}
