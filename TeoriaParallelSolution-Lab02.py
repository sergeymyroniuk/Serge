from PIL import Image
import numpy as np
import time
from concurrent.futures import ThreadPoolExecutor

# Функція об'єднання двох зображень вертикально
def glue_images_vertically(image1, image2):
    width = max(image1.width, image2.width)
    height = image1.height + image2.height
    result = Image.new('RGB', (width, height), 'white')
    result.paste(image1, (0, 0))
    result.paste(image2, (0, image1.height))
    return result

# Функція обрізки зображення за заданими координатами та розмірами
def crop_image(image, x, y, width, height):
    return image.crop((x, y, x + width, y + height))

# Перетворення зображення в матрицю чисел типу float
def image_to_matrix_float(image):
    width, height = image.size
    matrix = np.zeros((height, width), dtype=float)

    for i in range(height):
        for j in range(width):
            color = image.getpixel((j, i))
            blue = color[2]
            matrix[i][j] = blue

    return matrix

# Перетворення зображення в вектор чисел типу float
def image_to_vector_float(image):
    width, height = image.size
    vector = np.zeros(height, dtype=float)

    for i in range(height):
        color = image.getpixel((0, i))
        blue = color[2]
        vector[i] = blue

    return vector

# Друк матриці
def print_matrix(matrix):
    for row in matrix:
        print(row)

# Друк вектора
def print_vector(vector):
    print(vector)

# Функція обробки матриці, встановлення нульових значень
def process_matrix(matrix):
    rows, cols = matrix.shape
    result = np.zeros((rows, cols), dtype=float)

    for i in range(rows):
        for j in range(cols):
            if i == j or abs(i - j) == 1:
                result[i][j] = matrix[i][j]

    return result

# Послідовний метод розв'язання системи лінійних рівнянь за допомогою методу прогонки
def solve_tridiagonal(matrix, vector):
    n = len(vector)
    alpha = np.zeros(n - 1)
    beta = np.zeros(n)

    for i in range(1, n - 1):
        denominator = matrix[i][i] + matrix[i][i - 1] * alpha[i - 1]
        alpha[i] = -matrix[i][i + 1] / denominator
        beta[i] = (vector[i] - matrix[i][i - 1] * beta[i - 1]) / denominator

    x = np.zeros(n)
    x[-1] = (vector[-1] - matrix[-1][-2] * beta[-2]) / (matrix[-1][-1] + matrix[-1][-2] * alpha[-2])

    for i in range(n - 2, -1, -1):
        x[i] = alpha[i] * x[i + 1] + beta[i]

    return x

# Паралельний метод розв'язання системи лінійних рівнянь за допомогою методу прогонки
def solve_tridiagonal_parallel(matrix, vector):
    n = len(vector)
    alpha = np.zeros(n - 1)
    beta = np.zeros(n)

    with ThreadPoolExecutor(max_workers=2) as executor:
        futures_alpha = {executor.submit(calculate_alpha_beta, i, matrix, alpha, beta, vector): i for i in range(1, n - 1)}
        for future in futures_alpha:
            future.result()

    x = np.zeros(n)
    x[-1] = (vector[-1] - matrix[-1][-2] * beta[-2]) / (matrix[-1][-1] + matrix[-1][-2] * alpha[-2])

    with ThreadPoolExecutor(max_workers=2) as executor:
        futures_x = {executor.submit(calculate_x, i, alpha, beta, x): i for i in range(n - 2, -1, -1)}
        for future in futures_x:
            future.result()

    return x

# Розрахунок alpha та beta для методу прогонки
def calculate_alpha_beta(i, matrix, alpha, beta, vector):
    denominator = matrix[i][i] + matrix[i][i - 1] * alpha[i - 1]
    alpha[i] = -matrix[i][i + 1] / denominator
    beta[i] = (vector[i] - matrix[i][i - 1] * beta[i - 1]) / denominator

# Розрахунок x для методу прогонки
def calculate_x(i, alpha, beta, x):
    x[i] = alpha[i] * x[i + 1] + beta[i]

try:
    matrix_sizes = [150, 300, 500, 1000]

    for size in matrix_sizes:
        # Завантаження зображення "knure.jpg"
        image_knure = Image.open("images/knure.jpg")
        start_time = time.time()

        # Об'єднання та обрізка зображень
        image_glued_knure = glue_images_vertically(image_knure, image_knure)
        image_knure_matrix = crop_image(image_glued_knure, 60, 0, size, size)
        image_knure_vector = crop_image(image_glued_knure, 1163, 0, 1, size)

        # Перетворення зображень у числові представлення
        matrix_float = image_to_matrix_float(image_knure_matrix)
        vector_float = image_to_vector_float(image_knure_vector)

        # Перетворення у NumPy масиви
        matrix = np.array(matrix_float, dtype=float)
        vector = np.array(vector_float, dtype=float)

        # Обробка матриці
        result_matrix = process_matrix(matrix)

        # Розв'язання системи лінійних рівнянь (послідовний метод)
        start_time_sequential = time.time()
        result_vector = solve_tridiagonal(result_matrix, vector)
        end_time_sequential = time.time()
        elapsed_time_sequential = (end_time_sequential - start_time_sequential) * 1000  # у мілісекундах

        # Розв'язання системи лінійних рівнянь (паралельний метод)
        start_time_parallel = time.time()
        result_vector_parallel = solve_tridiagonal_parallel(result_matrix, vector)
        end_time_parallel = time.time()
        elapsed_time_parallel = (end_time_parallel - start_time_parallel) * 1000  # у мілісекундах

        # Виведення часу виконання
        print("Розмір матриці {}: ".format(size))
        print("Час виконання послідовного методу: {} мілісекунд".format(float(elapsed_time_sequential)))
        print("Час виконання паралельного методу: {} мілісекунд".format(float(elapsed_time_parallel)))


except IOError as e:
    print(e)
