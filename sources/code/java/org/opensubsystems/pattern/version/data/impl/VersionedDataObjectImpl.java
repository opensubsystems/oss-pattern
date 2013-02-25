/*
 * Copyright (C) 2003 - 2013 OpenSubsystems.com/net/org and its owners. All rights reserved.
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
 
package org.opensubsystems.pattern.version.data.impl;

import org.opensubsystems.pattern.version.data.*;
import java.sql.Timestamp;
import org.opensubsystems.core.data.DataDescriptor;

import org.opensubsystems.core.data.DataObject;
import org.opensubsystems.core.data.impl.ModifiableDataObjectImpl;
import org.opensubsystems.core.error.OSSException;
import org.opensubsystems.core.util.HashCodeUtils;

/**
 * Base class for all data objects, which can be versioned and therefore have to
 * track the current version, if they are tip and the base version of, which this
 * is a new version.
 * 
 * @author OpenSubsystems
 */
public abstract class VersionedDataObjectImpl extends ModifiableDataObjectImpl
                                              implements VersionedDataObject
{
   // Constants ////////////////////////////////////////////////////////////////

   // Attributes ///////////////////////////////////////////////////////////////

   /**
    * Generated serial version id for this class.
    */
   private static final long serialVersionUID = 5046577999839516986L;

   /**
    * Id of the data object, which is base version of this data object. If this 
    * data object is the base version (it is the first version) then this is 
    * equal to the id.
    */
   protected long m_lBaseVersionId; 

   /**
    * Flag if this data object is the latest (newest) version called tip.
    */
   protected boolean m_bTip;
   
   /**
    * Data object version number. This is the actual version number of this data
    * object.
    */
   protected int m_iVersion;
   
   // Constructors /////////////////////////////////////////////////////////////
   
   /**
    * Simple constructor creating new data object in particular domain.
    * 
    * @param clsDataDescriptor - class identifying data descriptor for the object
    * @param lDomainId - domain this data object belongs to
    * @throws OSSException - an error has occurred
    */
   public VersionedDataObjectImpl(
      Class<DataDescriptor> clsDataDescriptor,
      long                  lDomainId
   ) throws OSSException
   {
      this(DataObject.NEW_ID, clsDataDescriptor, lDomainId, DataObject.NEW_ID,  
           true, FIRST_VERSION_NUMBER, null, null);
   }

   /**
    * Full constructor.
    * 
    * @param lId - id of this data object
    * @param clsDataDescriptor - class identifying data descriptor for the object
    * @param lDomainId - domain this data object belongs to
    * @param lBaseVersionId - data object base version ID
    * @param bTip - flag if this data object is TIP
    * @param iVersion - version number of this data object
    * @param creationTimestamp - timestamp when the data object was created.
    * @param modificationTimestamp - timestamp when the data object was last 
    *                                time modified.
    * @throws OSSException - an error has occurred 
    */
   public VersionedDataObjectImpl(
      long                  lId,
      Class<DataDescriptor> clsDataDescriptor,
      long                  lDomainId,
      long                  lBaseVersionId,
      boolean               bTip,
      int                   iVersion,
      Timestamp             creationTimestamp, 
      Timestamp             modificationTimestamp
   ) throws OSSException
   {
      super(lId, clsDataDescriptor, lDomainId, creationTimestamp, 
            modificationTimestamp);
      
      m_lBaseVersionId = lBaseVersionId;
      m_bTip = bTip;
      m_iVersion = iVersion;
   }
   
   // Logic ////////////////////////////////////////////////////////////////////

   /**
    * {@inheritDoc}
    */
   @Override
   public long getBaseVersionId()
   {
      return m_lBaseVersionId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setBaseVersionId(
      long lBaseVersionId
   )
   {
      m_lBaseVersionId = lBaseVersionId;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isTip()
   {
      return m_bTip;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTip(
      boolean bTip
   )
   {
      m_bTip = bTip;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getVersion()
   {
      return m_iVersion;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public void setVersion(
      int iVersion
   )
   {
      m_iVersion = iVersion;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(
      Object oObject
   )
   {
      boolean bReturn = false;
      VersionedDataObject helper;

      if (oObject == this)
      {
         bReturn = true;
      }
      else if ((oObject != null) && (oObject instanceof VersionedDataObject))
      {
         helper = (VersionedDataObject) oObject;
         bReturn = (m_bTip == helper.isTip())
                   && (m_lBaseVersionId == helper.getBaseVersionId())
                   && (m_iVersion == helper.getVersion())
                   && (super.equals(oObject));
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
      iResult = HashCodeUtils.hash(iResult, m_bTip);
      iResult = HashCodeUtils.hash(iResult, m_lBaseVersionId);
      iResult = HashCodeUtils.hash(iResult, m_iVersion);
      iResult = HashCodeUtils.hash(iResult, super.hashCode());
      return iResult;
   }

   // Helper methods ///////////////////////////////////////////////////////////
   
   /**
    * Restore all values from specified values. This is here to reinitialize
    * object in case it needs to be reused or reconstructed (e.g. when rollback
    * is issued).
    * 
    * @param iId - id of this data object
    * @param clsDataDescriptor - class identifying data descriptor for the object
    * @param iDomainId - domain this data object belongs to
    * @param iBaseVersionId - data object base version ID
    * @param bTip - flag if this data object is TIP
    * @param iVersion - version number of this data object
    * @param creationTimestamp - timestamp when the data object was created.
    * @param modificationTimestamp - timestamp when the data object was last 
    *                                time modified.
    * @throws OSSException - an error has occurred
    */
   protected void restore(
      int                   iId,
      Class<DataDescriptor> clsDataDescriptor,
      int                   iDomainId,
      int                   iBaseVersionId,
      boolean               bTip,
      int                   iVersion,
      Timestamp             creationTimestamp, 
      Timestamp             modificationTimestamp
   ) throws OSSException
   {
      super.restore(iId, clsDataDescriptor, iDomainId, creationTimestamp, 
                    modificationTimestamp);
      
      m_lBaseVersionId = iBaseVersionId;
      m_bTip = bTip;
      m_iVersion = iVersion;
   }
}
