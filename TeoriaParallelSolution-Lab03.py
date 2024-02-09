import concurrent.futures
import time

def read_markers_from_csv(file_path):
    markers = []
    with open(file_path, 'r') as file:
        for line in file:
            markers.append(line.strip())
    return markers

def read_sequence_from_fasta(file_path):
    sequence = ""
    with open(file_path, 'r') as file:
        for line in file:
            if not line.startswith(">"):
                sequence += line.strip()
    return sequence

def boyer_moore_search(text, pattern):
    m = len(pattern)
    n = len(text)

    bad_char = [-1] * 256

    for i in range(m):
        bad_char[ord(pattern[i])] = i

    s = 0
    while s <= n - m:
        j = m - 1

        while j >= 0 and pattern[j] == text[s + j]:
            j -= 1

        if j < 0:
            return s
        else:
            s += max(1, j - bad_char[ord(text[s + j])])

    return -1

def parallel_marker_search(sequence, markers, batch_size=10):
    results = []

    with concurrent.futures.ProcessPoolExecutor() as executor:
        marker_chunks = [markers[i:i + batch_size] for i in range(0, len(markers), batch_size)]
        futures = []

        for chunk in marker_chunks:
            future = executor.submit(process_marker_chunk, sequence, chunk)
            futures.append(future)

        for future in concurrent.futures.as_completed(futures):
            results.extend(future.result())

    return results

def process_marker_chunk(sequence, marker_chunk):
    chunk_results = []

    for marker in marker_chunk:
        index = boyer_moore_search(sequence, marker)
        chunk_results.append(index)

    return chunk_results

def milliseconds_to_minutes(milliseconds):
    return milliseconds / 60000.0

if __name__ == "__main__":
    csv_file_path = "G:/Сергей/ХНУРЕ/3 семестр/Параллельные вычисления/Lab 03/sample_markers.csv"
    fasta_file_path = "G:/Сергей/ХНУРЕ/3 семестр/Параллельные вычисления/Lab 03/pseudo10006.fasta"

    markers = read_markers_from_csv(csv_file_path)
    sequence = read_sequence_from_fasta(fasta_file_path)

    marker_set_sizes = [100, 1000, 10000]

    for set_size in marker_set_sizes:
        print("\nРозмір тестового набору маркерів:", set_size)

        selected_markers = markers[:set_size]

        print("Послідовний пошук:")
        start_time_seq = time.time()
        for marker in selected_markers:
            index = boyer_moore_search(sequence, marker)
        end_time_seq = time.time()
        seq_execution_time = milliseconds_to_minutes((end_time_seq - start_time_seq) * 1000)
        print("Час виконання послідовного пошуку:", seq_execution_time, "хвилин")

        print("\nПаралельний пошук:")
        start_time_parallel = time.time()
        results = parallel_marker_search(sequence, selected_markers)
        end_time_parallel = time.time()
        parallel_execution_time = milliseconds_to_minutes((end_time_parallel - start_time_parallel) * 1000)

        parallel_execution_time = milliseconds_to_minutes((end_time_parallel - start_time_parallel) * 1000)
        print("Час виконання паралельного пошуку:", parallel_execution_time, "хвилин")

        num_processors = 4  # Замініть це значення на кількість доступних процесорів на вашій системі
        print("Число процесорів:", num_processors)
        print("Прискорення для", set_size, "маркерів:", (seq_execution_time / parallel_execution_time))
        print("Ефективність для", set_size, "маркерів:", ((seq_execution_time / parallel_execution_time) / num_processors))






