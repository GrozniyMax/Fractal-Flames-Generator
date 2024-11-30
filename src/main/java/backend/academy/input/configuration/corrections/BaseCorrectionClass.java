package backend.academy.input.configuration.corrections;

import backend.academy.input.configuration.Modes;
import backend.academy.processing.correction.Corrector;

/**
 * Данный класс является базовым классом для коррекций. Сделана данная иерархия, чтобы отделить получение данных из json
 * от логики работы программы и сделать понятнее структуру программы
 */
public abstract class BaseCorrectionClass {

    public abstract Corrector getRealType(Modes mode);
}
