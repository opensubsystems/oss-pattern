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

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.opensubsystems.core.data.DataDescriptor;
import org.opensubsystems.core.data.DataObject;
import org.opensubsystems.core.data.IdentifiableDataObject;
import org.opensubsystems.core.data.impl.IdentifiableDataObjectImpl;
import org.opensubsystems.core.error.OSSException;
import org.opensubsystems.core.util.HashCodeUtils;
import org.opensubsystems.pattern.parameter.data.Parameter;

/**
 * Parameter represents object that associates set of values with a name.
 * 
 * @author bastafidli
 */
public class ParameterImpl<T> extends IdentifiableDataObjectImpl
                              implements Parameter<T>
{
    // Attributes //////////////////////////////////////////////////////////////
    
    /**
     * Values associated with the given parameter name. 
     */
    protected List<T> m_lstValues;
    
    // Constructors ////////////////////////////////////////////////////////////
    
   /**
    * Simple constructor creating new data object in particular domain.
    * 
    * @param clsDataDescriptor - class identifying data descriptor for the object
    * @param lDomainId - domain this data object belongs to
    * @param strName - name of the data object
    * @param strDescription - description of the data object
    * @param lstValues - values associated with the parameter name
    * @throws OSSException - an error has occurred
    */
   public ParameterImpl(
      Class<DataDescriptor> clsDataDescriptor,
      long                  lDomainId,
      String                strName,
      String                strDescription,
      List<T>               lstValues
   ) throws OSSException
   {
      this(DataObject.NEW_ID, clsDataDescriptor, lDomainId, null, null, strName, 
           strDescription, lstValues);
   }

   /**
    * Full constructor.
    * 
    * @param lId - id of this data object
    * @param clsDataDescriptor - class identifying data descriptor for the object
    * @param lDomainId - domain this data object belongs to
    * @param creationTimestamp - timestamp when the data object was created.
    * @param modificationTimestamp - timestamp when the data object was last 
    *                                time modified.
    * @param strName - name of the data object
    * @param strDescription - description of the data object
    * @param lstValues - values associated with the parameter name
    * @throws OSSException - an error has occurred
    */
   public ParameterImpl(
      long                  lId,
      Class<DataDescriptor> clsDataDescriptor,
      long                  lDomainId,
      Timestamp             creationTimestamp, 
      Timestamp             modificationTimestamp,
      String                strName,
      String                strDescription,
      List<T>               lstValues
   ) throws OSSException
   {
      super(lId, clsDataDescriptor, lDomainId, creationTimestamp, 
            modificationTimestamp, strName, strDescription);
      
      m_lstValues = lstValues;
   }
    
   // Logic ////////////////////////////////////////////////////////////////////

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isSame(
      Object oObject
   )
   {
      boolean   bReturn = false;
      Parameter helper;

      if (oObject == this)
      {
         bReturn = true;
      }
      else if ((oObject != null) && (oObject instanceof IdentifiableDataObject))
      {
         helper = (Parameter) oObject;
         bReturn = CollectionUtils.isEqualCollection(getValues(), helper.getValues()) 
                   && (super.isSame(oObject));
      }

      return bReturn;
   }   

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      int iResult = HashCodeUtils.SEED;
 
      iResult = HashCodeUtils.hash(iResult, m_lstValues);
      iResult = HashCodeUtils.hash(iResult, super.hashCode());
      
      return iResult;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<T> getValues() 
   {
      return Collections.unmodifiableList(m_lstValues);
   }

   // Helper methods ///////////////////////////////////////////////////////////
   
   /**
    * Restore all values from specified values. This is here to reinitialize
    * object in case it needs to be reused or reconstructed (e.g. when rollback
    * is issued).
    * 
    * @param lId - id of this data object
    * @param clsDataDescriptor - class identifying data descriptor for the object
    * @param lDomainId - domain this data object belongs to
    * @param creationTimestamp - timestamp when the data object was created.
    * @param modificationTimestamp - timestamp when the data object was last 
    *                                time modified.
    * @param strName - name of the data object
    * @param strDescription - description of the data object
    * @param lstValues - values associated with the parameter name
    * @throws OSSException - an error has occurred
    */
   protected void restore(
      long                  lId,
      Class<DataDescriptor> clsDataDescriptor,
      long                  lDomainId,
      Timestamp             creationTimestamp, 
      Timestamp             modificationTimestamp,
      String                strName,
      String                strDescription,
      List<T>               lstValues
   ) throws OSSException
   {
      super.restore(lId, clsDataDescriptor, lDomainId, creationTimestamp, 
                    modificationTimestamp, strName, strDescription);
      
      m_lstValues = lstValues;
   }
}
