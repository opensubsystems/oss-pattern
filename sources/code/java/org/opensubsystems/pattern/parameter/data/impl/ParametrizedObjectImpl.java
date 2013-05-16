/*
 * Copyright (C) 2013 OpenSubsystems.com/net/org and its owners. All rights reserved.
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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.opensubsystems.core.error.OSSException;
import org.opensubsystems.core.util.DataObjectUtils;
import org.opensubsystems.core.util.HashCodeUtils;
import org.opensubsystems.core.util.OSSObject;
import org.opensubsystems.pattern.parameter.data.Parameter;
import org.opensubsystems.pattern.parameter.data.ParametrizedObject;

/**
 * Base class for all data objects that can be identified by their name and the 
 * description and parameterized by set of parameters which are set of values 
 * identifiable by name. 
 * 
 * This class is not abstract since name, description and parameters are sufficient 
 * attributes for some objects.
 * 
 * @author bastafidli
 */
public class ParametrizedObjectImpl extends OSSObject
                                       implements ParametrizedObject
{
    // Attributes //////////////////////////////////////////////////////////////
    
    /**
     * Parameters associated with this data object keyed by the parameter name. 
     */
    protected Map<String, Parameter> m_mpParamsByName;
    
    // Constructors ////////////////////////////////////////////////////////////
    
   /**
    * Simple constructor creating new object 
    * 
    * @param colParams - parameters associated with this data object 
    * @throws OSSException - an error has occurred
    */
   public ParametrizedObjectImpl(
      Collection<Parameter> colParams
   ) throws OSSException
   {
      m_mpParamsByName = DataObjectUtils.convertCollectionToMapByName(colParams);
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
      append(sb, ind + 0, "ParametrizedObjectImpl[");
      append(sb, ind + 1, "m_mpParams = ", m_mpParamsByName);
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
 
      iResult = HashCodeUtils.hash(iResult, m_mpParamsByName);
      iResult = HashCodeUtils.hash(iResult, super.hashCode());
      
      return iResult;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Map<String, Parameter>getParametersByName()
   {
      return m_mpParamsByName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Collection<Parameter>getParameters()
   {
      return (m_mpParamsByName != null) 
                ? Collections.unmodifiableCollection(m_mpParamsByName.values()) 
                : null;
   }
}
