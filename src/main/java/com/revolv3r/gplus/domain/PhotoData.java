package com.revolv3r.gplus.domain;

import javax.persistence.*;

@Entity
@Table(name = "gplus_photo_data")
public class PhotoData
{
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name ="pk")
  private int pk;

  @Column(name = "thumbnail_url")
  private String mThumbnailUrl;

  @Column(name = "image_url")
  private String mImageUrl;

  @Column(name="thumbnail_data")
  private byte[] mThumbnailData;

  @Column(name="image_data")
  private byte[] mImageData;

  @Column(name = "image_title")
  private String mTitle;

  @Column(name = "album")
  private int mAlbum;


  public PhotoData(String aTitle, String aThumb)
  {
    mTitle =aTitle;
    mThumbnailUrl = aThumb;
  }

  public String getThumbnailUrl() {
    return mThumbnailUrl;
  }

  public void setThumbnailUrl(String aThumbnailUrl) {
    this.mThumbnailUrl = aThumbnailUrl;
  }

  public String getImageUrl() {
    return mImageUrl;
  }

  public void setImageUrl(String mImageUrl) {
    this.mImageUrl = mImageUrl;
  }

  public byte[] getThumbnailData() {
    return mThumbnailData;
  }

  public void setThumbnailData(byte[] mThumbnailData) {
    this.mThumbnailData = mThumbnailData;
  }

  public byte[] getImageData() {
    return mImageData;
  }

  public void setImageData(byte[] mImageData) {
    this.mImageData = mImageData;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String mTitle) {
    this.mTitle = mTitle;
  }

  public int getAlbum() {
    return mAlbum;
  }

  public void setAlbum(int mAlbum) {
    this.mAlbum = mAlbum;
  }
}
