package com.panshi.springmvc.controller;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UploadController
 * @Description
 * @Author guolongfei
 * @Date 2019/12/11  9:42
 * @Version
 *
 */
@Controller
public class UploadController {

    @RequestMapping("/upload")
    public String getUpload() {
        return "upload";
    }


    /* 方法一:

     * 通过流的方式上传文件
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
     */
    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file")CommonsMultipartFile file) {
        if (!file.isEmpty()) {
            //用来检测程序的运行时间
            long startTime = System.currentTimeMillis();
            System.out.println("fileName: " + file.getOriginalFilename());
            String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (!".xls".equals(substring) && !".xlsx".equals(substring)) {
                return "error";
            }
            File  filePath = new File("D:\\a\\b\\" + System.currentTimeMillis() + file.getOriginalFilename());
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs(); //如果目录不存在,创建目录
            }
            try {
                //获取输出流
                OutputStream os = new FileOutputStream(filePath);
                //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
                InputStream is = file.getInputStream();

                int len;
                byte[] bys = new byte[2048];
                while ((len = is.read(bys)) != -1) {
                    os.write(bys, 0, len);
                    os.flush();
                }
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
            long endTime = System.currentTimeMillis();
            System.out.println("方法一的运行时间: " + String.valueOf(endTime - startTime) + "ms");
            return "success"; //返回成功页面
        }else {
            return "error";//返回失败页面
        }
    }


    /*方法二:
     *
     * 采用file.Transto 来保存上传的文件
     */
    @RequestMapping("/fileUpload2")
    public String fileUpload2(@RequestParam("file") CommonsMultipartFile file) {
        if (!file.isEmpty()) {
            long startTime = System.currentTimeMillis();
            System.out.println("fileName: " + file.getOriginalFilename());
            File  filePath = new File("D:\\a\\b\\" + System.currentTimeMillis() + file.getOriginalFilename());
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs(); //如果目录不存在,创建目录
            }

            try {
                //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
                file.transferTo(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
            long endTime = System.currentTimeMillis();
            System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime) + "ms");
            return "success";
        }else {
            return "error";
        }
    }


    /* 方法三:
     *  该方法 可以上传多个文件
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("/springUpload")
    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException {

        long startTime = System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        int count = 0;
        if (multipartResolver.isMultipart(request)) {

            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                List<MultipartFile> multipartFiles = multiRequest.getFiles(iter.next());
                String basePath = "D:\\a\\";
                for (MultipartFile multipartFile : multipartFiles) {
                    String fileName = multipartFile.getOriginalFilename();
                    if (!StringUtils.isEmpty(fileName)) {
                        File filePath = new File(basePath + fileName);
                        if (!filePath.getParentFile().exists()) {
                            filePath.getParentFile().mkdirs();
                        }

                        try {
                            multipartFile.transferTo(filePath);
                            count++;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return "error";
                        }
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        if (count == 0) {
            return "error";
        } else {
            return "success";
        }
    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String reg(HttpServletRequest request, @ModelAttribute Items items, Map<String,Object> map) {
        final String wrong = "error";
        final String good = "success";

        MultipartFile pic = items.getPic();
        boolean empty = pic.isEmpty();
        if (!empty) {

            String uploadPath = "D:\\a\\c";
            String originalFilename = pic.getOriginalFilename();

            assert originalFilename != null;
            File imageFile = new File(uploadPath,originalFilename);
            if (!imageFile.getParentFile().exists()) {
                imageFile.getParentFile().mkdirs();
            }
            try {
                pic.transferTo(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
                return wrong;
            }
            map.put("items",items);
            return "itemList";
        }else {
            return wrong;
        }
    }

   /* @RequestMapping(value = "/download",method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename, Model model) throws IOException {
        String downloadFilePath = "D:\\a\\c";
        File file = new File(downloadFilePath+File.separator+filename); //新建一个文件
        HttpHeaders headers = new HttpHeaders();// http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso8859-1"); // 设置编码

        headers.setContentDispositionFormData("attachment",downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
    }*/

    @RequestMapping("/download")
    public String download(@RequestParam("filename") String fileName , HttpServletRequest request, HttpServletResponse response){

        response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        String downLoadPath = "D:\\a\\d";  //注意不同系统的分隔符
        //	String downLoadPath =filePath.replaceAll("/", "\\\\\\\\");   //replace replaceAll区别 *****
        System.out.println(downLoadPath);
        File newFile = new File(downLoadPath);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        try {
            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(newFile));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
