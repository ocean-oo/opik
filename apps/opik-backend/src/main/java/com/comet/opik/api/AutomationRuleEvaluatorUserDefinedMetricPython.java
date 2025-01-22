package com.comet.opik.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.beans.ConstructorProperties;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.comet.opik.api.AutomationRuleEvaluatorUserDefinedMetricPython.UserDefinedMetricPythonCode;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class AutomationRuleEvaluatorUserDefinedMetricPython
        extends
            AutomationRuleEvaluator<UserDefinedMetricPythonCode> {

    @NotNull @JsonView({View.Public.class, View.Write.class})
    private final UserDefinedMetricPythonCode code;

    @Builder(toBuilder = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record UserDefinedMetricPythonCode(
            @JsonView( {
                    View.Public.class, View.Write.class}) @NotNull String metric,
            @JsonView({View.Public.class, View.Write.class}) @NotNull Map<String, String> arguments){
    }

    @ConstructorProperties({
            "id",
            "projectId",
            "name",
            "samplingRate",
            "code",
            "createdAt",
            "createdBy",
            "lastUpdatedAt",
            "lastUpdatedBy"
    })
    public AutomationRuleEvaluatorUserDefinedMetricPython(UUID id,
            UUID projectId,
            @NotBlank String name,
            Float samplingRate,
            @NotNull UserDefinedMetricPythonCode code,
            Instant createdAt,
            String createdBy,
            Instant lastUpdatedAt,
            String lastUpdatedBy) {
        super(id, projectId, name, samplingRate, createdAt, createdBy, lastUpdatedAt, lastUpdatedBy);
        this.code = code;
    }

    @Override
    public AutomationRuleEvaluatorType getType() {
        return AutomationRuleEvaluatorType.USER_DEFINED_METRIC_PYTHON;
    }
}
