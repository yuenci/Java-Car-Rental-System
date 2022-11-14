package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.funtions.MatrixImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.embed.swing.SwingFXUtils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class ImageTools {
    public static Image getCircleImages(String fileUrl) {
        try {
            BufferedImage avatarImage = ImageIO.read(new URL(fileUrl));
            return circleImage(avatarImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getCircleImages(Image image) {
        BufferedImage avatarImage = SwingFXUtils.fromFXImage(image, null);
        return circleImage(avatarImage);
    }

    private static Image circleImage(BufferedImage avatarImage) {
        try {
            avatarImage = scaleByPercentage(avatarImage, avatarImage.getWidth(), avatarImage.getWidth());
            if (avatarImage == null) {
                throw new RuntimeException("avatarImage is null");
            }
            int width = avatarImage.getWidth();
            BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int border = 1;
            Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, width - border * 2);
            graphics.setClip(shape);
            graphics.drawImage(avatarImage, border, border, width - border * 2, width - border * 2, null);
            graphics.dispose();
            graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int border1 = 3;
            Stroke s = new BasicStroke(5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(s);
            graphics.setColor(Color.WHITE);
            graphics.drawOval(border1, border1, width - border1 * 2, width - border1 * 2);
            graphics.dispose();

            WritableImage wr;
            wr = new WritableImage(formatAvatarImage.getWidth(), formatAvatarImage.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < formatAvatarImage.getWidth(); x++) {
                for (int y = 0; y < formatAvatarImage.getHeight(); y++) {
                    pw.setArgb(x, y, formatAvatarImage.getRGB(x, y));
                }
            }
            return wr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) {
        try {
            int type = inputImage.getColorModel().getTransparency();
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            BufferedImage img = new BufferedImage(newWidth, newHeight, type);
            Graphics2D graphics2d = img.createGraphics();
            graphics2d.setRenderingHints(renderingHints);
            graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
            graphics2d.dispose();
            return img;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImageObjFromPath(String path) {
        File file = new File(path);
        try {
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image getUIImageObjFromName(String name) {
        String rootPath = "src/main/resources/com/example/car_rental_sys/image/UI/";
        return getImageObjFromPath(rootPath + name);
    }

    public static Image getImageObjFromUserID(int userID) {
        String avatarRoot = "src/main/resources/com/example/car_rental_sys/image/avatar/" + userID + ".png";
        File file = new File(avatarRoot);
        String path;
        if (file.exists()) {
            path = "file:" + avatarRoot;
            return new Image(path);
        } else {
            if (Objects.equals(DataTools.getGenderFromUserID(userID), "female")) {
                path = avatarRoot + "avatar_female.png";
                return new Image(path);
            }
            path = avatarRoot + "avatar_male.png";
            return getCircleImages(path);
        }
    }

    public static void setImageShape(ImageView imageView, double radius) {
        double imageWidth = imageView.getFitWidth();
        double imageHeight = imageView.getFitHeight();

        javafx.scene.shape.Rectangle rectangle = new Rectangle(imageWidth, imageHeight);
        rectangle.setArcHeight(radius);
        rectangle.setArcWidth(radius);
        imageView.setClip(rectangle);
    }

    public static void setImageShapeToCircle(ImageView imageView) {
        double imageWidth = imageView.getFitWidth();
        double imageHeight = imageView.getFitHeight();
        double circleRadius = imageHeight >= imageWidth ? imageWidth / 2 : imageHeight / 2;
        Circle circle = new Circle(circleRadius, imageWidth / 2, imageHeight / 2);
        imageView.setClip(circle);
    }

    public static Image getBadgeImage(int UserID) {
        String[] badges = {"vip1.png", "vip2.png", "vip3.png", "vip4.png", "svip.png"};
        int orderCount = DataTools.getCustomerOrderNum(UserID);

        if (orderCount <= 5) {
            return getUIImageObjFromName(badges[0]);
        } else if (orderCount <= 10) {
            return getUIImageObjFromName(badges[1]);
        } else if (orderCount <= 15) {
            return getUIImageObjFromName(badges[2]);
        } else if (orderCount <= 20) {
            return getUIImageObjFromName(badges[3]);
        } else {
            return getUIImageObjFromName(badges[4]);
        }
    }

    public static Image getVIPCardImage(int UserID) {
        String[] badges = {"vip1Card.png", "vip2Card.png", "vip3Card.png", "vip4Card.png", "svipCard.png"};
        int orderCount = DataTools.getCustomerOrderNum(UserID);

        if (orderCount <= 5) {
            return getUIImageObjFromName(badges[0]);
        } else if (orderCount <= 10) {
            return getUIImageObjFromName(badges[1]);
        } else if (orderCount <= 15) {
            return getUIImageObjFromName(badges[2]);
        } else if (orderCount <= 20) {
            return getUIImageObjFromName(badges[3]);
        } else {
            return getUIImageObjFromName(badges[4]);
        }
    }

    public static Image getAvatarFromUserID(int userID) {
        return new Image("file:src/main/resources/com/example/car_rental_sys/image/avatar/" + userID + ".png");
    }

    public static int[] getImageSize(String path) {
//        File picture = new File("src/main/resources/com/example/demos/123456.jpg");
        File picture = new File(path);
        BufferedImage sourceImg = null;
        try {
            sourceImg = ImageIO.read(new FileInputStream(picture));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{sourceImg.getWidth(), sourceImg.getHeight()};
    }

    public static boolean covertImageToPng(String inputFile, String outputFile) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(inputFile));
            ImageIO.write(bufferedImage, "png", new File(outputFile));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void  generateQRCode(ImageView imageView, String content, int width, int height) {
        try {
            Image image = getQRCode(content, width, height);
            imageView.setImage(image);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }


        System.out.println("QrcodeHtml.initialize");
    }

    private static Image getQRCode(String content, int width, int height) throws WriterException, IOException {
//        int width = 200;
//        int height = 200;
        Map<EncodeHintType, Object> encodeHints = new HashMap<EncodeHintType, Object>();
        encodeHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // L, M, Q, H
        encodeHints.put(EncodeHintType.MARGIN, 0); // default = 4 ,0-10

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, encodeHints);
        MatrixImage matrixImage = new MatrixImage(bitMatrix);
        return matrixImage.getImage();
    }

    public static void yAxisFlip(ImageView imageView, double a1, double a2) {
        Translate flipTranslation = new Translate(imageView.getImage().getWidth() - a1, 0);
        Rotate flipRotation = new Rotate(a2, Rotate.Y_AXIS);
        imageView.getTransforms().addAll(flipTranslation, flipRotation);
    }
}
