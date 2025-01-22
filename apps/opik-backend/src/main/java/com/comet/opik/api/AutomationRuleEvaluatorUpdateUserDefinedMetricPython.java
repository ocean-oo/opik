package com.comet.opik.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import static com.comet.opik.api.AutomationRuleEvaluatorUserDefinedMetricPython.UserDefinedMetricPythonCode;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class AutomationRuleEvaluatorUpdateUserDefinedMetricPython
        extends
            AutomationRuleEvaluatorUpdate<UserDefinedMetricPythonCode> {

    @NotNull private final UserDefinedMetricPythonCode code;

    @Override
    public AutomationRuleEvaluatorType getType() {
        return AutomationRuleEvaluatorType.LLM_AS_JUDGE;
    }
}
