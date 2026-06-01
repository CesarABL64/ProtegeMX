package com.proyectovecinal.service;

import com.proyectovecinal.entity.enums.TipoEvidencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String store(MultipartFile file, String subdirectorio) {
        try {
            Path uploadPath = Paths.get(uploadDir, subdirectorio);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String extension = getExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "." + extension;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + subdirectorio + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage(), e);
        }
    }

    public String storeEvidencia(MultipartFile file, TipoEvidencia tipo) {
        return store(file, "evidencias/" + tipo.name().toLowerCase());
    }

    public void delete(String fileUrl) {
        try {
            Path filePath = Paths.get(uploadDir).getParent().resolve(
                    fileUrl.startsWith("/") ? fileUrl.substring(1) : fileUrl
            );
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo: " + e.getMessage(), e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "bin";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
}
