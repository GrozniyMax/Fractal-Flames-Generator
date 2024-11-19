package backend.academy.output.cli;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class ProgressBar {

    private final int maxIterations;
    private final int updateEvery;
    private final int length;
    private int currentN = 0;

    public void update(int currentIteration) {
        // todo дописать
        // Переводим из 0-количество итераций в 0-длинна прогресс бара
        // обновляем счетчик текущего n
        // если оно, то выводим прогресс бар
        // если нет, то ничего не делаем
    }

}
