package com.dependra.importing_exporitng.controller;

import com.dependra.importing_exporitng.model.Coupon;
import com.dependra.importing_exporitng.model.User;
import com.dependra.importing_exporitng.service.CouponService;
import com.dependra.importing_exporitng.service.UserService;
import com.dependra.importing_exporitng.util.ExcelExporter;
import com.dependra.importing_exporitng.util.FileUploadHelper;
import com.dependra.importing_exporitng.util.PDFExporter;
import javassist.bytecode.stackmap.TypeData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FileUploadHelper fileUploadHelper;

    @Autowired
    CouponService couponService;

    @GetMapping("/users")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.getAllUser();

        ExcelExporter excelExporter = new ExcelExporter(listUsers);

        excelExporter.export(response);
    }

    @PostMapping("/userUpload")
    public ResponseEntity importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {

        List<User> userList = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                User user = new User();

                XSSFRow row = worksheet.getRow(index);
                Integer id = (int) row.getCell(0).getNumericCellValue();

                user.setId(id);
                user.setAddress(row.getCell(1).getStringCellValue());
                user.setName(row.getCell(2).getStringCellValue());
                userList.add(user);


            }
        }
        userService.saveListUser(userList);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/upload/{id}")
    public ResponseEntity upload(@RequestParam MultipartFile file,@PathVariable int id) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file");
        }
        if (fileUploadHelper.uploadFile(file,id)) {

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @GetMapping("/download/{id}")
    public ResponseEntity downloadFileFromLocal(HttpServletResponse response,@PathVariable int id) throws IOException {
        String loc=userService.getFileLocation(id);
        System.out.println(loc);
        File file = new File(loc);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + file.getName();
        response.setHeader(headerKey, headerValue);

        ServletOutputStream servletOutputStream = response.getOutputStream();

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        byte buffer[] = new byte[8192];
        int byteRead = -1;

        while ((byteRead = inputStream.read(buffer)) != -1) {
            servletOutputStream.write(buffer, 0, byteRead);
        }
        inputStream.close();
        servletOutputStream.close();


        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/coupon")
    public ResponseEntity importCouponExcelFile(@RequestParam("file") MultipartFile files) throws IOException {

        List<Coupon> couponList = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(1);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                Coupon coupon = new Coupon();

                XSSFRow row = worksheet.getRow(index);

                coupon.setId(index+1);
                coupon.setOutletName(row.getCell(0).getStringCellValue());
                coupon.setMerchantName(row.getCell(1).getStringCellValue());
                coupon.setAddress1(row.getCell(2).getStringCellValue());
                coupon.setAddress2(row.getCell(3).getStringCellValue()); coupon.setOutletName(row.getCell(0).getStringCellValue());
                coupon.setArea(row.getCell(4).getStringCellValue());
                coupon.setCity(row.getCell(5).getStringCellValue());
                coupon.setState(row.getCell(6).getStringCellValue());
                int pin=(int) row.getCell(7).getNumericCellValue();
                coupon.setPin(pin);
                coupon.setNewCategory(row.getCell(8).getStringCellValue());
                coupon.setQrEnabled(row.getCell(9).getStringCellValue());


                //couponList.add(coupon);
                couponService.saveOne(coupon);

            }
        }
//        couponService.save(couponList);
        return  ResponseEntity.of(Optional.of(couponList));
    }

    @GetMapping("/pdf")
    public ResponseEntity getPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.getAllUser();

        PDFExporter exporter = new PDFExporter(listUsers);
        exporter.export(response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
