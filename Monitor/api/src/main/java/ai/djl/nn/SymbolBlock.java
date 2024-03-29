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
package main.java.ai.djl.nn;

import ai.djl.ndarray.types.Shape;
import ai.djl.util.PairList;

/**
 * {@code SymbolBlock} is a {@link Block} is used to load models that were exported directly from
 * the engine in its native format.
 */
public interface SymbolBlock extends Block {

    /** Removes the last block in the symbolic graph. */
    void removeLastBlock();

    /**
     * Returns a {@link PairList} of output names and shapes stored in model file.
     *
     * @return the {@link PairList} of output names, and shapes
     */
    PairList<String, Shape> describeOutput();
}
