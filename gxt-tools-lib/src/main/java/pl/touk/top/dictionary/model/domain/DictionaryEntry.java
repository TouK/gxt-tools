/*
 * Copyright (c) 2008 TouK.pl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.touk.top.dictionary.model.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
@Entity
@Table(name = "V_DICTIONARY")
public class DictionaryEntry implements Serializable {
// ------------------------------ FIELDS ------------------------------

    public static enum EntryProperty{id, category, entryKey, value, comment, lazyLoad}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String category;

    protected String entryKey;

    protected String value;

    @Column(name="DESCRIPTION")
    protected String comment;

    protected boolean lazyLoad;

// --------------------------- CONSTRUCTORS ---------------------------

    public DictionaryEntry() {
    }

    public DictionaryEntry(String category,String entryKey, String value, String comment, boolean lazyLoad) {
        this.category = category;
        this.entryKey = entryKey;
        this.value = value;
        this.comment = comment;
        this.lazyLoad = lazyLoad;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryKey() {
        return entryKey;
    }

    public void setEntryKey(String entryKey) {
        this.entryKey = entryKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isLazyLoad() {
        return lazyLoad;
    }

    public void setLazyLoad(boolean lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DictionaryEntry");
        sb.append("{id=").append(id);
        sb.append(", category='").append(category).append('\'');
        sb.append(", entryKey='").append(entryKey).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", lazyLoad=").append(lazyLoad);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryEntry that = (DictionaryEntry) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (entryKey != null ? !entryKey.equals(that.entryKey) : that.entryKey != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (entryKey != null ? entryKey.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
