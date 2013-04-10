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
 
package org.opensubsystems.pattern.lock.data;

import org.opensubsystems.core.data.ModifiableDataObject;

/**
 * Interface representing lock used to gain and guard exclusive access to another
 * data object. The lock object is modifiable to that the system can track when
 * it was acquired (created) and when it was reacquired (modified) in case of 
 * long lasting locks that shouldn't expire.
 * 
 * @author bastafidli
 */
public interface Lock extends ModifiableDataObject
{
   // Logic ////////////////////////////////////////////////////////////////////

   /**
    * Get id of the data object that is guarded by this lock.
    * 
    * @return long
    */
   long getLockedObjectId();

   /**
    * Get hostname of the system that acquired the lock.
    * 
    * @return String - always not null and not empty
    */
   String getHostname();

   /**
    * Get string representing the origin in the system from where or why the 
    * lock was acquired. This can represent code location, module name, etc.
    * 
    * @return String - always not null and not empty
    */
   String getOrigin();
}
