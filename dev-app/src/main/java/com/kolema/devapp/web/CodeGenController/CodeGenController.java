package com.kolema.devapp.web.CodeGenController;

import com.sigma.sigmacore.web.SigmaRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api
@RestController
@RequestMapping("/api/codgen")
public class CodeGenController {

    @ApiOperation(value = "get test")
    @GetMapping(value = "test")
    public String test() {
        return "ddd";
    }

    @ApiOperation(value = "post test")
    @PostMapping(value = "testPost")
    public Object testPost(@RequestBody SigmaRequest request) {
        return request;
    }

    @ApiOperation(value = "post file")
    @PostMapping(value = "testFile")
    public Object testPost(@RequestParam MultipartFile file) {
        return file;
    }
}
