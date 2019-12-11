package com.sdeevi.design.files;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FilePractice {

    public void printLastNLines(File file, int n) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        int blockSize = 2000;
        raf.seek(file.length() - blockSize);

        long i = 0;
        int lines = 0;
        StringBuffer sb = new StringBuffer();
        while (i < blockSize) {
            raf.readLine();
            char ch = raf.readChar();
            sb.append(ch);
            if (ch == '\n') {
                lines++;
            }
            i++;
        }
    }
}
