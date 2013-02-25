/*
 * Copyright (C) 2008 - 2012 OpenSubsystems.com/net/org and its owners. All rights reserved.
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

package org.opensubsystems.pattern.datalist.data;

/**
 * Series of constants that can be used to define order of attributes in the 
 * list.
 * 
 * @author bastafidli
 */
public interface ListOrder
{
   /**
    * Order the list in ascending order by specified column. This needs to be 
    * valid SQL modifier since it will be directly appended to the SQL query.
    * There has to be extra space in the front since there needs to be space
    * between column name and the identifier. This is just a design shortcut
    * to use SQL keywords as constants, since it will save us extra translation
    * effort and these constants are as good as any.
    */
   String ORDER_ASCENDING = " asc";
   
   /**
    * Convenience constant of array type.
    */
   String[] ORDER_ASCENDING_ARRAY = {ORDER_ASCENDING};

   /**
    * Convenience constant of array type.
    */
   String[] ORDER_ASCENDING2_ARRAY = {ORDER_ASCENDING, 
                                      ORDER_ASCENDING,
                                     };

   /**
    * Order the list in descending order by specified column. This needs to be 
    * valid SQL modifier since it will be directly appended to the SQL query.
    * There has to be extra space in the front since there needs to be space
    * between column name and the identifier. This is just a design shortcut
    * to use SQL keywords as constants, since it will save us extra translation
    * effort and these constants are as good as any.
    */
   String ORDER_DESCENDING = " desc";

   /**
    * Convenience constant of array type.
    */
   String[] ORDER_DESCENDING_ARRAY = {ORDER_DESCENDING};

   /**
    * Convenience constant of array type.
    */
   String[] ORDER_DESCENDING2_ARRAY = {ORDER_DESCENDING, 
                                       ORDER_DESCENDING,
                                      };
}
