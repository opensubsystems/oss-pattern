/*
 * Copyright (C) 2003 - 2012 OpenSubsystems.com/net/org and its owners. All rights reserved.
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

import org.opensubsystems.core.util.OSSObject;

/**
 * Class describing one column of list of items in the user interface. This is 
 * based on the horizontal layout of the list when items are in the rows and 
 * columns contain individual attributes of the items. You can of course layout 
 * the list of items differently and this class will be then used as a 
 * description of one of the attributes in the list 
 *  
 * @author bastafidli
 */
public class ListColumnDefinition extends OSSObject
{
   // Constants ////////////////////////////////////////////////////////////////
   
   /**
    * Possible values of boolean representation.
    */
   public static final String[] BOOLEAN_TEXTS_DISENABLED 
                                   = new String[] {"Disabled", "Enabled"};
   
   /**
    * Possible values of boolean representation.
    */
   public static final String[] BOOLEAN_TEXTS_NOYES 
                                   = new String[] {"No", "Yes"};
   
   // Attributes ///////////////////////////////////////////////////////////////
   
   /**
    * Code assigned to a given column. This code is unique only within a single 
    * data type, two data types can have the same column code but they have of 
    * course different data type codes.  
    */
   protected int m_iColumnCode;
   
   /**
    * Name of the column heading.
    */
   protected String m_strColumnName;

   /**
    * Tooltip for the column heading. 
    */
   protected String m_strColumnTooltip;

   /**
    * Integer representation of the column data type. 
    */
   protected int m_iColumnDataType;

   /**
    * Array of boolean values that will be used. Index 0 represents value for 
    * false and index 1 represents value for true.
    */
   protected String[] m_arrColumnBooleanValues;

    /**
    * Width of the column heading as a relative number compared to the width of 
    * other columns. This is used for internal calculation how wide the column 
    * should really be depending on what other columns exist.  
    */
   protected double m_dColumnWidth;
   
   /**
    * Flag signaling if the column has to be displayed when displaying list of 
    * items or not. Column should be flagged as mandatory to be displayed if it 
    * is for example used as a navigation element (e.g. hyperlink) to access 
    * data (for example name of the data). This is used when constructing list
    * definition since the user interface can indicate the user what columns will
    * be displayed regardless of his/her choice. 
    */
   protected boolean m_bIsColumnMandatory;

   // Constructors //////////////////////////////////////////////////////////
   
    /**
    * Constructor
    * 
    * @param iColumnCode - code assigned to a given column. This code is unique 
    *                      only within a single data type, two data types can
    *                      have the same column code but they have of course
    *                      different data type codes.  
    * @param strColumnName - column heading name
    * @param strColumnTooltip - column tooltip
    * @param iColumnDataType - type of data displayed in this column, one of the 
    *                          DataCondition.VALUE_TYPE_XYZ constants
    * @param dColumnWidth -  width of the column heading as a relative number 
    *                        compared to the width of other columns. 0 is valid  
    *                        value and means that the columns shouldn't be 
    *                        displayed   
    * @param bIsColumnMandatory - Flag signaling if the column has to be displayed 
    *                             when displaying list of items or not. Column 
    *                             should be flagged as mandatory to be displayed 
    *                             if it is for example used as a navigation element.                    
    */
   public ListColumnDefinition(
      int     iColumnCode,
      String  strColumnName,
      String  strColumnTooltip,
      int     iColumnDataType,
      double  dColumnWidth,
      boolean bIsColumnMandatory
   )
   {
      m_iColumnCode        = iColumnCode;
      m_strColumnName      = strColumnName;
      m_strColumnTooltip   = strColumnTooltip;
      m_iColumnDataType    = iColumnDataType;
      m_dColumnWidth       = dColumnWidth;
      m_bIsColumnMandatory = bIsColumnMandatory;
   }

