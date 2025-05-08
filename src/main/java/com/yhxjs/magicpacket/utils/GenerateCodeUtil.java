package com.yhxjs.magicpacket.utils;

import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GenerateCodeUtil {

    private static final Random RANDOM = new Random();

    private GenerateCodeUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, Integer> createNewImg(OutputStream os) throws IOException {
        int i = RANDOM.nextInt(5);
        BufferedImage banner = resizeImage(ImageIO.read(ResourceUtils.getFile("file:image/slideCode/" + i + ".jpg")), 768, 432);
        int high = banner.getHeight();
        int width = banner.getWidth();
        Graphics2D graphics = banner.createGraphics();
        graphics.setBackground(new Color(220, 220, 220));
        int x = RANDOM.nextInt(width - 100) + 100;
        while (x >= width - 100) {
            x = RANDOM.nextInt(width - 100) + 100;
        }
        int y = RANDOM.nextInt(high - 100);
        int x1 = RANDOM.nextInt(width - 100);
        while (Math.abs(x - x1) < 100) {
            x1 = RANDOM.nextInt(width - 100);
        }
        int y1 = RANDOM.nextInt(high - 100);
        while (Math.abs(y - y1) < 100) {
            y1 = RANDOM.nextInt(high - 100);
        }
        graphics.clearRect(x, y, 100, 100);
        graphics.clearRect(x1, y1, 100, 100);
        graphics.clearRect(x + 40, y - 20, 20, 20);
        graphics.drawRect(x, y, 100, 100);
        graphics.drawRect(x1, y1, 100, 100);
        graphics.drawRect(x + 40, y - 20, 20, 20);
        graphics.dispose();
        ImageIO.write(banner, "png", os);
        Map<String, Integer> result = new HashMap<>();
        result.put("x", x / 2);
        result.put("y", y / 2);
        return result;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int x = RANDOM.nextInt(originalImage.getWidth() - targetWidth);
        int y = RANDOM.nextInt(originalImage.getHeight() - targetHeight);
        Image resultingImage = originalImage.getSubimage(x, y, targetWidth, targetHeight);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
