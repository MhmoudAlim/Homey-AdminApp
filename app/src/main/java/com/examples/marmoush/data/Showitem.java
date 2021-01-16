
package com.examples.marmoush.data;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Showitem
{
  private String title;
  private Date updated;
  private String ownerId;
  private String ImageUrl;
  private String objectId;
  private Integer code;
  private Date created;
  private String desc;
  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getImageUrl()
  {
    return ImageUrl;
  }

  public void setImageUrl( String ImageUrl )
  {
    this.ImageUrl = ImageUrl;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Integer getCode()
  {
    return code;
  }

  public void setCode( Integer code )
  {
    this.code = code;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getDesc()
  {
    return desc;
  }

  public void setDesc( String desc )
  {
    this.desc = desc;
  }

                                                    
  public Showitem save()
  {
    return Backendless.Data.of( Showitem.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Showitem> callback )
  {
    Backendless.Data.of( Showitem.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Showitem.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Showitem.class ).remove( this, callback );
  }

  public static Showitem findById( String id )
  {
    return Backendless.Data.of( Showitem.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Showitem> callback )
  {
    Backendless.Data.of( Showitem.class ).findById( id, callback );
  }

  public static Showitem findFirst()
  {
    return Backendless.Data.of( Showitem.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Showitem> callback )
  {
    Backendless.Data.of( Showitem.class ).findFirst( callback );
  }

  public static Showitem findLast()
  {
    return Backendless.Data.of( Showitem.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Showitem> callback )
  {
    Backendless.Data.of( Showitem.class ).findLast( callback );
  }

  public static List<Showitem> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Showitem.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Showitem>> callback )
  {
    Backendless.Data.of( Showitem.class ).find( queryBuilder, callback );
  }
}