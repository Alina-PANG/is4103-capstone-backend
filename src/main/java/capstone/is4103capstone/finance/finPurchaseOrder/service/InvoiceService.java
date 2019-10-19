package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.finance.Repository.InvoiceRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetInvoiceRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.controller.AssessmentFormController;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res.GetAssessmentFormRes;
import capstone.is4103capstone.util.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

@Service
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    @Autowired
    InvoiceRepository invoiceRepository;
    public ResponseEntity<GeneralRes> storeFile(MultipartFile file) {

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
//            if(fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
            Invoice invoice= new Invoice();
            invoice.setFileName(fileName);
            invoice.setFileType(file.getContentType());
            invoice.setData(file.getBytes());

            invoiceRepository.save(invoice);
            logger.info("Successfully saved the invoice!");
            return ResponseEntity.ok().body(new GeneralRes("Successfully uploaded the invoice!", false));
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<Resource> getFile(String fileId) {
        try{
            Invoice invoice= invoiceRepository.getOne(fileId);
            if(invoice == null) return ResponseEntity
                    .notFound().build();
            Resource resource = new ByteArrayResource(invoice.getData());
            String contentType = "application/octet-stream";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest().build();
        }
    }
}
