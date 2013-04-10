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
 
package org.opensubsystems.pattern.issue.data;

import org.opensubsystems.core.data.ModifiableDataObject;

/**
 * Interface representing issue generated during system's function capturing
 * some abnormal condition that the system has encountered.
 * 
 * @author bastafidli
 */
public interface Issue extends ModifiableDataObject
{
   // Logic ////////////////////////////////////////////////////////////////////

   /**
    * Get id of the data object that caused this issue.
    * 
    * @return Long - may be null if the issue was not cause by any particular
    *                data.
    */
   Long getCauseId();

   /**
    * Get type of the data object that caused this issue.
    * 
    * @return String - may be null if the issue was not cause by any particular
    *                  data.
    */
   String getCauseType();

   /**
    * Get hostname where the issue has occurred.
    * 
    * @return String - always not null and not empty
    */
   String getHostname();

   /**
    * Get string representing the origin in the system from where the issue was
    * generated. This can represent code location, module name, etc.
    * 
    * @return String - always not null and not empty
    */
   String getOrigin();

   /**
    * Get the severity of the issue.
    * 
    * @return Severity
    */
   Severity getSeverity();

   /**
    * Get the description of the issue.
    * 
    * @return String
    */
   String getDescription();
}
