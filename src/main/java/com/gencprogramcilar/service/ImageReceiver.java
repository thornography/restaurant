package com.gencprogramcilar.service;

import com.vaadin.ui.Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageReceiver implements Upload.Receiver
{
    public File file;

    public OutputStream receiveUpload(String filename, String mimeType) {

        FileOutputStream fos = null;
        try {
            file = new File(filename);
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            return null;
        }
        return fos;
    }

}
