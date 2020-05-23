import com.dropbox.core.v2.DbxClientV2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread {

    public DbxClientV2 client;
    public int numberOfShots;
    final int TIME_SLEEP = 5000;

    @Override
    public void run() {
        for (int i = 1; i <= numberOfShots; i++) {
            try {
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                sleep(TIME_SLEEP);
                UploadThread uploadThread = new UploadThread();
                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "." + uploadThread.FILE_FORMAT;
                uploadThread.image = image;
                uploadThread.client = client;
                uploadThread.fileName = fileName;
                uploadThread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
