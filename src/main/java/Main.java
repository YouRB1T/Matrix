public class Main {

    public static void main(String[] args) {
        String[][] array1 = {
                {"1", "1", "1"},
                {"1", "1", "1"},
                {"1", "1", "1"}
        };

        String[][] array2 = {
                {"8 + x ** 2", "3 + x", "2"},
                {"7", "9 + x", "0"},
                {"6", "5", "4 + x"}
        };
        Matrix<String> matrix1 = new Matrix<>(array1);
        Matrix<String> matrix2 = new Matrix<>(array2);

        Matrix<String> result = matrix2.powerMatrix(3);
        result.printMatrix();
    }


}
