/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.twitter.sdk.android.core;

import android.test.suitebuilder.annotation.SmallTest;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.FabricAndroidTestCase;
import retrofit.client.Header;

@SmallTest
public class TwitterRateLimitTest extends FabricAndroidTestCase {

    public static final String X_RATE_LIMIT_LIMIT = "x-rate-limit-limit";
    public static final String X_RATE_LIMIT_REMAINING = "x-rate-limit-remaining";
    public static final String X_RATE_LIMIT_RESET = "x-rate-limit-reset";

    private List<Header> headers;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        headers = new ArrayList<>();
    }

    public void testConstructor_nonePublic() {
        final Constructor<?>[] constructors = TwitterRateLimit.class.getConstructors();
        assertEquals(0, constructors.length);
    }

    public void testCreator_populatedHeader() {

        final String limit = "10";
        final String remaining = "20";
        final String reset = "30";
        headers.add(new Header(X_RATE_LIMIT_LIMIT, limit));
        headers.add(new Header(X_RATE_LIMIT_REMAINING, remaining));
        headers.add(new Header(X_RATE_LIMIT_RESET, reset));

        final TwitterRateLimit rateLimit = new TwitterRateLimit(headers);
        assertEquals(10, rateLimit.getLimit());
        assertEquals(20, rateLimit.getRemaining());
        assertEquals(30L, rateLimit.getReset());
    }

    public void testCreator_emptyHeader() {
        final List<Header> headers = new ArrayList<>();
        final TwitterRateLimit rateLimit = new TwitterRateLimit(headers);
        assertEquals(0, rateLimit.getLimit());
        assertEquals(0, rateLimit.getRemaining());
        assertEquals(0, rateLimit.getReset());
    }
}
