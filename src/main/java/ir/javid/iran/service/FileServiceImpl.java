package ir.javid.iran.service;

/**
 * Created by jvd.molaei on 2022-01-25.
 */

import com.nimbusds.jose.shaded.gson.Gson;
import ir.javid.iran.helper.UploadFile.FileInfo;
import ir.javid.iran.helper.UploadFile.FileService;
import ir.javid.iran.helper.UploadFile.FileUploadJson;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {



    //    private final String PATH = "/home/files-directory/";
    private final String PATH = "C:/archive/";


    @Override
    public FileInfo uploadSingleFile(MultipartFile multipleFile) throws IOException {

//        FileUploadJson fileUploadJson = new Gson().fromJson(fileJson, FileUploadJson.class);
        UUID uniqueID = UUID.randomUUID();
        String randomUUIDString = uniqueID.toString();
        String filename = multipleFile.getOriginalFilename();

        String[] split = filename.split("\\.");
        String end = "." + split[1];
        filename = filename.replaceAll("\\s", "_");

//        String path = PATH + filename;
        String path = PATH + randomUUIDString + end;

        File file = new File(path);
            if (file.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(multipleFile.getBytes());
                }
            }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileLocation(path);
        fileInfo.setFilename(filename);
        return fileInfo;
    }


    @Override
    public List<FileInfo> uploadMultipleFiles(MultipartFile[] multipleFiles) {

        List<FileInfo> fileInfos = new ArrayList<>();

        for (MultipartFile multipleFile : multipleFiles) {
            String filename = multipleFile.getOriginalFilename();

            filename = filename.replaceAll("\\s", "_");

            String path = PATH + filename;

            File file = new File(path);
            try {
                if (file.createNewFile()) {
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(multipleFile.getBytes());
                    }
                }
            } catch (IOException e) {
            }

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileLocation(path);
            fileInfo.setFilename(filename);
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }




    File multipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }

    void multipartCheck(MultipartFile multipartFile) throws Exception {

        String type = Files.probeContentType(multipartToFile(multipartFile).toPath());
        String[] split = type.split("/");
        if (!Objects.equals(split[0], "video"))
            throw new Exception("خطا در نوع فایل");
        String end = split[1];
        boolean flag = false;
        String[] format = {"mp4", "webm", "mpg", "mp2", "mpeg", "mpe", "mpv", "ogg", "m4p", "m4v", "avi", "wmv", "mov", "qt", "flv", "swf", "avchd"};
        for (String s : format) {
            if (s.equals(end))
                flag = true;
        }
        if (!flag)
            throw new Exception("خطا در فرمت فایل");
    }

    private void newMultipartCheck(String contentType, String phoneNumber) throws Exception {
        String[] split = contentType.split("/");
        if (!Objects.equals(split[0], "video")) {
            throw new Exception("file input ex " + split[0]);
        }
        String end = split[1];
        boolean flag = false;
        String[] format = {"quicktime", "mp4", "webm", "mpg", "mp2", "mpeg", "mpe", "mpv", "ogg", "m4p", "m4v", "avi", "wmv", "mov", "qt", "flv", "swf", "avchd"};
        for (String s : format) {
            if (s.equalsIgnoreCase(end))
                flag = true;
        }
        if (!flag) {
            throw new Exception("format ex " + end);
        }
    }


    class MultipartInputStreamFileResource extends InputStreamResource {
        private final String filename;

        public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return super.getFilename();
        }

        @Override
        public long contentLength() throws IOException {
            return -1;
        }
    }


}
