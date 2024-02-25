/**
 * Copyright 2023  Yehor Kovalenko
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class SudokuFieldTest {
    @DisplayName("Value of the field can be changed")
    @Test
    void setFieldValue() {
        SudokuField field = new SudokuField();
        field.setFieldValue(5);
        assertEquals(5, field.getFieldValue());
    }

    @DisplayName("equals() method Positive Test")
    @Test
    void equalsPosTest() {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(5);
        SudokuField field2 = field1;
        SudokuField field3 = new SudokuField();
        field3.setFieldValue(5);

        assertEquals(field1, field2);
        assertEquals(field1, field3);
    }

    @DisplayName("equals() method Negative Test")
    @Test
    void equalsNegTest() {
        SudokuField field = new SudokuField();
        SudokuField field2 = new SudokuField();
        field2.setFieldValue(7);
        field.setFieldValue(5);
        assertFalse(field.equals(null));
        assertFalse(field.equals(field2));
    }

    @DisplayName("hashCode() method Positive Test")
    @Test
    void hashCodePosTest() {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(5);
        SudokuField field2 = new SudokuField();
        field2.setFieldValue(5);

        if (field1.equals(field2)) {
            assertTrue(field1.hashCode() == field2.hashCode());
        } else {
            fail();
        }
    }

    @DisplayName("hashCode() method Negative Test")
    @Test
    void hashCodeNegTest() {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(5);
        SudokuField field2 = new SudokuField();
        field2.setFieldValue(7);

        if (field1.hashCode() == field2.hashCode() && !field1.equals(field2)) {
            fail();
        }
        assertTrue(true);
    }

    @DisplayName("toString() method Test")
    @Test
    void toStringTest() {
        SudokuField field = new SudokuField();
        field.setFieldValue(5);
        String tmp = field.toString();
        assertEquals(tmp, field.toString());
    }

    @DisplayName("clone() method Test")
    @Test
    void cloneTest() {
        SudokuField field = new SudokuField();
        field.setFieldValue(5);
        try {
            SudokuField field1 = (SudokuField) field.clone();
            field1.setFieldValue(3);
            assertTrue(field.getFieldValue() != field1.getFieldValue());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Comparing test")
    @Test
    void compareToTest() {
        SudokuField field = new SudokuField();
        field.setFieldValue(4);
        SudokuField field2 = new SudokuField();
        field2.setFieldValue(1);
        assertEquals(field.compareTo(field2), 1);
        field2.setFieldValue(4);
        assertEquals(field.compareTo(field2), 0);
        field2.setFieldValue(8);
        assertEquals(field.compareTo(field2), -1);
        //assertThrows(NullPointerException.class, () -> field.compareTo(null));
    }
}