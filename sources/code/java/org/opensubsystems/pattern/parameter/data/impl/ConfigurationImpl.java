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

package org.opensubsystems.pattern.parameter.data.impl;

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
import org.opensubsystems.core.util.HashCodeUtils;
import org.opensubsystems.core.util.Log;
import org.opensubsystems.pattern.parameter.data.Configuration;
import org.opensubsystems.pattern.parameter.data.Parameter;

/**
 * Class representing configuration data. The configuration is defined as set of 
 * configuration parameters and their actual and default values. The default values
 * are used in case the actual values are not present. Configuration can be used
 * to determine actual parameter values of other parametrized objects.
 * 
 * This class assumes that each parameter of configuration has only a single 
 * value.
 * 
 * @author bastafidli
 */
public class ConfigurationImpl extends    ParametrizedObjectImpl
                               implements Configuration
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
   protected final Map<String, String> m_mpDefaultValuesByName = new HashMap<>();
   
   // Cached values ////////////////////////////////////////////////////////////

   /**
    * Commons logger variable used to log runtime information.
    */
   private static Logger s_logger = Log.getInstance(ConfigurationImpl.class);

   // Constructors /////////////////////////////////////////////////////////////

   /**
    * Default constructor.
    */
   public ConfigurationImpl(
   )
   {
      super();
      
      // Do nothing
   }

   /**
    * Copy constructor.
    * 
    * @param config - if not null then the object will be initialized as a copy
    *                 of the specified object
    */
   protected ConfigurationImpl(
      ConfigurationImpl config
   )
   {
      super();
      
      if (config != null)
      {
         m_mpDefaultValuesByName.putAll(config.m_mpDefaultValuesByName);
         m_mpParamsByName.putAll(config.m_mpParamsByName);
      }
   }
   
   // Logic ////////////////////////////////////////////////////////////////////
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   public void toString(
      StringBuilder sb,
      int           ind
   )
   {
      append(sb, ind + 0, "ConfigurationImpl[");
      append(sb, ind + 1, "m_mpDefaultValuesByName = ", m_mpDefaultValuesByName);
      super.toString(sb, ind + 1);
      append(sb, ind + 0, "]");
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      int iResult = HashCodeUtils.SEED;
 
      iResult = HashCodeUtils.hash(iResult, m_mpDefaultValuesByName);
      iResult = HashCodeUtils.hash(iResult, super.hashCode());
      
      return iResult;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void addParam(
      Parameter param
   )
   {
      Parameter oldParam;
      
      oldParam = m_mpParamsByName.put(param.getName(), param);
      if (oldParam != null)
      {
         s_logger.log(Level.WARNING, "Parameter with name {0} overwrote already"
                      + " existing parameter {1}", new Object[]{param.getName(), 
                      oldParam});
      }
   }

   /**
    * {@inheritDoc}
    * 
    * NOTE: Every method accessing parameters has to call this method in order 
    * to correctly process the replacement variables.
    */
   @Override
   public Parameter getParam(
      String strName
   ) throws OSSException
   {
      Parameter<String> temp;
      
      temp = getParamWithoutVariableResolution(strName);
      
      if (temp != null)
      {
         String strOriginalValue;
         
         strOriginalValue = temp.getValue();

         if ((strOriginalValue != null) && (!strOriginalValue.isEmpty()))
         {
            String strResolvedValue;

            // If the value contains any variables, replace them with an actual
            // value before creating the param
            strResolvedValue = replaceVariables(strOriginalValue);
            // We do want to do != since if there is no replacement, the method
            // returns the same object (memory location) and we do not have to 
            // call equals
            if ((strOriginalValue != strResolvedValue)
               && (!strOriginalValue.equals(strResolvedValue)))
            {
               // Since we are modifying the value, create copy of the parameter
               Parameter<String> copy;

               copy = new ParameterImpl<>(DataDescriptor.NO_DATA_DESCRIPTOR_CLASS,
                                          DataObject.NEW_ID,
                                          temp.getName(), null, strResolvedValue);
               temp = copy;
            }
         }
      }
      
      return temp;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Parameter getParam(
      String strConfigPrefix,
      String strName
   ) throws OSSException
   {
      Parameter     temp;
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
    * {@inheritDoc}
    */
   @Override
   public Parameter getParamWithoutVariableResolution(
      String strName
   ) throws OSSException
   {
      Parameter    temp;
      List<String> lstValues = null;
      String       strValue = null;
         
      temp = m_mpParamsByName.get(strName);
      if (temp != null)
      {
         lstValues = temp.getValues();
      }
      if ((lstValues == null) || (lstValues.isEmpty()) 
         || (lstValues.get(0).isEmpty()))
      {
         String strDefaultValue;
         
         strDefaultValue = m_mpDefaultValuesByName.get(strName);
         if (strDefaultValue != null)
         {
            // If the value contains any variables, resolve them to an actual
            // value before creating the param
            temp = new ParameterImpl<>(DataDescriptor.NO_DATA_DESCRIPTOR_CLASS,
                                       DataObject.NEW_ID,
                                       strName, null, strDefaultValue);
         }
      }
      
      return temp;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
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
    * {@inheritDoc}
    */
   @Override
   public final void addDefault(
      String strParamName,
      String strDefaultValue
   )
   {
      String strOldDefault;
      
      strOldDefault = m_mpDefaultValuesByName.put(strParamName, strDefaultValue);
      if (strOldDefault != null)
      {
         s_logger.log(Level.WARNING, "Default value {0} for parameter with name"
                      + " {1} overwrote already existing default value {2}", 
                      new Object[]{strDefaultValue, strParamName, strOldDefault});
      }
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
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
    * {@inheritDoc}
    */
   @Override
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
            
            String strReplacementValue = replacement.getValue();
            
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
      ConfigurationImpl source,
      String             strSourceName
   ) throws OSSConfigException
   {
      inheritAndOverride(strLogPrefix, "Parameter Default Values",
                         m_mpDefaultValuesByName, strOverrideName,
                         source.m_mpDefaultValuesByName, strSourceName);
      inheritAndOverride(strLogPrefix, "Parameters",
                         m_mpParamsByName, strOverrideName,
                         source.m_mpParamsByName, strSourceName);
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
