package com.revolv3r.gplus.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlHelper
{
  public byte[] getByteDataFromUrl(String s) throws IOException
  {
    ByteArrayOutputStream baos = null;
    InputStream is = null;
    try {
      URL urlPath = new URL(s);
      baos = new ByteArrayOutputStream();

      is = urlPath.openStream ();
      byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
      int n;

      while ( (n = is.read(byteChunk)) > 0 ) {
        baos.write(byteChunk, 0, n);
      }
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (is != null)
        is.close();
    }
    if (baos!=null)
      return baos.toByteArray();
    else
      return null;
  }
}
