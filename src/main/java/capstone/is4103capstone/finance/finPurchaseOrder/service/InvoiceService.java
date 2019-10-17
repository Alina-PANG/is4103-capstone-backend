package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.finance.Repository.InvoiceRepository;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    public ResponseEntity<GeneralRes> storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Invoice invoice= new Invoice();
            invoice.setFileName(fileName);
            invoice.setFileType(file.getContentType());
            invoice.setData(file.getBytes());

            invoiceRepository.save(invoice);


        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
        return null;
    }

    public ResponseEntity<Invoice> getFile(String fileId) {
        Optional invoice = invoiceRepository.findById(fileId);
        return null;

    }
}
