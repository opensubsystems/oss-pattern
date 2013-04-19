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

import java.util.Collection;
import java.util.Map;
import org.opensubsystems.core.data.IdentifiableDataObject;

/**
 * Interface representing data object with name, description and set of 
 * parameters that can be used to further characterize the behavior of the object.
 *
 * @author bastafidli
 */
public interface ParametrizedIdentifiableDataObject extends IdentifiableDataObject
{
    /**
     * Get parameters, values of which are used to further characterize 
     * the behavior of the object.
     * 
     * @return Map<String, ? extends Parameter> - key is the parameter name, 
     *                                            value is the parameter itself.
     *                                            Can be null.
     */
    Map<String, ? extends Parameter>getParameterMap();

   /**
    * Get collection of the parameters, values of which are used to further 
    * characterize the behavior of the object.
    * 
    * @return Collection<? extends Parameter> - collection of parameters. Can be 
    *                                           null.
    */
   Collection<? extends Parameter>getParameterCollection();
}
