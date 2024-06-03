package kz.kazpost.dp.core.storage.dto;

import lombok.Data;


public class FileDownloadResponse {
    private byte[] fileContent;
    private String contentType;
    private String fileName;

    public FileDownloadResponse(byte[] fileContent, String contentType, String fileName) {
        this.fileContent = fileContent;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
