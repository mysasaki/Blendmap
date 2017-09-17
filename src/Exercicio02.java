import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Mylla on 14/09/2017.
 *
 * EXERCICIO 02.
 * GERE A BLENDMAP PARA TEXTURAS LENDO UM ARQUIVO HEIGHTMAP
 */
public class Exercicio02 {
    public static void run () throws IOException {
        String PATH = "C:/prog3d-img/opengl/heights";
        BufferedImage img = ImageIO.read(new File(PATH, "river.png"));
        BufferedImage imgrgb = heightToRGB(img);

        ImageIO.write(imgrgb, "png", new File("blendmap.png"));
        System.out.println("ok");
    }

    public static BufferedImage heightToRGB(BufferedImage img) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        int maxHeight = 0;

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int tone = new Color(img.getRGB(x, y)).getRed();
                if (tone > maxHeight) {
                    maxHeight = tone;
                }
            }
        }

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int tone = new Color(img.getRGB(x, y)).getRed();

                float h = tone / (float) maxHeight;

                float r = calcLinear(0.66f,1.0f, h, false);
                float g = calcPiramid(0.33f, 0.68f, h);
                float b = calcLinear(0.00f, 0.35f, h, true);

                Color outColor = new Color(r, g, b);
                out.setRGB(x, y, outColor.getRGB());
            }
        }

        return out;
    }

    public static float calcLinear(float min, float max,
                                    float value, boolean inverse) {
        float range = max - min;
        float result = (value - min) / range;
        result = result < 0 ? 0 :
                (result > 1 ? 1 : result);
        return inverse ? 1 - result : result;
    }

    public static float calcPiramid(float min, float max,
                                     float value) {
        float mid = (min + max) / 2.0f;
        return value <= mid ?
                calcLinear(min, mid, value, false) :
                calcLinear(mid, max, value, true);
    }

    public static void main(String[] arg) throws IOException {
        new Exercicio02().run();
    }
}
