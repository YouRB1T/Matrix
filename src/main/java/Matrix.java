import java.lang.reflect.Array;

public class Matrix<T> {
    protected T[][] nonTypeMatrix;

    protected boolean stringMatrix = false;
    private int rows, cols;

    public Matrix(int rows) {
        this.rows = rows;
        this.cols = rows;
        nonTypeMatrix = (T[][]) new Object[rows][rows];
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        nonTypeMatrix = (T[][]) new Object[rows][cols];
    }

    public Matrix(T[][] nonTypeMatrix) {
        this.nonTypeMatrix = nonTypeMatrix;
        this.rows = nonTypeMatrix.length;
        this.cols = nonTypeMatrix[0].length;
        stringMatrix = nonTypeMatrix instanceof String[][];
    }

    public void setInPos(int[] pos, T element) {
        if (pos.length == 2) {
            int row = pos[0];
            int col = pos[1];
            if (row >= 0 && row < rows && col >= 0 && col < cols) {
                nonTypeMatrix[row][col] = element;
            } else {
                System.out.println("Position is out of the bounds of the matrix.");
            }
        } else {
            System.out.println("Position array must have exactly two elements for row and column.");
        }
    }

    public Matrix multiplyMatrix(Matrix b) {
        if (!stringMatrix) {
                if (this.cols != b.rows) {
                    System.out.println("Error: Matrices cannot be multiplied due to incompatible dimensions.");
                    return null;
                }
                Double[][] matrix = (Double[][]) nonTypeMatrix;
                Double[][] matrixB = (Double[][]) b.nonTypeMatrix;
                Double[][] resultMatrix = new Double[this.rows][b.cols];

                for (int row = 0; row < this.rows; row++) {
                    for (int col = 0; col < b.cols; col++) {
                        resultMatrix[row][col] = 0.0;
                        for (int k = 0; k < this.cols; k++) {
                            resultMatrix[row][col] += matrix[row][k] * matrixB[k][col];
                        }
                    }
                }
                return new Matrix(resultMatrix);
        } else {
            String[][] matrix = (String[][]) nonTypeMatrix;
            String[][] matrixB = (String[][]) b.nonTypeMatrix;
            String[][] resultMatrix = new String[this.rows][b.cols];


            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < b.cols; col++) {
                    resultMatrix[row][col] = "";
                    for (int k = 0; k < this.cols; k++) {
                        if (k == 0) {
                            resultMatrix[row][col] += matrix[row][k] + "*" + matrixB[k][col];
                        } else {
                            resultMatrix[row][col] += "+" + matrix[row][k] + "*" + matrixB[k][col];
                        }
                    }
                    resultMatrix[row][col] = Formula.simplifyExpression(resultMatrix[row][col]);
                }
            }

