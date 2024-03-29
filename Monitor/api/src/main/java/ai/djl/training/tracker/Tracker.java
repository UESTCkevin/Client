/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package main.java.ai.djl.training.tracker;

import ai.djl.training.tracker.*;

/**
 * A {@code Tracker} represents a hyper-parameter that changes gradually through the training
 * process.
 */
public interface Tracker {

    /**
     * Fetches the value after the given number of steps/updates.
     *
     * @param numUpdate the total number of steps/updates
     * @return this {@code Builder}
     */
    float getNewValue(int numUpdate);

    /**
     * Returns a new instance of {@link ai.djl.training.tracker.FactorTracker.Builder} that can
     * build an {@link FactorTracker}.
     *
     * @return the {@link FactorTracker} {@link FactorTracker.Builder}
     */
    static FactorTracker.Builder factor() {
        return new FactorTracker.Builder();
    }

    /**
     * Returns a new instance of {@link WarmUpTracker.Builder} that can build an {@link
     * WarmUpTracker}.
     *
     * @return the {@link WarmUpTracker} {@link WarmUpTracker.Builder}
     */
    static WarmUpTracker.Builder warmUp() {
        return new WarmUpTracker.Builder();
    }

    /**
     * Returns a new instance of {@link ai.djl.training.tracker.MultiFactorTracker.Builder} that can
     * build an {@link MultiFactorTracker}.
     *
     * @return the {@link MultiFactorTracker} {@link
     *     MultiFactorTracker.Builder}
     */
    static MultiFactorTracker.Builder multiFactor() {
        return new MultiFactorTracker.Builder();
    }

    /**
     * Returns a new instance of {@link ai.djl.training.tracker.CosineTracker.Builder} that can
     * build an {@link CosineTracker}.
     *
     * @return the {@link CosineTracker} {@link CosineTracker.Builder}
     */
    static CosineTracker.Builder cosine() {
        return new CosineTracker.Builder();
    }

    /**
     * Returns a new instance of {@link Tracker} with a fixed value.
     *
     * @param value the fixed value
     * @return a instance of {@link Tracker} with a fixed value
     */
    static Tracker fixed(float value) {
        return FixedTracker.builder().setValue(value).build();
    }
}
