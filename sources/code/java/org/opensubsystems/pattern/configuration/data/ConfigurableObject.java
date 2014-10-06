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

package org.opensubsystems.pattern.configuration.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opensubsystems.core.data.DataDescriptor;
import org.opensubsystems.core.data.DataObject;
import org.opensubsystems.core.error.OSSConfigException;
import org.opensubsystems.core.error.OSSException;
import org.opensubsystems.core.util.Log;
import org.opensubsystems.core.util.OSSObject;
import org.opensubsystems.pattern.parameter.data.Parameter;
import org.opensubsystems.pattern.parameter.data.impl.ParameterImpl;

/**
 * Class holding configuration parameters and their actual and default values.
 * 
 * This class assumes that each parameter of configuration has only a single 
 * value.
 * 
 * @author bastafidli
 */
public class ConfigurableObject extends OSSObject
{
   // Constants ////////////////////////////////////////////////////////////////
   
   /**
    * Delimiter representing start of variable.
    */
   public static final String VARIABLE_START = "{$";

   /**
    * Delimiter representing end of variable.
    */
   public static final String VARIABLE_END = "}";
   
   // Attributes ///////////////////////////////////////////////////////////////
   // NOTE: Every time new attribute is added, please review method 
   // inheritAndOverride
   // and ensure that this new attribute is correctly accounted for
   
   /**
    * List of all defined configuration parameters where the key is the name of 
    * the configuration parameter and the value is the default value.
    */
   protected final Map<String, String> m_mpDefaults = new HashMap<>();
   
   /**
    * Collection of parameters keyed by the parameter name.
    */
   protected final Map<String, Parameter> m_mpParams = new HashMap<>();
   
   // Cached values ////////////////////////////////////////////////////////////

   /**
    * Commons logger variable used to log runtime information.
    */
   private static Logger s_logger = Log.getInstance(ConfigurableObject.class);

   // Constructors /////////////////////////////////////////////////////////////

   /**
    * Default constructor.
    */
   public ConfigurableObject(
   )
   {
      // Do nothing
   }

   /**
    * Copy constructor.
    * 
    * @param config - if not null then the object will be initialized as a copy
    *                 of the specified object
    */
   protected ConfigurableObject(
      ConfigurableObject config
   )
   {
      if (config != null)
      {
         m_mpDefaults.putAll(config.m_mpDefaults);
         m_mpParams.putAll(config.m_mpParams);
      }
   }
   
   // Logic ////////////////////////////////////////////////////////////////////
   
   public void addParam(
      Parameter param
   )
   {
      Parameter oldParam;
      
      oldParam = m_mpParams.put(param.getName(), param);
      if (oldParam != null)
      {
         s_logger.log(Level.WARNING, "Parameter with name {0} overwrote already"
                      + " existing parameter {1}", new Object[]{param.getName(), 
                      oldParam});
      }
   }

   /**
    * Get value of the specified parameter. 
    * 
    * NOTE: Every method accessing parameters has to call this method to do so
    * in order to correctly process the replacement variables.
    * 
    * @param strName - name of the configuration parameter to get value for. 
    *                  The Configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return Parameter - value or null if it is not defined and it doesn't have 
    *                 a default value
    * @throws OSSException - an error has occurred
    */
   public Parameter getParam(
      String strName
   ) throws OSSException
   {
      Parameter temp;
      
      temp = getParamWithoutVariableResolution(strName);
      
      if (temp != null)
      {
         List<String> lstValues = temp.getValues();
         
         if (lstValues.size() > 1)
         {
            throw new OSSConfigException("Parameter " + temp.getName() 
                                         + " has multiple values but only one is"
                                         + " supported at this time.");
         }
         
         String strOriginalValue;
         
         strOriginalValue = lstValues.get(0);

         if ((strOriginalValue != null) && (!strOriginalValue.isEmpty()))
         {
            String strResolvedValue;

            // If the value contains any variables, replace them with an actual
            // value before creating the param
            strResolvedValue = replaceVariables(strOriginalValue);
            // We do want to do != since if there is no replacement, the method
            // returns the same object (memory location) and we do not have to call
            // equals
            if ((strOriginalValue != strResolvedValue)
               && (!strOriginalValue.equals(strResolvedValue)))
            {
               // Since we are modifying the value, create copy of the parameter
               Parameter<String> copy;

               copy = new ParameterImpl(DataDescriptor.NO_DATA_DESCRIPTOR_CLASS,
                                        DataObject.NEW_ID,
                                        temp.getName(), null, strResolvedValue);
               temp = copy;
            }
         }
      }
      
      return temp;
   }

