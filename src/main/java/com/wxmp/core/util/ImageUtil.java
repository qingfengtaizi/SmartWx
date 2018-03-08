package com.wxmp.core.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageUtil {
	/**
	 * w h为负值时，以为正值的 等比例缩小或者放大 此时force应为false
	 * @param bs
	 * @param w
	 * @param h
	 * @param force
	 * @param f
	 * @param format 为后缀名
	 * @throws IOException 
	 * @throws Throwable
	 */
	public static void resize(byte[] bs, int w, int h, boolean force, File f,
			String format) throws IOException {
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(bs));
		if (w <= 0 && h <= 0) {
			w = img.getWidth();
			h = img.getHeight();
		} else if (w <= 0) {
			w = h * img.getWidth() / img.getHeight();
		} else if (h <= 0) {
			h = w * img.getHeight() / img.getWidth();
		}
		if (!force) {
			if (img.getWidth() < w && img.getHeight() < h) {
				if (img.getWidth() / img.getHeight() > w / h) {
					h = img.getWidth() * h / w;
					w = img.getWidth();
				} else {
					w = img.getHeight() * w / h;
					h = img.getHeight();
				}
			}
		}
		BufferedImage target = new BufferedImage(w, h,
				BufferedImage.TYPE_USHORT_565_RGB);
		Graphics g = target.getGraphics();
		g.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(), img.getHeight(),
				null);
		g.dispose();
		ImageIO.write(target, format, f);
	}

	public static void resize(InputStream is, int w, int h,boolean force, OutputStream os,
			String format) throws Exception {
		BufferedImage img = ImageIO.read(is);
		if (w <= 0 && h <= 0) {
			w = img.getWidth();
			h = img.getHeight();
		} else if (w <= 0) {
			w = h * img.getWidth() / img.getHeight();
		} else if (h <= 0) {
			h = w * img.getHeight() / img.getWidth();
		}
		if (!force) {
			if (img.getWidth() < w && img.getHeight() < h) {
				if (img.getWidth() / img.getHeight() > w / h) {
					h = img.getWidth() * h / w;
					w = img.getWidth();
				} else {
					w = img.getHeight() * w / h;
					h = img.getHeight();
				}
			}
		}
		BufferedImage target = new BufferedImage(w, h,
				BufferedImage.TYPE_USHORT_565_RGB);
		Graphics g = target.getGraphics();
		g.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(), img.getHeight(),
				null);
		g.dispose();
		ImageIO.write(target, format, os);
	}

	public static void resize(byte[] bs, int w,boolean force, File f,  String format)
			throws Throwable {
		resize(bs, w, -1, force, f, format);
	}

	public static void resize(InputStream is, int w, boolean force, OutputStream os,
			String format) throws Exception {
		resize(is, w, -1, force, os, format);
	}
	
//	public String savePic(MultipartFile file, String uploadUrl, final int w,
//			final int h, final boolean force) {
//		InputStream is = null;
//		PipedOutputStream pos = null;
//		PipedInputStream pis = null;
//		try {
//			String path = FlyCoreMethods.createFilePath(
//					FlyIdCreators.idCreator.nextId() + "", file);
//			is = file.getInputStream();
//			pos = new PipedOutputStream();
//			pis = new PipedInputStream();
//			pis.connect(pos);
//
//			final InputStream is2 = is;
//			final PipedOutputStream pos2 = pos;
//
//			new Thread() {
//				@Override
//				public void run() {
//					try {
//						ImageUtil.resize(is2, w, h, force, pos2, "png");
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						try {
//							if (pos2 != null)
//								pos2.close();
//						} catch (Exception e) {
//						}
//					}
//				}
//			}.start();
//			FlyDocUpload.upload(uploadUrl, path, pis);
//			pis.close();
//			pos.close();
//			is.close();
//			return path;
//		} catch (IOException e) {
//			throw new Exception("文件服务器出错!");
//		} finally {
//			try {
//				if (is != null)
//					is.close();
//			} catch (Exception e) {
//
//			}
//			try {
//				if (pis != null)
//					pis.close();
//			} catch (Exception e) {
//
//			}
//			try {
//				if (pos != null)
//					pos.close();
//			} catch (Exception e) {
//
//			}
//		}
//
//	}
//
//	public String savePic(MultipartFile file, String uploadUrl) {
//		return savePic(file, uploadUrl, -1, -1, false);
//	}
}