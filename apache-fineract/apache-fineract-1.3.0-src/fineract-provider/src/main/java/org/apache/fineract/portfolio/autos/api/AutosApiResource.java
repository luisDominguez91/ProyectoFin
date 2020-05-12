/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.autos.api;

import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import javax.ws.rs.Path;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import io.swagger.annotations.*;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.portfolio.autos.data.AutosData;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/auto")
@Component
@Scope("singleton")
public class AutosApiResource {

    private final ToApiJsonSerializer<AutosData> toApiJsonSerializer;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    @Autowired
    public AutosApiResource(final ToApiJsonSerializer<AutosData> toApiJsonSerializer,
                            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService){
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Create a Auto", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AutosData", required = true, type = "body", dataTypeClass = AutosApiResourceSwagger.PostAutosRequest.class) })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = AutosApiResourceSwagger.PostAutosResponse.class) })
    public String create(@ApiParam(hidden = true) final String apiRequestBodyAsJson) {

        final CommandWrapper commandRequest = new CommandWrapperBuilder() //
                .crearAuto() //
                .withJson(apiRequestBodyAsJson) //
                .build(); //

        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return this.toApiJsonSerializer.serialize(result);
    }

}
