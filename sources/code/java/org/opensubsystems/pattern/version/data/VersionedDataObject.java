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
 
package org.opensubsystems.pattern.version.data;

import org.opensubsystems.core.data.ModifiableDataObject;

/**
 * Base interface for all data objects, which can be versioned and therefore have 
 * to track the current version, if they are tip and the base version of, which 
 * this is a new version. Versioning of data objects implies modification since 
 * without modification there is no need to version the data object and therefore 
 * this interface extends ModifiableDataObject.
 * 
 * @author OpenSubsystems
 */
public interface VersionedDataObject extends ModifiableDataObject
{
   // Constants ////////////////////////////////////////////////////////////////

   /**
    * This is the first version number for any versionable data object.
    */
   long FIRST_VERSION_NUMBER = 1;
   
   // Logic ////////////////////////////////////////////////////////////////////

   /**
    * Get id of the data object, which is base version of this data object.
    * 
    * @return long
    */
   long getBaseVersionId();

   /**
    * Set id of the data object, which is base version of this data object.
    * 
    * @param lBaseVersionId - id of the version on which is this object based on 
    */
   void setBaseVersionId(
      long lBaseVersionId
   );
   
   /**
    * Get flag if this data object is the latest (newest) version called tip.
    * 
    * @return boolean - true if this is the latest version 
    */
   public boolean isTip();

   /**
    * Set flag if this data object is the latest (newest) version called tip.
    * 
    * @param bTip - true if this is the tip - latest (newest) version
    */
   public void setTip(
      boolean bTip
   );

   /**
    * Get the actual version number of this data object.
    * 
    * @return long
    */
   public long getVersion();
   
   /**
    * Set the actual version number of this data object.
    * 
    * @param lVersion - version of this object
    */
   public void setVersion(
      long lVersion
   );
}
