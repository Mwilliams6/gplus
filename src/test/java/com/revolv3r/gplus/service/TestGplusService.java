package com.revolv3r.gplus.service;

import com.revolv3r.gplus.domain.PhotoData;
import com.revolv3r.gplus.service.interfaces.GplusService;
import org.hibernate.service.spi.InjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGplusService
{
  Logger mLogger = Logger.getLogger("test-dev");

  @Autowired
  private GplusService mService;

  public WebDriver mWebdriver;

  @Before
  public void setupTests()
  {
    System.setProperty("webdriver.chrome.driver", "C:\\_repo\\gplusexporter\\src\\main\\resources\\chromedriver.exe");
    mLogger.info("setting up test");
    mWebdriver = new ChromeDriver();
  }

  @Test
  public void TestLargeParse()
  {
    mLogger.info("TestingLargeParse");
    String testUrl = "albumarchive/116749500979671626219/album/AF1QipMq9a56RNjV5FPo2_-FivV3JSlV0CdV89YMcYZi";

    mService.setWebDriver(mWebdriver);
    List<PhotoData> items = mService.getPhotosFromUrl(testUrl, 1);

    //mWebdriver.get("https://www.google.com/");
  }
}
