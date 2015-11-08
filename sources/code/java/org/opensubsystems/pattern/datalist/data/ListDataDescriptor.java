/*
 * Copyright (C) 2003 - 2015 OpenSubsystems.com/net/org and its owners. All rights reserved.
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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.opensubsystems.core.data.DataDescriptor;

import org.opensubsystems.core.data.impl.DataDescriptorImpl;
import org.opensubsystems.core.error.OSSException;

/**
 * Collection of metadata elements that describe or define behavior of data 
 * objects when they are part of the list of other data objects. 
 * 
 * The metadata include
 * - constant (data type) identifying the data object 
 * - constants identifying each attribute of the data object
 * - constants identifying various sets of attributes, such as all attributes, 
 *   filterable attributes, attributes to display in list
 * - constants identifying default values of attributes 
 *
 * @param <E> - enumeration representing fields of the described data
 * @author bastafidli
 */
public class ListDataDescriptor<E extends Enum<E>>  extends DataDescriptorImpl<E>
{
   // Constants ////////////////////////////////////////////////////////////////
   
   // Attributes ///////////////////////////////////////////////////////////////

   /**
    * Array of default attributes to show to user for each data object when 
    * they are displayed in the list. Each attribute should have assigned unique 
    * code that can be used to identify that attribute.
    */
   protected int[] m_arrDefaultListDisplayColumnCodes;

   /**
    * Array of unique codes assigned to each attribute by which should be the 
    * list of data objects ordered by. Null if no sorting should be done.
    */
   protected int[] m_arrDefaultListOrderColumnCodes;
   
   /**
    * Array of default directions how to order the list of data objects. These 
    * directions are applied to the attribute codes defined in 
    * m_arrDefaultOrderColumnCodes. See ListOrder.ORDER_XXX constants. 
    */
   protected String[] m_arrDefaultListOrderDirections;

   /**
    * Array of unique codes of attributes that can be used to filter list of items. 
    * Each attribute should have assigned unique code that can be used to identify 
    * that attribute.
    */
   // TODO: Improve: This should be moved to filter package.
   protected int[] m_arrFilterableColumnCodes;
   
   /**
    * List defining what columns representing attributes of the data object are 
    * available to be displayed in the list. Elements are ListColumnDefinition 
    * objects describing the columns to display in the list. 
    * This attribute is kept as List so it is easier for caller to construct 
    * it rather than Map which would require to match the proper key with the 
    * proper value.
    */
   private List<ListColumnDefinition> m_lstColumnDefinitions;
   
   // Cached values ////////////////////////////////////////////////////////////
   
   /**
    * Map List defining what columns representing attributes of the data object 
    * are available to be displayed in the list. Elements are ListColumnDefinition.
    */ 
   private Map<Integer, ListColumnDefinition> m_mpColumnDefinitions;
   
   // Constructors /////////////////////////////////////////////////////////////
   
