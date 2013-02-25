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

import java.io.Serializable;
import java.sql.Timestamp;
import org.opensubsystems.core.util.OSSObject;

/**
 * Logical condition used for filtering data.
 * 
 * @author OpenSubsystems
 */
public class DataCondition extends    OSSObject
                           implements Serializable
{
   // Constants ////////////////////////////////////////////////////////////////

   /**
    * Constant for no attribute set in the data condition. No attribute can be
    * used for uninitialized data conditions or for data conditions operations
    * of which is OPERATION_SQL_QUERY and value of which is VALUE_TYPE_SQL_QUERY
    * since in such case the value represent a portion of a query that should
    * be appended to the main query and therefore there is no need for any 
    * attribute. 
    */
   public static final int NO_ATTRIBUTE = 0;
   
   /**
    * Constant for no attribute set in the data condition.
    */
   public static final Integer NO_ATTRIBUTE_OBJ = new Integer(NO_ATTRIBUTE);

   // TODO: Everyone: The following values have to be synchronized with those in 
   // filter.js
   
   /**
    * Code for no operation set in the data condition.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int NO_OPERATION = 0;
   
   /**
    * Code for no operation set in the data condition.
    */
   public static final Integer NO_OPERATION_OBJ = new Integer(NO_OPERATION);
   
   /**
    * = (equals)operation code 
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_EQUALS = 1;
   
   /**
    * IN operation code
    * Value can be String, comma delimited String or array of objects (Object[])
    * or Collection of Objects.
    * This allows to for example do things like this ID: in (1,2,3,4)when 
    * Integer[] = [1,2,3,4,5] is passed in or when String "1,2,3,4,5" is passed 
    * in and the value type is id or integer.
    * But it is also possible to do ID in (SELECT ID FROM xxx WHERE yyy)if
    * String SELECT ID FROM xxx WHERE yyy is passed in, but in this case the
    * value type has to be sql query.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_IN = 2;
   
   /**
    * NOT IN operation code
    * Value can be String, comma delimited String or array of objects (Object[])
    * or Collection of Objects.
    * This allows to for example do things like this: ID not in (1,2,3,4)when 
    * Integer[] = [1,2,3,4,5] is passed in or when String "1,2,3,4,5" is passed 
    * in and the value type is id or integer.
    * But it is also possible to do: ID not in (SELECT ID FROM xxx WHERE yyy)
    * if String SELECT ID FROM xxx WHERE yyy is passed in, but in this case the
    * value type has to be sql query.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_IN = 3;

   /**
    * <> (not equals)operation code 
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_EQUALS = 4;
   
   /**
    * > (Greater than)operation code 
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_GREATER = 5;

   /**
    * >= (Greater or equals than)operation code 
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_GREATER_EQUALS = 6;

   /**
    * < (Less than)operation code 
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_LESS = 7;

   /**
    * <= (Less or equals than)operation code
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_LESS_EQUALS = 8;

   /**
    * Case unsensitive like %str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_CONTAINS_CASEUNSENSITIVE = 9;

   /**
    * Case sensitive like %str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_CONTAINS_CASESENSITIVE = 10;

   /**
    * Sql query operation
    * This operation is used if special query part have to be in the final query
    * In this case just create data condition of this type and set what ever 
    * attribute you want except NO_ATTRIBUTE, value will be String representing
    * query value type will be VALUE_TYPE_STRING.
    * In that case this query will be added to the final query as 
    * ... AND MY_QUERY AND ...
    * This way you can do very special queries.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_SQL_QUERY = 11;

   /**
    * Operation comparing two values or allowing null instead of one of the 
    * values. The value has to match index of value if 
    * ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_EQUALS_OR_NULL = 12;
   
   /**
    * Case unsensitive not like %str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_CONTAINS_CASEUNSENSITIVE = 13;
   
   /**
    * Case sensitive not like %str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_CONTAINS_CASESENSITIVE = 14;
   
   /**
    * Case unsensitive like str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_STARTS_CASEUNSENSITIVE = 15;

   /**
    * Case sensitive like str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_STARTS_CASESENSITIVE = 16;
   
   /**
    * Case unsensitive not like str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_STARTS_CASEUNSENSITIVE = 17;
   
   /**
    * Case sensitive not like str%. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_STARTS_CASESENSITIVE = 18;
   
   /**
    * Case unsensitive like %str. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_ENDS_CASEUNSENSITIVE = 19;
   
   /**
    * Case sensitive like %str. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_ENDS_CASESENSITIVE = 20;
   
   /**
    * Case unsensitive not like %str. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_ENDS_CASEUNSENSITIVE = 21;
   
   /**
    * Case sensitive not like %str. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_NOT_ENDS_CASESENSITIVE = 22;
   
   /**
    * Case unsensitive = str. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_EQUALS_CASEUNSENSITIVE = 23;
   
   /**
    * Case sensitive = str. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS. 
    */
   public static final int OPERATION_EQUALS_CASESENSITIVE = 24;

   /**
    * Case unsensitive <> str. Value type has to be VALUE_TYPE_STRING.
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS.
    */
   public static final int OPERATION_NOT_EQUALS_CASEUNSENSITIVE = 25;
   
   /**
    * Case sensitive <> str. Value type has to be VALUE_TYPE_STRING. 
    * The value has to match index of value if ListDatabaseUtils.OPERATIONS.
    */
   public static final int OPERATION_NOT_EQUALS_CASESENSITIVE = 26;

   /**
    * code for YES flag
    */
   public static final int DATA_CODE_FLAG_YES = 1;
   
   /**
    * Object code for YES flag
    */
   public static final Integer DATA_CODE_FLAG_YES_OBJ 
                                  = new Integer(DATA_CODE_FLAG_YES);
      
   /**
    * code for NO flag
    */
   public static final int DATA_CODE_FLAG_NO = 0;
   
   /**
    * Object code for NO flag
    */
   public static final Integer DATA_CODE_FLAG_NO_OBJ 
                                  = new Integer(DATA_CODE_FLAG_NO);

   // TODO: Everyone: The following values have to be synchronized with those in 
   // filter.js
   
   /**
    * Unknown value type constant.
    */
   public static final int VALUE_TYPE_UNKNOWN =  0;
   
   /**
    * Value type constant for ID. The value can be specified as a string 
    * representing an integer number or as an Integer value.
    */
   public static final int VALUE_TYPE_ID =  1;

   /**
    * Value type constant for Boolean. The value can be specified as a string 
    * representing an integer number where 0 is false and 1 is true or as a 
    * string representing boolean value true of false or as an Integer value 
    * where 0 is false and 1 is true or as a Boolean.
    */
   public static final int VALUE_TYPE_BOOLEAN =  2;

   /**
    * Value type constant for Integer. The value can be specified as an Integer
    * or an an String representing integer.
    */
   public static final int VALUE_TYPE_INTEGER =  3;
  
   /**
    * Value type constant for Double. The value can be specified as a Double, 
    * as an Integer or an an String representing double.
    */
   public static final int VALUE_TYPE_DOUBLE =  4;
   
   /**
    * Value type constant for String. The value has to be an string object.
    */
   public static final int VALUE_TYPE_STRING =  5;
   
   /**
    * Value type constant for Timestamp. The value can be specified as a 
    * Timestamp, as an Date or an an String representing timestamp that can be 
    * parsed using DateUtils.parseDateTime method.
    */
   public static final int VALUE_TYPE_TIMESTAMP =  6;

   /**
    * Value type constant for sql query, which is string that should be made
    * part of query without any additional processing.
    */
   public static final int VALUE_TYPE_SQL_QUERY =  7;

   // Attributes ///////////////////////////////////////////////////////////////
   
   /**
    * Generated serial version uid 
    */
   private static final long serialVersionUID = 8522583231285548064L;

   /**
    * Constant for logical attribute of a data object, which has relation to 
    * some value.
    */
   protected int m_iAttribute;
   
   /**
    * Operation, constant for the operation between the data object attribute 
    * the value of the specified attribute. 
    */
   protected int m_iOperation;
   
   /**
    * Value which applies to operation for given attribute. 
    */
   protected Object m_objValue;
   
   /**
    * Type of the value. One of VALUE_TYPE_XXX constants
    */
   protected int m_iValueType;
   
   /**
    * Original value which applies to operation for given attribute.
    * This attribute has to be set up because some operations can modify value
    * and original value will be lost. Therefore we need to store this original 
    * value within the extra parameter.   
    */
   protected Object m_objOriginalValue;

   // Constructors /////////////////////////////////////////////////////////////
   
   /**
    * Default empty constructor
    */
   public DataCondition()
   {
      this(DataCondition.NO_ATTRIBUTE, DataCondition.NO_OPERATION,
           null, VALUE_TYPE_STRING);
   }
   
   /**
    * Full constructor
    * 
    * @param iAttribute - the code for the attribute on which the operation 
    *                     should be performed, see the OPERATION_XXX constants
    * @param iOperation - what operation should be performed between the 
    *                     attribute and the value
    * @param objValue - any data object which is compared or which operate on 
    *                   the attribute in some fashion (e.g. attribute "count", 
    *                   operation "greater than", value "5"
    * @param iValueType - type of the value (see constants VALUE_TYPE_XXX),
    *                     this is used when inserting value to the SQL query
    *                     to put it there in correct format
    */
   public DataCondition(
      int    iAttribute,
      int    iOperation,
      Object objValue,
      int    iValueType
   )
   {
      this(iAttribute, iOperation, objValue, iValueType, objValue);
   }

   /**
    * Copy constructor
    * 
    * @param iAttribute - the code for the attribute on which the operation 
    *                     should be performed, see the OPERATION_XXX constants
    * @param iOperation - what operation should be performed between the 
    *                     attribute and the value
    * @param objValue - any data object which is compared or which operate on 
    *                   the attribute in some fashion (e.g. attribute "count", 
    *                   operation "greater than", value "5"
    * @param iValueType - type of the value (see constants VALUE_TYPE_XXX),
    *                     this is used when inserting value to the SQL query
    *                     to put it there in correct format
    * @param objOriginalValue - original value
    */
   public DataCondition(
      int    iAttribute,
      int    iOperation,
      Object objValue,
      int    iValueType,
      Object objOriginalValue
   )
   {
      m_iAttribute       = iAttribute;
      m_iOperation       = iOperation;
      m_objValue         = objValue;
      m_iValueType       = iValueType;
      m_objOriginalValue = objOriginalValue;
   }

   // Logic ////////////////////////////////////////////////////////////////////
   
   /**
    * @return int - attribute
    */
   public int getAttribute()
   {
      return m_iAttribute;
   }

   /**
    * @return int - operation
    */
   public int getOperation()
   {
      return m_iOperation;
   }

   /**
    * @return Object - value
    */
   public Object getValue()
   {
      return m_objValue;
   }
   
   /**
    * @param objValue - new object value
    */
   public void setValue(
      Object objValue
   )
   {
      m_objValue = objValue;
   }

   /**
    * @return int - code of value type
    */
   public int getValueType()
   {
      return m_iValueType;
   }

   /**
    * @return Object - originalValue
    */
   public Object getOriginalValue()
   {
      return m_objOriginalValue;
   }
   
   /**
    * @param objOriginalValue - new object original value
    */
   public void setOriginalValue(
      Object objOriginalValue
   )
   {
      m_objOriginalValue = objOriginalValue;
   }
   
   /**
    * @return String - representation of value
    */
   public String getValueString()
   {
      return getValueAsString(m_objValue);
   }
   
   /**
    * @return String - representation of original value
    */
   public String getOriginalValueString()
   {
      return getValueAsString(m_objOriginalValue);
   }
   
   // Helper methods ///////////////////////////////////////////////////////////
   
   /**
    * @param objValue - value to convert to string
    * @return String - representation of value
    */
   public String getValueAsString(
      Object objValue
   )
   {
      StringBuilder sbReturn = new StringBuilder();
      if (objValue != null)
      {
         
         if (m_iOperation != DataCondition.OPERATION_IN
            && m_iOperation != DataCondition.OPERATION_NOT_IN)
         {
            // parse data condition simple value
            switch (m_iValueType)
            {
               case (DataCondition.VALUE_TYPE_ID):
               {
                  sbReturn.append(((Integer)objValue).intValue());
                  break;
               }
               case (DataCondition.VALUE_TYPE_BOOLEAN):
               {
                  sbReturn.append(((Boolean)objValue).booleanValue());
                  break;
               }
               case (DataCondition.VALUE_TYPE_INTEGER):
               {
                  sbReturn.append(((Integer)objValue).intValue());
                  break;
               }
               case (DataCondition.VALUE_TYPE_DOUBLE):
               {
                  sbReturn.append(((Double)objValue).doubleValue());
                  break;
               }
               case (DataCondition.VALUE_TYPE_STRING):
               {
                  sbReturn.append(objValue);
                  break;
               }
               case (DataCondition.VALUE_TYPE_TIMESTAMP):
               {
                  sbReturn.append(((Timestamp)objValue).getTime());
                  break;
               }
               default :
               {
                  assert false : "Not supported data condition value type.";
               }
            }
         }
         else
         {
            // Parse data condition multiple value (IN or NOT IN operations)
            Object[] arrObject = (Object[])objValue;
            Object oHelp;
            for (int iCounter = 0; iCounter < arrObject.length; iCounter++)
            {
               oHelp = arrObject[iCounter];
               if (sbReturn.length()> 0)
               {
                  sbReturn.append(",");
               }
               switch (m_iValueType)
               {
                  case (DataCondition.VALUE_TYPE_ID):
                  {
                     sbReturn.append(((Integer)oHelp).intValue());
                     break;
                  }
                  case (DataCondition.VALUE_TYPE_BOOLEAN):
                  {
                     sbReturn.append(((Boolean)oHelp).booleanValue());
                     break;
                  }
                  case (DataCondition.VALUE_TYPE_INTEGER):
                  {
                     sbReturn.append(((Integer)oHelp).intValue());
                     break;
                  }
                  case (DataCondition.VALUE_TYPE_DOUBLE):
                  {
                     sbReturn.append(((Double)oHelp).doubleValue());
                     break;
                  }
                  case (DataCondition.VALUE_TYPE_STRING):
                  {
                     sbReturn.append(oHelp);
                     break;
                  }
                  case (DataCondition.VALUE_TYPE_TIMESTAMP):
                  {
                     sbReturn.append(((Timestamp)oHelp).getTime());
                     break;
                  }
                  default :
                  {
                     assert false : "Not supported data condition value type.";
                  }
               }
            }
         }
      }
      return sbReturn.toString();
   }
}
