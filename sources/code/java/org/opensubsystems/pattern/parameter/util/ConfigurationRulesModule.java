/*
 * Copyright (C) 2011 - 2014 OpenSubsystems.com/net/org and its owners. All rights reserved.
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

package org.opensubsystems.pattern.parameter.util;

import org.apache.commons.digester3.binder.AbstractRulesModule;
import org.opensubsystems.pattern.parameter.data.impl.ConfigurationImpl;
import org.opensubsystems.pattern.parameter.data.impl.ParameterImpl;

/**
 * The common-digester description of XML format describing a configuration 
 * settings.
 * 
 * @author bastafidli
 */
public class ConfigurationRulesModule extends AbstractRulesModule
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected void configure()
   {
      
      forPattern("config").createObject().ofType(ConfigurationImpl.class);

      // Parameters ////////////////////////////////////////////////////////////
      forPattern("config/param").createObject().ofType(ParameterImpl.class)
         .then().setNext("addParam");
      forPattern("config/param/name").setBeanProperty();
      forPattern("config/param/value").setBeanProperty();
   } 
}
