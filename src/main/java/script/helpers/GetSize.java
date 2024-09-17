package script.helpers;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class GetSize {
    public long getMb(File file) {
        if (file.isDirectory()) {
            long l1 = FileUtils.sizeOfDirectory(file);
            long l2 = 1048576L;
            return l1 / l2;
        }
        long size = FileUtils.sizeOf(file);
        long MEGABYTE = 1048576L;
        return size / MEGABYTE;
    }

    public long getKb(File file) {
        if (file.isDirectory()) {
            long l1 = FileUtils.sizeOfDirectory(file);
            long l2 = 1024L;
            return l1 / l2;
        }
        long size = FileUtils.sizeOf(file);
        long KILOBYTE = 1024L;
        return size / KILOBYTE;
    }
    public long getSize(File file) {
        if (file.isDirectory()) {
            long s1 = FileUtils.sizeOfDirectory(file);
            return s1;
        }
        long s2= FileUtils.sizeOf(file);
        return s2;
    }

}
