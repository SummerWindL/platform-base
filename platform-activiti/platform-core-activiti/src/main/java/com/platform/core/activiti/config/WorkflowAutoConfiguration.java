//package com.platform.core.activiti.config;
//
//import org.activiti.engine.cfg.ProcessEngineConfigurator;
//import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
//import org.activiti.engine.impl.history.HistoryLevel;
//import org.activiti.engine.impl.persistence.StrongUuidGenerator;
//import org.activiti.spring.ProcessEngineFactoryBean;
//import org.activiti.spring.SpringAsyncExecutor;
//import org.activiti.spring.SpringProcessEngineConfiguration;
//import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
//import org.activiti.spring.boot.ActivitiProperties;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Arrays;
//
///**
// * @author Advance
// * @date 2021年12月17日 11:39
// * @since V1.0.0
// */
//@Configuration
//@EnableConfigurationProperties(ActivitiProperties.class)
//public class WorkflowAutoConfiguration extends AbstractProcessEngineAutoConfiguration {
//    /**
//     *
//     * @param dataSource 数据源
//     * @param transactionManager 事物管理器
//     * @param springAsyncExecutor SpringAsyncExecutor
//     * @return SpringProcessEngineConfiguration
//     * @throws IOException 异常
//     */
//    @Bean
//    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
//            DataSource dataSource,
//            PlatformTransactionManager transactionManager,
//            SpringAsyncExecutor springAsyncExecutor) throws IOException {
//        activitiProperties.setProcessDefinitionLocationSuffixes(Arrays.asList("**.bpmn20.xml", "**.bpmn", "**.bar", "**.zip"));
//        return this.baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration configuration)
//            throws Exception {
//        configure(configuration);
//        return super.processEngine(configuration);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WorkflowTemplateSelector simpleWorkflowTemplateSelector(WorkflowMapper workflowMapper) {
//        return new SimpleWorkflowTemplateSelector(workflowMapper);
//    }
//
//    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
//        // 设置acitiviti主键生成策略
//        processEngineConfiguration.setIdGenerator(new StrongUuidGenerator());
//        processEngineConfiguration.setAsyncExecutorActivate(false);
//        processEngineConfiguration.setDbIdentityUsed(true);
//        processEngineConfiguration.setDeploymentMode("single-resource");
//        processEngineConfiguration.setHistory(HistoryLevel.FULL.getKey());
//        processEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);
//        processEngineConfiguration.setActivityFontName("宋体");
//        processEngineConfiguration.setLabelFontName("宋体");
//        processEngineConfiguration.setAnnotationFontName("宋体");
//        processEngineConfiguration.setCustomDefaultBpmnParseHandlers(
//                Arrays.asList(new JoyinUserTaskParseHandler(), new JoyinSequenceFlowParseHandler()));
//
//        processEngineConfiguration.addConfigurator(new ProcessEngineConfigurator() {
//
//            @Override
//            public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
//
//            }
//
//            @Override
//            public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
//                processEngineConfiguration.getCommandExecutor().execute(processEngineConfiguration.getSchemaCommandConfig(), new SchemaOperationsBuild());
//            }
//
//            @Override
//            public int getPriority() {
//                return 0;
//            }
//
//        });
//    }
//}
