package ir.ac.kntu;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class BoundingBoxView extends Group {

    double orgSceneX, orgSceneY;
    Circle[] circles;
    double[] xCor;
    double[] yCor;
    Line lineTo, lineTo1, lineTo2, lineTo3;

    public void display(int sides) {

        this.getChildren().clear();
        creatP(sides, 300, 150, 100);

    }

    //creat a regular polygon based on input

    public void creatP(int sides, double centerX, double centerY, double radius) {

        circles = new Circle[sides];
        xCor = new double[sides];
        yCor = new double[sides];
        final double angleStep = Math.PI * 2 / sides;
        double angle = 0; // assumes one point is located directly beneat the center point

        circles[0] = createCircle((Math.sin(angle) * radius + centerX), (Math.cos(angle) * radius + centerY) + 100, Color.color(Math.random(), Math.random(), Math.random()));
        xCor[0] = circles[0].getCenterX();
        yCor[0] = circles[0].getCenterY();
        circles[0].toFront();
        angle += angleStep;
        this.getChildren().add(circles[0]);


        for (int i = 1; i < sides; i++, angle += angleStep) {
            circles[i] = circles[i] = createCircle((Math.sin(angle) * radius + centerX), (Math.cos(angle) * radius + centerY) + 100, Color.color(Math.random(), Math.random(), Math.random()));
            xCor[i] = circles[i].getCenterX();
            yCor[i] = circles[i].getCenterY();
            Line line = connect(circles[i - 1], circles[i]);
            this.getChildren().addAll(line, circles[i]);
            circles[i].toFront();


        }
        addLine();
        this.getChildren().addAll(connect(circles[sides - 1], circles[0]));

    }

    //connecting two circles

    private Line connect(Circle c1, Circle c2) {
        Line line = new Line();

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());
        line.setStroke(Color.YELLOW);

        return line;
    }

    //creat circle and implement handler

    private Circle createCircle(double x, double y, Color color) {

        Circle circle = new Circle(x, y, 5.0f, color);
        //circle.toFront();

        circle.setCursor(Cursor.HAND);

        circle.setOnMousePressed((t) -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Circle c = (Circle) (t.getSource());
            c.toFront();
        });
        circle.setOnMouseDragged((t) -> {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            Circle c = (Circle) (t.getSource());

            c.setCenterX(c.getCenterX() + offsetX);
            c.setCenterY(c.getCenterY() + offsetY);

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            for (int i = 0; i < circles.length; i++) {
                xCor[i] = circles[i].getCenterX();
                yCor[i] = circles[i].getCenterY();
            }
            this.getChildren().removeAll(lineTo, lineTo1, lineTo2, lineTo3);

            addLine();
            circle.toFront();
        });


        return circle;
    }

    //add lines of minimal bounding box

    public void addLine() {

        Pair pair1 = maxMin(yCor, 0, yCor.length - 1);
        Pair pair2 = maxMin(xCor, 0, xCor.length - 1);

        lineTo = new Line(pair2.min, pair1.max, pair2.max, pair1.max);
        lineTo.setStroke(Color.RED);
        lineTo1 = new Line(pair2.max, pair1.max, pair2.max, pair1.min);
        lineTo1.setStroke(Color.RED);
        lineTo2 = new Line(pair2.max, pair1.min, pair2.min, pair1.min);
        lineTo2.setStroke(Color.RED);
        lineTo3 = new Line(pair2.min, pair1.min, pair2.min, pair1.max);
        lineTo3.setStroke(Color.RED);
        this.getChildren().addAll(lineTo, lineTo1, lineTo2, lineTo3);

    }

    static class Pair {
        double min;
        double max;
    }

    public static Pair maxMin(double[] arr, int upper, int lower) {

        Pair pair = new Pair();

        //one element

        if (lower == upper) {
            pair.max = arr[lower];
            pair.min = arr[upper];
            return pair;
        }

        //two elements

        if (upper == lower + 1) {

            pair.max = Math.max(arr[lower], arr[upper]);
            pair.min = Math.min(arr[lower], arr[upper]);

            return pair;
        }

        //more than two elements

        Pair right;
        Pair left;

        int middle = (lower + upper) / 2;

        left = maxMin(arr, lower, middle);
        right = maxMin(arr, middle + 1, upper);

        //compare the result of two parts

        pair.max = Math.max(left.max, right.max);

        pair.min = Math.min(left.min, right.min);

        return pair;
    }
}
