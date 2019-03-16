package Test;

import static org.junit.Assert.*;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.junit.Test;

public class Execel {

	@Test
	public void test() throws Exception{
		WritableWorkbook writerBook=Workbook.createWorkbook(new File("F:/a.xls"));
		WritableSheet sheet=writerBook.createSheet("表1", 0);
		sheet.addCell(new Label(0,0,"软件名字"));
		sheet.addCell(new Label(1,0,"软件状态"));
		sheet.addCell(new Label(2,0,"软件信息"));
		sheet.addCell(new Label(3,0,"软件名字"));
		sheet.addCell(new Label(4,0,"软件名字"));
		sheet.addCell(new Label(5,0,"软件名字"));
		writerBook.write();
		writerBook.close();
	}

}
