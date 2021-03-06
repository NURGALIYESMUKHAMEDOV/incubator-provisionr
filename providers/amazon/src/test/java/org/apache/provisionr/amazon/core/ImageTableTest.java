/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.provisionr.amazon.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.io.IOException;
import java.util.Map;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

public class ImageTableTest {

    @Test
    public void testLoadAmiTableFromResource() throws IOException {
        ImageTable table = ImageTable.fromCsvResource("/org/apache/provisionr/amazon/ubuntu.csv");

        String id = table.query().filterBy("region", "us-east-1").filterBy("version", "12.04 LTS")
            .filterBy("arch", "amd64").filterBy("type", "instance-store").singleResult();

        assertThat(id).isEqualTo("ami-9a873ff3");
    }

    @Test
    public void testExtractHeaderLine() throws Exception {
        Iterable<String> headers = ImageTable.extractHeaders(ImmutableList.of("a,b,c", "1,2,3"));
        assertThat(headers).containsAll(ImmutableList.of("a", "b", "c"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCombineHeadersWithLinePartsAsTableCells() {
        final ImmutableList<String> headers = ImmutableList.of("a", "b");
        final ImmutableList<String> lineParts = ImmutableList.of("1", "2");

        Iterable<Table.Cell<String, String, String>> cells =
            ImageTable.combineHeadersWithLinePartsAsTableCells(0, headers, lineParts);

        assertThat(cells).contains(Tables.immutableCell("0", "a", "1"));
    }

    @Test
    public void testZipIterators() {
        Iterable<Map.Entry<String, String>> entries = ImageTable.zip(
            ImmutableList.of("a", "b"), ImmutableList.of("1", "2"));

        assertThat(entries).containsAll(ImmutableList.of(
            Maps.immutableEntry("a", "1"), Maps.immutableEntry("b", "2")));
    }
}
