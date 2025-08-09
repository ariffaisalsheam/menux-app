package com.menux.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.menux.entity.QrCode;
import com.menux.entity.Restaurant;
import com.menux.exception.ResourceNotFoundException;
import com.menux.repository.QrCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Service class for QR Code operations
 */
@Service
@Transactional
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;

    @Value("${app.qr.base-url}")
    private String qrBaseUrl;

    @Value("${app.qr.width:300}")
    private int qrWidth;

    @Value("${app.qr.height:300}")
    private int qrHeight;

    @Autowired
    public QrCodeService(QrCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }

    /**
     * Generate QR code for restaurant
     */
    public QrCode generateQrCodeForRestaurant(Restaurant restaurant, String tableNumber) {
        String code = generateUniqueCode(restaurant, tableNumber);
        
        QrCode qrCode = new QrCode();
        qrCode.setRestaurant(restaurant);
        qrCode.setCode(code);
        qrCode.setTableNumber(tableNumber);
        
        return qrCodeRepository.save(qrCode);
    }

    /**
     * Generate QR code image as Base64 string
     */
    public String generateQrCodeImage(UUID qrCodeId) {
        QrCode qrCode = getQrCodeById(qrCodeId);
        String url = qrBaseUrl + "/" + qrCode.getRestaurant().getId() + "?table=" + qrCode.getTableNumber();
        
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, qrWidth, qrHeight);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            
            byte[] qrCodeBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(qrCodeBytes);
            
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code image", e);
        }
    }

    /**
     * Get QR code by ID
     */
    @Transactional(readOnly = true)
    public QrCode getQrCodeById(UUID id) {
        return qrCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found with id: " + id));
    }

    /**
     * Get QR code by code string
     */
    @Transactional(readOnly = true)
    public QrCode getQrCodeByCode(String code) {
        return qrCodeRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found with code: " + code));
    }

    /**
     * Get QR codes by restaurant
     */
    @Transactional(readOnly = true)
    public List<QrCode> getQrCodesByRestaurant(Restaurant restaurant) {
        return qrCodeRepository.findByRestaurant(restaurant);
    }

    /**
     * Get active QR codes by restaurant
     */
    @Transactional(readOnly = true)
    public List<QrCode> getActiveQrCodesByRestaurant(Restaurant restaurant) {
        return qrCodeRepository.findByRestaurantAndIsActive(restaurant, true);
    }

    /**
     * Update QR code
     */
    public QrCode updateQrCode(UUID id, String tableNumber) {
        QrCode qrCode = getQrCodeById(id);
        qrCode.setTableNumber(tableNumber);
        
        // Generate new code if table number changed
        String newCode = generateUniqueCode(qrCode.getRestaurant(), tableNumber);
        qrCode.setCode(newCode);
        
        return qrCodeRepository.save(qrCode);
    }

    /**
     * Activate/Deactivate QR code
     */
    public QrCode toggleQrCodeStatus(UUID id) {
        QrCode qrCode = getQrCodeById(id);
        qrCode.setIsActive(!qrCode.getIsActive());
        return qrCodeRepository.save(qrCode);
    }

    /**
     * Delete QR code
     */
    public void deleteQrCode(UUID id) {
        QrCode qrCode = getQrCodeById(id);
        qrCodeRepository.delete(qrCode);
    }

    /**
     * Generate unique code for QR
     */
    private String generateUniqueCode(Restaurant restaurant, String tableNumber) {
        String baseCode = restaurant.getName().replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        if (baseCode.length() > 10) {
            baseCode = baseCode.substring(0, 10);
        }
        
        String code = baseCode + "-" + tableNumber + "-" + System.currentTimeMillis();
        
        // Ensure uniqueness
        while (qrCodeRepository.existsByCode(code)) {
            code = baseCode + "-" + tableNumber + "-" + System.currentTimeMillis();
        }
        
        return code;
    }

    /**
     * Validate QR code and get restaurant info
     */
    @Transactional(readOnly = true)
    public QrCodeValidationResult validateQrCode(String code) {
        try {
            QrCode qrCode = getQrCodeByCode(code);
            
            if (!qrCode.getIsActive()) {
                return new QrCodeValidationResult(false, "QR Code is inactive", null, null);
            }
            
            if (!qrCode.getRestaurant().getIsActive()) {
                return new QrCodeValidationResult(false, "Restaurant is inactive", null, null);
            }
            
            return new QrCodeValidationResult(true, "Valid QR Code", qrCode.getRestaurant(), qrCode.getTableNumber());
            
        } catch (ResourceNotFoundException e) {
            return new QrCodeValidationResult(false, "Invalid QR Code", null, null);
        }
    }

    /**
     * QR Code validation result
     */
    public static class QrCodeValidationResult {
        private final boolean valid;
        private final String message;
        private final Restaurant restaurant;
        private final String tableNumber;

        public QrCodeValidationResult(boolean valid, String message, Restaurant restaurant, String tableNumber) {
            this.valid = valid;
            this.message = message;
            this.restaurant = restaurant;
            this.tableNumber = tableNumber;
        }

        // Getters
        public boolean isValid() { return valid; }
        public String getMessage() { return message; }
        public Restaurant getRestaurant() { return restaurant; }
        public String getTableNumber() { return tableNumber; }
    }
}
