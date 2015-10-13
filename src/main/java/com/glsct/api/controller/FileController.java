package com.glsct.api.controller;

import com.glsct.api.constant.services.ResponseResult;
import com.glsct.api.constant.services.ServiceStatusCode;
import com.glsct.api.constant.url.RequestMapperURL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.HttpRetryException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015/10/7.
 */
@Controller
public class FileController extends AbstractController {

    @RequestMapping(value = {"/index.html", "index", "/"}, method = RequestMethod.GET)
    public String testIndex() {
        return "index";
    }

    @RequestMapping(value = {RequestMapperURL.FILE_UPLOAD}, method = RequestMethod.POST)
    public
    @ResponseBody
    Callable<?> uploadFile(final @RequestParam("file") List<MultipartFile> files ,final HttpServletRequest request) {
        final String webRoot = request.getServletContext().getRealPath("");
        final String savePath = webRoot +File.separator + "res" + File.separator  + "upload" +File.separator;
        try{
            return   new Callable<Map<String,Object>>() {
                @Override
                public Map<String,Object> call() throws Exception {
                    final List<String> filesPaths = new ArrayList<>();
                    final String prefix = "/res/upload/";
                    for (MultipartFile file : files) {
                        if(file.getContentType().contains("image")){
                            String originFileName = file.getOriginalFilename();
                            String suffix = originFileName.substring(originFileName.lastIndexOf("."));
                            String newFileName = UUID.randomUUID().toString() +  suffix;
                            file.transferTo(new File(savePath + newFileName));
                            filesPaths.add(prefix + newFileName);
                        }
                    }
                    Map<String,Object> result = new HashMap<>();
                    result.put("files" ,filesPaths);
                    result.put(ResponseResult.Status_Code, ServiceStatusCode.Sucess);
                    return result;
                }
            };
        }catch (final Exception e){
            return super.warpFeatureExecption(e);
        }
    }

}

