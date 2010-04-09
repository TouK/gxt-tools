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
package pl.touk.top.dictionary.model.commons;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class Commons {

    public static void notNullOrEmptyString(String errorMessage, String... args) {

        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Nie podano argumentow do ewaluacji");
        }

        for (String s : args) {
            if (s == null || "".equals(s.trim())) {
                throw new IllegalArgumentException("Argument jest nullem lub pustym stringiem");
            }
        }
    }

    public static void assertNotNull(String errorMessage, Object... args) {

        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Nie podano argumentow do ewaluacji");
        }

        for (Object s : args) {
            if (s == null) {
                throw new IllegalArgumentException("Argument nie moze byc nullem");
            }
        }

    }

    /**
     * Returns true if input string is null or empty
     * 
     * @param value
     * @return
     */
    public static boolean nullOrEmptyString(String value) {

        if (value == null || "".equals(value)) {
            return true;
        }
        return false;
    }
}
