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
		WritableSheet sheet=writerBook.createSheet("��1", 0);
		sheet.addCell(new Label(0,0,"�������"));
		sheet.addCell(new Label(1,0,"���״̬"));
		sheet.addCell(new Label(2,0,"�����Ϣ"));
		sheet.addCell(new Label(3,0,"�������"));
		sheet.addCell(new Label(4,0,"�������"));
		sheet.addCell(new Label(5,0,"�������"));
		writerBook.write();
		writerBook.close();
	}

}
