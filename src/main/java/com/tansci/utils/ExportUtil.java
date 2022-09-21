package com.tansci.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ExportUtil {
  public ExportUtil() {
  }

  public static void exportTxt(HttpServletResponse response, String text) {
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/plain");
    response.addHeader("Content-Disposition", "attachment;filename=data_" + System.currentTimeMillis() + ".txt");
    PrintWriter writer = null;

    try {
      writer = response.getWriter();
      writer.write(text);
      writer.flush();
    } catch (Exception var12) {
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (Exception var11) {
      }

    }

  }


  public static void exportZip(HttpServletResponse response, Map<String, String> resultMap) {
    response.reset();
    response.setCharacterEncoding("utf-8");
//    response.setContentType("multipart/form-data");
    response.setContentType("application/octet-stream;charset=utf-8");
    response.addHeader("Content-Disposition", "attachment;filename=data_" + DateUtil.date2Str(new Date(), "yyyyMMddHHmmss") + ".zip");
    ZipOutputStream out = null;

    try {
      out = new ZipOutputStream(response.getOutputStream());
      ZipOutputStream finalOut = out;
      resultMap.entrySet().stream().forEach((entry) -> {
        System.out.println((String)entry.getKey() + "," + (String)entry.getValue());

        try {
          finalOut.putNextEntry(new ZipEntry((String)entry.getKey() + ".txt"));
          finalOut.write(((String)entry.getValue()).getBytes());
          finalOut.closeEntry();
          finalOut.flush();
        } catch (IOException var3) {
          var3.printStackTrace();
        }

      });
    } catch (Exception var7) {
      var7.printStackTrace();
    } finally {
      ResourcesUtil.multiClose(out);
    }

  }

//
//  public static void exportZip(HttpServletResponse response, Map<String, String> resultMap) {
//    response.setCharacterEncoding("utf-8");
//    response.setContentType("multipart/form-data");
////    response.setContentType("application/force-download");
//    response.addHeader("Content-Disposition",
//        "attachment;filename=data_" + DateUtil.date2Str(new Date(), "yyyyMMddHHmmss") + ".zip");
//    ZipOutputStream out = null;
//
//    try {
//      out = new ZipOutputStream(response.getOutputStream());
//      ZipOutputStream finalOut = out;
//      resultMap.entrySet().stream().forEach((entry) -> {
//        System.out.println((String) entry.getKey() + "," + (String) entry.getValue());
//
//        try {
//          finalOut.putNextEntry(new ZipEntry((String) entry.getKey() + ".txt"));
//          finalOut.write(((String) entry.getValue()).getBytes());
//          finalOut.closeEntry();
//          finalOut.flush();
//        } catch (IOException var3) {
//          var3.printStackTrace();
//        }
//
//      });
//    } catch (Exception var7) {
//      var7.printStackTrace();
//    } finally {
////      ResourcesUtil.multiClose(new ZipOutputStream[] { out });
//    }
//
//  }
  //
  //  public static void main(String[] args) {
  //    Map<String, String> resultMap = new HashMap();
  //    resultMap.put("a1", "a111111111111111111111111111111111111111");
  //    resultMap.put("b1", "b111111111111111111111111111111111111111");
  //    resultMap.put("c1", "c111111111111111111111111111111111111111");
  //    String filePath = "D:\\test\\" + System.currentTimeMillis() + ".zip";
  //    ZipOutputStream out = null;
  //
  //    try {
  //      out = new ZipOutputStream(new FileOutputStream(filePath));
  //      resultMap.entrySet().stream().forEach((entry) -> {
  //        System.out.println((String)entry.getKey() + "," + (String)entry.getValue());
  //
  //        try {
  //          out.putNextEntry(new ZipEntry((String)entry.getKey() + ".txt"));
  //          out.write(((String)entry.getValue()).getBytes());
  //          out.closeEntry();
  //        } catch (IOException var3) {
  //          var3.printStackTrace();
  //        }
  //
  //      });
  //    } catch (Exception var8) {
  //      var8.printStackTrace();
  //    } finally {
  //      ResourcesUtil.multiClose(new ZipOutputStream[]{out});
  //    }
  //
  //    System.out.println("===========over");
  //  }
}