/*
 * Copyright (C) 2011 - 2014 OpenSubsystems.com/net/org and its owners. All rights reserved.
 * 
 * This file is part of OpenSubsystems.
 *
 * OpenSubsystems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

package org.opensubsystems.pattern.configuration.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.opensubsystems.core.error.OSSConfigException;
import org.opensubsystems.core.util.FileUtils;
import org.opensubsystems.core.util.Log;
import org.opensubsystems.core.util.OSSObject;
import org.opensubsystems.core.util.ResourceUtils;
import org.opensubsystems.pattern.configuration.data.ConfigurableObject;
import org.xml.sax.SAXException;

/**
 * Class for accessing configuration parameters defined in XML format.
 * 
 * Note: At this time This class assumes that each parameter of configuration has 
 *       only a single value as constrained by data format definition in 
 *       ConfigurationRulesModule.
 * 
 * @author bastafidli
 */
public class XMLConfig extends OSSObject 
{
   // Constants ////////////////////////////////////////////////////////////////
   
   public static final String DEFAULT_CONFIGURATION_FILE = "ossconfig.xml";
   
   /**
    * File name of the configuration file to use to read the settings from.
    */
   private static String s_strConfigurationFileName;
   
   // Cached values ////////////////////////////////////////////////////////////

   /**
    * Commons logger variable used to log runtime information.
    */
   private static Logger s_logger = Log.getInstance(XMLConfig.class);

   // Constructors /////////////////////////////////////////////////////////////
    
   /** 
    * Private constructor since this class cannot be instantiated
    */
   private XMLConfig(
   )
   {
      // Do nothing
   }
   
   // Logic ////////////////////////////////////////////////////////////////////
   
   public static String getConfigurationFileName()
   {
      return s_strConfigurationFileName;
   }
   
   public static void getConfigurationFileName(
      String strConfigurationFileName
   )
   {
      s_strConfigurationFileName = strConfigurationFileName;
   }
   
   /**
    * Read configuration from a previously configured file. If no file was 
    * configured then default location will be used.
    * 
    * @return ConfigurableObject 
    * @throws OSSConfigException - an error has occurred
    */
   public static ConfigurableObject read(
   ) throws OSSConfigException
   {
      ConfigurableObject  configData = null;
      File                configFile = null;
      URL                 configURL = null;
      
      try
      {
         if ((s_strConfigurationFileName != null) 
            && (s_strConfigurationFileName.length() > 0))
         {
            configFile = new File(s_strConfigurationFileName);
            if ((!configFile.exists()) || (configFile.isDirectory()))
            {
               configFile = null;
               configURL = FileUtils.findFileOnClassPath(DEFAULT_CONFIGURATION_FILE);
               s_logger.log(Level.WARNING,"Configuration file {0} doesn't exist"
                            + " or is not a valid file, using default configuration"
                            + " file " + DEFAULT_CONFIGURATION_FILE + " at {1}", new
                            Object[]{s_strConfigurationFileName, configURL});
            }
         }
         else
         {
            configURL = FileUtils.findFileOnClassPath(DEFAULT_CONFIGURATION_FILE);
            s_logger.log(Level.WARNING,"Configuration file is not specified,"
                         + " using default location " + DEFAULT_CONFIGURATION_FILE 
                         + " at {0}", configURL);
         }
      }
      catch (IOException exc)
      {
         throw new OSSConfigException(exc);
      }
      
      if (configFile != null)
      {
         configData = read(configFile);
      }
      else if (configURL != null)
      {
         InputStream isConfigFile = null;
         
         try
         {
            isConfigFile = configURL.openStream();
            configData = read(isConfigFile);
         }
         catch (IOException exc) 
         {
            throw new OSSConfigException("Error reading file " + configURL, 
                                             exc);
         }
         finally
         {
            ResourceUtils.close(isConfigFile);
         }
      }
      
      return configData;
   }
   
   /**
    * Read configuration from a specified file
    * 
    * @param strfileConfig - file from which to read configuration
    * @return ConfigurableObject 
    * @throws OSSConfigException - an error has occurred
    */
   public static ConfigurableObject read(
      String strfileConfig
   ) throws OSSConfigException
   {
      return read(new File(strfileConfig));
   }
   
   /**
    * Read configuration from a specified file
    * 
    * @param fileConfig - file from which to read configuration
    * @return ConfigurableObject 
    * @throws OSSConfigException - an error has occurred
    */
   public static ConfigurableObject read(
      File fileConfig
   ) throws OSSConfigException
   {
      Digester digester = DigesterLoader.newLoader(
                             new ConfigurationRulesModule()).newDigester();
      ConfigurableObject config = null;
      
      try
      {
         config = digester.parse(fileConfig);
         s_logger.log(Level.FINE, "Read configuration {0}", config);
      }
      catch (IOException | SAXException exc)
      {
         throw new OSSConfigException("Error reading input file " 
                                          + fileConfig.getName(), 
                                          exc);
      }
      
      return config;
   }

   
   /**
    * Read configuration from a specified input stream
    * 
    * @param streamConfig - stream from which to read configuration
    * @return ConfigurableObject 
    * @throws OSSConfigException - an error has occurred
    */
   public static ConfigurableObject read(
      InputStream streamConfig
   ) throws OSSConfigException
   {
      Digester digester = DigesterLoader.newLoader(
                             new ConfigurationRulesModule()).newDigester();
      ConfigurableObject config = null;
      
      try
      {
         config = digester.parse(streamConfig);
         s_logger.log(Level.FINE, "Read configuration {0}", config);
      }
      catch (IOException | SAXException exc)
      {
         throw new OSSConfigException("Error reading input stream " 
                                          + streamConfig.toString(), 
                                          exc);
      }
      
      return config;
   }
}
