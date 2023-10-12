public class keyExpansion {

    /**
     * Key expansion core function
     * 
     * @param b     4 byte array
     * @param round key expansion round
     * @return 4 byte array
     */
    public static byte[] keyExpansionCore(byte[] b, int round) {
        b = util.rotateLeft(b);
        for (int i = 0; i < 4; i++)
            b[i] = util.subByte(b[i]);
        b[0] = util.addRoundConstant(b[0], round - 1);
        return b;
    }

    /**
     * Key expansion method for 128 bit key
     * 
     * @param initialKey 128 bit key
     * @return expanded key
     */
    public static String keyExpansion128(String initialKey) {
        StringBuilder expansionKey = new StringBuilder(initialKey);
        int round = 1;

        // loop until expanded key length is met
        while (expansionKey.length() < 176 * 2) {
            for (int i = 0; i < 4; i++) {
                byte[] temp1 = util.getFourBytes(expansionKey.substring(expansionKey.length() - 8)); // get temp1 from
                                                                                                     // expanded key
                if (i == 0)
                    temp1 = keyExpansionCore(temp1, round); // perform expansion core on temp 1
                byte[] temp2 = util.getFourBytes(
                        expansionKey.substring(expansionKey.length() - 32, expansionKey.length() - 24)); // get temp 2
                                                                                                         // from
                                                                                                         // expanded key
                expansionKey.append(util.toStringByteArray(util.XORBytes(temp1, temp2))); // append XORed bytes to
                                                                                          // expanded key
            }
            round++;
        }

        return expansionKey.toString();
    }

    /**
     * Key expansion method for 192 bit key
     * 
     * @param initialKey 192 bit key
     * @return expanded key
     */
    public static String keyExpansion192(String initialKey) {
        StringBuilder expansionKey = new StringBuilder(initialKey);
        int round = 1;

        while (expansionKey.length() < 208 * 2) {
            for (int i = 0; i < 6; i++) {
                byte[] temp1 = util.getFourBytes(expansionKey.substring(expansionKey.length() - 8));
                if (i == 0)
                    temp1 = keyExpansionCore(temp1, round);
                byte[] temp2 = util.getFourBytes(
                        expansionKey.substring(expansionKey.length() - 48, expansionKey.length() - 40));
                expansionKey.append(util.toStringByteArray(util.XORBytes(temp1, temp2)));
            }
            round++;
        }

        return expansionKey.toString().substring(0, 2 * 208);
    }

    /**
     * Key expansion method for 256 bit key
     * 
     * @param initialKey 256 bit key
     * @return expanded key
     */
    public static String keyExpansion256(String initialKey) {
        StringBuilder expansionKey = new StringBuilder(initialKey);
        int round = 1;

        while (expansionKey.length() < 240 * 2) {
            for (int i = 0; i < 8; i++) {
                byte[] temp1 = util.getFourBytes(expansionKey.substring(expansionKey.length() - 8));
                if (i == 0)
                    temp1 = keyExpansionCore(temp1, round);
                if (i == 4) { // perform sub bytes on temp1 every 16 bit
                    for (int j = 0; j < 4; j++)
                        temp1[j] = util.subByte(temp1[j]);
                }
                byte[] temp2 = util.getFourBytes(
                        expansionKey.substring(expansionKey.length() - 64, expansionKey.length() - 56));
                expansionKey.append(util.toStringByteArray(util.XORBytes(temp1, temp2)));
            }
            round++;
        }

        return expansionKey.toString().substring(0, 2 * 240);
    }

}
