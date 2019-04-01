package com.revolv3r.gplus;

import com.revolv3r.gplus.service.interfaces.GplusService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@SpringBootApplication
@Controller
public class Application extends SpringBootServletInitializer
{
  @Resource
  private GplusService mService;

  @RequestMapping("/")
  public ModelAndView get() {
    System.setProperty("webdriver.chrome.driver", this.getClass().getClassLoader().getResource("chromedriver.exe").getFile());
    return new ModelAndView("index");
  }

  @RequestMapping(value = "scrapeAlbumData")
  public ModelAndView startScrape()
  {
    //jsoup album scrape
    mService.scrapeAlbumDataFromHtml();

    return new ModelAndView("index");
  }

  @RequestMapping(value = "scrapePhotoData")
  public ModelAndView startScrapePhoto()
  {
    //selenium photo scrape
    mService.scrapePhotoDataFromProfile();

    return new ModelAndView("index");
  }

  /**
   * SpringApplication
   * @param args args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
