/**    
 * 文件名：ExcelHelper.java       
 * 版本信息：    
 * 日期：2016年5月10日    
 * Copyright AICAI 2016     
 * 版权所有    
 */
package com.insigma.webtool.util;

import star.util.ExcelUtil;
import star.util.vo.ExcelVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 项目名称：cps-web-backend 类名称：ExcelHelper 类描述： 创建人：wangjingcheng 创建时间：2016年5月10日
 * 上午11:10:36 修改人：wangjingcheng 修改时间：2016年5月10日 上午11:10:36 修改备注：
 * 
 * @version
 */
public class ExcelHelper {
	/** logger */
	static Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

	/**
	 * 
	 * @param response
	 *            response
	 * @param fileName
	 *            fileName
	 * @param sheetName
	 *            sheetName
	 * @param data
	 *            data
	 * @throws IOException
	 *             {@link java.io.IOException}
	 */
	public static void exportExcel(HttpServletResponse response, String fileName, String sheetName, List<List<String>> data) throws IOException {
		if (fileName == null) {
			fileName = "export-";
		}
		OutputStream out = null;
		Workbook wb = null;
		try {
			out = response.getOutputStream();
			response.reset();
			response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GBK");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");

			ExcelVo vo = new ExcelVo();
			List<Object[]> rows = new ArrayList<Object[]>();
			for (List<String> l : data) {
				rows.add(l.toArray());
			}
			vo.setRows(rows);
			vo.setSheetName(sheetName);

			wb = ExcelUtil.createExcleFile(vo);
			wb.write(out);
			out.flush();
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("下载excel失败,e=" + e.getMessage());
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
