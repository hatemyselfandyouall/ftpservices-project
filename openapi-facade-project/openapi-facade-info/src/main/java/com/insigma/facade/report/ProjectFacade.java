package com.insigma.facade.report;


import java.util.HashMap;
import java.util.List;


import com.insigma.facade.report.vo.ProjectDTO;


/**
 * 报表test
 *@Author:jinxm
 *@since：2019年3月26日
 *@return:
 */
public interface ProjectFacade {

	/**
	 * 
	 * @author jinxm
	 * @since:2019年3月26日
	 * @param id
	 * @return
	 */
	public ProjectDTO getByPrimaryKey(Long id);
	/**
	    *   获取项目列表，带分页
	 * @return
	 */
	public List<ProjectDTO> getProjectList(HashMap<String, Object> searchMap,int start,int size);
	/**
	    *   获取项目列表
	 * @return
	 */
	public List<ProjectDTO> getProjectList(HashMap<String, Object> searchMap);
	
	/**
	 * 插入项目
	 * @param projectDTO
	 * @return
	 */
	int insertProject(ProjectDTO projectDTO); 
	/**
	 * 更新项目
	 * @param projectDTO
	 * @return
	 */
	int updateProject(ProjectDTO projectDTO);
	/**
	 * 删除项目
	 * @param projectDTO
	 * @return
	 */
	int deleteProject(ProjectDTO projectDTO);
	
}
