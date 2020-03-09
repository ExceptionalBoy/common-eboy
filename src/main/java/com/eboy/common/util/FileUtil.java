package com.eboy.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 
	 * @Title: multipartFileToFile 
	 * @Description:将MultipartFile对象转为File对象 TODO
	 * @author ExceptionalBoy 
	 * @param @param multipartFile
	 * @param @return
	 * @param @throws exception
	 * @return File   
	 * @throws
	 * @date 2017年12月28日 上午8:52:13
	 */
	public static File multipartFileToFile(MultipartFile multipartFile) throws Exception{
		if(multipartFile != null){
			File file = null;
			file = File.createTempFile("tmp", null);
			multipartFile.transferTo(file);
			file.deleteOnExit();
			return file;
		}else{
			return null;
		}
	}

	/**
	 * 
	 * @Title: fileToZipInputStream 
	 * @Description:将File转为ZipInputStream TODO
	 * @author ExceptionalBoy 
	 * @param @param file
	 * @param @return
	 * @param @throws exception
	 * @return ZipInputStream   
	 * @throws
	 * @date 2017年12月28日 上午9:01:22
	 */
	public static ZipInputStream fileToZipInputStream(File file) throws Exception{

		if(file != null)
			return new ZipInputStream(new FileInputStream(file)); 
		else 
			return null;
	}

	/**
	 * 
	 * @Title: inputStreamToBrowser 
	 * @Description:将输入流文件发送到浏览器页面显示 TODO
	 * @author ExceptionalBoy 
	 * @param @param inputStream
	 * @param @throws exception
	 * @return void   
	 * @throws
	 * @date 2017年12月29日 上午8:55:07
	 */
	public static void inputStreamToBrowser(HttpServletResponse response,InputStream inputStream) throws Exception{
		//判断参数输入流是否为空
		OutputStream outputStream = null;
		if(inputStream != null){
			try {
				//获取输出流对象
				outputStream = response.getOutputStream();
				int b = 0;
				//设置每次读取1024字节
				byte[] tytes = new byte[1024];
				while((b = inputStream.read(tytes)) != -1 ){
					//将每次读取的数据写到浏览器
					outputStream.write(tytes, 0, b);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					//关闭输入流
					inputStream.close();
					//判断输出流是否为空
					if(outputStream != null){
						//刷新输出流，确保所有数据都被写出
						outputStream.flush();
						//关闭输入流
						outputStream.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	/**
	 * 
	 * @Title: writeFlie 
	 * @Description:将文件写入指定的路劲下 TODO 
	 * @param @param file
	 * @param @param path
	 * @param @param filename
	 * @param @throws exception
	 * @return void 
	 * @throws
	 * 
	 */
	public static void writeFlie(File file,String path,String filename) throws Exception{
		if(file != null && !StringUtil.isEmpty(path) && !StringUtil.isEmpty(filename)){
			String fullPath = path +"\\"+ filename;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(fullPath);
				byte bytes[] = new byte[1024];
				int len = 0;
				while((len = fis.read(bytes)) != -1){
					fos.write(bytes, 0, len);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(fis != null){
						fis.close();
					}
					if(fos != null){
						fos.flush();
						fos.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
	}

	/**
	 * 
	 * @Title: deleteFile 
	 * @Description:删除文件 TODO 
	 * @param @param file 
	 * @return boolean 
	 * @throws
	 */
	public static boolean deleteFile(File file){
		if(file != null){
			//如果是文件夹，递归删除文件夹下的文件
			if(file.isDirectory()){
				String [] files = file.list();
				for (String name : files) {
					boolean flag = deleteFile(new File(file, name));
					if(!flag)
						return false;
				}
			}
			//如果是文件，直接删除
			return file.delete();
		}else{
			return false;
		}
	}

	/**
	 * 
	 * @Title: downloadFile 
	 * @Description:文件下载 TODO 
	 * @param @param file 文件
	 * @param @param filename 文件名称(下载后的名称)
	 * @param @param response 
	 * @return void 
	 * @throws
	 */
	public static void downloadFile(File file,String filename,HttpServletResponse response){
		if(file != null ){
			// 设置强制下载不打开
			response.setContentType("application/force-download");

			try {
				String charsetfilename = new String(filename.getBytes("utf-8"),"iso-8859-1");
				filename = charsetfilename;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 设置文件名
			response.addHeader("Content-Disposition","attachment;fileName=" + filename);
			InputStream is = null;
			OutputStream os = null;

			try {
				is = new FileInputStream(file);
				os = response.getOutputStream();
				byte tytes[] = new byte[1024];
				int len = 0;
				while((len = is.read(tytes)) != -1){
					os.write(tytes, 0, len);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{

				try {
					if(is != null){
						is.close();
					}
					if(os != null){
						os.flush();
						os.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @Title: zip
	 * @Description: TODO(压缩文件)
	 * @param @param sourceFileName 源文件(完整路径)
	 * @param @param targetZipFileName 目标文件(完整路径)
	 * @return void    返回类型
	 * @throws
	 */
	public static void zip(String sourceFileName,String targetZipFileName){

		logger.info("开始压缩文件...");
		ZipOutputStream zipOut = null;//压缩文件输出流
		try{
			//封装源文件
			File sourceFile = new File(sourceFileName);
			//判断源文件是否存在
			if(sourceFile.exists()){
				File targetFile = new File(targetZipFileName);
				File parentFile = targetFile.getParentFile();
				if(parentFile != null && !parentFile.exists()){
					parentFile.mkdirs();
				}
				//根据目标文件名实例化压缩文件输出流
				zipOut = new ZipOutputStream(new FileOutputStream(targetZipFileName));
				//压缩文件
				recursionZipOut(zipOut, sourceFile, sourceFile.getName());
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("写入文件出错：" +e.getMessage());
		}finally{
			try{
				zipOut.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("写入信息时，关闭流错误：" +e.getMessage());
			}
		}
	}

	/**
	 *
	 * @Title: recursionZipOut
	 * @Description: TODO(递归压缩文件)
	 * @param @param zipOutputStream
	 * @param @param sourceFile
	 * @param @param basePath
	 * @param @throws exception    参数
	 * @return void    返回类型
	 * @throws
	 */
	private static void recursionZipOut(
			ZipOutputStream zipOutputStream,
			File sourceFile,String basePath) throws Exception{
		if(sourceFile != null){
			//判断目标文件是否为文件夹
			if(sourceFile.isDirectory()){//目标文件为文件夹
				//获取目标文件夹下面的所有文件
				File[] files = sourceFile.listFiles();
				//判断文件夹是否为空
				if(files.length == 0){//目标文件夹为空文件夹
					//直接写入空文件夹
					zipOutputStream.putNextEntry(new ZipEntry(basePath + "/"));
				}else{//目标文件夹不是空文件夹
					//循环遍历文件列表
					for (File file : files) {
						String path = basePath + "/" + file.getName();
						recursionZipOut(zipOutputStream, file, path);
					}
				}
			}else{//目标文件为单个文件
				//直接将单个文件写入
				FileInputStream fis = null;
				try{
					zipOutputStream.putNextEntry(new ZipEntry(basePath));
					fis = new FileInputStream(sourceFile);
					byte[] bytes = new byte[1024];
					int length = 0;
					while((length = fis.read(bytes)) != -1 ){
						zipOutputStream.write(bytes, 0, length);;
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					logger.error("写入文件出错：" +e.getMessage());
				}finally {
					try{
						fis.close();
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error("写入文件时，关闭流出错：" +e.getMessage());
					}
				}
			}
		}else{//文件为空，直接抛异常
			throw new Exception("源文件为空，压缩文件失败!");
		}
	}

	/**
	 *
	 * @Title: imgAddWaterMark
	 * @Description: TODO(图片添加水印)
	 * @param @param sourceImgPath 源图片路径
	 * @param @param targetImgPath 目标图片路径
	 * @param @param waterMarkContent 水印内容
	 * @param @param font 水印字体
	 * @param @param color 水印颜色
	 * @return void    返回类型
	 * @throws
	 */
	public static void imgAddWaterMark(String sourceImgPath,String targetImgPath,String waterMarkContent,Font font,Color color){

		FileOutputStream outImgStream = null;
		try{
			//判断图片是否存在
			File sourceImg = new File(sourceImgPath);
			if(!sourceImg.exists()){
				logger.error("图片不存在");
				return;
			}
			//封装图片对象
			Image image = ImageIO.read(sourceImg);
			//获取图片的长和宽
			Integer imgWidth = image.getWidth(null);
			Integer imgHeight = image.getHeight(null);
			//添加水印
			BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics gp = bi.getGraphics();
			gp.drawImage(image, 0, 0, imgWidth, imgHeight, null);
			gp.setColor(color);
			gp.setFont(font);
			//获取水印长度
			Integer waterMarkLength = gp.getFontMetrics(gp.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
			int x = imgWidth - 2*waterMarkLength;
			int y = imgHeight - 2*waterMarkLength;
			gp.drawString(waterMarkContent, x, y);  //画出水印
			gp.dispose();
			// 输出图片
			outImgStream = new FileOutputStream(targetImgPath);
			ImageIO.write(bi, "jpg", outImgStream);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("水印添加失败：" + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(null != outImgStream){
					outImgStream.flush();
					outImgStream.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}			
		}
	}
}
