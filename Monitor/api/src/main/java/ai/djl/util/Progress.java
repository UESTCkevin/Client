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
package main.java.ai.djl.util;

/** An interface that allows tracking the progress of a task. */
public interface Progress {

    /**
     * Resets the progress tracking indicators, and sets the message and max to the given values.
     *
     * @param message the message to be shown
     * @param max the max value that the progress tracking indicator can take
     */
    default void reset(String message, long max) {
        reset(message, max, null);
    }

    /**
     * Resets the progress tracking indicators, and sets the message and max to the given values.
     *
     * @param message the message to be shown
     * @param max the max value that the progress tracking indicator can take
     * @param trailingMessage the trailing message to be shown
     */
    void reset(String message, long max, String trailingMessage);

    /**
     * Starts tracking the progress of the progress tracking indicators at the given initial value.
     *
     * @param initialProgress the initial value of the progress
     */
    void start(long initialProgress);

    /** Updates the tracking indicators to indicate that the task is complete. */
    void end();

    /**
     * Increments the progress tracking indicator by the given value.
     *
     * @param increment the value to increment the progress by
     */
    void increment(long increment);

    /**
     * Updates the progress tracking indicator to the given value.
     *
     * @param progress the value of the progress tracking indicator
     */
    default void update(long progress) {
        update(progress, null);
    }

    /**
     * Updates the progress tracking indicator to the given value, and displays the optional
     * message.
     *
     * @param progress the value of the progress tracking indicator
     * @param message the optional message
     */
    void update(long progress, String message);
}
