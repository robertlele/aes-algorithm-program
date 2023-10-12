public class util {

    // Matrix for mixColumns function
    protected static final byte[][] MC_MATRIX = { { 0x02, 0x03, 0x01, 0x01 }, { 0x01, 0x02, 0x03, 0x01 },
            { 0x01, 0x01, 0x02, 0x03 },
            { 0x03, 0x01, 0x01, 0x02 } };

    // Matrix for invMixColumns function
    protected static final byte[][] IMC_MATRIX = { { 0x0E, 0x0B, 0x0D, 0x09 }, { 0x09, 0x0E, 0x0B, 0x0D },
            { 0x0D, 0x09, 0x0E, 0x0B },
            { 0x0B, 0x0D, 0x09, 0x0E } };

    // Round constants for key expansion
    protected static final int[] roundConstant = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36 };

    // Bytes for subBytes
    protected static final int[] sbox = { 0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe,
            0xd7, 0xab, 0x76,
            0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
            0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
            0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
            0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
            0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
            0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
            0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
            0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
            0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
            0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
            0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
            0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
            0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
            0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16 };

    /**
     * Helper method to rotate array elements left
     * 
     * @param bytes byte array of length 4
     * @return byte array
     */
    public static byte[] rotateLeft(byte[] bytes) {
        byte temp = bytes[0];
        bytes[0] = bytes[1];
        bytes[1] = bytes[2];
        bytes[2] = bytes[3];
        bytes[3] = temp;
        return bytes;
    }

    /**
     * Helper method to rotate array elements right
     * 
     * @param bytes byte array of length 4
     * @return byte array
     */
    public static byte[] rotateRight(byte[] bytes) {
        byte temp = bytes[3];
        bytes[3] = bytes[2];
        bytes[2] = bytes[1];
        bytes[1] = bytes[0];
        bytes[0] = temp;
        return bytes;
    }

    /**
     * Helper method to perform subByte on a byte
     * 
     * @param b byte
     * @return byte
     */
    public static byte subByte(byte b) {
        return (byte) sbox[Byte.toUnsignedInt(b)];
    }

    /**
     * Helper method to perform invSubByte on a byte
     * 
     * @param b byte
     * @return byte
     */
    public static byte invSubByte(byte b) {
        // reverse lookup
        for (int i = 0; i < sbox.length; i++) {
            if ((byte) sbox[i] == b) {
                return (byte) (i & 0xff);
            }
        }
        return (byte) 0;
    }

    /**
     * Helper method to add round constant to byte
     * 
     * @param b     byte
     * @param round round
     * @return byte
     */
    public static byte addRoundConstant(byte b, int round) {
        int newByte = b ^ roundConstant[round];
        return (byte) newByte;
    }

    /**
     * Helper method to perform XOR on byte array
     * 
     * @param b1 byte array of length 4
     * @param b2 byte array of length 4
     * @return byte array
     */
    public static byte[] XORBytes(byte[] b1, byte[] b2) {
        byte[] newBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            newBytes[i] = (byte) (b1[i] ^ b2[i]);
        }
        return newBytes;
    }

    /**
     * Helper method to get byte array from string representation
     * 
     * @param s 8 bit string
     * @return byte array of length 4
     */
    public static byte[] getFourBytes(String s) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (Integer.parseInt(s.substring(0, 2), 16) & 0xff);
        bytes[1] = (byte) (Integer.parseInt(s.substring(2, 4), 16) & 0xff);
        bytes[2] = (byte) (Integer.parseInt(s.substring(4, 6), 16) & 0xff);
        bytes[3] = (byte) (Integer.parseInt(s.substring(6, 8), 16) & 0xff);
        return bytes;
    }

    /**
     * Helper method to convert byte array to string representation
     * 
     * @param b byte array
     * @return string
     */
    public static String toStringByteArray(byte[] b) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            s.append(String.format("%02x", b[i]));
        }
        return s.toString();
    }

    /**
     * Takes two arrays of coefficients of polynomials [c0,c1,c2,c3,c4,c5,c6,c7]
     * and returns an array representing the sum of the polynomials. 1 + x^2 + x^5
     * 
     * @param p1 Integer array of size 8 consisting of 0s and 1s.
     * @param p2 Integer array of size 8 consisting of 0s and 1s.
     * @return Integer array of size 8 representing the sum of the coefficients of
     *         the inputs.
     */
    public static int[] sumPolynomials(int[] p1, int[] p2) {
        int[] sum = new int[8];

        for (int i = 0; i < 8; i++) {
            sum[i] = (p1[i] + p2[i]) % 2;
        }

        return sum;
    }

    /**
     * Takes two arrays of coefficients of polynomials [c0,c1,c2,c3,c4,c5,c6,c7]
     * and returns an array representing the product of the polynomials.
     * 
     * @param p1 Integer array of size 8 consisting of 0s and 1s.
     * @param p2 Integer array of size 8 consisting of 0s and 1s.
     * @return Integer array up to size 15 consisting of 0s and 1s.
     */
    public static int[] productPolynomials(int[] p1, int[] p2) {

        int[] product = null;
        boolean sized = false;

        for (int i = 7; i >= 0; i--) {
            if (p1[i] == 1) {
                for (int j = 7; j >= 0; j--) {
                    if (p2[j] == 1) {
                        if (!sized) {
                            product = new int[i + j + 1];
                            sized = true;
                        }
                        product[i + j] += 1;
                    }
                }
            }
        }

        for (int i = 0; i < product.length; i++)
            product[i] %= 2;

        return product;
    }

    /**
     * Method to perform xTime function on two arrays of coefficients of polynomials
     * 
     * @param p1 Integer array of size 8 consisting of 0s and 1s.
     * @param p2 Integer array of size 8 consisting of 0s and 1s.
     * @return Integer array up to size 8 consisting of 0s and 1s.
     */
    public static int[] xTimePolynomials(int[] p1, int[] p2) {
        int[] AES_SPEC = { 1, 1, 0, 1, 1, 0, 0, 0, 1 };

        int[][] polynomials = new int[util.getHighestDegree(p2) + 1][8];

        for (int i = 0; i < polynomials.length; i++) {
            if (i == 0)
                polynomials[i] = p1;
            else {
                int[] tempP = util.leftShiftPolynomial(polynomials[i - 1]);
                if (util.getHighestDegree(tempP) == 8) {
                    tempP = util.XORPolynomials(tempP, AES_SPEC);
                }
                if (tempP.length == 9) {
                    for (int j = 0; j < 8; j++) {
                        polynomials[i][j] = tempP[j];
                    }
                } else
                    polynomials[i] = tempP;
            }
        }

        int[] result = new int[8];

        for (int i = 0; i < polynomials.length; i++) {
            if (p2[i] == 1) {
                for (int j = 0; j < polynomials[i].length; j++) {
                    result[j] = (result[j] + polynomials[i][j]) % 2;
                }
            }
        }

        return result;
    }

    private static int getHighestDegree(int[] p) {
        for (int i = p.length - 1; i >= 0; i--) {
            if (p[i] == 1)
                return i;
        }
        return 0;
    }

    private static int[] leftShiftPolynomial(int[] p) {
        int[] result = new int[p.length + 1];
        for (int i = p.length; i >= 1; i--)
            result[i] = p[i - 1];
        return result;
    }

    private static int[] XORPolynomials(int[] p1, int[] p2) {
        int[] result = null;

        if (p1.length > p2.length) {
            result = new int[p1.length];
            for (int i = 0; i < result.length; i++) {
                if (i < p2.length)
                    result[i] = (p1[i] + p2[i]) % 2;
                else
                    result[i] = p1[i];
            }
        } else {
            result = new int[p2.length];
            for (int i = 0; i < result.length; i++) {
                if (i < p1.length)
                    result[i] = (p1[i] + p2[i]) % 2;
                else
                    result[i] = p2[i];
            }
        }

        return result;
    }

    public static int[] byteToBinaryArray(byte b) {
        String binary = String.format("%16s", Integer.toBinaryString(b)).replace(' ', '0');
        binary = binary.substring(binary.length() - 8);

        int[] arr = new int[8];
        for (int i = 0; i < 8; i++) {
            arr[i] = Integer.parseInt(binary.substring(7 - i, 8 - i));
        }

        return arr;
    }

    public static String printArray(int[] array) {
        StringBuilder b = new StringBuilder();
        for (int i = array.length - 1; i >= 0; i--) {
            b.append(array[i]);
        }
        return b.toString();
    }

}
