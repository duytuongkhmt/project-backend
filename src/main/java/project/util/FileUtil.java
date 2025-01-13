package project.util;

import org.springframework.web.multipart.MultipartFile;
import project.common.Constant;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static String storeFile(MultipartFile file, String type) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException(type + " file is required.");
        }

        String fileName = AuthUtils.getCurrentUsername() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String destinationPath = Constant.UPLOAD_DIR + "/" + type + "/" + fileName;

        try {
            File destinationFile = new File(destinationPath);
            destinationFile.getParentFile().mkdirs(); // Tạo thư mục nếu chưa tồn tại
            file.transferTo(destinationFile); // Lưu tệp

            return destinationPath; // Trả về đường dẫn hoặc URL
        } catch (IOException e) {
            throw new RuntimeException("Failed to save " + type + " file.", e);
        }
    }
}
