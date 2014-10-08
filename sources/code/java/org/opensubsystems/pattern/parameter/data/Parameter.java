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

package org.opensubsystems.pattern.parameter.data;

import java.util.List;
import org.opensubsystems.core.data.IdentifiableDataObject;
import org.opensubsystems.core.error.OSSInconsistentDataException;

/**
 * Interface representing single parameter that can be used to further 
 * characterize other objects.
 *
 * @author bastafidli
 */
public interface Parameter<T> extends IdentifiableDataObject
{
   /**
    * Get values of the parameter.
    * 
    * @return List<T> - list of values associated with the given parameter name. 
    *                   Can be null if there are no values.
    */
   List<T> getValues();

   /**
    * Get first value from the list of values of the parameter.
    * 
    * @return T - first value from one or multiple values of the parameter or 
    *             null if the parameter has no values.
    */
   T getFirstValue(
   );
   
   /**
    * Get value of the parameter if the parameter has single value.
    * 
    * @return T - value of the parameter or null if the parameter has no values.
    * @throws OSSInconsistentDataException - parameter has more than one value
    */
   T getValue(
   )  throws OSSInconsistentDataException;

   /**
    * Set values of the parameter.
    * 
    * @param lstValues - list of values associated with the given parameter name. 
    *                    Can be null if there are no values.
    */
   void setValues(List<T> lstValues);

   /**
    * Set value of the parameter.
    * 
    * @param value - single value associated with the given parameter name. 
    *                Can be null if there are no values.
    */
   void setValue(T value);

   /**
    * Test if parameter has any value.
    * 
    * @return boolean
    */
   boolean hasAnyValue(); 
   
   /**
    * Test if parameter has multiple values.
    * 
    * @return boolean
    */
   boolean hasMultipleValues(); 
}
