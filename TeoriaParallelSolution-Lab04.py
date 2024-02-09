import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

public class Lab004 {

    static BufferedImage glueImagesVerticallyWithFlip(BufferedImage image1, BufferedImage image2) {
        int width = Math.max(image1.getWidth(), image2.getWidth());
        int height = image1.getHeight() + image2.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = result.createGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, 0, image1.getHeight(), null);
        g.dispose();

        return result;
    }

    // New method for horizontal concatenation
    static BufferedImage glueImagesHorizontallyWithFlip(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth() + image2.getWidth();
        int height = Math.max(image1.getHeight(), image2.getHeight());
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = result.createGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, image1.getWidth(), 0, null);
        g.dispose();

        return result;
    }

    static BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height) {
        BufferedImage croppedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        croppedImage.getGraphics().drawImage(image.getSubimage(x, y, width, height), 0, 0, null);
        return croppedImage;
    }

    static double[][] imageToMatrixFloat(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][] matrix = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = image.getRGB(j, i);
                int blue = (color >> 0) & 255;
                matrix[i][j] = blue;
            }
        }

        return matrix;
    }

    static double[] imageToVectorFloat(BufferedImage image) {
        int height = image.getHeight();
        double[] vector = new double[height];

        for (int i = 0; i < height; i++) {
            int color = image.getRGB(0, i);
            int blue = (color >> 0) & 255;
            vector[i] = blue;
        }

        return vector;
    }

    static double[] solveMatrixEquationJacobi(double[][] matrix, double[] vector, int maxIterations, double tolerance) {
        int n = vector.length;
        double[] x = new double[n];
        double[] xNew = new double[n];

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (int i = 0; i < n; i++) {
                double sigma = 0.0;
                for (int j = 0; j < i; j++) {
                    sigma += matrix[i][j] * x[j];
                }
                for (int j = i + 1; j < n; j++) {
                    sigma += matrix[i][j] * x[j];
                }

                if (matrix[i][i] != 0) {
                    xNew[i] = (vector[i] - sigma) / matrix[i][i];
                } else {
                    xNew[i] = 0;
                }
            }

            if (normInfinity(xNew, x) < tolerance) {
                return xNew;
            }

            System.arraycopy(xNew, 0, x, 0, n);
        }

        return null;
    }

    static double[][] solveMatrixEquationParallelJacobi(double[][] matrix, double[] vector, int maxIterations,
                                                        double tolerance) {
        int n = vector.length;
        double[] x = new double[n];
        double[] xNew = new double[n];
        int totalIterations = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (int i = 0; i < n; i++) {
                double sigma = 0.0;
                for (int j = 0; j < i; j++) {
                    sigma += matrix[i][j] * x[j];
                }
                for (int j = i + 1; j < n; j++) {
                    sigma += matrix[i][j] * x[j];
                }

                if (matrix[i][i] != 0) {
                    xNew[i] = (vector[i] - sigma) / matrix[i][i];
                } else {
                    xNew[i] = 0;
                }
            }

            if (normInfinity(xNew, x) < tolerance) {
                return new double[][] { xNew, { (double) totalIterations } };
            }

            System.arraycopy(xNew, 0, x, 0, n);
            totalIterations++;
        }

        return null;
    }

    static double normInfinity(double[] a, double[] b) {
        double maxDiff = 0.0;
        for (int i = 0; i < a.length; i++) {
            double diff = Math.abs(a[i] - b[i]);
            if (diff > maxDiff) {
                maxDiff = diff;
            }
        }
        return maxDiff;
    }

    static class MatrixSolverTask implements Callable<Map<String, Object>> {
        private final int size;
        private final int repetitions;
        private int finalImageWidth;
        private int finalImageHeight;

        MatrixSolverTask(int size, int repetitions) {
            this.size = size;
            this.repetitions = repetitions;
        }

        @Override
        public Map<String, Object> call() throws IOException {
            BufferedImage knureImage = ImageIO.read(new File("images/knure.jpg"));
            BufferedImage gluedKnureImage = knureImage;

            // Vertically concatenate the same image 18 times
            for (int rep = 1; rep < repetitions; rep++) {
                gluedKnureImage = glueImagesVerticallyWithFlip(gluedKnureImage, knureImage);
            }

            // Horizontally concatenate the same image 18 times
            BufferedImage finalImage = gluedKnureImage;
            for (int rep = 1; rep < repetitions; rep++) {
                finalImage = glueImagesHorizontallyWithFlip(finalImage, gluedKnureImage);
            }

            finalImageWidth = finalImage.getWidth();
            finalImageHeight = finalImage.getHeight();

            // Adjust the final image dimensions to be square
            int maxSize = Math.max(finalImageWidth, finalImageHeight);
            BufferedImage adjustedImage = new BufferedImage(maxSize, maxSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = adjustedImage.createGraphics();
            g.setColor(Color.WHITE); // Set background color to white
            g.fillRect(0, 0, maxSize, maxSize);
            g.drawImage(finalImage, 0, 0, null);
            g.dispose();

            BufferedImage knureMatrixImage = cropImage(adjustedImage, 60, 0, size, size);
            BufferedImage knureVectorImage = cropImage(adjustedImage, 1163, 0, 1, size);

            double[][] matrixFloat = imageToMatrixFloat(knureMatrixImage);
            double[] vectorFloat = imageToVectorFloat(knureVectorImage);

            double[][] matrix = new double[size][size];
            double[] vector = new double[size];

            for (int i = 0; i < size; i++) {
                System.arraycopy(matrixFloat[i], 0, matrix[i], 0, size);
                vector[i] = vectorFloat[i];
            }

            int maxIter = iterationsSettings.get(size)[0];
            double tol = iterationsSettings.get(size)[1];

            long startTimeSequential = System.currentTimeMillis();
            double[] resultVector = solveMatrixEquationJacobi(matrix, vector, maxIter, tol);
            long endTimeSequential = System.currentTimeMillis();
            long elapsedSequential = endTimeSequential - startTimeSequential;

            long startTimeParallel = System.currentTimeMillis();
            double[][] resultParallel = solveMatrixEquationParallelJacobi(matrix, vector, maxIter, tol);
            long endTimeParallel = System.currentTimeMillis();
            long elapsedParallel = endTimeParallel - startTimeParallel;

            Map<String, Object> result = new HashMap<>();
            result.put("size", size);
            result.put("elapsedSequential", elapsedSequential);
            result.put("elapsedParallel", elapsedParallel);
            result.put("totalIterationsParallel", resultParallel != null ? resultParallel[1][0] : null);
            result.put("finalImageWidth", maxSize);
            result.put("finalImageHeight", maxSize);

            return result;
        }
    }

    private static final Map<Integer, int[]> iterationsSettings = new HashMap<>();

    static {
        iterationsSettings.put(1000, new int[] { 1000, 8 });
        iterationsSettings.put(5000, new int[] { 500, 8 });
        iterationsSettings.put(9000, new int[] { 200, 8 });
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            for (int size : new int[] {1000, 5000, 9000}) {
                int repetitions = 18;

                Future<Map<String, Object>> future = executorService.submit(new MatrixSolverTask(size, repetitions));
                Map<String, Object> result = future.get();

                System.out.println("Розмір матриці " + result.get("size") + ": ");
                System.out.println("Час виконання послідовного методу: " + result.get("elapsedSequential")
                        + " мілісекунд");
                System.out.println("Час виконання паралельного методу: " + result.get("elapsedParallel") + " мілісекунд");
                System.out.println("Кількість ітерацій: " + result.get("totalIterationsParallel"));

                // Print the size of the final image
                //System.out.println("Розмір склеєної картинки: " + result.get("finalImageWidth") + " x " + result.get("finalImageHeight"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
