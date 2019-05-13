package com.insigma.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.insigma.facade.openapi.ProjectFacade;
import com.insigma.facade.openapi.vo.ProjectDTO;
import com.insigma.modules.ProjectService;

public class ProjectImpl implements ProjectFacade{

	@Autowired
	ProjectService projectService;
	
	@Override
	public ProjectDTO getByPrimaryKey(Long id) {
		return projectService.getByPrimaryKey(id);
	}
	@Override
	public List<ProjectDTO> getProjectList(HashMap<String, Object> searchMap,int start,int size) {
		return projectService.getProjectList(searchMap, start, size);
	}

	@Override
	public List<ProjectDTO> getProjectList(HashMap<String, Object> searchMap) {
		return projectService.getProjectList(searchMap);
	}

	@Override
	public int insertProject(ProjectDTO projectDTO) {
		return projectService.insertProject(projectDTO);
	}

	@Override
	public int updateProject(ProjectDTO projectDTO) {
		return projectService.updateProject(projectDTO);
	}

	@Override
	public int deleteProject(ProjectDTO projectDTO) {
		return projectService.deleteProject(projectDTO);
	}

}
