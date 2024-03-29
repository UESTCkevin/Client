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
package main.java.ai.djl.nn.recurrent;

import ai.djl.nn.Block;
import ai.djl.nn.recurrent.RecurrentBlock;
import ai.djl.util.Preconditions;

/**
 * {@code RNN} is an implementation of recurrent neural networks which applies a single-gate
 * recurrent layer to input. Two kinds of activation function are supported: ReLU and Tanh.
 *
 * <p>Current implementation refers the [paper](https://crl.ucsd.edu/~elman/Papers/fsit.pdf),
 * Finding structure in time - Elman, 1988.
 *
 * <p>The RNN operator is formulated as below:
 *
 * <p>With ReLU activation function: \(h_t = relu(W_{ih} * x_t + b_{ih} + W_{hh} * h_{(t-1)} +
 * b_{hh})\)
 *
 * <p>With Tanh activation function: \(h_t = \tanh(W_{ih} * x_t + b_{ih} + W_{hh} * h_{(t-1)} +
 * b_{hh})\)
 */
public class RNN extends RecurrentBlock {

    /**
     * Creates a vanilla RNN block.
     *
     * @param builder the builder used to create the RNN block
     */
    RNN(Builder builder) {
        super(builder);
        mode = builder.activation == Activation.RELU ? "rnn_relu" : "rnn_tanh";
        gates = 1;
    }

    /**
     * Creates a builder to build a {@link RNN}.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /** The Builder to construct a {@link RNN} type of {@link Block}. */
    public static final class Builder extends BaseBuilder<Builder> {

        /** {@inheritDoc} */
        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Sets the activation for the RNN - ReLu or Tanh.
         *
         * @param activation the activation
         * @return this Builder
         */
        public Builder setActivation(Activation activation) {
            this.activation = activation;
            return self();
        }

        /**
         * Builds a {@link RNN} block.
         *
         * @return the {@link RNN} block
         */
        public RNN build() {
            Preconditions.checkArgument(
                    stateSize > 0 && numStackedLayers > 0,
                    "Must set stateSize and numStackedLayers");
            return new RNN(this);
        }
    }

    /** An enum that enumerates the type of activation. */
    public enum Activation {
        RELU,
        TANH
    }
}
