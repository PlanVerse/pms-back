package seg.playground.pms_back.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import seg.playground.pms_back.common.exception.BaseException;
import seg.playground.pms_back.common.exception.code.StatusCode;

@Slf4j
@Component
public class RestUtil {

    private static RestTemplate restTemplate;
    private static ObjectMapper objectMapper;

    @SuppressWarnings("static-access")
    public RestUtil(RestTemplateBuilder restTemplateBuilder,
                    ObjectMapper objectMapper) {

        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public static <T> ResponseEntity<T> get(String url, String token, Map<String, String> params, Class<T> responseType) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        if (StringUtils.isNotBlank(token)) {
            headers.setBearerAuth(token);
        }

        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType, params);
    }

    public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, Map<String, ?> params, Class<T> responseType) throws RestClientException {
        if (CollectionUtils.isEmpty(params)) {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
        } else {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType, params);
        }
    }

    public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, Map<String, ?> params, ParameterizedTypeReference<T> responseType) throws RestClientException {
        if (CollectionUtils.isEmpty(params)) {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
        } else {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType, params);
        }
    }

    public static <T1, T2> ResponseEntity<T2> post(String url, String token, T1 params, Class<T2> responseType) throws RestClientException, Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        if (StringUtils.isNotBlank(token)) {
            headers.setBearerAuth(token);
        }

        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<T1>(params, headers), responseType);
    }

    public static <T1, T2> ResponseEntity<T2> post(String url, HttpHeaders headers, T1 params, Class<T2> responseType) throws RestClientException {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<T1>(params, headers), responseType);
    }

    public static <T1, T2> ResponseEntity<T2> post(String url, HttpHeaders headers, T1 params, ParameterizedTypeReference<T2> responseType) throws RestClientException {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<T1>(params, headers), responseType);
    }

    public static <T1, T2> ResponseEntity<T2> put(String url, HttpHeaders headers, T1 params, ParameterizedTypeReference<T2> responseType) throws RestClientException {
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<T1>(params, headers), responseType);
    }

    public static <T> void responseCheck(ResponseEntity<T> response, int checkCode) throws Exception {
        if (response == null) {
            throw new BaseException(StatusCode.BAD_REQUEST);
        }

        if (response.getStatusCode().value() != checkCode) {
            throw new BaseException(StatusCode.NOT_EXPECT);
        }
    }

    public static HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }

    public static String getJsonToPrettyString(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                if (objectMapper == null) {
                    ObjectMapper om = new ObjectMapper();
                    om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                    return om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                } else {
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                }
            } catch (Exception e) {
                log.error("RestUtil : getJsonStr method error", e);
                return "error";
            }
        }
    }

    public static String getJsonToString(Object object) {
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("RestUtil : getJsonStr method error", e);
        }

        return jsonString;
    }

    public static <T> T getStringToObject(String str, Class<T> clazz) throws JsonProcessingException {
        if (str == null) {
            return null;
        }
        return objectMapper.readValue(str, clazz);
    }

    public static <T> T getStringToObject(String str, TypeReference<T> typeRef) throws IOException {
        if (str == null) {
            return null;
        }
        return objectMapper.readValue(str, typeRef);
    }

    public static Map<String, String> getObjectToMap(Object param) {
        return objectMapper.convertValue(param, new TypeReference<>() {
        });
    }
}
