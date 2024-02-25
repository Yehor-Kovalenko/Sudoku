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

import exceptions.BadCompareArgument;
import exceptions.BadSudokuFieldValueException;
import exceptions.CloningException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Represents the field that the SudokuBoard consists of
 */
public class SudokuField implements Serializable, Comparable<SudokuField>, Cloneable {
    private int value = 0;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", value).toString();
    }

    /**
     * Getter for the value of the SudokuField object
     * @return number from 0 to 9
     */
    public int getFieldValue() {
        return this.value;
    }

    /**
     * Setter for the value of the SudokuField object
     * @param value - new value to be set, should be between 0 and 9
     */
    public void setFieldValue(int value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
        } else {
            logger.throwing(Level.ERROR, new BadSudokuFieldValueException());
            throw new BadSudokuFieldValueException("sudokuField_set");
        }
    }

    @Override
    public int compareTo(SudokuField o) throws NullPointerException {
        if (o == null) {
            logger.throwing(Level.ERROR, new BadCompareArgument());
            throw new BadCompareArgument("sudokuField_compare");
        }
        if (this.equals(o)) {
            return 0;
        } else if (this.value > o.value) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public SudokuField clone() throws CloningException {
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            logger.catching(Level.ERROR, new CloningException(e));
            throw new CloningException("sudokuField_clone");
        }
    }
}
