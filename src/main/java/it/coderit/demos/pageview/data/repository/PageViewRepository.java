package it.coderit.demos.pageview.data.repository;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PageViewRepository {
    private final KeyCommands<String> keyCommands;
    private final ValueCommands<String, Long> longCommands;

    public PageViewRepository(RedisDataSource ds) {
        longCommands = ds.value(Long.class);
        keyCommands = ds.key();
    }

    public long incrementPageView(String pageName) {
        String redisKey = String.format("pageviews:%s", pageName);
        return longCommands.incr(redisKey);
    }

    public Map<String, Long> getStatistics() {
        // Chiave: nome pagina
        // Valore: numero di pageViews
        // KEYS pageviews:*
        // for page in ....
        //  GET pageviews:nomePagina
        Map<String, Long> statistics = new HashMap<>();
        List<String> allRedisKeys = keyCommands.keys("pageviews:*");
        for (String redisKey : allRedisKeys) {
            String pageName = redisKey.replace("pageviews:", "");
            long pageViews = longCommands.get(redisKey);
            statistics.put(pageName, pageViews);
        }
        return statistics;
    }
    public Map<String, Long> getStatistics2() {
        // Chiave: nome pagina
        // Valore: numero di pageViews
        // KEYS pageviews:*
        // for page in ....
        //  GET pageviews:nomePagina
        Map<String, Long> statistics = new HashMap<>();
        List<String> allRedisKeys = keyCommands.keys("pageviews:*");
        Map<String, Long> allValues = longCommands.mget(allRedisKeys.toArray(new String[0]));
        for (Map.Entry<String, Long> e : allValues.entrySet()) {
            String pageName = e.getKey().replace("pageviews:", "");
            Long pageViews = e.getValue();
            statistics.put(pageName, pageViews);
        }
        return statistics;
    }


}
