package com.dc.distributed.content.searching.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ResourceLoader {


  /**
   * The will load external.app.properties as external configurations
   * @param propPath
   * @return
   */
  public static Properties loadAppProperties(String propPath) {

    Properties properties = new Properties();
    FileInputStream fileInputStream = null;
    if (propPath != null) {
      try {
        fileInputStream = new FileInputStream(propPath);
        properties.load(fileInputStream);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (fileInputStream != null) {
          try {
            fileInputStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    return properties;
  }
}
