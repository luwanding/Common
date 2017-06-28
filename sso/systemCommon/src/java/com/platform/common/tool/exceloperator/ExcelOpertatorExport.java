package com.platform.common.tool.exceloperator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableSheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 导出Excel 主类
 * @author 许德麟
 * @version 1.0
 */
public class ExcelOpertatorExport {
	
	private WritableWorkbook wwb;
	
	/**
	 * 一个Sheet最大行数 50000
	 */
	private int maxRows = 50004;
	
	/**
	 * 当前可写的Sheet
	 */
	private WritableSheet writeSheet;
	
	/**
	 * 当前可写入的Sheet 索引
	 */
	private int sheetIndex = 0;
	
	/**
	 * 数据开始写入行索引
	 */
	private int dataBeginIndex = 3;
	
	/**
	 * 当前写入了多少行的记录
	 */
	private int sheetDataWriteSize = dataBeginIndex;
	
	
	/**
	 * 写Execl时文件基本属性
	 */
	private ExcelAttribute excelAttribute;
	
	public ExcelOpertatorExport(ExcelAttribute excelAttribute) throws IOException{
		this.excelAttribute = excelAttribute;
		wwb = Workbook.createWorkbook(new File(this.excelAttribute.getFileName()));
		
	}
	
	/**
	 * 向Excel写入一行
	 * ,数据写完后需要调用release() 方法失望文件资源。
	 * @param datas
	 * @throws WriteException 
	 */
	public void writeRow(List datas) throws WriteException{
		for(int i = 0; i < datas.size() ; i ++ ) {
			Object obj = datas.get(i);
			if( obj == null ){
				obj = "";
			}
			Label label = new Label(i, this.sheetDataWriteSize, datas.get(i).toString(),getTextStyle());
			writeSheet.addCell(label);
		}
		
		synchronized (this){
			if(this.sheetDataWriteSize >= maxRows ){
				createSheet();
				this.sheetDataWriteSize = dataBeginIndex-1;
			}
			this.sheetDataWriteSize ++;
		}
	}
	
	/**
	 * 创建一个新的Sheet
	 * @throws WriteException
	 */
	private void createSheet() throws WriteException{
		writeSheet = wwb.createSheet(this.excelAttribute.getSheetName()+sheetIndex, sheetIndex);
		String[] fields = this.excelAttribute.getFieldNames();
		
		writeSheet.mergeCells(0, 0, fields.length -1 , 0);
		//写入大标题
		Label title = new Label(0, 0, this.excelAttribute.getTitle(), getHeaderSytle()); 
		writeSheet.addCell(title);
		
		writeSheet.mergeCells(0, 1, fields.length -1 , 1);
		//写页眉
		Label header = new Label(0, 1, this.excelAttribute.getHander(),getTextStyle()); 
		writeSheet.addCell(header);
		
		
		//写小标题
		for(int i = 0; i < fields.length ; i ++ ) {
			Label label = new Label(i, 2, fields[i],getTitleStyle());
			writeSheet.addCell(label);
			writeSheet.setColumnView(i, 30);
			
		}
		
		
		
		sheetIndex ++;
	}
	
	/**
	 * 写Excel 写完后
	 * 自动调用release() 释放资源文件
	 * @param datas
	 * @param title
	 * @throws WriteException 
	 * @throws IOException 
	 */
	public void writeExcel(List datas) throws WriteException, IOException{
		if(writeSheet == null){
			createSheet();
		}
		for(int i = 0; i < datas.size(); i++){
		   writeRow((List)datas.get(i));
		}
		
		release();
	}
	
	
	/**
	 * 释放资源
	 * @throws IOException 
	 * @throws WriteException 
	 */
	public void release() throws WriteException, IOException{
		this.wwb.write();
		this.wwb.close();
	}
	
	 /**
	  * 设置头的样式
	  * @return 
	 * @throws WriteException 
	  */
	 private  WritableCellFormat getHeaderSytle() throws WriteException{
	       WritableFont font = new  WritableFont(WritableFont.TIMES, 20 ,WritableFont.BOLD);//定义字体
		   try {
		       font.setColour(Colour.BLACK);//黑色字体
   		   } catch (WriteException e1) {
			  throw e1;
	  	   }
		   WritableCellFormat format = new  WritableCellFormat(font);
		   try {
			   format.setAlignment(jxl.format.Alignment.CENTRE);//左右居中
			   format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//上下居中
			   format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);//黑色边框
			   format.setBackground(Colour.WHITE);//白背景
		  } catch (WriteException e) {
			  throw e;
		  }
		  return format;
	 }
	 
	 /**
	  * 设置内容样式
	  * @return 
	 * @throws WriteException 
	  */
	 private  WritableCellFormat getTextStyle() throws WriteException{
		   WritableCellFormat format = new  WritableCellFormat();
		   try {
			   //format.setAlignment(jxl.format.Alignment.CENTRE);//左右居中
			   format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//上下居中
			   format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);//黑色边框
			   format.setBackground(Colour.WHITE);//白背景
		  } catch (WriteException e) {
			  throw e;
		  }
		  return format;
	 }
	 
	 /**
	  * 设置内容样式
	  * @return 
	 * @throws WriteException 
	  */
	 private  WritableCellFormat getTitleStyle() throws WriteException{
		 WritableFont font = new  WritableFont(WritableFont.TIMES, 12 ,WritableFont.BOLD);//定义字体
		   try {
		       font.setColour(Colour.BLACK);//黑色字体
 		   } catch (WriteException e1) {
			  throw e1;
	  	   }
		   WritableCellFormat format = new  WritableCellFormat(font);
		   try {
			   format.setAlignment(jxl.format.Alignment.CENTRE);//左右居中
			   format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//上下居中
			   format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);//黑色边框
			   format.setBackground(Colour.WHITE);//白背景
		  } catch (WriteException e) {
			  throw e;
		  }
		  return format;
	 }
	 
	 
	 public static void main(String [] args) throws IOException, WriteException{
		 ExcelAttribute excelAttribute = new ExcelAttribute();
		 excelAttribute.setFileName("d:\\1.xls");
		 String [] a = {"文件名称","文件大小","备注"};
		 excelAttribute.setFieldNames(a);
		 excelAttribute.setFooter("2011-09-09 20:90:00");
		 excelAttribute.setHander("2011-09-09 20:90:00");
		 excelAttribute.setTitle("文件属性");
		 excelAttribute.setSheetName("文件");
		 
		 List datas = new ArrayList();
		 for(int i =0; i <  200000; i ++){
			 List data = new ArrayList();
			 data.add("Name"+i);
			 data.add("150MB");
			 data.add("SSSSSSSSSSS");
			 datas.add(data);
		 }
		 ExcelOpertatorExport  ex = new ExcelOpertatorExport(excelAttribute);
		 ex.writeExcel(datas);
	 }



}
