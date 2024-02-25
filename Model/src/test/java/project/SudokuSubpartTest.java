/**
 * Copyright 2023 Yehor Kovalenko and Andrei Pivavarau
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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SudokuSubpartTest {

    @DisplayName("Subpart contains only unique values")
    @Test
    void verifyPosTest() {
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            row.set(i, i);
        }
        assertTrue(row.verify());
    }

    @DisplayName("Subpart contains duplicates")
    @Test
    void verifyNegTest() {
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row.set(i, i);
        }
        row.set(3, 2);
        assertFalse(row.verify());
    }

    @DisplayName("Value in subpart can be changed")
    @Test
    void setFieldValueTest() {
        SudokuRow row = new SudokuRow();
        row.set(0, 5);
        assertEquals(5, row.get(0));
    }

    @DisplayName("equals() method Positive Test")
    @Test
    void equalsPosTest() {
        SudokuRow row1 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row1.set(i, i);
        }
        SudokuRow row2 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row2.set(i, i);
        }
        SudokuRow row3 = row1;
        assertTrue(row1.equals(row2));
        assertTrue(row1.equals(row3));
    }

    @DisplayName("equals() method Negative Test")
    @Test
    void equalsNegTest() {
        SudokuRow row1 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row1.set(i, i);
        }

        SudokuRow row2 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row2.set(i, i + 1);
        }

        assertFalse(row1.equals(null));
        assertFalse(row1.equals(row2));
    }

    @DisplayName("hashCode() method Positive Test")
    @Test
    void hashCodePosTest() {
        SudokuRow row1 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row1.set(i, i);
        }
        SudokuRow row2 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row2.set(i, i);
        }

        if (row1.equals(row2)) {
            assertTrue(row1.hashCode() == row2.hashCode());
        } else {
            fail();
        }
    }

    @DisplayName("hashCode() method Negative Test")
    @Test
    void hashCodeNegTest() {
        SudokuRow row1 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row1.set(i, i);
        }
        SudokuRow row2 = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row2.set(i, i + 1);
        }

        if (row1.hashCode() == row2.hashCode() && !row1.equals(row2)) {
            fail();
        }
        assertTrue(true);
    }

    @DisplayName("toString() method Test")
    @Test
    void toStringTest() {
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 8; i++) {
            row.set(i, i);
        }

        String tmp = row.toString();
        if (!tmp.equals(row.toString())) {
            fail();
        }
        assertTrue(true);
    }

    @DisplayName("clone() method Test")
    @Test
    void cloneTest() {
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            row.set(i, i);
        }
        SudokuColumn col = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            col.set(i, i);
        }
        SudokuBox box = new SudokuBox();
        for (int i = 0; i < 9; i++) {
            box.set(i, i);
        }
        SudokuRow row2 = row.clone();
        SudokuColumn col2 = col.clone();
        SudokuBox box2 = box.clone();
        assertNotSame(row2, row);
        assertNotSame(col2, col);
        assertNotSame(box2, box);
    }

}