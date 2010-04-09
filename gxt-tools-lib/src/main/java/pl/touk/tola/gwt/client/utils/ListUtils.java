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

package pl.touk.tola.gwt.client.utils;

import java.util.List;
import java.util.ArrayList;

public class ListUtils {

  /**
   * This operation exists because GWT doesn't support List.subList as of
   * release 1.5 RC1.
   *
   * Returns a new list that contains the elements in the specified range
   * of the given list.
   *
   * @param list
   * @param fromIndex low endpoint (inclusive) of the subList
   * @param toIndex high endpoint (exclusive) of the subList
   * @return a view of the specified range within this list
   * @throws IndexOutOfBoundsException for an illegal endpoint index value
   *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
   *         fromIndex &gt; toIndex</tt>)
   *
   * @since 0.3.1
   */
  public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
    // this implementation only runs fast in javascript code,
    // since all list implementations in GWT use javascript arrays, which
    // are random access
    ArrayList<T> ret = new ArrayList<T>();
    for (int i = fromIndex; i < toIndex; i++) {
      ret.add(list.get(i));
    }
    return ret;
  }
}
