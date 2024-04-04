/*
 * Copyright 2023 - 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ai.bedrock.anthropic3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;

import org.springframework.ai.bedrock.anthropic3.api.Anthropic3ChatBedrockApi;
import org.springframework.ai.chat.BaseChatEngineTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

/**
 * @author Christian Tzolov
 */
@SpringBootTest
@EnabledIfEnvironmentVariable(named = "AWS_ACCESS_KEY_ID", matches = ".*")
@EnabledIfEnvironmentVariable(named = "AWS_SECRET_ACCESS_KEY", matches = ".*")
public class BedrockAnthropic3ChatEngineIT extends BaseChatEngineTests {

	@Autowired
	BedrockAnthropic3ChatEngineIT(BedrockAnthropic3ChatClient chatClient) {
		super(chatClient, chatClient);
	}

	@SpringBootConfiguration
	public static class TestConfiguration {

		@Bean
		public Anthropic3ChatBedrockApi anthropicApi() {
			return new Anthropic3ChatBedrockApi(Anthropic3ChatBedrockApi.AnthropicChatModel.CLAUDE_V3_SONNET.id(),
					EnvironmentVariableCredentialsProvider.create(), Region.US_EAST_1.id(), new ObjectMapper());
		}

		@Bean
		public BedrockAnthropic3ChatClient anthropicChatClient(Anthropic3ChatBedrockApi anthropicApi) {
			return new BedrockAnthropic3ChatClient(anthropicApi);
		}

	}

}