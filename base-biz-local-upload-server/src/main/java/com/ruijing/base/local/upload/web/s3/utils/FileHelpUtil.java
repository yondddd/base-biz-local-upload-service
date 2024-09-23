package com.ruijing.base.local.upload.web.s3.utils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description: 文件工具
 * @Author: WangJieLong
 * @Date: 2024-07-24
 */
public class FileHelpUtil {
    
    private static final int BUFFER_SIZE = 8192;
    private static final long FLUSH_FREQUENCY = 10 * 1024 * 1024;
    
    /**
     * @param sourceFolderPath 压缩目录
     * @param zipFilePath      压缩包位置
     * @throws IOException
     */
    public static void compressFolder(String sourceFolderPath, String zipFilePath) throws IOException {
        Path sourcePath = Paths.get(sourceFolderPath);
        AtomicLong byteCounter = new AtomicLong(0);
        
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ZipOutputStream zos = new ZipOutputStream(bos)) {
            
            zos.setLevel(Deflater.BEST_COMPRESSION);
            zos.setMethod(ZipOutputStream.DEFLATED);
            
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!dir.equals(sourcePath)) {
                        zos.putNextEntry(new ZipEntry(sourcePath.relativize(dir) + "/"));
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(file).toString());
                    zos.putNextEntry(zipEntry);
                    
                    try (InputStream in = new BufferedInputStream(Files.newInputStream(file))) {
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                            byteCounter.addAndGet(len);
                            
                            if (byteCounter.get() >= FLUSH_FREQUENCY) {
                                zos.flush();
                                fos.getFD().sync();
                                byteCounter.set(0);
                            }
                        }
                    }
                    zos.closeEntry();
                    
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
    
    /**
     * 完全删除目录
     */
    public static void deleteCompletely(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteCompletely(entry);
                }
            }
        }
        Files.deleteIfExists(path);
    }
    
}
