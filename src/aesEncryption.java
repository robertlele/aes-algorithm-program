public class aesEncryption {

    /**
     * Main encryption handler
     * 
     * @param plaintext plaintext to encrypt
     * @param key       initial key
     * @param mode      encryption mode
     * @param size      key size
     * @return ciphertext
     */
    public static String encryptAES(String plaintext, String key, Mode mode, Size size) {

        String output = "";

        String eKey = "";
        int rounds = 0;
        // get amount of rounds
        switch (size) {
            case BIT128:
                eKey = keyExpansion.keyExpansion128(key);
                rounds = 10;
                break;
            case BIT192:
                eKey = keyExpansion.keyExpansion192(key);
                rounds = 12;
                break;
            case BIT256:
                eKey = keyExpansion.keyExpansion256(key);
                rounds = 14;
                break;
        }

        // check encryption mode
        if (mode == Mode.ECB) {
            // loop through each block
            for (int i = 0; i * 32 < plaintext.length(); i++) {
                String block = plaintext.substring(i * 32, (i * 32) + 32);
                // loop rounds
                for (int j = 0; j < rounds; j++) {
                    String sKey = eKey.substring(j * 32, (j * 32) + 32); // get schedule key from expanded key
                    if (j != rounds - 1) {
                        // last round operations
                        block = addRoundKey(block, sKey);
                        block = subBytes(block);
                        block = shiftRows(block);
                        block = mixColumns(block);
                    } else {
                        // other round operations
                        block = addRoundKey(block, sKey);
                        block = subBytes(block);
                        block = shiftRows(block);
                        block = addRoundKey(block, eKey.substring(eKey.length() - 32));
                    }
                }
                output += block;
            }
        } else if (mode == Mode.CBC) {
            String prevCipher = "";
            // loop through each block
            for (int i = 0; i * 32 < plaintext.length(); i++) {
                String block = plaintext.substring(i * 32, (i * 32) + 32);
                // loop rounds
                for (int j = 0; j < rounds; j++) {
                    String sKey = eKey.substring(j * 32, (j * 32) + 32); // get scheduled key from expanded key
                    if (i == 0) {
                        // first block operations
                        if (j != rounds - 1) {
                            // last round operations
                            block = addRoundKey(block, sKey);
                            block = subBytes(block);
                            block = shiftRows(block);
                            block = mixColumns(block);
                        } else {
                            block = addRoundKey(block, sKey);
                            block = subBytes(block);
                            block = shiftRows(block);
                            block = addRoundKey(block, eKey.substring(eKey.length() - 32));
                        }
                    } else {
                        // other block operations
                        if (j == 0) {
                            // first round operations
                            block = addRoundKey(block, prevCipher);
                            block = addRoundKey(block, sKey);
                            block = subBytes(block);
                            block = shiftRows(block);
                            block = mixColumns(block);
                        } else if (j != rounds - 1 && j != 0) {
                            // other rounds except for last
                            block = addRoundKey(block, sKey);
                            block = subBytes(block);
                            block = shiftRows(block);
                            block = mixColumns(block);
                        } else {
                            // last round operations
                            block = addRoundKey(block, sKey);
                            block = subBytes(block);
                            block = shiftRows(block);
                            block = addRoundKey(block, eKey.substring(eKey.length() - 32));
                        }
                    }
                }
                prevCipher = block;
                output += block;
            }
        }

        return output;
    }

    /**
     * XORs the 128 bit strings
     * 
     * @param block 128 bit
     * @param key   128 bit
     * @return 128 bit string
     */
    public static String addRoundKey(String block, String key) {
        String output = "";

        for (int i = 0; i < 4; i++) {
            byte[] bytes = util.getFourBytes(block.substring(8 * i, 8 * (i + 1)));
            byte[] bytes2 = util.getFourBytes(key.substring(8 * i, 8 * (i + 1)));

            byte[] xored = util.XORBytes(bytes, bytes2);

            output += util.toStringByteArray(xored);
        }

        return output;
    }

    /**
     * Performs mixColumns on 128 bit block
     * 
     * @param block 128 bit
     * @return 128 bit string
     */
    public static String mixColumns(String block) {
        String output = "";

        // performs matrix multiplication
        for (int i = 0; i < 4; i++) {
            byte[] product = new byte[4];
            byte[] bytes = util.getFourBytes(block.substring(8 * i, 8 * (i + 1)));
            for (int j = 0; j < 4; j++) {
                int[] sum = new int[8];
                for (int k = 0; k < 4; k++) {
                    sum = util.sumPolynomials(sum,
                            util.xTimePolynomials(util.byteToBinaryArray(util.MC_MATRIX[j][k]),
                                    util.byteToBinaryArray(bytes[k])));
                }
                product[j] = (byte) Integer.parseInt(util.printArray(sum), 2);
            }
            output += util.toStringByteArray(product);
        }

        return output;
    }

    /**
     * Performs shiftRows on 128 bit block
     * 
     * @param block 128 bit
     * @return 128 bit string
     */
    public static String shiftRows(String block) {
        String output = "";

        // convert block to byte matrix
        byte[][] matrix = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String b = block.substring((i * 8) + (j * 2), (i * 8) + (j * 2) + 2);
                matrix[j][i] = (byte) (Integer.parseInt(b, 16) & 0xff);
            }
        }

        // perform rotate methods
        matrix[1] = util.rotateLeft(matrix[1]);
        matrix[2] = util.rotateLeft(util.rotateLeft(matrix[2]));
        matrix[3] = util.rotateLeft(util.rotateLeft(util.rotateLeft(matrix[3])));

        // convert matrix back to string
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                output += String.format("%02x", matrix[j][i]);
            }
        }

        return output;
    }

    /**
     * Perform subBytes on 128 bit block
     * 
     * @param block 128 bit
     * @return 128 bit string
     */
    public static String subBytes(String block) {
        String output = "";

        for (int i = 0; i < 16; i++) {
            output += String.format("%02x",
                    util.subByte((byte) (Integer.parseInt(block.substring(i * 2, i * 2 + 2), 16) & 0xff)));
        }

        return output;
    }

}
