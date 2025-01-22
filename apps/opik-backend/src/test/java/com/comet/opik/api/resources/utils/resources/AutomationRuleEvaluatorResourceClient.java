package com.comet.opik.api.resources.utils.resources;

import com.comet.opik.api.AutomationRuleEvaluator;
import com.comet.opik.api.AutomationRuleEvaluatorUpdateLlmAsJudge;
import com.comet.opik.api.resources.utils.TestHttpClientUtils;
import com.comet.opik.api.resources.utils.TestUtils;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import ru.vyarus.dropwizard.guice.test.ClientSupport;

import java.util.UUID;

import static com.comet.opik.infrastructure.auth.RequestContext.WORKSPACE_HEADER;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class AutomationRuleEvaluatorResourceClient {

    private static final String RESOURCE_PATH = "%s/v1/private/automations/projects/%s/evaluators/";

    private final ClientSupport client;
    private final String baseURI;

    public Response getEvaluator(UUID id, UUID projectId, String workspaceName, String apiKey, int expectedStatus) {
        var actualResponse = client.target(RESOURCE_PATH.formatted(baseURI, projectId))
                .path(id.toString())
                .request()
                .header(WORKSPACE_HEADER, workspaceName)
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(actualResponse.getStatusInfo().getStatusCode()).isEqualTo(expectedStatus);

        return actualResponse;
    }

    public Response findEvaluator(
            UUID projectId, String name, Integer page, Integer size, String workspaceName, String apiKey, int expectedStatus) {
        var actualResponse = client.target(RESOURCE_PATH.formatted(baseURI, projectId))
                .queryParam("name", name)
                .queryParam("page", page)
                .queryParam("size", size)
                .request()
                .header(WORKSPACE_HEADER, workspaceName)
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(actualResponse.getStatusInfo().getStatusCode()).isEqualTo(expectedStatus);

        return actualResponse;
    }
    public UUID createEvaluator(AutomationRuleEvaluator<?> evaluator, UUID projectId, String workspaceName,
            String apiKey) {
        try (var actualResponse = createEvaluator(evaluator, projectId, workspaceName, apiKey, HttpStatus.SC_CREATED)) {
            assertThat(actualResponse.getStatusInfo().getStatusCode()).isEqualTo(201);
            assertThat(actualResponse.hasEntity()).isFalse();
            return TestUtils.getIdFromLocation(actualResponse.getLocation());
        }
    }

    public Response createEvaluator(AutomationRuleEvaluator<?> evaluator, UUID projectId, String workspaceName,
            String apiKey,
            int expectedStatus) {
        var actualResponse = client.target(RESOURCE_PATH.formatted(baseURI, projectId))
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .header(WORKSPACE_HEADER, workspaceName)
                .post(Entity.json(evaluator));

        assertThat(actualResponse.getStatusInfo().getStatusCode()).isEqualTo(expectedStatus);

        return actualResponse;
    }

    public void updateEvaluator(UUID evaluatorId, UUID projectId, String workspaceName,
            AutomationRuleEvaluatorUpdateLlmAsJudge updatedEvaluator, String apiKey, boolean isAuthorized) {
        try (var actualResponse = client.target(RESOURCE_PATH.formatted(baseURI, projectId))
                .path(evaluatorId.toString())
                .request()
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(WORKSPACE_HEADER, workspaceName)
                .method(HttpMethod.PATCH, Entity.json(updatedEvaluator))) {

            if (isAuthorized) {
                assertThat(actualResponse.getStatusInfo().getStatusCode()).isEqualTo(204);
                assertThat(actualResponse.hasEntity()).isFalse();
            } else {
                assertThat(actualResponse.getStatusInfo().getStatusCode()).isEqualTo(401);
                assertThat(actualResponse.readEntity(io.dropwizard.jersey.errors.ErrorMessage.class))
                        .isEqualTo(TestHttpClientUtils.UNAUTHORIZED_RESPONSE);
            }
        }
    }

}
