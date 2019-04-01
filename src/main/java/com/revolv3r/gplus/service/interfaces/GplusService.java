package com.revolv3r.gplus.service.interfaces;

import com.revolv3r.gplus.domain.AlbumData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface GplusService
{
  /**
   * Retrieves albumURLS from profile
   * @param userId profile id
   * @return list of string album URLs
   */
//  List<AlbumData> retrieveAlbumsFromProfile(String userId);

  /**
   * Bypasses the processing of any outstanding jobs
   * @param aState cancellation state
   */
//  void setCancelled(boolean aState);


  List<AlbumData> scrapeAlbumDataFromHtml();

  List<AlbumData> scrapePhotoDataFromProfile();
}
