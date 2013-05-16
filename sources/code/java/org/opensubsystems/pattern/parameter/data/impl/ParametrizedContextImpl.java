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

import java.util.Stack;
import org.opensubsystems.core.util.OSSObject;
import org.opensubsystems.pattern.parameter.data.ParametrizedContext;
import org.opensubsystems.pattern.parameter.data.ParametrizedObject;

/**
 * Default implementation of the ParametrizedContext interface.
 * 
 * @author bastafidli
 */
public class ParametrizedContextImpl extends OSSObject
                                     implements ParametrizedContext
{
   // Attributes ///////////////////////////////////////////////////////////////
   
   /**
    * The collection of objects that can parametrize processing using this context.
    */
   protected Stack<ParametrizedObject> m_skParamContext;
   
   // Constructors /////////////////////////////////////////////////////////////
   
   /**
    * Default constructor.
    */
   public ParametrizedContextImpl(
   )
   {
      super();
      
      m_skParamContext = new Stack<>();
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
      append(sb, ind + 0, "ParametrizedContextImpl[");
      append(sb, ind + 1, "m_skParamContext = ", m_skParamContext);
      super.toString(sb, ind + 1);
      append(sb, ind + 0, "]");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Stack<ParametrizedObject> getParamContext()
   {
      return m_skParamContext;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void pushParamContext(
       ParametrizedObject newContext
   )
   {
      m_skParamContext.push(newContext);
   }
    
   /**
    * {@inheritDoc}
    */
   @Override
   public ParametrizedObject popParamContext()
   {
      return m_skParamContext.pop();
   }
}