   /**
    * Get value of the specified parameter.
    * 
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
    * @return Param - value or null if it is not defined and it doesn't have 
    *                 a default value
    * @throws OSSException - an error has occurred
    */
   public Parameter getParam(
      String strConfigPrefix,
      String strName
   ) throws OSSException
   {
      Parameter         temp;
      StringBuilder sbPrefixedName = new StringBuilder(strConfigPrefix);
      
      sbPrefixedName.append(".");
      sbPrefixedName.append(strName);
      
      temp = getParam(sbPrefixedName.toString());
      if (temp == null)
      {
         temp = getParam(strName);
      }
      
      return temp;
   }

   
   /**
    * Get value of the specified parameter without trying to resolve any parameters
    * the value may contain.
    * 
    * @param strName - name of the configuration parameter to get value for. 
    *                  The Configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return Param - value or null if it is not defined and it doesn't have 
    *                 a default value
    * @throws OSSException  - an error has occurred
    */
   public Parameter getParamWithoutVariableResolution(
      String strName
   ) throws OSSException
   {
      Parameter    temp;
      List<String> lstValues = null;
      String       strValue = null;
         
      temp = m_mpParams.get(strName);
      if (temp != null)
      {
         lstValues = temp.getValues();
      }
      if ((lstValues == null) || (lstValues.isEmpty()) 
         || (lstValues.get(0).isEmpty()))
      {
         String strDefaultValue;
         
         strDefaultValue = m_mpDefaults.get(strName);
         if (strDefaultValue != null)
         {
            // If the value contains any variables, resolve them to an actual
            // value before creating the param
            temp = new ParameterImpl(DataDescriptor.NO_DATA_DESCRIPTOR_CLASS,
                                     DataObject.NEW_ID,
                                     strName, null, strDefaultValue);
         }
      }
      
      return temp;
   }
   
   /**
    * Get value of the specified parameter without trying to resolve any parameters
    * the value may contain.
    * 
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
    * @return Param - value or null if it is not defined and it doesn't have 
    *                 a default value
    * @throws OSSException - an error has occurred
    */
   public Parameter getParamWithoutVariableResolution(
      String strConfigPrefix,
      String strName
   ) throws OSSException
   {
      Parameter         temp;
      StringBuilder sbPrefixedName = new StringBuilder(strConfigPrefix);
      
      sbPrefixedName.append(".");
      sbPrefixedName.append(strName);
      
      temp = getParamWithoutVariableResolution(sbPrefixedName.toString());
      if (temp == null)
      {
         temp = getParamWithoutVariableResolution(strName);
      }
      
      return temp;
   }
   
