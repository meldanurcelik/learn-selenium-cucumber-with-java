package Selenium.day40;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ReadingQRCode {

    static WebDriver driver;

    public static void main(String[] args) throws NotFoundException, IOException, com.google.zxing.NotFoundException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://testautomationpractice.blogspot.com/");

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2[contains(text(),'Bar Codes')]")));

        String qrCodeURL = driver.findElement(By.xpath("//*[@id='HTML4']/div[1]/img")).getAttribute("src");

        System.out.println(qrCodeURL);

        URL url = new URL(qrCodeURL);

        BufferedImage bufferedimage = ImageIO.read(url);

        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedimage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));

        Result result = new MultiFormatReader().decode(binaryBitmap);

        System.out.println(result.getText());

        driver.close();

    }

}