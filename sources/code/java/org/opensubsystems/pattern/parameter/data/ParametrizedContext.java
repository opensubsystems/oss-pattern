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

import java.util.Map;
import java.util.Stack;

/**
 * Interface representing context within which some processing is being executed
 * that can be configured using set of parameters.
 *
 * @author bastafidli
 */
public interface ParametrizedContext 
{
   /**
    * Get common cache used by the clients of this context to store name value 
    * pairs.
    */
   Map<String, Object> getCache();
           
   /**
    * Get the collection of objects that can parametrize processing using this 
    * context.
    */
   Stack<ParametrizedObject> getParamContext();

   /**
    * Add new parametrized object to the context used for some kind of processing.
    * Parameters defined in the added object will take precedence over any 
    * previously added parameters.
    * 
    * @param newContext - new parametrized object for some kind of processing.
    */
   void pushParamContext(
      ParametrizedObject newContext
   );
   
   /**
    * Remove the last added parametrized object from the context used for some 
    * kind of processing. If parameters from the removed object redefined some 
    * parameters from earlier added objects, those parameters will not be 
    * restored.
    * 
    * @return ParametrizedObject - previously added parametrized object 
    */
   ParametrizedObject popParamContext();
}
