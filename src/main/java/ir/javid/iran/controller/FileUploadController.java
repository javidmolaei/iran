package ir.javid.iran.controller;

/**
 * Created by jvd.molaei on 2022-01-25.
 */

import ir.javid.iran.helper.UploadFile.FileInfo;
import ir.javid.iran.helper.UploadFile.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/files")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload-single-file")
    public ResponseEntity<FileInfo> uploadASingleFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("json") String fileJson) throws IOException {

        return ResponseEntity.ok(fileService.uploadSingleFile(multipartFile));

    }

    @PostMapping(value = "/upload-multiple-files")
    public ResponseEntity<List<FileInfo>> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] multipleFiles) {

        return ResponseEntity.ok(fileService.uploadMultipleFiles(multipleFiles));

    }


}
