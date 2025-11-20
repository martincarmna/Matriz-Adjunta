// AdjuntaMatriz.java
public class AdjuntaMatriz {

    // Imprime matriz
    public static void imprimir(double[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%10.4f ", M[i][j]);
            }
            System.out.println();
        }
    }

    // Obtiene la submatriz quitando fila p y columna q
    public static double[][] submatrix(double[][] A, int p, int q) {
        int n = A.length;
        double[][] sub = new double[n - 1][n - 1];
        int subi = 0;
        for (int i = 0; i < n; i++) {
            if (i == p) continue;
            int subj = 0;
            for (int j = 0; j < n; j++) {
                if (j == q) continue;
                sub[subi][subj] = A[i][j];
                subj++;
            }
            subi++;
        }
        return sub;
    }

    // Determinante recursivo (expansión por cofactores)
    public static double determinante(double[][] A) {
        int n = A.length;
        if (n == 1) return A[0][0];
        if (n == 2) return A[0][0] * A[1][1] - A[0][1] * A[1][0];

        double det = 0.0;
        for (int j = 0; j < n; j++) {
            double[][] sub = submatrix(A, 0, j);
            double cofactor = ((j % 2 == 0) ? 1 : -1) * determinante(sub);
            det += A[0][j] * cofactor;
        }
        return det;
    }

    // Matriz de cofactores
    public static double[][] matrizCofactores(double[][] A) {
        int n = A.length;
        double[][] C = new double[n][n];
        if (n == 1) {
            C[0][0] = 1; // por convención la adjunta de [a] es [1]
            return C;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[][] sub = submatrix(A, i, j);
                double detSub = determinante(sub);
                C[i][j] = (( (i + j) % 2 == 0) ? 1 : -1) * detSub;
            }
        }
        return C;
    }

    // Transponer matriz
    public static double[][] transponer(double[][] M) {
        int n = M.length;
        double[][] T = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                T[i][j] = M[j][i];
        return T;
    }

    // Adjunta = transpuesta(matrizCofactores(A))
    public static double[][] adjunta(double[][] A) {
        double[][] C = matrizCofactores(A);
        return transponer(C);
    }

    // Ejemplo y prueba
    public static void main(String[] args) {
        double[][] A = {
            {4, 3, 2},
            {3, 2, 1},
            {2, 1, 3}
        };

        System.out.println("Matriz A:");
        imprimir(A);

        double detA = determinante(A);
        System.out.printf("\nDeterminante de A: %.4f\n\n", detA);

        double[][] adj = adjunta(A);
        System.out.println("Adjunta (adjugate) de A:");
        imprimir(adj);

        // Si quieres la inversa (si det != 0): A^{-1} = adj(A) / det(A)
        if (Math.abs(detA) > 1e-12) {
            int n = A.length;
            double[][] inv = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    inv[i][j] = adj[i][j] / detA;
            System.out.println("\nInversa de A (usando adj/ det):");
            imprimir(inv);
        } else {
            System.out.println("\nLa matriz no es invertible (det = 0).");
        }
    }
}
