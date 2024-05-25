import java.util.Scanner;

public class EnkripsiDekripsi {

    // Metode untuk mencari FPB
    private static int fpb(int a, int b) {
        if (b == 0)
            return a;
        return fpb(b, a % b);
    }

    // Metode untuk mencari invers mod
    // Metode ini mencari invers modular dari a dengan modulus m. Invers modular
    // diperlukan untuk proses dekripsi affine cipher.
    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

    // Metode untuk mengenkripsi teks
    public static String enkripsi(String plaintext, int a, int b) {
        if (fpb(a, 26) != 1) {
            throw new IllegalArgumentException("a dan 26 tidak coprime, enkripsi tidak mungkin dilakukan");
        }
        StringBuilder ciphertext = new StringBuilder();
        plaintext = plaintext.toLowerCase(); // Ubah teks menjadi huruf kecil
        for (int i = 0; i < plaintext.length(); i++) { // Melakukan loop untuk merubah semua plaintext menjadi
                                                       // ciphertext
            char ch = plaintext.charAt(i);
            if (Character.isLetter(ch)) {
                char base = 'a';
                ch = (char) (((a * (ch - base) + b) % 26) + base);
            }
            ciphertext.append(ch);
        }
        return ciphertext.toString(); // Mengubah menjadi bentuk ciphertext
    }

    // Metode untuk mendekripsi teks
    public static String dekripsi(String ciphertext, int a, int b) {
        int a_inv = modInverse(a, 26);
        StringBuilder plaintext = new StringBuilder();
        ciphertext = ciphertext.toLowerCase(); // Merubah semua huruf menjadi huruf kecil
        for (int i = 0; i < ciphertext.length(); i++) { // Melakukan loop untuk merubah semua huruf yang ada pada
                                                        // chipertext
            char ch = ciphertext.charAt(i);
            if (Character.isLetter(ch)) {
                char base = 'a';
                ch = (char) (((a_inv * ((ch - base - b + 26)) % 26) + base)); // Semua dimodulus 26 karena ada 26 huruf
                                                                              // dari A sampai Z
            }
            plaintext.append(ch);
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Masukkan plaintext: ");
        String plaintext = s.nextLine();
        int a = 1; // Contoh kunci a (Kunci a harus bilangan prima dari angka 0-26)
        int b = 20; // Contoh kunci b (kunci b harus berisi angka dari anka 0-25)
        // Kunci diatas menjadi acuan bagaimana hasil plain text setelah di enkripsi

        // Membuat menjadi ciphertext
        String enkripsied = enkripsi(plaintext, a, b);
        System.out.println("Teks terenkripsi: " + enkripsied);

        // Membuat menjadi plaintext kembali
        String decrypted = dekripsi(enkripsied, a, b);
        System.out.println("Teks didekripsi: " + decrypted);

    }
}
