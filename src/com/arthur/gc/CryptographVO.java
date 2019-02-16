package com.arthur.gc;

public class CryptographVO
{
  private String key;
  private String data;
  
  public void setData(String data)
  {
    this.data = data;
  }
  
  public String getData()
  {
    return this.data;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public String getKey()
  {
    return this.key;
  }

  @Override
  public String toString() {
    return "CryptographVO{" +
            "key='" + key + '\'' +
            ", data='" + data + '\'' +
            '}';
  }
}
