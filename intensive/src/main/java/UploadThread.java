import com.dropbox.core.v2.DbxClientV2;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UploadThread extends Thread {

    public BufferedImage image;
    public DbxClientV2 client;
    public String fileName;
    @Getter
    public final String FILE_FORMAT = "png";

    @Override
    public void run() {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, FILE_FORMAT, baos);
            client.files().uploadBuilder("/" + fileName)
                    .uploadAndFinish(new ByteArrayInputStream(baos.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
