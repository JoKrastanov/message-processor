package com.example.message_processor.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RuleLoaderService
{
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Value("${application.rules.config-file}")
    private String ruleConfigFilePath;

    @Getter
    private List<Rule> rules = new ArrayList<>();

    @PostConstruct
    public void loadRules()
    {
        try
        {
            Resource ruleConfigFile = this.resourceLoader.getResource(
                this.ruleConfigFilePath
            );

            if (!ruleConfigFile.exists())
            {
                // Throw error?
            }

            this.rules = Arrays.asList(
                objectMapper
                    .readValue(
                        ruleConfigFile.getInputStream(),
                        Rule[].class
                    )
            );
        }
        catch (Exception e)
        {

        }
    }
}
