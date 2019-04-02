package com.revolv3r.gplus.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.revolv3r.gplus.dao.AlbumRepository;
import com.revolv3r.gplus.dao.PhotoRepository;
import com.revolv3r.gplus.domain.AlbumData;
import com.revolv3r.gplus.domain.PhotoData;
import com.revolv3r.gplus.service.interfaces.GplusService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.URI;

@Service
public class GplusServiceImpl implements GplusService
{
  Logger mLogger = Logger.getLogger(GplusServiceImpl.class);

  @Resource
  private AlbumRepository mAlbumRepo;

  @Resource
  private PhotoRepository mPhotoRepo;

  private UrlHelper mUrlHelper = new UrlHelper();

  private static final String GET_GOOGLE_BASE_URL = "https://get.google.com/";

  private WebDriver mWebDriver;

  public List<AlbumData> scrapeAlbumDataFromHtml()
  {
    List<AlbumData> albumList = new ArrayList<>();

    URL urlPath = this.getClass().getClassLoader().getResource("audreyAlbumHome.html");
    Elements albumMatches = null;
    try
    {
      Document doc = Jsoup.parse(new File(urlPath.toURI()), "ISO-8859-1");
      albumMatches = doc.select("div.NzRmxf");

      for (Element individualAlbum : albumMatches) {
        String title = individualAlbum.child(0).attr("aria-label");
        String thumbnail = individualAlbum.attr("data-bpu");
        String pageUrl = individualAlbum.attr("data-link");
        AlbumData albumData = new AlbumData(title, thumbnail, pageUrl);
        try
        {
          albumData.setThumbnailData(mUrlHelper.getByteDataFromUrl(thumbnail));
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        albumList.add(albumData);
        mAlbumRepo.save(albumData);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return albumList;
  }

  /**
   * Scrapes photo data from profile
   * @return
   */
  public List<AlbumData> scrapePhotoDataFromProfile()
  {
    List<AlbumData> newCompletedAlbumData = new ArrayList<>();

    mWebDriver = new ChromeDriver(getOptions());
    //first login to gplus
    mWebDriver.get("https://plus.google.com/discover");
//    WebElement loginButton = mWebDriver.findElement(By.xpath("//*[@id=\"gb_70\"]"));
//
//    //"//*[@id=\"orb-modules\"]/div[3]//button[contains(@class,'sp-c-football-scores-button')]"
//    loginButton.click();
//    pause(2);

    //fill out login form
    WebElement usernameInput = mWebDriver.findElement(By.xpath("//*[@id=\"identifierId\"]"));
    usernameInput.sendKeys("<user>");

    WebElement nextButton = mWebDriver.findElement(By.xpath("//*[@id=\"identifierNext\"]"));
    nextButton.click();
    pause(1);

    WebElement passwordInput = mWebDriver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"));
    passwordInput.sendKeys("<pass>>");

    nextButton = mWebDriver.findElement(By.xpath("//*[@id=\"passwordNext\"]"));
    nextButton.click();
    pause(1);

    for (AlbumData album : mAlbumRepo.findAll())
    {
      List<PhotoData> photos = getPhotosFromUrl(album.getPageUrl(), album.getPk());

      mPhotoRepo.saveAll(photos);
      newCompletedAlbumData.add(album);
    }

    return newCompletedAlbumData;
  }

  private ChromeOptions getOptions()
  {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("enable-automation");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-extensions");
    options.addArguments("--dns-prefetch-disable");
    options.addArguments("--disable-gpu");
    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

    return options;
  }

  public List<PhotoData> getPhotosFromUrl(String aPageUrl, int aAlbumFk)
  {
    List<PhotoData> photoList = new ArrayList<>();

    //navigate to album url
    mWebDriver.get(GET_GOOGLE_BASE_URL + aPageUrl);
    //scrollPage(20, mWebDriver);
//    pause(1);

    List<WebElement> thumbs = mWebDriver.findElements(By.cssSelector(".XmeTyb"));

    String nextButton = "initial";
    if (thumbs.size()>0)
    {
      String firstImageUniqueKey = thumbs.get(0).getAttribute("data-mk");
      mWebDriver.get(mWebDriver.getCurrentUrl()+"/"+firstImageUniqueKey);
      //scrollPage(20, mWebDriver);

      //getting thumbs
      int j = 1;

      while (nextButton!=null)
      {
        WebElement lwe = mWebDriver.findElement(By.cssSelector(".nKtIqb.GOVOFb"));

        String fullSizeImageUrl=null,fullSizeTitle=null, thumbUrl=null;
        PhotoData photo = null;

        pause(1);

        //WebElement fullImageTag = mWebDriver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz/div[2]/div/content/c-wiz/div/div[2]/div["+ (j++) +"]/img"));
        WebElement parent = lwe.findElement(By.xpath("./.."));

        String parentAria = parent.getAttribute("aria-label");
        if ("Start Video".equals(parentAria))
        {
          //found video
          fullSizeImageUrl = parent.getAttribute("data-dlu");
          thumbUrl = lwe.getAttribute("src");
        }
        else
        {
          fullSizeImageUrl = lwe.getAttribute("data-dlu");
        }

        fullSizeTitle = lwe.getAttribute("alt");

        photo = new PhotoData(fullSizeTitle, thumbUrl);
        photo.setImageUrl(fullSizeImageUrl);

        try
        {
          //get fullsized image data
          photo.setImageData(mUrlHelper.getByteDataFromUrl(fullSizeImageUrl));
          photo.setAlbum(aAlbumFk);

          photoList.add(photo);
          //go next

            WebElement nextButtonDiv = mWebDriver.findElement(By.cssSelector(".hj44f .Ce1Y1c"));
            nextButtonDiv.click();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        catch(NoSuchElementException e)
        {
          break;
        }
        catch(ElementNotVisibleException e)
        {
          break;
        }
      }
    }

    return photoList;
  }

  public void scrollPage(int counter, WebDriver aDriver)  {

    //String script = "window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));";
    String script = "window.scrollTo(0, document.body.scrollHeight)";
    int count = 0;

    while (count != counter)
    {
      ((JavascriptExecutor)aDriver).executeScript(script);

      try
      {
        Thread.sleep(500);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }

      count++;
    }
  }

  private void pause(int seconds) {
    try {
      Thread.currentThread().sleep(seconds * 500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void setWebDriver(WebDriver aWebdriver)
  {
    mWebDriver = aWebdriver;
  }
}
