package it.prisma.prismabooking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RequestLoggingService {

    public void logRequest(HttpServletRequest request, Object body) {
        if (request.getRequestURI().equals("/error"))
            return;

        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> params = buildParametersMap(request);

        stringBuilder.append("Request ");
        stringBuilder.append(request.getMethod()).append(" ");
        stringBuilder.append(request.getRequestURI());

        if (!params.isEmpty())
            stringBuilder.append(" params=").append(params.toString().replace("=", ":")).append(" ");

        if (body != null)
            stringBuilder.append(" body=[").append(body).append("]");

        log.info(stringBuilder.toString());
    }

    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        if (request.getRequestURI().equals("/error"))
            return;

        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> params = buildParametersMap(request);

        stringBuilder.append("Response ");
        String statusCode = String.valueOf(response.getStatus());
        stringBuilder.append(statusCode).append(" ");
        stringBuilder.append(request.getMethod()).append(" ");
        stringBuilder.append(request.getRequestURI()).append(" ");

        if (!params.isEmpty())
            stringBuilder.append(" params=").append(params.toString().replace("=", ":")).append(" ");

        if (statusCode.matches("^2.."))
            log.info(stringBuilder.toString());
        else
            log.error(stringBuilder.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        Map<String, String[]> params = request.getParameterMap();

        params.forEach((key, value) -> result.put(key, String.join(", ", value)));
        return result;
    }
}