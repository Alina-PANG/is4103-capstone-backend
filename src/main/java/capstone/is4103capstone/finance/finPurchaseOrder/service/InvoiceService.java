package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.InvoiceRepository;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.Repository.StatementOfAccountLineItemRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.POEntityCodeHPGeneration;
import capstone.is4103capstone.finance.finPurchaseOrder.model.InvoiceModel;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateInvoiceReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetInvoiceRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.controller.AssessmentFormController;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res.GetAssessmentFormRes;
import capstone.is4103capstone.util.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import java.util.Date;
import java.util.Optional;

@Service
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    StatementOfAccountLineItemRepository statementOfAccountLineItemRepository;
    @Autowired
    VendorRepository vendorRepository;

    public ResponseEntity<GeneralRes> update(CreateInvoiceReq createInvoiceReq, String soaId, String username) {
        try {
            StatementOfAcctLineItem soa = statementOfAccountLineItemRepository.getOne(soaId);

            if(soa == null)
                return ResponseEntity.notFound().build();

            Invoice invoice = soa.getInvoice();
            if(invoice == null)
                invoice = new Invoice();

            invoice.setCurrencyCode(createInvoiceReq.getCurrencyCode());
            invoice.setDescription(createInvoiceReq.getDescription());
            invoice.setPaymentAmount(createInvoiceReq.getTotalAmt());

            invoice.setStatementOfAcctLineItem(soa);
            invoice.setLastModifiedBy(username);
            invoice.setLastModifiedDateTime(new Date());

            invoice = invoiceRepository.saveAndFlush(invoice);
            invoice.setCode(POEntityCodeHPGeneration.getCode(invoiceRepository,invoice));
            invoiceRepository.saveAndFlush(invoice);
            soa.setInvoice(invoice);
            soa.setPaidAmt(invoice.getPaymentAmount());
            statementOfAccountLineItemRepository.saveAndFlush(soa);
            logger.info("Successfully updated the invoice!");
            return ResponseEntity.ok().body(new GeneralRes("Successfully updated the invoice information!", false));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> storeFile(MultipartFile file, String soaId, String username) {
        try {
            StatementOfAcctLineItem soa = statementOfAccountLineItemRepository.getOne(soaId);
            if(soa == null) return ResponseEntity.notFound().build();

            Invoice invoice = soa.getInvoice();
            if(invoice == null) invoice = new Invoice();

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            invoice.setFileName(fileName);
            invoice.setFileType(file.getContentType());
            invoice.setData(file.getBytes());
            invoice.setCreatedBy(username);
            invoice.setCreatedDateTime(new Date());
            invoice.setStatementOfAcctLineItem(soa);

            invoice = invoiceRepository.saveAndFlush(invoice);
            invoice.setCode(POEntityCodeHPGeneration.getCode(invoiceRepository,invoice));
            invoiceRepository.saveAndFlush(invoice);
            soa.setInvoice(invoice);
            if(invoice.getPaymentAmount() != null) {
                soa.setPaidAmt(invoice.getPaymentAmount());
            }
            statementOfAccountLineItemRepository.saveAndFlush(soa);
            logger.info("Successfully saved the invoice!");
            return ResponseEntity.ok().body(new GeneralRes("Successfully uploaded the invoice!", false));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<byte[]> getFile(String fileId) {
        try{
            Invoice invoice = invoiceRepository.getOne(fileId);
            if(invoice == null) return ResponseEntity
                    .notFound().build();
            Resource resource = new ByteArrayResource(invoice.getData());
            String contentType = "application/octet-stream";

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.valueOf(invoice.getFileType()));
            header.setContentLength(invoice.getData().length);
            header.set("Content-Disposition", "attachment; filename=" + invoice.getFileName());

            return new ResponseEntity<>(invoice.getData(), header, HttpStatus.OK);
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(contentType))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                    .body(resource);

//            return ResponseEntity
//                    .ok()
//                    .headers(headers)
//                    .body(new InputStreamResource(resource.getInputStream()));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest().build();
        }
    }

    public ResponseEntity<GeneralRes> getDetail(String id) {
        try{
            Invoice invoice = invoiceRepository.getOne(id);
            if(invoice == null) return ResponseEntity
                    .notFound().build();
            InvoiceModel model = new InvoiceModel();
            model.setCurrencyCode(invoice.getCurrencyCode());
            model.setDescription(invoice.getDescription());
            model.setFileName(invoice.getFileName());
            model.setPaymentAmount(invoice.getPaymentAmount());
            model.setId(invoice.getId());
            return ResponseEntity.ok()
                    .body(new GetInvoiceRes("Successfully retrieved the invoice!", false, model));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest().build();
        }
    }
}
