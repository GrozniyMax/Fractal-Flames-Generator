package backend.academy.input.configuration;

/**
 * Режимы работы приложения
 */
public enum Modes {

    /**
     * Однопоточный
     */
    SINGLE_THREAD,

    /**
     * Многопоточный
     */
    MULTI_THREAD,

    /**
     * Оптимальный(сочетает элементы предыдущих)
     */
    OPTIMAL
}
