/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ai.chat.observation;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;

import org.springframework.ai.model.observation.ModelUsageMetricsGenerator;

/**
 * Handler for generating metrics from chat model observations.
 *
 * @author Thomas Vitale
 * @since 1.0.0
 */
public class ChatModelMeterObservationHandler implements ObservationHandler<ChatModelObservationContext> {

	private final MeterRegistry meterRegistry;

	public ChatModelMeterObservationHandler(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	@Override
	public void onStop(ChatModelObservationContext context) {
		if (context.getResponse() != null && context.getResponse().getMetadata() != null
				&& context.getResponse().getMetadata().getUsage() != null) {
			ModelUsageMetricsGenerator.generate(context.getResponse().getMetadata().getUsage(), context,
					this.meterRegistry);
		}
	}

	@Override
	public boolean supportsContext(Observation.Context context) {
		return context instanceof ChatModelObservationContext;
	}

}