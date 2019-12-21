package me.varmetek.plugin.prisonminerefill.util;

import com.google.common.base.Preconditions;

/**
 * Holds an immutable string to be used
 * when case insensitive keys need to be used in Maps
 * */
public final class StringKey
{

  private final String key;


  /**
   * Constructs the key
   *
   * @param  key the string to be used as a key
   * */
  public StringKey (String key){
    this.key = Preconditions.checkNotNull(key,"Key cannot be null").toLowerCase();
  }


  /**
   * Returns the lowercase internal string key
   * @return String key
   * */
  @Override
  public String toString(){
    return key;
  }

  @Override
  public int hashCode (){
    return key.hashCode();
  }

  @Override
  public boolean equals(Object other){
    return other instanceof StringKey
             && key.equals(((StringKey)other).key);
  }
}
