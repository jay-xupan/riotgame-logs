package com.riotgame.controller;

import com.riotgame.model.ReponseSummary;
import com.riotgame.utils.LogUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @GetMapping("/percentile")
    @ApiOperation("API reponse percentitle")
    public List<ReponseSummary> percentile() throws IOException {
        return LogUtil.percentile();
    }

}