   /**
    * Constructor.
    * 
    * @param iDesiredDataType - Desired value of data type assigned to the data 
    *                           object described by this descriptor during 
    *                           development. This value can be accepted or 
    *                           changed if there is a conflict between various 
    *                           data types of object from which the application 
    *                           is constructed of.
    * @param strDataTypeName - displayable name for specified data type code object
    * @param strDataTypeViewName - logical name identifying the default view for 
    *                              the specified data type object. Data type 
    *                              objects can be displayed in multiple various 
    *                              ways called views. This constant identifies \
    *                              the default one.
	 * @param setDataFields - enumeration representing data fields for the class
    * @param arrDefaultListDisplayColumnCodes - Array of default attributes to 
    *                                           show to user for each data object 
    *                                           when they are displayed in the 
    *                                           list. Each attribute should have 
    *                                           assigned unique code that can 
    *                                           be used to identify that attribute.
    *                                           The order is important since the
    *                                           attributes will be displayed to
    *                                           user in this order.
    * @param arrDefaultListOrderColumnCodes - Array of unique codes assigned to 
    *                                         each attribute by which should be 
    *                                         the list of data objects ordered by. 
    *                                         Null if no sorting should be done.
    * @param arrDefaultListOrderDirections - Array of default directions how to 
    *                                        order the list of data objects. 
    *                                        These directions are applied to the 
    *                                        attribute codes defined in 
    *                                        arrDefaultListOrderColumnCodes. See 
    *                                        ListOrder.ORDER_XXX constants. 
    * @param arrFilterableColumnCodes - Array of unique codes of attributes that 
    *                                   can be used to filter list of items. Each 
    *                                   attribute should have assigned unique code 
    *                                   that can be used to identify that 
    *                                   attribute.
    * @param lstColumnDefinitions - List defining what columns representing 
    *                               attributes of the data object are 
    *                               available to be displayed in the list. 
    *                               Elements are ColumnDefinition objects 
    *                               describing the columns to display in the list.
	 * @throws OSSException - an error has occurred
    */
   public ListDataDescriptor(
      int                        iDesiredDataType,
      String                     strDataTypeName,
      String                     strDataTypeViewName,
		EnumSet<E>                 setDataFields,
      int[]                      arrDefaultListDisplayColumnCodes,
      int[]                      arrDefaultListOrderColumnCodes,
      String[]                   arrDefaultListOrderDirections,
      int[]                      arrFilterableColumnCodes,
      List<ListColumnDefinition> lstColumnDefinitions
   ) throws OSSException
   {
      super(iDesiredDataType, strDataTypeName, strDataTypeViewName, setDataFields);
      
      m_arrDefaultListDisplayColumnCodes = arrDefaultListDisplayColumnCodes;
      m_arrDefaultListOrderColumnCodes = arrDefaultListOrderColumnCodes;
      m_arrDefaultListOrderDirections = arrDefaultListOrderDirections;
      m_arrFilterableColumnCodes = arrFilterableColumnCodes;
      m_lstColumnDefinitions = lstColumnDefinitions;
   }

   /**
    * Constructor.
    * 
    * @param clsParentDescriptor - Class identifying parent data descriptor for
    *                              which this data descriptor provides just a 
    *                              different view for the same data objects. 
    *                              These two data descriptors share the same 
    *                              desired and actual data types.
    * @param strDataTypeName - displayable name for specified data type code object
    * @param strDataTypeViewName - logical name identifying the default view for 
    *                              the specified data type object. Data type 
    *                              objects can be displayed in multiple various 
    *                              ways called views. This constant identifies \
    *                              the default one.
	 * @param setDataFields - enumeration representing data fields for the class
    * @param arrDefaultListDisplayColumnCodes - Array of default attributes to 
    *                                           show to user for each data object 
    *                                           when they are displayed in the 
    *                                           list. Each attribute should have 
    *                                           assigned unique code that can 
    *                                           be used to identify that attribute.
    *                                           The order is important since the
    *                                           attributes will be displayed to
    *                                           user in this order.
    * @param arrDefaultListOrderColumnCodes - Array of unique codes assigned to 
    *                                         each attribute by which should be 
    *                                         the list of data objects ordered by. 
    *                                         Null if no sorting should be done.
    * @param arrDefaultListOrderDirections - Array of default directions how to 
    *                                        order the list of data objects. 
    *                                        These directions are applied to the 
    *                                        attribute codes defined in 
    *                                        arrDefaultListOrderColumnCodes. See 
    *                                        ListOrder.ORDER_XXX constants. 
    * @param arrFilterableColumnCodes - Array of unique codes of attributes that 
    *                                   can be used to filter list of items. Each 
    *                                   attribute should have assigned unique code 
    *                                   that can be used to identify that 
    *                                   attribute.
    * @param lstColumnDefinitions - List defining what columns representing 
    *                               attributes of the data object are 
    *                               available to be displayed in the list. 
    *                               Elements are ColumnDefinition objects 
    *                               describing the columns to display in the list.
	 * @throws OSSException - an error has occurred
    */
   public ListDataDescriptor(
      Class<DataDescriptor>      clsParentDescriptor,
      String                     strDataTypeName,
      String                     strDataTypeViewName,
		EnumSet<E>                 setDataFields,
      int[]                      arrDefaultListDisplayColumnCodes,
      int[]                      arrDefaultListOrderColumnCodes,
      String[]                   arrDefaultListOrderDirections,
      int[]                      arrFilterableColumnCodes,
      List<ListColumnDefinition> lstColumnDefinitions
   ) throws OSSException
   {
      super(clsParentDescriptor, strDataTypeName, strDataTypeViewName, setDataFields);
      
      m_arrDefaultListDisplayColumnCodes = arrDefaultListDisplayColumnCodes;
      m_arrDefaultListOrderColumnCodes = arrDefaultListOrderColumnCodes;
      m_arrDefaultListOrderDirections = arrDefaultListOrderDirections;
      m_arrFilterableColumnCodes = arrFilterableColumnCodes;
      m_lstColumnDefinitions = lstColumnDefinitions;
   }
   