   /**
    * Constructor
    * 
    * @param iColumnCode - code assigned to a given column. This code is unique 
    *                      only within a single data type, two data types can
    *                      have the same column code but they have of course
    *                      different data type codes.  
    * @param strColumnName - column heading name
    * @param strColumnTooltip - column tooltip
    * @param arrColumnBooleanValues - array of boolean values that will be used
    *                                 to display booleans values in this column
    *                                 assuming that the column data type is 
    *                                 boolean. Index 0 represents value for 
    *                                 false and index 1 represents value for 
    *                                 true. Provide are common use values in 
    *                                 form of BOOLEAN_TEXTS_XYZ constants
    * @param dColumnWidth -  width of the column heading as a relative number 
    *                        compared to the width of other columns. 0 is valid  
    *                        value and means that the columns shouldn't be 
    *                        displayed
    * @param bIsColumnMandatory - Flag signaling if the column has to be displayed 
    *                             when displaying list of items or not. Column 
    *                             should be flagged as mandatory to be displayed 
    *                             if it is for example used as a navigation element.                    
    */
   public ListColumnDefinition(
      int      iColumnCode,
      String   strColumnName,
      String   strColumnTooltip,
      String[] arrColumnBooleanValues,
      double   dColumnWidth,
      boolean  bIsColumnMandatory
   )
   {
      m_iColumnCode        = iColumnCode;
      m_strColumnName      = strColumnName;
      m_strColumnTooltip   = strColumnTooltip;
      m_iColumnDataType    = DataCondition.VALUE_TYPE_BOOLEAN;
      m_dColumnWidth       = dColumnWidth;
      m_bIsColumnMandatory = bIsColumnMandatory;
   }

   // Logic ////////////////////////////////////////////////////////////////////
   
   /**
    * @return int - column code
    */
   public int getColumnCode(
   )
   {
      return m_iColumnCode;
   }
   
   /**
    * @return double - column width
    */
   public double getColumnWidth()
   {
      return m_dColumnWidth;
   }

   /**
    * @return String - column name
    */
   public String getColumnName()
   {
      return m_strColumnName;
   }

   /**
    * @return int - column data type
    */
   public int getColumnDataType()
   {
      return m_iColumnDataType;
   }

   /**
    * @return String - column tooltip
    */
   public String getColumnTooltip()
   {
      return m_strColumnTooltip;
   }
   
   /**
    * Get string representation of boolean values to use for this columns.
    * 
    * @return String[] - index 0 represents value for false and index 1 
    *                    represents value for true.
    */
   public String[] getColumnBooleanValues()
   {
      return m_arrColumnBooleanValues;
   }

   /**
    * Method returns string values for boolean column. 
    * These values are separated by separator.
    * 
    * @param strSeparator - separator the values will be separated by
    * @return String - value names of the boolean column
    *                  [value for false][separator][value for true].
    */
   public String getColumnBooleanValueNames(
      String strSeparator
   )
   {
      StringBuilder sbOutput = new StringBuilder();

      if (m_arrColumnBooleanValues != null 
          && m_arrColumnBooleanValues.length > 0)
      {
         sbOutput.append(m_arrColumnBooleanValues[0]);
         sbOutput.append(strSeparator);
         sbOutput.append(m_arrColumnBooleanValues[1]);
      }
      else
      {
         sbOutput.append(strSeparator);
      }
      
      return sbOutput.toString();
   }

   /**
    * Check if the column has to be displayed when displaying list of items or 
    * not. Column should be flagged as mandatory to be displayed if it is for 
    * example used as a navigation element (e.g. hyperlink) to access data (for 
    * example name of the data). This is used when constructing list definition 
    * since the user interface can indicate the user what columns will be 
    * displayed regardless of his/her choice. 
    * 
    * @return boolean - true if the column is mandatory false otherwise
    */
   public boolean isColumnMandatory()
   {
      return m_bIsColumnMandatory;
   }
}
