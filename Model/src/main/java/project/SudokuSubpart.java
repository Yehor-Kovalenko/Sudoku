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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class SudokuSubpart implements Serializable, Cloneable {

    private final List<SudokuField> group = Arrays.asList(new SudokuField[9]);

    public SudokuSubpart() {
        this.group.replaceAll(x -> new SudokuField());
    }

    public boolean verify() {
        for (int i = 0; i < group.size(); i++) {
            for (int j = i + 1; j < group.size(); j++) {
                if (group.get(j).equals(group.get(i))) {
                    return false;
                }
            }
        }
        return true;

    }

    public void set(int i, int value) {
        SudokuField field = new SudokuField();
        field.setFieldValue(value);
        this.group.set(i, field);
    }

    public int get(int i) {
        return group.get(i).getFieldValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuSubpart that = (SudokuSubpart) o;

        return new EqualsBuilder().append(group, that.group).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(group).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("group", group).toString();
    }

    @Override
    public abstract SudokuSubpart clone();
}
