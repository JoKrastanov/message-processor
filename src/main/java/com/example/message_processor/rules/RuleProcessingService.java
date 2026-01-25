package com.example.message_processor.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.rules.action.Action;
import com.example.message_processor.rules.action.ActionExecutionService;
import com.example.message_processor.rules.condition.Condition;
import com.example.message_processor.rules.condition.ConditionEvaluationService;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class RuleProcessingService 
{
    @Nonnull
    private final ConditionEvaluationService conditionEvaluationService;   

    @Nonnull
    private final ActionExecutionService actionExecutionService;   

    @Nonnull
    private final RuleLoaderService ruleLoaderService;

    public Map<String, Object> applyRules(Map<String, Object> message)
    {
        Map<String, Object> output = new HashMap<>();
        log.info("Loading rule-set");
        List<Rule> rules = ruleLoaderService.getRules();
        log.info("Applying rules to message");
        rules.forEach(
            rule ->
                this.applyRule(
                    rule,
                    message,
                    output
                )
        );

        return output;
    }

    private void applyRule(
        Rule rule,
         Map<String, Object> message,
        Map<String, Object> output
    )
    {
        List<Condition> conditions = rule.getConditions();
        Boolean allConditionsPass = 
            conditions
                .stream()
                .allMatch(
                    condition ->
                        this.conditionEvaluationService.evaluate(
                            condition,
                            message
                        )
                );
                
        if (!allConditionsPass)
        {
            return;
        }

        List<Action> actions = rule.getActions();
        actions.forEach(
            action ->
                this.actionExecutionService.apply(
                    action,
                    message,
                    output
                )
        );
    }
}
