/*
 * Copyright 2012-2018 MarkLogic Corporation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.marklogic.quickstart.web;


import com.marklogic.hub.ApplicationConfig;
import com.marklogic.hub.HubConfig;
import com.marklogic.hub.impl.HubConfigImpl;
import com.marklogic.quickstart.DataHubApiConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DataHubApiConfiguration.class, ApplicationConfig.class, HubConfigJsonTest.class})
@JsonTest
public class HubConfigJsonTest {

    protected static final String PROJECT_PATH = "ye-olde-project";

    @Autowired
    private JacksonTester<HubConfig> jsonSerializer;

    @Autowired
    private HubConfigImpl hubConfig;

    @Test
    public void testSerialize() throws IOException {
        hubConfig.createProject(PROJECT_PATH);
        hubConfig.initHubProject();
        hubConfig.refreshProject();

        String expected = "{\n" +
            "  \"stagingDbName\": \"data-hub-STAGING\",\n" +
            "  \"stagingHttpName\": \"data-hub-STAGING\",\n" +
            "  \"stagingForestsPerHost\": 3,\n" +
            "  \"stagingPort\": 8010,\n" +
            "  \"stagingAuthMethod\": \"digest\",\n" +
            "  \"finalDbName\": \"data-hub-FINAL\",\n" +
            "  \"finalHttpName\": \"data-hub-FINAL\",\n" +
            "  \"finalForestsPerHost\": 3,\n" +
            "  \"finalPort\": 8011,\n" +
            "  \"finalAuthMethod\": \"digest\",\n" +
            "  \"jobDbName\": \"data-hub-JOBS\",\n" +
            "  \"jobHttpName\": \"data-hub-JOBS\",\n" +
            "  \"jobForestsPerHost\": 4,\n" +
            "  \"jobPort\": 8013,\n" +
            "  \"jobAuthMethod\": \"digest\",\n" +
            "  \"modulesDbName\": \"data-hub-MODULES\",\n" +
            "  \"stagingTriggersDbName\": \"data-hub-staging-TRIGGERS\",\n" +
            "  \"stagingSchemasDbName\": \"data-hub-staging-SCHEMAS\",\n" +
            "  \"finalTriggersDbName\": \"data-hub-final-TRIGGERS\",\n" +
            "  \"finalSchemasDbName\": \"data-hub-final-SCHEMAS\",\n" +
            "  \"modulesForestsPerHost\": 1,\n" +
            "  \"stagingTriggersForestsPerHost\": 1,\n" +
            "  \"stagingSchemasForestsPerHost\": 1,\n" +
            "  \"finalTriggersForestsPerHost\": 1,\n" +
            "  \"finalSchemasForestsPerHost\": 1,\n" +
            "  \"hubRoleName\": \"data-hub-role\",\n" +
            "  \"hubUserName\": \"data-hub-user\",\n" +
            "  \"customForestPath\": \"forests\",\n" +
            "  \"modulePermissions\": \"rest-reader,read,rest-writer,insert,rest-writer,update,rest-extension-user,execute,data-hub-role,read,data-hub-role,execute\",\n" +
            "  \"jarVersion\": \"" + hubConfig.getJarVersion() + "\"\n" +
            "}";
        assertThat(jsonSerializer.write(hubConfig)).isEqualToJson(expected);
    }
}