   // Logic ////////////////////////////////////////////////////////////////////

   /**
    * Get the array of default attributes to show to user for each data object 
    * when they are displayed in the list. Each attribute should have assigned 
    * unique code that can be used to identify that attribute.
    * 
    * @return int[]
    */
   public int[] getDefaultListDisplayColumnCodes()
   {
      return m_arrDefaultListDisplayColumnCodes;
   }

   /**
    * Get array of unique codes assigned to each attribute by which should be 
    * the list of data objects ordered by. Null if no sorting should be done.
    * 
    * @return int[]
    */
   public int[] getDefaultListOrderColumnCodes()
   {
      return m_arrDefaultListOrderColumnCodes;
   }

   /**
    * Get array of default directions how to order the list of data objects. 
    * These directions are applied to the attribute codes defined in 
    * getDefaultListOrderColumnCodes(). See ListOrder.ORDER_XXX constants. 
    * 
    * @return String[]
    */
   public String[] getDefaultListOrderDirections()
   {
      return m_arrDefaultListOrderDirections;
   }

   /**
    * Get array of unique codes of attributes that  can be used to filter list 
    * of items. Each attribute should have assigned unique code that can be used 
    * to identify that attribute.
    * 
    * @return the filterableColumnCodes
    */
   public int[] getFilterableColumnCodes()
   {
      return m_arrFilterableColumnCodes;
   }
   
   /**
    * Get list defining what columns representing attributes of the data object 
    * are available to be displayed in the list. Elements are ListColumnDefinition 
    * objects describing the columns to display in the list.
    * 
    * @return List - list of ColumnDefinition objects
    */
   public List<ListColumnDefinition> getColumnDefinitions()
   {
      return m_lstColumnDefinitions;
   }

   /**
    * Get map defining what columns representing attributes of the data object 
    * are available to be displayed in the list. Key is the code assigned to the 
    * attribute represented by the column and value is the ListColumnDefinition 
    * object.
    * 
    * @return Map - map where key is column code and value is ColumnDefinition 
    *               objects for this column
    */
   public Map<Integer, ListColumnDefinition> getColumnDefinitionsMap()
   {
      if (m_mpColumnDefinitions == null)
      {
         Map<Integer, ListColumnDefinition> mpColumnDefinitions;
          
         mpColumnDefinitions = new HashMap<>(m_lstColumnDefinitions.size());
         for (ListColumnDefinition definition : m_lstColumnDefinitions)
         {
            mpColumnDefinitions.put(new Integer(definition.getColumnCode()), 
                                    definition);
         }
         
         m_mpColumnDefinitions = mpColumnDefinitions;
      }
      return m_mpColumnDefinitions;
   }
}
