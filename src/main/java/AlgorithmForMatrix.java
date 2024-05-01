import java.util.ArrayList;
import java.util.List;

public class AlgorithmForMatrix extends Matrix {
    public AlgorithmForMatrix(Object[][] nonTypeMatrix) {
        super(nonTypeMatrix);
    }

    private boolean isSquare = (nonTypeMatrix.length == nonTypeMatrix[1].length) ? true : false;

    private Double[] takeColum(int numCol) {
        Double[] col = new Double[nonTypeMatrix.length];
        for (int i = 0; i < nonTypeMatrix.length; i++) {
            col[i] = (Double) nonTypeMatrix[i][numCol];
        }
        return col;
    }

    public void toUpperTriangular() {
        if (isSquare) {
            int n = nonTypeMatrix.length;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    Double[] tempCol = takeColum(i);
                    double factor = tempCol[j] / ((Double) nonTypeMatrix[j][j]);

                    for (int k = 0; k < n; k++) {
                        nonTypeMatrix[i][k] = (Double) nonTypeMatrix[i][k]
                                - factor * (Double) nonTypeMatrix[j][k];
                    }
                }
            }
        } else {
            System.out.println("This is not square matrix");
        }
    }

    public void toLowerTriangular() {
        if (isSquare) {

            for (int col = 0; col < nonTypeMatrix[0].length - 1; col++) { // start col
                int row = col;
                for (int currCol = col + 1; currCol < nonTypeMatrix[0].length; currCol++) {
                    Double k = (Double) nonTypeMatrix[row][currCol] / (Double) nonTypeMatrix[row][col];
                        for (int currRow = row; currRow < nonTypeMatrix.length; currRow++) {

                            nonTypeMatrix[currRow][currCol] = (Double) nonTypeMatrix[currRow][currCol] -
                                    (Double) nonTypeMatrix[currRow][col] * k;
                        }

                }
            }
        } else {
            System.out.println("This is not square matrix");
        }
    }

    public void partiallyTriangularMatrix() {
        for (int row = 0; row < Math.min(nonTypeMatrix.length, nonTypeMatrix[0].length); row++){
            int col = row;
            for (int currRow = row + 1; currRow < nonTypeMatrix.length; currRow++) {
                Double k = (Double) nonTypeMatrix[currRow][col] / (Double) nonTypeMatrix[row][col];
                for (int currCol = col; currCol < nonTypeMatrix[0].length; currCol++) {
                    nonTypeMatrix[currRow][currCol] = (Double) nonTypeMatrix[currRow][currCol] -
                            (Double) nonTypeMatrix[currRow][col] * k;
                }
            }
        }
    }

    public double determine() {
        double determinant = 1.0;
        if (stringMatrix) {

        } else {
            for(int i = 0; i < nonTypeMatrix.length; i++) {
                determinant *= (Double) nonTypeMatrix[i][i];
            }
        }
        return determinant;
    }

    public double calculateDeterminant(Double[][] matrix) {
        if (matrix.length == 1) {
            return (double) matrix[0][0];
        }
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        double determinant = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            determinant += Math.pow(-1, i) * matrix[0][i] * calculateDeterminant(getMinor(matrix, 0, i));
        }
        return determinant;
    }

    public int calculateRankFromLines() {
        int rank = 0;
        if (stringMatrix) {

        } else {
            for (Double[] row : (Double[][]) nonTypeMatrix) {
                for (double element : row) {
                    if (element != 0) {
                        rank++;
                        break;
                    }
                }
            }
        }
        return rank;
    }

    public int calculateRankFromMinors() {
        int n = nonTypeMatrix.length;
        int m = nonTypeMatrix[0].length;
        int rank = Math.min(n, m);

        for (int i = 0; i < rank; i++) {
            boolean nonZeroMinorFound = false;

            for (int row = 0; row < n - i; row++) {
                for (int col = 0; col < m - i; col++) {
                    Double[][] minor = getMinor((Double[][]) nonTypeMatrix, row, col);
                    double determinant = calculateDeterminant(minor);

                    System.out.println("Minor (" + (row+1) + ", " + (col+1) + "):");
                    Matrix matrix = new Matrix(minor);
                    matrix.printMatrix();

                    if (determinant != 0) {
                        nonZeroMinorFound = true;
                    }
                    if (nonZeroMinorFound) {
                        break;
                    }
                }
                if (nonZeroMinorFound) {
                    break;
                }
            }

            if (!nonZeroMinorFound) {
                rank = i;
                break;
            }
        }

        return rank;
    }

    public static Double[][] getMinor(Double[][] matrix, int rowToRemove, int colToRemove) {
        Double[][] minor = new Double[matrix.length - 1][matrix[0].length - 1];
        List<Double[]> rows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            if (i != rowToRemove) {
                rows.add(matrix[i]);
            }
        }

        for (int i = 0; i < rows.size(); i++) {
            int colIndex = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if (j != colToRemove) {
                    minor[i][colIndex++] = rows.get(i)[j];
                }
            }
        }

        return minor;
    }

    public void inverseMatrix(){

    }
}