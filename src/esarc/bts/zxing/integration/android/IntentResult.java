package esarc.bts.zxing.integration.android;

public final class IntentResult {

    private static final int STRINGBUILDER = 100;
    private final String     contents;
    private final String     formatName;
    private final byte[]     rawBytes;
    private final Integer    orientation;
    private final String     errorCorrectionLevel;

    IntentResult() {
        this(null, null, null, null, null);
    }

    IntentResult(final String pContents, final String pFormatName,
            final byte[] pRawBytes, final Integer pOrientation,
            final String pErrorCorrectionLevel) {
        this.contents = pContents;
        this.formatName = pFormatName;
        this.rawBytes = pRawBytes;
        this.orientation = pOrientation;
        this.errorCorrectionLevel = pErrorCorrectionLevel;
    }

    /**
     * @return raw content of barcode
     */
    public String getContents() {
        return contents;
    }

    /**
     * @return name of format, like "QR_CODE", "UPC_A". See
     *         {@code BarcodeFormat} for more format names.
     */
    public String getFormatName() {
        return formatName;
    }

    /**
     * @return raw bytes of the barcode content, if applicable, or null
     *         otherwise
     */
    public byte[] getRawBytes() {
        return rawBytes;
    }

    /**
     * @return rotation of the image, in degrees, which resulted in a successful
     *         scan. May be null.
     */
    public Integer getOrientation() {
        return orientation;
    }

    /**
     * @return name of the error correction level used in the barcode, if
     *         applicable
     */
    public String getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    @Override
    public String toString() {
        StringBuilder dialogText = new StringBuilder(STRINGBUILDER);
        dialogText.append("Format: ").append(formatName).append('\n');
        dialogText.append("Contents: ").append(contents).append('\n');
        int rawBytesLength;
        if (rawBytes == null) {
            rawBytesLength = 0;
        } else {
            rawBytesLength = rawBytes.length;
        }
        dialogText.append("Raw bytes: (").append(rawBytesLength)
                .append(" bytes)\n");
        dialogText.append("Orientation: ").append(orientation).append('\n');
        dialogText.append("EC level: ").append(errorCorrectionLevel)
                .append('\n');
        return dialogText.toString();
    }
}
