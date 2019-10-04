package capstone.is4103capstone.general.controller;

import capstone.is4103capstone.general.model.FilePathReq;
import capstone.is4103capstone.general.model.UploadFileResponse;
import capstone.is4103capstone.general.service.ExportToFileService;
import capstone.is4103capstone.general.service.FileStorageService;
import capstone.is4103capstone.general.service.ReadFromFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    ExportToFileService exportToFileService;

    @Autowired
    ReadFromFileService readFromFileService;

    @PostMapping(value = "/upload")
    public Boolean uploadFile(@RequestBody FilePathReq filePathReq) {
        List<List<String>> test = readFromFileService.readFromExcel(filePathReq.getFilePath());
        for(List<String> list: test){
            for(String s: list){
                System.out.print(s+" | ");
            }
            System.out.println();
        }
        return (test != null);
    }

//    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
//        if (file == null) {
//            throw new RuntimeException("You must select the a file for uploading");
//        }
//        InputStream inputStream = file.getInputStream();
//        String originalName = file.getOriginalFilename();
//        String name = file.getName();
//        String contentType = file.getContentType();
//        long size = file.getSize();
//        logger.info("inputStream: " + inputStream);
//        logger.info("originalName: " + originalName);
//        logger.info("name: " + name);
//        logger.info("contentType: " + contentType);
//        logger.info("size: " + size);
//        // Do processing with uploaded file data in Service layer
//        return new ResponseEntity<String>(originalName, HttpStatus.OK);
//    }

    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<InputStreamResource> excelCustomersReport(@PathVariable("filename") String filename) {
        List<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
        ArrayList<String> contentItem01 = new ArrayList<String>(){
            {
                add("1");
                add("test1");
                add("password");
                add("7");
            }
        };
        ArrayList<String> contentItem02 = new ArrayList<String>(){
            {
                add("2");
                add("test2");
                add("password");
                add("234");
            }
        };
        ArrayList<String> contentItem03 = new ArrayList<String>(){
            {
                add("3");
                add("test3");
                add("password");
                add("53");
            }
        };
        content.add(contentItem01);
        content.add(contentItem02);
        content.add(contentItem03);
        ByteArrayInputStream in = exportToFileService.exportToExcel(filename, new String[]{"Id", "Name", "Password", "Age"}, content);
        // return IOUtils.toByteArray(in);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+filename);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}