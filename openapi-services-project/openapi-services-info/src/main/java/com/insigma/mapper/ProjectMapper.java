package com.insigma.mapper;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.insigma.po.Project;

/**
 * 
 * 对系统用户表操作
 * @author haoxz11MyBatis 
 * @created Thu Mar 21 14:10:27 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface ProjectMapper {

	/**
	 * 根据主键得到系统用户表表记录
	 *
	 * @haoxz11MyBatis
	 */
	Project getByPrimaryKey(Long id);
	
	/**
	 * 获取项目列表，带分页
	 * @return
	 */
	List<Project> getProjectList(HashMap<String, Object> searchMap, RowBounds rowBounds);
	/**
	 * 获取项目列表
	 * @return
	 */
	List<Project> getProjectList(HashMap<String, Object> searchMap);

	/**
	 * 插入项目
	 * @param projectDTO
	 * @return
	 */
	int insertProject(Project project); 
	/**
	 * 更新项目
	 * @param projectDTO
	 * @return
	 */
	int updateProject(Project project);
	/**
	 * 删除项目
	 * @param projectDTO
	 * @return
	 */
	int deleteProject(Project project);
}