package org.pentaho.di.trans.steps.common;

import org.pentaho.di.core.injection.Injection;

public class KeyField implements Cloneable {

  /** which field in input stream to compare with? */
  @Injection( name = "KEY_STREAM", group = "KEYS" )
  private String keyStream;

  /** field in table */
  @Injection( name = "KEY_LOOKUP", group = "KEYS", required = true )
  private String keyLookup;

  /** Comparator: =, <>, BETWEEN, ... */
  @Injection( name = "KEY_CONDITION", group = "KEYS", required = true )
  private String keyCondition;

  /** Extra field for between... */
  @Injection( name = "KEY_STREAM2", group = "KEYS")
  private String keyStream2;

  public KeyField() {
    this.keyStream = null;
  }

  public String getKeyStream() {
    return keyStream;
  }

  public void setKeyStream( String keyStream ) {
    this.keyStream = keyStream;
  }

  public String getKeyLookup() {
    return keyLookup;
  }

  public void setKeyLookup( String keyLookup ) {
    this.keyLookup = keyLookup;
  }

  public String getKeyCondition() {
    return keyCondition;
  }

  public void setKeyCondition( String keyCondition ) {
    this.keyCondition = keyCondition;
  }

  public String getKeyStream2() {
    return keyStream2;
  }

  public void setKeyStream2( String keyStream2 ) {
    this.keyStream2 = keyStream2;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( keyStream == null ) ? 0 : keyStream.hashCode() );
    result = prime * result + ( ( keyLookup == null ) ? 0 : keyLookup.hashCode() );
    result = prime * result + ( ( keyCondition == null ) ? 0 : keyCondition.hashCode() );
    result = prime * result + ( ( keyStream2 == null ) ? 0 : keyStream2.hashCode() );
    return result;
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
    if ( keyStream == null ) {
      if ( other.keyStream != null ) {
        return false;
      }
    } else if ( !keyStream.equals( other.keyStream ) ) {
      return false;
    }
    if ( keyLookup == null ) {
      if ( other.keyLookup != null ) {
        return false;
      }
    } else if ( !keyLookup.equals( other.keyLookup ) ) {
      return false;
    }
    if ( keyCondition == null ) {
      if ( other.keyCondition != null ) {
        return false;
      }
    } else if ( !keyCondition.equals( other.keyCondition ) ) {
      return false;
    }
    if ( keyStream2 == null ) {
      if ( other.keyStream2 != null ) {
        return false;
      }
    } else if ( !keyStream2.equals( other.keyStream2 ) ) {
      return false;
    }
    return true;
  }

  @Override
  public Object clone() {
    try {
      return (KeyField) super.clone();
    } catch ( CloneNotSupportedException e ) {
      throw new RuntimeException( e );
    }
  }
}