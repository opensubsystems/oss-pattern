/*
 * Copyright (c) 2011 - 2014 OpenSubsystems.com/net/org and its owners. All rights reserved.
 * 
 * Project: Tainted
 */

package org.opensubsystems.pattern.parameter.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.opensubsystems.core.error.OSSException;
import org.opensubsystems.core.util.Log;
import org.opensubsystems.core.util.StringUtils;
import org.opensubsystems.pattern.parameter.data.Configuration;
import org.opensubsystems.pattern.parameter.data.Parameter;

/**
 * Utility methods for working with configuration objects.
 * 
 * @author bastafidli
 */
public class ConfigurationUtils
{
   // Cached values ////////////////////////////////////////////////////////////

   /**
    * Commons logger variable used to log runtime information.
    */
   private static Logger s_logger = Log.getInstance(ConfigurationUtils.class);

   // Constructors /////////////////////////////////////////////////////////////
    
   /** 
    * Private constructor since this class cannot be instantiated
    */
   private ConfigurationUtils(
   )
   {
      // Do nothing
   }
   
   // Public methods ///////////////////////////////////////////////////////////


   /**
    * Get value of the specified parameter as an Integer.
    * 
    * @param config - configurable object holding the parameters
    * @param strName - name of the configuration parameter to get value for. 
    *                  The configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return Integer - value or null if it is not defined or it is not an Integer
    * @throws OSSException - an error has occurred
    */
   public static Integer getParamAsInt(
      Configuration config,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      Integer           iValue = null;
      
      temp = config.getParam(strName);
      if (temp != null)
      {
         try
         {
            iValue = Integer.valueOf(temp.getValue());
         }
         catch (NumberFormatException nfeExc)
         {
            s_logger.log(Level.SEVERE, "Cannot parse property {0} with value {1}", 
                         new Object[]{strName, StringUtils.valueIfNotNull(temp.getValue())});
         }
      }
      
      return iValue;
   }

   /**
    * Get value of the specified parameter as an Integer.
    * 
    * @param config - configurable object holding the parameters
    * @param strConfigPrefix - prefix that can be used to modify default 
    *                          configuration settings, e.g. if prefix is "example"
    *                          and default value of qs.ProcessingCapacity=10, 
    *                          one can define example.qs.ProcessingCapacity=20
    *                          to override this particular value while leaving
    *                          all the other qs.xxx values intact. The Configuration
    *                          will first attempt to find value of 
    *                          strConfigPrefix.strName, if it doesn't find, it 
    *                          will attempt to find default value for 
    *                          strConfigPrefix.strName, if it doesn't find it, it
    *                          will attempt to find value for strName and if it 
    *                          doesn't find it, it will return default value.
    * @param strName - name of the configuration parameter to get value for.
    * @return Integer - value or null if it is not defined or it is not an Integer
    * @throws OSSException - an error has occurred
    */
   public static Integer getParamAsInt(
      Configuration config,
      String        strConfigPrefix,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      Integer           iValue = null;
      
      temp = config.getParam(strConfigPrefix, strName);
      if (temp != null)
      {
         try
         {
            iValue = Integer.valueOf(temp.getValue());
         }
         catch (NumberFormatException nfeExc)
         {
            s_logger.log(Level.SEVERE, "Cannot parse property {0} with prefix {1}"
                         + " and with value {2}", new Object[]{strName, 
                         strConfigPrefix, StringUtils.valueIfNotNull(temp.getValue())});
         }
      }
      
      return iValue;
   }

   /**
    * Get value of the specified parameter as an Boolean.
    * 
    * @param config - configurable object holding the parameters
    * @param strName - name of the configuration parameter to get value for. 
    *                  The configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return Integer - value or null if it is not defined or it is not an Boolean
    * @throws OSSException - an error has occurred
    */
   public static Boolean getParamAsBoolean(
      Configuration config,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      Boolean           bValue = null;
      
      temp = config.getParam(strName);
      if (temp != null)
      {
         bValue = Boolean.valueOf(temp.getValue());
      }
      
      return bValue;
   }

   /**
    * Get value of the specified parameter as an Boolean.
    * 
    * @param config - configurable object holding the parameters
    * @param strConfigPrefix - prefix that can be used to modify default 
    *                          configuration settings, e.g. if prefix is "example"
    *                          and default value of qs.ProcessingCapacity=10, 
    *                          one can define example.qs.ProcessingCapacity=20
    *                          to override this particular value while leaving
    *                          all the other qs.xxx values intact. The Configuration
    *                          will first attempt to find value of 
    *                          strConfigPrefix.strName, if it doesn't find, it 
    *                          will attempt to find default value for 
    *                          strConfigPrefix.strName, if it doesn't find it, it
    *                          will attempt to find value for strName and if it 
    *                          doesn't find it, it will return default value.
    * @param strName - name of the configuration parameter to get value for.
    * @return Integer - value or null if it is not defined or it is not an Boolean
    * @throws OSSException - an error has occurred
    */
   public static Boolean getParamAsBoolean(
      Configuration config,
      String        strConfigPrefix,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      Boolean           bValue = null;
      
      temp = config.getParam(strConfigPrefix, strName);
      if (temp != null)
      {
         bValue = Boolean.valueOf(temp.getValue());
      }
      
      return bValue;
   }
   
