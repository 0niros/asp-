package cn.com.oniros.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * cn.com.oniros.security.util.JsonUtils
 *
 * @author Li Xiaoxu
 * 2024/4/14 15:56
 */
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static String serialize(Object obj) throws JsonProcessingException {
        return MAPPER.writeValueAsString(obj);
    }

}
