package org.pentaho.di.trans.steps.common;

import org.pentaho.di.core.injection.Injection;

public class UpdateField implements Cloneable {
  /** Field value to update after lookup */
  @Injection( name = "UPDATE_LOOKUP", group = "UPDATES" )
  private String updateLookup;

  /** Stream name to update value with */
  @Injection( name = "UPDATE_STREAM", group = "UPDATES" )
  private String updateStream;

  /** boolean indicating if field needs to be updated */
  @Injection( name = "UPDATE_FLAG", group = "UPDATES" )
  private Boolean update;

  public String getUpdateLookup() {
    return updateLookup;
  }

  public void setUpdateLookup( String updateLookup ) {
    this.updateLookup = updateLookup;
  }

  public String getUpdateStream() {
    return updateStream;
  }

  public void setUpdateStream( String updateStream ) {
    this.updateStream = updateStream;
  }

  public Boolean getUpdate() {
    return update;
  }

  public void setUpdate( Boolean update ) {
    this.update = update;
  }

  public UpdateField() {
    updateLookup = null;
  }
  @Override
  public boolean equals( Object obj ) {
    if ( this == obj ) {
      return true;
    }
    if ( obj == null ) {
      return false;
    }
    if ( getClass() != obj.getClass() ) {
      return false;
    }
    KeyField other = (KeyField) obj;
    if ( updateLookup == null ) {
      if ( other.getKeyStream() != null ) {
        return false;
      }
    } else if ( !updateLookup.equals( other.getKeyStream() ) ) {
      return false;
    }
    if ( updateStream == null ) {
      if ( other.getKeyLookup() != null ) {
        return false;
      }
    } else if ( !updateStream.equals( other.getKeyLookup() ) ) {
      return false;
    }
    if ( update == null ) {
      if ( other.getKeyCondition() != null ) {
        return false;
      }
    } else if ( !update.equals( other.getKeyCondition() ) ) {
      return false;
    }
    return true;
  }
  @Override
  public Object clone() {
    try {
      return (UpdateField) super.clone();
    } catch ( CloneNotSupportedException e ) {
      throw new RuntimeException( e );
    }
  }
}