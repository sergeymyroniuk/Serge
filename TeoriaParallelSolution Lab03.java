import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Lab03 - BoyeraMura {

    public static void main(String[] args) {
        // Шляхи до файлів
        String csvFilePath = "G:/Сергей/ХНУРЕ/3 семестр/Параллельные вычисления/Lab 03/sample_markers.csv";
        String fastaFilePath = "G:/Сергей/ХНУРЕ/3 семестр/Параллельные вычисления/Lab 03/pseudo10006.fasta";

        // Читання маркерів із CSV-файлу
        List<String> markers = readMarkersFromCSV(csvFilePath);

        // Читання послідовності з FASTA-файлу
        String sequence = readSequenceFromFasta(fastaFilePath);

        // Розмір тестового набору маркерів
        int[] markerSetSizes = {100, 1000, 10000};

        for (int setSize : markerSetSizes) {
            System.out.println("\nРозмір тестового набору маркерів: " + setSize);

            // Відібрані маркери
            List<String> selectedMarkers = markers.subList(0, setSize);

            // Послідовний пошук маркерів
            System.out.println("Послідовний пошук:");
            long startTimeSeq = System.currentTimeMillis();
            for (String marker : selectedMarkers) {
                int index = boyerMooreSearch(sequence, marker);
                if (index != -1) {
                    //System.out.println("Маркер '" + marker + "' найден по индексу " + index);
                } else {
                    //System.out.println("Маркер '" + marker + "' не найден");
                }
            }
            long endTimeSeq = System.currentTimeMillis();
            double sequentialTime = millisecondsToMinutes(endTimeSeq - startTimeSeq);
            System.out.println("Час виконання послідовного пошуку: " + sequentialTime + " хвилин");

            // Паралельний пошук маркерів
            System.out.println("\nПараллельний пошук:");
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            long startTimeParallel = System.currentTimeMillis();
            ParallelMarkerSearchTask searchTask = new ParallelMarkerSearchTask(sequence, selectedMarkers, 0, selectedMarkers.size());
            List<Integer> results = forkJoinPool.invoke(searchTask);
            long endTimeParallel = System.currentTimeMillis();
            double parallelTime = millisecondsToMinutes(endTimeParallel - startTimeParallel);

            // Вивід результатів паралельного пошуку
            for (int i = 0; i < selectedMarkers.size(); i++) {
                if (results.get(i) != -1) {
                    //System.out.println("Маркер '" + selectedMarkers.get(i) + "' найден по индексу " + results.get(i));
                } else {
                    //System.out.println("Маркер '" + selectedMarkers.get(i) + "' не найден");
                }
            }

            System.out.println("Час виконання паралельного пошуку: " + parallelTime + " хвилин");

            // Вивід прискорення та ефективності
            int numProcessors = Runtime.getRuntime().availableProcessors();
            System.out.println("Число процесорів: " + numProcessors);
            System.out.println("Прискорення для " + setSize + " маркерів: " + (sequentialTime / parallelTime));
            System.out.println("Ефективність для " + setSize + " маркерів: " + ((sequentialTime / parallelTime) / numProcessors));
        }
    }

    // Метод для читання маркерів із CSV-файлу
    private static List<String> readMarkersFromCSV(String filePath) {
        List<String> markers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                markers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return markers;
    }

    // Метод для читання послідовності з FASTA-файлу
    private static String readSequenceFromFasta(String filePath) {
        StringBuilder sequence = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(">")) {
                    sequence.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sequence.toString();
    }

    // Метод для пошуку маркера в послідовності методом Бойєра-Мура
    private static int boyerMooreSearch(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();

        int[] badChar = new int[256];

        // Заповнення масиву зсувів для поганих символів
        for (int i = 0; i < 256; i++) {
            badChar[i] = -1;
        }

        for (int i = 0; i < m; i++) {
            badChar[pattern.charAt(i)] = i;
        }

        int s = 0; // Зміщення шаблону щодо тексту
        while (s <= n - m) {
            int j = m - 1;

            // Перевірка шаблону праворуч наліво
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                // Шаблон знайдено, повертаємо індекс початку входження
                return s;
            } else {
                // Зміщення на максимум зі зміщення поганого символу та зміщення готовності
                s += Math.max(1, j - badChar[text.charAt(s + j)]);
            }
        }

        return -1; // Шаблон не знайдено
    }

    // Клас для паралельного пошуку маркерів з використанням ForkJoinPool
    private static class ParallelMarkerSearchTask extends RecursiveTask<List<Integer>> {
        private final String sequence;
        private final List<String> markers;
        private final int start;
        private final int end;

        public ParallelMarkerSearchTask(String sequence, List<String> markers, int start, int end) {
            this.sequence = sequence;
            this.markers = markers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<Integer> compute() {
            List<Integer> results = new ArrayList<>();
            if (end - start <= 1) {
                // Якщо маркерів мало, виконуємо послідовний пошук
                for (int i = start; i < end; i++) {
                    results.add(boyerMooreSearch(sequence, markers.get(i)));
                }
            } else {
                // Якщо маркерів багато, ділимо завдання на підзавдання
                int middle = (start + end) / 2;
                ParallelMarkerSearchTask leftTask = new ParallelMarkerSearchTask(sequence, markers, start, middle);
                ParallelMarkerSearchTask rightTask = new ParallelMarkerSearchTask(sequence, markers, middle, end);

                // Запускаємо підзавдання паралельно
                invokeAll(leftTask, rightTask);

                // Збираємо результати
                results.addAll(leftTask.join());
                results.addAll(rightTask.join());
            }
            return results;
        }
    }

    // Допоміжний метод для конвертації милісекунд у хвилини
    private static double millisecondsToMinutes(long milliseconds) {
        return (double) milliseconds / 60000.0;
    }
}
