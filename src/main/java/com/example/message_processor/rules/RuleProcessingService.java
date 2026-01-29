package com.example.message_processor.rules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.messaging.repository.Message;
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

    public void applyRules(
        Map<String, Object> message,
        Message processedMessage
    )
    {
        log.info("Loading rule-set");
        List<Rule> rules = ruleLoaderService.getRules();
        log.info("Applying rules to message");
        rules.forEach(
            rule ->
                this.applyRule(
                    rule,
                    message,
                    processedMessage
                )
        );
    }

    private void applyRule(
        Rule rule,
        Map<String, Object> message,
        Message processedMessage
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
            {
                this.actionExecutionService.apply(
                    action,
                    message
                );
                
                // After each rule is applied, update the message in the db
                processedMessage.setLast_update(LocalDateTime.now());
                processedMessage.setCurrentMessage(message);
            }
        );

        // After all rules have been applied update the status and time of processing in the db
        processedMessage.setStatus("COMPLETED");
        processedMessage.setTime_processed(LocalDateTime.now());
    }
}
