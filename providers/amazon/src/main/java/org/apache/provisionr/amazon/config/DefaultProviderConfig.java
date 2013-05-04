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

package org.apache.provisionr.amazon.config;

import org.apache.provisionr.amazon.AmazonProvisionr;
import org.apache.provisionr.api.provider.Provider;
import org.apache.provisionr.api.provider.ProviderBuilder;
import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkNotNull;

public class DefaultProviderConfig {

    private final String accessKey;
    private final String secretKey;
    private final String region;
    private final String endpoint;

    public DefaultProviderConfig(String accessKey, String secretKey, String region, String endpoint) {
        this.accessKey = checkNotNull(accessKey, "accessKey is null");
        this.secretKey = checkNotNull(secretKey, "secretKey is null");
        this.region = checkNotNull(region, "region is null");
        this.endpoint = checkNotNull(endpoint, "endpoint is null");
    }

    public Optional<Provider> createProvider() {
        if (accessKey.isEmpty() || secretKey.isEmpty()) {
            return Optional.absent();
        }
        final ProviderBuilder builder = Provider.builder()
            .id(AmazonProvisionr.ID)
            .accessKey(accessKey)
            .secretKey(secretKey);

        if (!region.isEmpty()) {
            builder.option("region", region);
        }

        if (endpoint.isEmpty()) {
            builder.endpoint(Optional.<String>absent());
        } else {
            builder.endpoint(endpoint);
        }

        return Optional.of(builder.createProvider());
    }
}
