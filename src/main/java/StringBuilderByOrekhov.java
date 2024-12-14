import java.util.Stack;

/**
 * Кастомная реализация StringBuilder с функцией отмены изменений.
 * Реализует паттерн "Снимок" для сохранения состояний.
 * StringBuilder — предоставляющий изменяемую последовательность символов.
 * Удобен для создания и манипулирования строками (например, добавления, удаления, вставки символов),
 * так как работает быстрее, чем String, в случаях, когда строки часто изменяются.
 */
public class StringBuilderByOrekhov {

    private StringBuilder stringBuilder;
    private Stack<Memento> history; // Стек (коллекция) для хранения состояний строки структура данных,
                                    // работающая по принципу "Last In, First Out" — LIFO

    /**
     * Конструктор инициализирует StringBuilder и стек истории.
     */
    public StringBuilderByOrekhov() {
        this.stringBuilder = new StringBuilder();
        this.history = new Stack<>();
    }

    /**
     * Сохраняет текущее состояние строки в стек истории.
     * Этот метод вызывается перед внесением изменений в строку.
     */
    private void saveState() {
        history.push(new Memento(stringBuilder.toString()));
    }

    /**
     * Восстанавливает последнее сохранённое состояние строки из стека истории.
     * Если история пуста, выводится сообщение, и изменений не происходит.
     * Метод pop() вызывается на объекте history, который является стеком (Stack<Memento>).
     * Стек работает по принципу LIFO (Last In, First Out), то есть извлекается последний добавленный элемент.
     * Из стека history вызывается метод pop().
     * Если стек содержит элементы, pop() удаляет последний элемент и возвращает его.
     * Если стек пуст, метод выбрасывает исключение EmptyStackException.
     * Возвращённый объект (Memento) сохраняется в переменную memento.
     */
    public void undo() {
        if (!history.isEmpty()) {
            Memento memento = history.pop(); // Возвращает последний снимок из стека.
            stringBuilder = new StringBuilder(memento.getState()); // memento.getState() извлекает сохранённое состояние строки.
        } else {
            System.out.println("Невозможно отменить: история пуста.");
        }
    }

    /**
     * Добавляет указанную строку к текущей строке.
     * Сохраняет состояние перед внесением изменений.
     *
     * @param str строка для добавления
     * @return текущий экземпляр StringBuilderByOrekhov
     */
    public StringBuilderByOrekhov append(String str) {
        saveState();
        stringBuilder.append(str);
        return this;
    }

    /**
     * Удаляет символы в указанном диапазоне из текущей строки.
     * Сохраняет состояние перед внесением изменений.
     *
     * @param start начальный индекс, включительно
     * @param end конечный индекс, исключительно
     * @return текущий экземпляр StringBuilderByOrekhov
     */
    public StringBuilderByOrekhov delete(int start, int end) {
        saveState();
        stringBuilder.delete(start, end);
        return this;
    }

    /**
     * Вставляет указанную строку в заданной позиции в текущей строке.
     * Сохраняет состояние перед внесением изменений.
     *
     * @param offset позиция, где нужно вставить строку
     * @param str строка для вставки
     * @return текущий экземпляр StringBuilderByOrekhov
     */
    public StringBuilderByOrekhov insert(int offset, String str) {
        saveState();
        stringBuilder.insert(offset, str);
        return this;
    }

    /**
     * Заменяет символы в указанном диапазоне на указанную строку.
     * Сохраняет состояние перед внесением изменений.
     *
     * @param start начальный индекс, включительно
     * @param end конечный индекс, исключительно
     * @param str строка для замены
     * @return текущий экземпляр StringBuilderByOrekhov
     */
    public StringBuilderByOrekhov replace(int start, int end, String str) {
        saveState();
        stringBuilder.replace(start, end, str);
        return this;
    }

    /**
     * Возвращает текущее состояние строки.
     *
     * @return текущая строка
     */
    public String toString() {
        return stringBuilder.toString();
    }

    /**
     * Приватный статический класс, представляющий снимок состояния строки.
     */
    private static class Memento {
        private final String state;

        /**
         * Конструктор инициализирует объект Memento с заданным состоянием.
         *
         * @param state состояние строки для сохранения
         */
        public Memento(String state) {
            this.state = state;
        }

        /**
         * Возвращает сохранённое состояние.
         *
         * @return сохранённое состояние строки
         */
        public String getState() {
            return state;
        }
    }

    /**
     * Главный метод демонстрирует использование StringBuilderByOrekhov.
     *
     * @param args аргументы командной строки
     * append — это метод класса StringBuilder в Java, который используется для добавления строки или другого значения к существующей строке.
     * Этот метод изменяет текущий объект StringBuilder, вместо создания нового объекта, как это делает класс String
     */
    public static void main(String[] args) {
        StringBuilderByOrekhov sb = new StringBuilderByOrekhov();
        sb.append("Привет")
                .append(", мир!");
        System.out.println(sb); // Привет, мир!

        sb.undo();
        System.out.println(sb); // Привет

        sb.append(" Java!");
        System.out.println(sb); // Привет Java!

        sb.undo();
        System.out.println(sb); // Привет
    }
}