   /**
    * Get value of the specified parameter as an Long.
    * 
    * @param config - configurable object holding the parameters
    * @param strName - name of the configuration parameter to get value for. 
    *                  The configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return Long - value or null if it is not defined or it is not an Long
    * @throws OSSException - an error has occurred
    */
   public static Long getParamAsLong(
      Configuration config,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      Long              lValue = null;
      
      temp = config.getParam(strName);
      if (temp != null)
      {
         try
         {
            lValue = Long.valueOf(temp.getValue());
         }
         catch (NumberFormatException nfeExc)
         {
            s_logger.log(Level.SEVERE, "Cannot parse property {0} with value {1}", 
                         new Object[]{strName, StringUtils.valueIfNotNull(temp.getValue())});
         }
      }
      
      return lValue;
   }

   /**
    * Get value of the specified parameter as an Long.
    * 
    * @param config - configurable object holding the parameters
    * @param strConfigPrefix - prefix that can be used to modify default 
    *                          configuration settings, e.g. if prefix is "example"
    *                          and default value of qs.ProcessingCapacity=10, 
    *                          one can define example.qs.ProcessingCapacity=20
    *                          to override this particular value while leaving
    *                          all the other qs.xxx values intact. The Configuration
    *                          will first attempt to find value of 
    *                          strConfigPrefix.strName, if it doesn't find, it 
    *                          will attempt to find default value for 
    *                          strConfigPrefix.strName, if it doesn't find it, it
    *                          will attempt to find value for strName and if it 
    *                          doesn't find it, it will return default value.
    * @param strName - name of the configuration parameter to get value for.
    * @return Long - value or null if it is not defined or it is not an Long
    * @throws OSSException - an error has occurred
    */
   public static Long getParamAsLong(
      Configuration config,
      String        strConfigPrefix,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      Long              lValue = null;
      
      temp = config.getParam(strConfigPrefix, strName);
      if (temp != null)
      {
         try
         {
            lValue = Long.valueOf(temp.getValue());
         }
         catch (NumberFormatException nfeExc)
         {
            s_logger.log(Level.SEVERE, "Cannot parse property {0} with prefix {1}"
                         + " and with value {2}", new Object[]{strName, 
                         strConfigPrefix, StringUtils.valueIfNotNull(temp.getValue())});
         }
      }
      
      return lValue;
   }
   
   /**
    * Get value of the specified parameter as an String.
    * 
    * @param config - configurable object holding the parameters
    * @param strName - name of the configuration parameter to get value for. 
    *                  The configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return String - value or null if it is not defined or it is not an String
    * @throws OSSException - an error has occurred
    */
   public static String getParamAsString(
      Configuration config,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      String            strValue = null;
      
      temp = config.getParam(strName);
      if (temp != null)
      {
         strValue = temp.getValue();
      }
      
      return strValue;
   }

   /**
    * Get value of the specified parameter as an Integer.
    * 
    * @param config - configurable object holding the parameters
    * @param strConfigPrefix - prefix that can be used to modify default 
    *                          configuration settings, e.g. if prefix is "example"
    *                          and default value of qs.ProcessingCapacity=10, 
    *                          one can define example.qs.ProcessingCapacity=20
    *                          to override this particular value while leaving
    *                          all the other qs.xxx values intact. The Configuration
    *                          will first attempt to find value of 
    *                          strConfigPrefix.strName, if it doesn't find, it 
    *                          will attempt to find default value for 
    *                          strConfigPrefix.strName, if it doesn't find it, it
    *                          will attempt to find value for strName and if it 
    *                          doesn't find it, it will return default value.
    * @param strName - name of the configuration parameter to get value for.
    * @return String - value or null if it is not defined or it is not an String
    * @throws OSSException - an error has occurred
    */
   public static String getParamAsString(
      Configuration config,
      String        strConfigPrefix,
      String        strName
   ) throws OSSException
   {
      Parameter<String> temp;
      String            strValue = null;
      
      temp = config.getParam(strConfigPrefix, strName);
      if (temp != null)
      {
         strValue = temp.getValue();
      }
      
      return strValue;
   }
}
