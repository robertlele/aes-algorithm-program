import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class core {
        public static void main(String[] args) throws IOException {
                runAESProgram();
        }

        /**
         * Main function to run UI and scan inputs.
         * 
         * @throws IOException
         */
        private static void runAESProgram() throws IOException {
                Scanner scan = new Scanner(System.in);

                print("=====================================================");
                print("AES Encryption / Decryption Program by Robert Le");
                print("Last update: 05/03/2022");
                print("Supported Modes: ECB CBC");
                print("=====================================================\n");

                print("Enter 'E' to encrypt or 'D' to decrypt: ");
                String ED = scan.nextLine().toUpperCase();

                // encrypting
                if (ED.equals("E")) {

                        String ptFilename;
                        String keyFilename;
                        Mode mode;
                        Size size;

                        print("Enter your plain text file name (plaintext.txt): ");
                        ptFilename = scan.nextLine();
                        File ptFile = new File(ptFilename);
                        if (!ptFile.exists()) {
                                print("File does not exist! Terminating program...");
                                scan.close();
                                return;
                        }

                        print("Enter your initial key file name (key.txt): ");
                        keyFilename = scan.nextLine();
                        File keyFile = new File(keyFilename);
                        if (!keyFile.exists()) {
                                print("File does not exist! Terminating program...");
                                scan.close();
                                return;
                        }

                        String plaintext = Files.readString(ptFile.toPath());
                        String key = Files.readString(keyFile.toPath());

                        switch (key.length()) {
                                case 32:
                                        size = Size.BIT128;
                                        break;
                                case 48:
                                        size = Size.BIT192;
                                        break;
                                case 64:
                                        size = Size.BIT256;
                                        break;
                                default:
                                        print("Invalid key size! Terminating program...");
                                        scan.close();
                                        return;

                        }

                        print("Enter which mode to encrypt in (ECB CBC): ");
                        String m = scan.nextLine();
                        if (m.equals("ECB"))
                                mode = Mode.ECB;
                        else if (m.equals("CBC"))
                                mode = Mode.CBC;
                        else {
                                print("Invalid Mode! Terminating program...");
                                scan.close();
                                return;
                        }

                        print("\nEncrypting in " + mode.toString() + " mode with " + size.toString() + " key...");

                        String cipherText = aesEncryption.encryptAES(plaintext, key, mode, size);
                        print("\nCiphertext: " + cipherText);
                        scan.close();
                        // decrypting
                } else if (ED.equals("D")) {

                        String ptFilename;
                        String keyFilename;
                        Mode mode;
                        Size size;

                        print("Enter your cipher text file name (ciphertext.txt): ");
                        ptFilename = scan.nextLine();
                        File ptFile = new File(ptFilename);
                        if (!ptFile.exists()) {
                                print("File does not exist! Terminating program...");
                                scan.close();
                                return;
                        }

                        print("Enter your initial key file name (key.txt): ");
                        keyFilename = scan.nextLine();
                        File keyFile = new File(keyFilename);
                        if (!keyFile.exists()) {
                                print("File does not exist! Terminating program...");
                                scan.close();
                                return;
                        }

                        String plaintext = Files.readString(ptFile.toPath());
                        String key = Files.readString(keyFile.toPath());

                        switch (key.length()) {
                                case 32:
                                        size = Size.BIT128;
                                        break;
                                case 48:
                                        size = Size.BIT192;
                                        break;
                                case 64:
                                        size = Size.BIT256;
                                        break;
                                default:
                                        print("Invalid key size! Terminating program...");
                                        scan.close();
                                        return;

                        }

                        print("Enter which mode to decrypt in (ECB CBC): ");
                        String m = scan.nextLine();
                        if (m.equals("ECB"))
                                mode = Mode.ECB;
                        else if (m.equals("CBC"))
                                mode = Mode.CBC;
                        else {
                                print("Invalid Mode! Terminating program...");
                                scan.close();
                                return;
                        }

                        print("\nDecrypting in " + mode.toString() + " mode with " + size.toString() + " key...");

                        String cipherText = aesDecryption.decryptAES(plaintext, key, mode, size);
                        print("\nPlaintext: " + cipherText);
                        scan.close();
                } else {
                        print("Invalid input! Terminating program...");
                        scan.close();
                        return;
                }
        }

        private static void print(String msg) {
                System.out.println(msg);
        }
}
