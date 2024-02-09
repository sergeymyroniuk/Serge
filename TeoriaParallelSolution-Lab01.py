import concurrent

from PIL import Image                       # Python Imaging Library, використовується для роботи з зображеннями
import time                                 # Надає різноманітні функції, пов’язані з часом
import numpy as np                          # бібліотека для числових операцій у Python
from concurrent.futures import ThreadPoolExecutor   # частина модуля concurrent.futures для паралельної обробки
from scipy.linalg import lstsq                  # розв’язок лінійного матричного рівняння

# Функція об'єднання двох зображень вертикально з переворотом другого зображення
def glue_images_vertically_with_flip(image1, image2):  # склеює їх разом вертикально та перевертає друге зображення горизонтально перед вставленням
    width = max(image1.width, image2.width)
    height = image1.height + image2.height
    result = Image.new('RGB', (width, height), 'white')
    result.paste(image1, (0, 0))

    # Перевернути друге зображення перед приклеюванням
    image2_flipped = image2.transpose(method=Image.FLIP_LEFT_RIGHT)

    result.paste(image2_flipped, (0, image1.height))
    return result

# Функція обрізки зображення за заданими координатами та розмірами
def crop_image(image, x, y, width, height):
    return image.crop((x, y, x + width, y + height))    # обрізає зображення на основі заданих координат (x, y) і розмірів (ширина, висота)

# Перетворення зображення в матрицю чисел типу float
def image_to_matrix_float(image):
    width, height = image.size
    matrix = np.zeros((height, width), dtype=float)

    for i in range(height):                 # перетворює зображення на матрицю чисел з плаваючою комою, вилучаючи синій канал
        for j in range(width):
            color = image.getpixel((j, i))
            blue = color[2]
            matrix[i][j] = blue

    return matrix

# Перетворення зображення в вектор чисел типу float
def image_to_vector_float(image):           # перетворює зображення на вектор чисел з плаваючою комою, вилучаючи синій канал
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

# Послідовний метод розв'язання системи лінійних рівнянь
def solve_matrix_equation(matrix, vector):       # розв’язок системи лінійних рівнянь
    try:
        result_vector = np.linalg.solve(matrix, vector)
        return result_vector
    except np.linalg.LinAlgError:
        print("Matrix is singular. Cannot solve the system of linear equations.")
        return None

# Паралельний метод розв'язання системи лінійних рівнянь
def solve_matrix_equation_parallel(matrix, vector):
    try:
        result_vector = lstsq(matrix, vector)[0]
        return result_vector
    except np.linalg.LinAlgError:
        print("Matrix is singular. Cannot solve the system of linear equations.")
        return None

def main():
    try:
        # Розміри матриць, для яких буде виконано розв'язання систем
        matrix_sizes = [150, 300, 500, 1000]

        # вказуємо кількість потоків у ThreadPoolExecutor
        with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
            for size in matrix_sizes:
                # Завантаження зображення "knure.jpg"
                image_knure = Image.open("images/knure.jpg")
                start_time = time.time()

                # Об'єднання та обрізка зображень с переворотом второго изображения
                image_glued_knure = glue_images_vertically_with_flip(image_knure, image_knure)
                image_knure_matrix = crop_image(image_glued_knure, 60, 0, size, size)
                image_knure_vector = crop_image(image_glued_knure, 1163, 0, 1, size)

                # Перетворення зображень у числові представлення
                matrix_float = image_to_matrix_float(image_knure_matrix)
                vector_float = image_to_vector_float(image_knure_vector)

                # Перетворення у NumPy масиви
                matrix = np.array(matrix_float, dtype=float)
                vector = np.array(vector_float, dtype=float)

                # Розв'язання системи лінійних рівнянь (послідовний метод)
                start_time_sequential = time.time()
                result_vector = solve_matrix_equation(matrix, vector)
                end_time_sequential = time.time()
                elapsed_time_sequential = (end_time_sequential - start_time_sequential) * 1000  # у мілісекундах

                # Розв'язання системи лінійних рівнянь (паралельний метод)
                start_time_parallel = time.time()
                result_vector_parallel = executor.submit(solve_matrix_equation_parallel, matrix, vector)
                end_time_parallel = time.time()
                elapsed_time_parallel = (end_time_parallel - start_time_parallel) * 1000  # у мілісекундах

                # Виведення часу виконання
                print("Розмір матриці {}: ".format(size))
                print("Час виконання послідовного методу: {:.5f} мілісекунд".format(elapsed_time_sequential))
                print("Час виконання паралельного методу: {:.5f} мілісекунд".format(elapsed_time_parallel))

    except IOError as e:
        print(e)

if __name__ == "__main__":
    main()