            return new Matrix(resultMatrix);
        }
    }


    public Matrix<Double> multiplyDoubleMatrixWithVectors(Matrix<Double> b) {

            if (this.cols != b.rows) {
                System.out.println("Error: Matrices cannot be multiplied due to incompatible dimensions.");
                return null;
            }

            Double[][] resultMatrix = new Double[this.rows][b.cols];

            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < b.cols; col++) {
                    Double[] vec1 = (Double[]) getVectorFromRow(row);
                    Double[] vec2 = b.getVectorFromCol(col);
                    resultMatrix[row][col] = (Double) numFromSumOfVectors((T[]) vec1, (T[]) vec2);
                }
            }

            return new Matrix<>(resultMatrix);

    }


    public Matrix<String> multiplyStringMatrixWithVectors(Matrix<String> b) {
        if (this.cols != b.rows) {
            System.out.println("Error: Matrices cannot be multiplied due to incompatible dimensions.");
            return null;
        }

        String[][] resultMatrix = new String[this.rows][b.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < b.cols; col++) {
                String[] vec1 = (String[]) getVectorFromRow(row);
                String[] vec2 = b.getVectorFromCol(col);
                resultMatrix[row][col] = (String) numFromSumOfVectors((T[]) vec1,(T[]) vec2);
            }
        }

        return new Matrix<>((String[][]) resultMatrix);
    }

    private T numFromSumOfVectors(T[] vec1, T[] vec2) {
        if (!stringMatrix) {
            Double ans = (double) 0;
            for (int i = 0; i < cols; i++) {
                ans += (Double) vec1[i] * (Double) vec2[i];
            }
            return (T) ans;
        } else {
            String ans = "";
            for (int i = 0; i < cols; i++) {
                ans += (Double) vec1[i] * (Double) vec2[i];
            }
            ans = Formula.simplifyExpression(ans);
            return (T) ans;
        }
    }

    private T[] getVectorFromCol(int col) {
        if (!stringMatrix) {
            @SuppressWarnings("unchecked")
            T[] ans = (T[]) Array.newInstance(nonTypeMatrix[0][0].getClass(), nonTypeMatrix.length);
            for (int row = 0; row < nonTypeMatrix.length; row++) {
                ans[row] = nonTypeMatrix[row][col];
            }
            return ans;
        } else {
            return null;
        }
    }

    private T[] getVectorFromRow(int row) {
        if (!stringMatrix) {
            @SuppressWarnings("unchecked")
            T[] ans = (T[]) Array.newInstance(nonTypeMatrix[0][0].getClass(), nonTypeMatrix[row].length);
            for (int col = 0; col < nonTypeMatrix[row].length; col++) {
                ans[col] = nonTypeMatrix[row][col];
            }
            return ans;
        } else {
            return null;
        }
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(nonTypeMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Matrix addMatrices(Matrix B) {
        if (!stringMatrix) {
            int m = nonTypeMatrix.length;
            int n = nonTypeMatrix[0].length;
            Double[][] C = new Double[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] = (Double) nonTypeMatrix[i][j] + (Double) B.nonTypeMatrix[i][j];
                }
            }

            return new Matrix(C);
        } else {
            int m = nonTypeMatrix.length;
            int n = nonTypeMatrix[0].length;
            String[][] C = new String[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] = nonTypeMatrix[i][j] + "+" + B.nonTypeMatrix[i][j];
                    C[i][j] = Formula.simplifyExpression(C[i][j]);
                }
            }

            return new Matrix(C);
        }
    }

    public Matrix subtractMatrices(Matrix B) {
        if (!stringMatrix) {
            int m = nonTypeMatrix.length;
            int n = nonTypeMatrix[0].length;
            Double[][] C = new Double[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] = (Double) nonTypeMatrix[i][j] - (Double) B.nonTypeMatrix[i][j];
                }
            }

            return new Matrix(C);
        } else {
            int m = nonTypeMatrix.length;
            int n = nonTypeMatrix[0].length;
            String[][] C = new String[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] = nonTypeMatrix[i][j] + "-" + B.nonTypeMatrix[i][j];
                    C[i][j] = Formula.simplifyExpression(C[i][j]);
                }
            }

            return new Matrix(C);
        }
    }

    public Matrix powerMatrix(int n) {
        if (this.rows != this.cols) {
            System.out.println("Error: Matrix is not square.");
            return null;
        }
        Matrix base = new Matrix(nonTypeMatrix);
        if (!stringMatrix) {
            Matrix result = new Matrix(createIdentityMatrixDou(this.rows));
            while (n > 0) {
                if (n % 2 == 1) {
                    result = result.multiplyMatrix(base);
                }
                base = base.multiplyMatrix(base);
                n /= 2;
            }

            return result;
        } else {
            Matrix result = new Matrix(createIdentityMatrixStr(this.rows));
            while (n > 0) {
                if (n % 2 == 1) {
                    result = result.multiplyMatrix(base);
                }
                base = base.multiplyMatrix(base);
                n /= 2;
            }
            return result;
        }
    }

    public static Double[][] createIdentityMatrixDou(int size) {
        Double[][] identityMatrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                identityMatrix[i][j] = (i == j) ? 1.0 : 0.0;
            }
        }
        return identityMatrix;
    }
    public static String[][] createIdentityMatrixStr(int size) {
        String[][] identityMatrix = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                identityMatrix[i][j] = (i == j) ? "1" : "0";
            }
        }
        return identityMatrix;
    }

}
