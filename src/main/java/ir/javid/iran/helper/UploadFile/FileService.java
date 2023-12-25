package ir.javid.iran.helper.UploadFile;

/**
 * Created by jvd.molaei on 2022-01-25.
 */

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileInfo uploadSingleFile(MultipartFile multipartFile) throws IOException;
    List<FileInfo> uploadMultipleFiles(MultipartFile[] multipleFiles);

}
