package com.quasar.rest;

import com.quasar.model.Point;
import com.quasar.service.SatelliteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Controller("/satellite_location")
@Slf4j
public class SatelliteController {


    @Inject
    private SatelliteService satelliteService;

    @Post("/{satelliteName}")
    public HttpResponse post(@PathVariable(value = "satelliteName") @NotNull String satelliteName,
                                          @Body @Valid Point satelliteLocation) {
        log.debug("received POST: /satellite_location/{} {}", satelliteName, satelliteLocation);
        satelliteService.saveLocation(satelliteName, satelliteLocation);
        return HttpResponse.ok();
    }

    @Get
    public Map<String, Point> getLocaltions(){
        return satelliteService.getAllLocations();
    }




}
