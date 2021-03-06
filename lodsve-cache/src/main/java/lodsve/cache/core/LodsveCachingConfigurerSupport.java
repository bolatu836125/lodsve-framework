package lodsve.cache.core;

import javax.annotation.PostConstruct;
import lodsve.base.utils.PropertyPlaceholderHelper;
import lodsve.base.config.SystemConfig;
import lodsve.base.config.auto.AutoConfigurationCreator;
import lodsve.base.config.auto.annotations.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;

/**
 * 缓存配置.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 16/3/31 上午10:12
 */
public class LodsveCachingConfigurerSupport extends CachingConfigurerSupport {
    protected CacheProperties cacheProperties;

    @PostConstruct
    public void init() throws Exception {
        AutoConfigurationCreator.Builder<CacheProperties> builder = new AutoConfigurationCreator.Builder<>();
        builder.setAnnotation(CacheProperties.class.getAnnotation(ConfigurationProperties.class));
        builder.setClazz(CacheProperties.class);

        cacheProperties = builder.build();
    }

    protected String replaceValue(String value) {
        return PropertyPlaceholderHelper.replacePlaceholder(value, false, SystemConfig.getAllConfigs());
    }
}