   /**
    * Add setting with corresponding default value to the set of configuration
    * settings.
    * 
    * This method must be final so that we can call it from the constructor.
    * 
    * @param strParamName - name of the configuration setting
    * @param strDefaultValue - default value
    */
   public final void addDefault(
      String strParamName,
      String strDefaultValue
   )
   {
      m_mpDefaults.put(strParamName, strDefaultValue);
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public void toString(
      StringBuilder sb,
      int           iIndentIndex
   )
   {
      sb.append(INDENTATION[iIndentIndex]);
      sb.append("ConfigurableObject{");
      sb.append(INDENTATION[iIndentIndex + 1]);
      sb.append("m_mpParams=");
      append(sb, iIndentIndex + 1, m_mpParams); 
      sb.append(INDENTATION[iIndentIndex + 1]);
      sb.append("m_mpDefaults=");
      append(sb, iIndentIndex + 1, m_mpDefaults); 
      sb.append(INDENTATION[iIndentIndex]);
      sb.append("}");
   }
   
   /**
    * Replace variables for all values in the list.
    * 
    * @param lstValues - list of items which can contain some variables
    * @return List<String> - new list of items with all variables replaced
    * @throws OSSException - an error has occurred
    */
   public List<String> replaceVariables(
      List<String> lstValues
   ) throws OSSException
   {
      List<String> lstReturn;
      
      if (lstValues != null)
      {
         String strNewValue;
         
         lstReturn = new ArrayList<>(lstValues.size());
         for (String strValue : lstValues)
         {
            strNewValue = replaceVariables(strValue);
            lstReturn.add(strNewValue);
         }
      }
      
      return lstValues;
   }
   
   /**
    * Replace variables for the specified value.
    * 
    * @param strValue - value which may contain variables
    * @return String - new value with all variables replaced
    * @throws OSSException - an error has occurred
    */
   public String replaceVariables(
      String strValue
   ) throws OSSException
   {
      int iIndexStart;
      
      do
      {
         iIndexStart = strValue.indexOf(VARIABLE_START);
         if (iIndexStart != -1)
         {
            int iIndexEnd;

            iIndexEnd = strValue.indexOf(VARIABLE_END, iIndexStart + 1);
            if (iIndexEnd == -1)
            {
               throw new OSSConfigException("Value " + strValue 
                  + " contains variable name which is not properly terminated.");
            }

            // Full variable is "{$variablename}"
            String strFullVariable = strValue.substring(
                                        iIndexStart,
                                        iIndexEnd + VARIABLE_END.length());
            // Variable is "variablename"
            String strVariable = strValue.substring(
                                    iIndexStart + VARIABLE_START.length(),
                                    iIndexEnd);
            Parameter<String> replacement = getParamWithoutVariableResolution(
                                               strVariable);
            
            List<String> lstValues = replacement.getValues();

            if (lstValues.size() > 1)
            {
               throw new OSSConfigException("Parameter " + replacement.getName() 
                                            + " has multiple values but only one is"
                                            + " supported at this time.");
            }
            
            String strReplacementValue = null;
            
            if (replacement.hasAnyValue())
            {
               strReplacementValue = replacement.getValues().get(0);
            }
            
            if ((replacement == null) || (strReplacementValue == null))
            {
               throw new OSSConfigException("Variable " + strVariable 
                                            + " used in value " + strValue 
                                            + " is not defined.");
            }
            
            strValue = strValue.replace(strFullVariable, strReplacementValue);
         }
      }
      while (iIndexStart != -1);
      
      return strValue;
   }
   
   // Helper methods ///////////////////////////////////////////////////////////
   
   /**
    * Inherit all the elements from the parent object and override any elements
    * that are also defined in this object.
    * 
    * @param strLogPrefix - log prefix used for all log output to tie together
    *                       the same invocations
      @param strOverrideName - name of the current object, object that contains
      *                        the overrides
    * @param source - source object from which to inherit and override elements
    * @param strSourceName - name of object that contains the sources
    * @throws OSSConfigException - an error has occurred
    */
   protected void inheritAndOverride(
      String             strLogPrefix,
      String             strOverrideName,
      ConfigurableObject source,
      String             strSourceName
   ) throws OSSConfigException
   {
      inheritAndOverride(strLogPrefix, "Parameter Default Values",
                         m_mpDefaults, strOverrideName,
                         source.m_mpDefaults, strSourceName);
      inheritAndOverride(strLogPrefix, "Parameters",
                         m_mpParams, strOverrideName,
                         source.m_mpParams, strSourceName);
   }

   /**
    * Inherit the elements from source that are not already overriden.
    * 
    * @param strLogPrefix - log prefix used for all log output to tie together
    *                       the same invocations
    * @param strObjectName - name of the objects that are being processed
    * @param overrides - map of overrides, that is updated with the elements
    *                    from source that it doesn't contain yet
    * @param strOverrideName - name of the object that contains the overrides
    * @param source - source map from which the elements are taken and included
    *                 to overrides if they are not present yet
    * @param strSourceName  - name of object that contains the sources
    */
   protected void inheritAndOverride(
      String strLogPrefix,
      String strObjectName,
      Map    overrides,
      String strOverrideName,
      Map    source,
      String strSourceName
   )
   {
      Map.Entry entry;
      String    key;
      Object    value;
      
      for (Object temp : source.entrySet())
      {
         entry = (Map.Entry)temp;
         key = (String)entry.getKey();
         value = (Object)entry.getValue();
         if (overrides.containsKey(key))
         {
            s_logger.log(Level.FINEST, "{0}{1}{2} in the current object {3}"
                         + " overrides value from parent object {4}", 
                         new Object[]{strLogPrefix, strObjectName, key, 
                                      strOverrideName, strSourceName});
         }
         else
         {
            // This key is not in the list of overrides therefore inherit it
            s_logger.log(Level.FINEST, "{0}{1}{2} is being inherited to current"
                         + " object {3} with value {4} from parent object {5}", 
                        new Object[]{strLogPrefix, strObjectName, key, 
                                     strOverrideName, value, strSourceName});
            overrides.put(key, value);
         }
      }
   }
}
