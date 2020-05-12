package org.apache.fineract.portfolio.autos.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

final class AutosApiResourceSwagger{
    private AutosApiResourceSwagger(){
    }

    @ApiModel(value = "PostAutosRequest")
    public final static class PostAutosRequest{
        private PostAutosRequest(){
        }

        @ApiModelProperty(example="Marca de Auto")
        public String marca;

        @ApiModelProperty(example="04 March 2020")
        public String fechaApertura;
    }

    @ApiModel(value="PostAutosResponse")
    public final static class PostAutosResponse{
        private PostAutosResponse(){
        }

        @ApiModelProperty(example="1")
        public Integer ctmId;
    }
}