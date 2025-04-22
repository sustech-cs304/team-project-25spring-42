package sustech.cs304.AIDE.Elements;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/download")
public class DownloadController {

    private static final Path ALLOWED_BASE_DIR = Paths.get("/home/cse12311018/Documents/Save");

    @PostMapping(value = "/downloadBinaryByPath", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadBinaryByPath(
            @RequestParam String filePath,
            @RequestParam(required = false) String customFilename) {
        System.out.println("Received request to download file: " + filePath);        
        try {
            Path requestedPath = ALLOWED_BASE_DIR.resolve(filePath).normalize();
            if (!requestedPath.startsWith(ALLOWED_BASE_DIR)) {
                return ResponseEntity.badRequest().build();
            }

            Resource resource = new UrlResource(requestedPath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String mimeType = Files.probeContentType(requestedPath);
            MediaType mediaType = (mimeType != null) ? 
                MediaType.parseMediaType(mimeType) : 
                MediaType.APPLICATION_OCTET_STREAM;

            String filename = (customFilename != null) ? 
                customFilename : 
                requestedPath.getFileName().toString();

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                           "attachment; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
