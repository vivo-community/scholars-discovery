/*
 * Copyright 2012-2022 the original author or authors.
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
package org.springframework.data.rest.webmvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import edu.tamu.scholars.middleware.discovery.resolver.BoostArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.FacetArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.FilterArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.HighlightArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.QueryArgumentResolver;
import edu.tamu.scholars.middleware.export.resolver.ExportArgumentResolver;

/**
 * {@link RequestMappingHandlerAdapter} implementation that adds a couple argument resolvers for controller method
 * parameters used in the REST exporter controller. Also only looks for handler methods in the Spring Data REST provided
 * controller classes to help isolate this handler adapter from other handler adapters the user might have configured in
 * their Spring MVC context.
 *
 * @author Jon Brisbin
 * @author Oliver Gierke
 */
public class RepositoryRestHandlerAdapter extends RequestMappingHandlerAdapter {

	private final List<HandlerMethodArgumentResolver> argumentResolvers;

	// MODIFIED: added to afford non default async thread executor
	private final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

	/**
	 * Creates a new {@link RepositoryRestHandlerAdapter} using the given {@link HandlerMethodArgumentResolver}s.
	 *
	 * @param argumentResolvers must not be {@literal null}.
	 */
	public RepositoryRestHandlerAdapter(List<HandlerMethodArgumentResolver> argumentResolvers) {
		this.argumentResolvers = new ArrayList<>(argumentResolvers);
		// MODIFIED: custom argument resolvers
		this.argumentResolvers.add(new QueryArgumentResolver());
		this.argumentResolvers.add(new FilterArgumentResolver());
		this.argumentResolvers.add(new FacetArgumentResolver());
		this.argumentResolvers.add(new BoostArgumentResolver());
		this.argumentResolvers.add(new HighlightArgumentResolver());
		this.argumentResolvers.add(new ExportArgumentResolver());

		// MODIFIED: custom thread executor settings
		executor.setCorePoolSize(16);
		executor.setMaxPoolSize(128);
		executor.setQueueCapacity(64);
		executor.setThreadNamePrefix("async-task-executor-");
		executor.initialize();
		setAsyncRequestTimeout(900000);
		setTaskExecutor(executor);
	}

	@Override
	public void afterPropertiesSet() {
		setCustomArgumentResolvers(argumentResolvers);
		super.afterPropertiesSet();
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	protected boolean supportsInternal(HandlerMethod handlerMethod) {

		Class<?> controllerType = handlerMethod.getBeanType();

		return AnnotationUtils.findAnnotation(controllerType, BasePathAwareController.class) != null;
	}
}
