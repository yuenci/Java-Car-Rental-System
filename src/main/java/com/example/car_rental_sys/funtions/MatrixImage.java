package com.example.car_rental_sys.funtions;

import com.google.zxing.common.BitMatrix;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Helper class for converting a com.google.zxing.common.BitMatrix into an Image object instance in JavaFX.
 */
public class MatrixImage extends WritableImage {

    /**
     * The base BitMatrix
     */
    private BitMatrix bitMatrix;

    /**
     * Color for dark (set) pixels
     */
    private Color darkColor;

    /**
     * Color for light (unset) pixels
     */
    private Color lightColor;

    /**
     * Generates a new object instance for MatrixImage from a BitMatrix.
     *
     * @param bitMatrix The base BitMatrix
     */
    public MatrixImage(BitMatrix bitMatrix) {
        super(bitMatrix.getWidth(), bitMatrix.getHeight());

        this.bitMatrix = bitMatrix;
        this.darkColor = Color.BLACK;
        this.lightColor = Color.WHITE;
    }

    /**
     * Generates a new object instance for MatrixImage from a BitMatrix. Set the darkColor
     * and lightColor to specify the color scheme of the resulting graphic.
     *
     * @param bitMatrix The base BitMatrix
     * @param darkColor The color for dark (set) pixels
     * @param lightColor The color for light (unset) pixels
     */
    public MatrixImage(BitMatrix bitMatrix, Color darkColor, Color lightColor) {
        super(bitMatrix.getWidth(), bitMatrix.getHeight());

        this.bitMatrix = bitMatrix;
        this.darkColor = darkColor;
        this.lightColor = lightColor;
    }

    /**
     * Generates an Image instance from the BitMatrix.
     *
     * @return The resulting Image instance
     */
    public Image getImage() {
        PixelWriter pw = this.getPixelWriter();
        for (int x = 0; x < this.bitMatrix.getWidth(); x++) {
            for (int y = 0; y < this.bitMatrix.getHeight(); y++) {
                if (this.bitMatrix.get(x, y)) {
                    pw.setColor(x, y, this.darkColor);
                } else {
                    pw.setColor(x, y, this.lightColor);
                }
            }
        }

        return new ImageView(this).getImage();
    }
}