/**
 * @FileName: UploadService
 * @Author: code-fusheng
 * @Date: 2020/5/4 20:19
 * Description: 文件服务工具类
 */
package com.fr.commom.utils;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class UploadService {

    @Resource
    private FastFileStorageClient storageClient;

    public String uploadImage(MultipartFile file) {
        // 1.校验文件类型
        String contentType = file.getContentType();
        try {
            // 3、上传到FastDFS
            // 3.1、获取扩展名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            // 3.2、上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            // 返回路径
            return storePath.getFullPath();
        } catch (IOException e) {
            System.out.println("【文件上传】上传文件失败！....");
            throw new RuntimeException("【文件上传】上传文件失败！" + e.getMessage());
        }
    }

    /**
     * @Description: 上传文件
     * @Param: [file]
     * @return: java.lang.String
     * @Author: CHENLH
     * @Date: 2020-05-19 22:42:18
     */
    public String uploadFile(MultipartFile file) {
        try {
            // 3、上传到FastDFS
            // 3.1、获取扩展名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            // 3.2、上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            // 返回路径
            return storePath.getFullPath();
        } catch (IOException e) {
            System.out.println("【文件上传】上传文件失败！....");
            throw new RuntimeException("【文件上传】上传文件失败！" + e.getMessage());
        }
    }

    /**
     * @Description: 删除文件
     * @Param: [fileUrl]
     * @return: void
     * @Author: CHENLH
     * @Date: 2020-05-19 22:41:51
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {

        }
    }

}
