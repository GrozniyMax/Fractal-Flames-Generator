package backend.academy.model.plot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class Plot {

    private final double x;
    private final double y;

    private final double width;
    private final double height;


    public Point getRandomPoint() {
        return new Point(
            x + Math.random() * width,
            y + Math.random() * height
        );
    }
}
