package com.tansci.utils;

import com.alibaba.fastjson.JSON;
import com.tansci.common.constant.Constants;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
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

  public static void exportTxt(HttpServletResponse response, List<RecordData> recordDataList) {
    response.reset();
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/octet-stream;charset=utf-8");
    response.addHeader("Content-Disposition", "attachment;filename=data_" + System.currentTimeMillis() + ".txt");
    PrintWriter writer = null;

    try {
      writer = response.getWriter();
      StringBuffer sb = new StringBuffer();
      String jsonStr = JSON.toJSONString(recordDataList);

//      recordDataList.stream().forEach(recordData -> {
//        sb.append(DateUtil.date2Str(recordData.getTimestamp(), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)).append("===>")
//            .append(recordData.getSubtitle()).append(Constants.OS_LINE_SPERATOR);
//      });
      writer.write(jsonStr);
      writer.flush();
    } catch (Exception var12) {
    } finally {
      ResourcesUtil.multiClose(writer);
    }

  }

  public static void exportSrt(HttpServletResponse response, List<RecordData> recordDataList) {
    response.reset();
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/octet-stream;charset=utf-8");
    response.addHeader("Content-Disposition", "attachment;filename=data_" + System.currentTimeMillis() + ".srt");
    PrintWriter writer = null;

    try {
      writer = response.getWriter();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < recordDataList.size(); i++) {
        int index = i + 1;
        String offset = recordDataList.get(i).getOffset();
        String duration = recordDataList.get(i).getDuration();
        String text = recordDataList.get(i).getSubtitle();
        SrtUtil.StringFromCaption(sb, index, offset, duration, text);
        //        sb.append(i + 1).append("\n").append(startTime).append(" --> ").append(endTime).append("\n").append(msg).append("\n").append("\n");
        //        sb.append(DateUtil.date2Str(recordData.getTimestamp(),DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)).append("===>")
        //            .append(recordData.getSubtitle()).append(Constants.OS_LINE_SPERATOR);
      }
      //      recordDataList.stream().forEach(recordData -> {
      //
      //      });
      writer.write(sb.toString());
      writer.flush();
    } catch (Exception var12) {
    } finally {
      ResourcesUtil.multiClose(writer);
    }

  }

  public static void exportWav(HttpServletResponse response, Record record) {
    response.reset();
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/octet-stream;charset=utf-8");
    String filePath = record.getFilePath();
    String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    PrintWriter writer = null;
    OutputStream toClient = null;
    InputStream fis = null;

    try {
//      writer = response.getWriter();
      //      StringBuilder sb = new StringBuilder();
      //      File file = new File(filePath);
      // 以流的形式下载文件。
      fis = new BufferedInputStream(new FileInputStream(filePath));
      byte[] buffer = new byte[fis.available()];
      fis.read(buffer);
      fis.close();
      // 清空response
      response.reset();
      toClient = new BufferedOutputStream(response.getOutputStream());
      toClient.write(buffer);
      toClient.flush();
      toClient.close();
      //
      //      writer.print(new OutputStream(file));
      //      writer.flush();
    } catch (Exception var12) {
    } finally {
      ResourcesUtil.multiClose(toClient,fis);
    }

  }

  public static void exportZip(HttpServletResponse response, Map<String, String> resultMap) {
    response.reset();
    response.setCharacterEncoding("utf-8");
    //    response.setContentType("multipart/form-data");
    response.setContentType("application/octet-stream;charset=utf-8");
    response.addHeader("Content-Disposition",
        "attachment;filename=data_" + DateUtil.date2Str(new Date(), "yyyyMMddHHmmss") + ".zip");
    ZipOutputStream out = null;

    try {
      out = new ZipOutputStream(response.getOutputStream());
      ZipOutputStream finalOut = out;
      resultMap.entrySet().stream().forEach((entry) -> {
        System.out.println((String) entry.getKey() + "," + (String) entry.getValue());

        try {
          finalOut.putNextEntry(new ZipEntry((String) entry.getKey() + ".txt"));
          finalOut.write(((String) entry.getValue()).getBytes());
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
  public static void main(String[] args) {

    System.out.println("aaaaaaaaaa" + System.getProperty("line.separator") + "bbbbbbbbb");
  }
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