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

package org.opensubsystems.pattern.parameter.data;

import java.util.List;
import org.opensubsystems.core.error.OSSException;

/**
 * Interface representing configuration data. The configuration is defined as set 
 * of configuration parameters and their actual and default values. The default 
 * values are used in case the actual values are not present. Configuration can 
 * be used to determine actual parameter values of other parametrized objects.
 * 
 * @author bastafidli
 */
public interface Configuration extends ParametrizedObject
{
   /**
    * Add new parameter to the configuration object.
    * 
    * @param param - new parameter for configuration object to track.
    */
   void addParam(
      Parameter param
   );

   /**
    * Get value of the specified parameter. The value of the parameter is checked
    * for existence of any variables and all variables found are then resolved
    * to their actual values.
    * 
    * @param strName - name of the configuration parameter to get value for. 
    *                  The configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return Parameter - value or null if it is not defined and it doesn't have 
    *                     a default value
    * @throws OSSException - an error has occurred
    */
   Parameter getParam(
      String strName
   ) throws OSSException;

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
   Parameter getParam(
      String strConfigPrefix,
      String strName
   ) throws OSSException;
   
   /**
    * Get value of the specified parameter without trying to resolve any variables
    * the value may contain.
    * 
    * @param strName - name of the configuration parameter to get value for. 
    *                  The configuration will first attempt to find value of 
    *                  strName and if it doesn't find it, it will return default 
    *                  value.
    * @return Param - value or null if it is not defined and it doesn't have 
    *                 a default value
    * @throws OSSException  - an error has occurred
    */
   Parameter getParamWithoutVariableResolution(
      String strName
   ) throws OSSException;
   
   /**
    * Get value of the specified parameter without trying to resolve any variables
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
   Parameter getParamWithoutVariableResolution(
      String strConfigPrefix,
      String strName
   ) throws OSSException;
   
   /**
    * Add setting with corresponding default value to the set of configuration
    * settings.
    * 
    * This method must be final so that we can call it from the constructor.
    * 
    * @param strParamName - name of the configuration setting
    * @param strDefaultValue - default value
    */
   void addDefault(
      String strParamName,
      String strDefaultValue
   );
   
   /**
    * Replace variables for all values in the list.
    * 
    * @param lstValues - list of items which can contain some variables
    * @return List<String> - new list of items with all variables replaced
    * @throws OSSException - an error has occurred
    */
   List<String> replaceVariables(
      List<String> lstValues
   ) throws OSSException;
   
   /**
    * Replace variables for the specified value.
    * 
    * @param strValue - value which may contain variables
    * @return String - new value with all variables replaced
    * @throws OSSException - an error has occurred
    */
   String replaceVariables(
      String strValue
   ) throws OSSException;
}
