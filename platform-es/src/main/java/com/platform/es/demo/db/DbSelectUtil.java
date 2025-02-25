package com.platform.es.demo.db;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 14:16
 */
public class DbSelectUtil {
    public static ThreadLocal<List<String>> DB_SELECTOR = new ThreadLocal<>();
}
