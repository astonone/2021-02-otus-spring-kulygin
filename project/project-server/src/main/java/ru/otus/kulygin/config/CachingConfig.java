package ru.otus.kulygin.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.dto.InterviewDto;
import ru.otus.kulygin.dto.InterviewTemplateDto;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.dto.pageable.*;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.util.List;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public javax.cache.CacheManager ehCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        CacheConfigurationBuilder<List, CandidatePageableDto> candidateCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        List.class, CandidatePageableDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(100, MemoryUnit.MB));
        javax.cache.configuration.Configuration<List, CandidatePageableDto> candidateConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(candidateCacheConfiguration);

        CacheConfigurationBuilder<List, InterviewerPageableDto> interviewersCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        List.class, InterviewerPageableDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(5, MemoryUnit.MB));
        javax.cache.configuration.Configuration<List, InterviewerPageableDto> interviewersConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(interviewersCacheConfiguration);

        CacheConfigurationBuilder<SimpleKey, InterviewerDto> interviewerCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        SimpleKey.class, InterviewerDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(5, MemoryUnit.MB));
        javax.cache.configuration.Configuration<SimpleKey, InterviewerDto> interviewerConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(interviewerCacheConfiguration);

        CacheConfigurationBuilder<List, InterviewPageableDto> interviewsCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        List.class, InterviewPageableDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(20, MemoryUnit.MB));
        javax.cache.configuration.Configuration<List, InterviewPageableDto> interviewsConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(interviewsCacheConfiguration);

        CacheConfigurationBuilder<String, InterviewDto> interviewCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class, InterviewDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(20, MemoryUnit.MB));
        javax.cache.configuration.Configuration<String, InterviewDto> interviewConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(interviewCacheConfiguration);

        CacheConfigurationBuilder<List, InterviewTemplateCriteriaPageableDto> criteriasCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        List.class, InterviewTemplateCriteriaPageableDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(10, MemoryUnit.MB));
        javax.cache.configuration.Configuration<List, InterviewTemplateCriteriaPageableDto> criteriasConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(criteriasCacheConfiguration);

        CacheConfigurationBuilder<InterviewTemplateCriteria, InterviewTemplateCriteria> criteriaCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        InterviewTemplateCriteria.class, InterviewTemplateCriteria.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(5, MemoryUnit.MB));
        javax.cache.configuration.Configuration<InterviewTemplateCriteria, InterviewTemplateCriteria> criteriaConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(criteriaCacheConfiguration);

        CacheConfigurationBuilder<List, InterviewTemplatePageableDto> templatesCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        List.class, InterviewTemplatePageableDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(10, MemoryUnit.MB));
        javax.cache.configuration.Configuration<List, InterviewTemplatePageableDto> templatesConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(templatesCacheConfiguration);

        CacheConfigurationBuilder<String, InterviewTemplateDto> templateCacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class, InterviewTemplateDto.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder().offheap(10, MemoryUnit.MB));
        javax.cache.configuration.Configuration<String, InterviewTemplateDto> templateConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(templateCacheConfiguration);

        cacheManager.createCache("candidates", candidateConfiguration);
        cacheManager.createCache("interviewers", interviewersConfiguration);
        cacheManager.createCache("interviewer", interviewerConfiguration);
        cacheManager.createCache("interviews", interviewsConfiguration);
        cacheManager.createCache("interview", interviewConfiguration);
        cacheManager.createCache("criterias", criteriasConfiguration);
        cacheManager.createCache("criteria", criteriaConfiguration);
        cacheManager.createCache("templates", templatesConfiguration);
        cacheManager.createCache("template", templateConfiguration);

        return cacheManager;
    }
}
